/*********************************************
 * Runs the class enrollment algorithms
 * using multithreading.
 * Calculates statistics form runtime
 * Borrowed heavily from format of Professor Mak offichour program
 * @author Team Theta 1: David-Eric Thorpe, Nathan Kong, Luke Sieben, Dennis Hsu
 * CS 149
 **********************************************/

 #include "Student.h"

//Major variables of this simulation
 #define STUDENT_COUNT 75
 #define REGISTRATION_DURATION 120
 #define ID_BASE 101
 #define NUMBER_OF_SECTIONS 3
 #define GS_PROCESS_TIME_MIN 1
 #define GS_PROCESS_TIME_MAX 2
 #define RS_PROCESS_TIME_MIN 2
 #define RS_PROCESS_TIME_MAX 4
 #define EE_PROCESS_TIME_MIN 3
 #define EE_PROCESS_TIME_MAX 6
 #define SECTION_CAPACITY 20

// function declarations
void printSection(STUDENT section[], char *sectionType, int indexSelectionLast);
void print(char *event);
void timerHandler(int signal);
void dropStudent(STUDENT s);
void *gsThread(void *param);
void *rsThread(void *param);
void *eeThread(void *param);
void *student(void *param);
void studentArrives(STUDENT student);
void eeAdd();
void gsAdd();
void rsAdd();

//Circular buffers
 int ALL_STUDENTS[STUDENT_COUNT];
 STUDENT gsQueue[STUDENT_COUNT];
 STUDENT rsQueue[STUDENT_COUNT];
 STUDENT eeQueue[STUDENT_COUNT];
 STUDENT SECTION_1[SECTION_CAPACITY];
 STUDENT SECTION_2[SECTION_CAPACITY];
 STUDENT SECTION_3[SECTION_CAPACITY];
 STUDENT SECTION_DROPPED[SECTION_CAPACITY];
 STUDENT SECTION_IMPATIENT[SECTION_CAPACITY];

 //Circular buffer counters
 int section1Counter = 0;
 int section2Counter = 0;
 int section3Counter = 0;
 int sectionDropperCounter = 0;
 int sectionImpatientCounter = 0;
 int gsHead = 0;
 int rsHead = 0;
 int eeHead = 0;
 int gsTail = 0;
 int rsTail = 0;
 int eeTail = 0;


//Mutexes protecting queues and sections and print
 pthread_mutex_t gsMutex;
 pthread_mutex_t rsMutex;
 pthread_mutex_t eeMutex;
 pthread_mutex_t SECTION_1_MUTEX;
 pthread_mutex_t SECTION_2_MUTEX;
 pthread_mutex_t SECTION_3_MUTEX;
 pthread_mutex_t SECTION_DROPPED_MUTEX;
 pthread_mutex_t SECTION_IMPATIENT_MUTEX;
 pthread_mutex_t PRINT_MUTEX;

 int firstPrint = 1;
 int timesUp = 0;

//Semaphore for busy section
 sem_t gsSem;
 sem_t rsSem;
 sem_t eeSem;


//Registration timers
 struct itimerval timer;
 time_t startTime;

/**
* Main method
*/
int main(void) {
    int cnt = 0;

    sem_init(&eeSem, 0, 0);
    sem_init(&gsSem, 0, 0);
    sem_init(&rsSem, 0, 0);

    // set up rand
    srand(time(NULL));

    time(&startTime);
    print("Registration begins");

    pthread_t ee_t;
    pthread_attr_t eeAttr;
    pthread_attr_init(&eeAttr);
    pthread_create(&ee_t, &eeAttr, eeThread, NULL);

    pthread_t gs_t;
    pthread_attr_t gsAttr;
    pthread_attr_init(&gsAttr);
    pthread_create(&gs_t, &gsAttr, gsThread, NULL);

    pthread_t rs_t;
    pthread_attr_t rsAttr;
    pthread_attr_init(&rsAttr);
    pthread_create(&rs_t, &rsAttr, rsThread, NULL);


    //creates 75 student threads
    for (cnt = 0; cnt < STUDENT_COUNT; cnt++)
    {
        ALL_STUDENTS[cnt] = ID_BASE + cnt;
        pthread_t studentThreadId;
        pthread_attr_t studentAttr;
        pthread_attr_init(&studentAttr);
        pthread_create(&studentThreadId, &studentAttr, student, &ALL_STUDENTS[cnt]);

    }

    //Set the timer signal handler.
    signal(SIGALRM, timerHandler);

    //set the timer
    timer.it_value.tv_sec = REGISTRATION_DURATION;
    setitimer(ITIMER_REAL, &timer, NULL);

    // wait for threads to finish
    pthread_join(ee_t, NULL);
    pthread_cancel(gs_t);
    pthread_cancel(rs_t);

    //drops remaining students
    for(cnt = gsHead; cnt < gsTail; cnt++){
        dropStudent(gsQueue[cnt]);
    }
    for(cnt = rsHead; cnt < rsTail; cnt++){
        dropStudent(rsQueue[cnt]);
    }
    for(cnt = eeHead; cnt < eeTail; cnt++){
        dropStudent(eeQueue[cnt]);
    }

    //stats
    printf("\n\n");
    printSection(SECTION_1, "in section 1", section1Counter);
    printSection(SECTION_2, "in section 2", section2Counter);
    printSection(SECTION_3, "in section 3", section3Counter);
    printSection(SECTION_DROPPED, "who were dropped", sectionDropperCounter);
    printSection(SECTION_IMPATIENT, "who were impatient", sectionImpatientCounter);

    return 0;
}

/**
 * Gets the current time in seconds.
 * @return the current time in seconds
 */
int getTime() {
	time_t now;
    time(&now);
    double elapsed = difftime(now, startTime);
	return (int) elapsed;
}

/**
* Print a line for each event:
* elapsed time
* who is registering from what queue
* who is waiting in what queue
* what action they take: Register/drop/gaveup and where
*/
void print(char *event){
    time_t now;
    time(&now);
    double elapsed = difftime(now, startTime);
    int min = 0;
    int sec = (int) elapsed;

    while (sec >=60){
        min++;
        sec -=60;
    }
    //locks the mutex for printing
    pthread_mutex_lock(&PRINT_MUTEX);

    if (firstPrint) {
        printf("TIME | EVENT (ATTRIBUTES)\n");
        firstPrint = 0;
    }

    // Elapsed time.
    printf("%1d:%02d | ", min, sec);

    //What they are doing
    printf(event);
    printf("\n");

    //unlocks the mutx after printing
    pthread_mutex_unlock(&PRINT_MUTEX);

}

// Timer signal handler.
void timerHandler(int signal)
{
    timesUp = 1;  // Registration is closed
    print("Registration is closed");
}

/**
* Creates a Student thread
*/
void *student(void *param)
{
    int num = *((int *) param);

    // Students will arrive at random times during the office hour.
    int sleepTime = rand()%REGISTRATION_DURATION;

    sleep(sleepTime);

    //create student
    STUDENT x;//new student
    x.arriveTime = getTime();
    x.section = rand() % 4;
    x.id = num; // id must be unique
    int temp = (rand()% 3) + 1;
    if (temp > 2) {   strcpy(x.priority, "GS");  }
    else if (temp < 2) {    strcpy(x.priority, "EE");    }
    else {   strcpy(x.priority, "RS");   }

    //function putting the student in line
    if(!timesUp) {
        studentArrives(x);
    }

    return NULL;
}

/**
* Student function
* puts the student into
* his/her persepective group
*/
void studentArrives(STUDENT student) {

    if(isPriority(student, "EE"))
    {
        pthread_mutex_lock(&eeMutex);

        eeQueue[eeTail] = student;
        eeTail++;

        pthread_mutex_unlock(&eeMutex);

        char event[80];
        sprintf(event,"Student %d arrives to EE line wants section %d", student.id, student.section);
        print(event);

        sem_post(&eeSem);
    }

    if(isPriority(student, "GS"))
    {
        pthread_mutex_lock(&gsMutex);

        gsQueue[gsTail] = student;
        gsTail++;

        pthread_mutex_unlock(&gsMutex);

        char event[80];
        sprintf(event,"Student %d arrives to GS line wants section %d", student.id, student.section);
        print(event);

        sem_post(&gsSem);
    }

    if(isPriority(student, "RS"))
    {
        pthread_mutex_lock(&rsMutex);

        rsQueue[rsTail] = student;
        rsTail++;

        pthread_mutex_unlock(&rsMutex);

        char event[80];
        sprintf(event,"Student %d arrives to RS line wants section %d", student.id, student.section);
        print(event);

        sem_post(&rsSem);
    }

}

void *eeThread(void *param) {
    //time to register for classes
    while(!timesUp || (section1Counter == SECTION_CAPACITY && section2Counter == SECTION_CAPACITY && section3Counter == SECTION_CAPACITY)) {
        eeAdd();
    }

    return NULL;
}

void eeAdd() {
    sem_wait(&eeSem);

    sleep(rand()%EE_PROCESS_TIME_MAX + EE_PROCESS_TIME_MIN);

	pthread_mutex_lock(&eeMutex);
    char event[80];
    if(isImpatient(eeQueue[eeHead],getTime() ) ) {  // is impatient
		pthread_mutex_lock(&SECTION_IMPATIENT_MUTEX);

        eeQueue[eeHead].finishTime = getTime();
        SECTION_IMPATIENT[sectionImpatientCounter] = eeQueue[eeHead];
        sectionImpatientCounter++;

		sprintf(event,"Student %d from %s is impatient and left", eeQueue[eeHead].id, eeQueue[eeHead].priority);
        print(event);

		pthread_mutex_unlock(&SECTION_IMPATIENT_MUTEX);

        goto end;
    }

    sprintf(event,"EE queue begins processing Student %d.", eeQueue[eeHead].id);
    if(!timesUp)
    {   print(event);   }

	// section 1
    if(canEnroll(eeQueue[eeHead], 1))
	{
		pthread_mutex_lock(&SECTION_1_MUTEX);
		if(section1Counter < SECTION_CAPACITY) { // see if there is space in the section
			eeQueue[eeHead].finishTime = getTime();
			SECTION_1[section1Counter] = eeQueue[eeHead];
			section1Counter++;

			char event[80];
			sprintf(event,"Student %d enrolls into Section 1", eeQueue[eeHead].id);
			if(!timesUp)
			{   print(event);   }

			pthread_mutex_unlock(&SECTION_1_MUTEX);

			goto end;
		}

		pthread_mutex_unlock(&SECTION_1_MUTEX);
    }

	// section 2
    if(canEnroll(eeQueue[eeHead], 2)) {
		pthread_mutex_lock(&SECTION_2_MUTEX);
		if(section2Counter < SECTION_CAPACITY) { // see if there is space in the section
			eeQueue[eeHead].finishTime = getTime();
			SECTION_2[section2Counter] = eeQueue[eeHead];
			section2Counter++;

			char event[80];
			sprintf(event,"Student %d enrolls into Section 2", eeQueue[eeHead].id);
			if(!timesUp)
			{   print(event);   }

			pthread_mutex_unlock(&SECTION_2_MUTEX);

			goto end;
		}

		pthread_mutex_unlock(&SECTION_2_MUTEX);
    }

	// section 3
    if(canEnroll(eeQueue[eeHead], 3)) {
		pthread_mutex_lock(&SECTION_3_MUTEX);
		if(section3Counter < SECTION_CAPACITY) { // see if there is space in the section
			eeQueue[eeHead].finishTime = getTime();
			SECTION_3[section3Counter] = eeQueue[eeHead];
			section3Counter++;

			char event[80];
			sprintf(event,"Student %d enrolls into Section 3", eeQueue[eeHead].id);
			if(!timesUp)
			{   print(event);   }

			pthread_mutex_unlock(&SECTION_3_MUTEX);

			goto end;
		}

		pthread_mutex_unlock(&SECTION_3_MUTEX);
    }

    // dropped section
    if(!timesUp)
    {   dropStudent(eeQueue[eeHead]);   }

    // goto necessary to break out and stop section 0 students adding every section
    end:

    eeHead++;
	pthread_mutex_unlock(&eeMutex);
}


void *gsThread(void *param) {
    //time to register for classes
    while(!timesUp || (section1Counter == SECTION_CAPACITY && section2Counter == SECTION_CAPACITY && section3Counter == SECTION_CAPACITY)) {
        gsAdd();
    }

    return NULL;
}

void gsAdd() {
    sem_wait(&gsSem);

    sleep(rand()%GS_PROCESS_TIME_MAX + GS_PROCESS_TIME_MIN);

    pthread_mutex_lock(&gsMutex);
    char event[80];
    if(isImpatient(gsQueue[gsHead],getTime() ) ) {  // is impatient
		pthread_mutex_lock(&SECTION_IMPATIENT_MUTEX);

        gsQueue[gsHead].finishTime = getTime();
        SECTION_IMPATIENT[sectionImpatientCounter] = gsQueue[gsHead];
        sectionImpatientCounter++;

		sprintf(event,"Student %d from %s is impatient and left", gsQueue[gsHead].id, gsQueue[gsHead].priority);
        print(event);

		pthread_mutex_unlock(&SECTION_IMPATIENT_MUTEX);

        goto end;
    }

    sprintf(event,"GS queue begins processing Student %d.", gsQueue[gsHead].id);
    if(!timesUp)
    {   print(event);   }

	// section 1
    if(canEnroll(gsQueue[gsHead], 1))
	{
		pthread_mutex_lock(&SECTION_1_MUTEX);
		if(section1Counter < SECTION_CAPACITY) { // sgs if there is space in the section
			gsQueue[gsHead].finishTime = getTime();
			SECTION_1[section1Counter] = gsQueue[gsHead];
			section1Counter++;

			char event[80];
			sprintf(event,"Student %d enrolls into Section 1", gsQueue[gsHead].id);
			if(!timesUp)
			{   print(event);   }

			pthread_mutex_unlock(&SECTION_1_MUTEX);

			goto end;
		}

		pthread_mutex_unlock(&SECTION_1_MUTEX);
    }

	// section 2
    if(canEnroll(gsQueue[gsHead], 2)) {
		pthread_mutex_lock(&SECTION_2_MUTEX);
		if(section2Counter < SECTION_CAPACITY) { // sgs if there is space in the section
			gsQueue[gsHead].finishTime = getTime();
			SECTION_2[section2Counter] = gsQueue[gsHead];
			section2Counter++;

			char event[80];
			sprintf(event,"Student %d enrolls into Section 2", gsQueue[gsHead].id);
			if(!timesUp)
			{   print(event);   }

			pthread_mutex_unlock(&SECTION_2_MUTEX);

			goto end;
		}

		pthread_mutex_unlock(&SECTION_2_MUTEX);
    }

	// section 3
    if(canEnroll(gsQueue[gsHead], 3)) {
		pthread_mutex_lock(&SECTION_3_MUTEX);
		if(section3Counter < SECTION_CAPACITY) { // sgs if there is space in the section
			gsQueue[gsHead].finishTime = getTime();
			SECTION_3[section3Counter] = gsQueue[gsHead];
			section3Counter++;

			char event[80];
			sprintf(event,"Student %d enrolls into Section 3", gsQueue[gsHead].id);
			if(!timesUp)
			{   print(event);   }

			pthread_mutex_unlock(&SECTION_3_MUTEX);

			goto end;
		}

		pthread_mutex_unlock(&SECTION_3_MUTEX);
    }

    // dropped section
    if(!timesUp)
    {   dropStudent(gsQueue[gsHead]);   }

    // goto necessary to break out and stop section 0 students adding every section
    end:

    gsHead++;
	pthread_mutex_unlock(&gsMutex);
}

void *rsThread(void *param) {
    //time to register for classes
    while(!timesUp || (section1Counter == SECTION_CAPACITY && section2Counter == SECTION_CAPACITY && section3Counter == SECTION_CAPACITY)) {
        rsAdd();
    }

    return NULL;
}

void rsAdd() {
    sem_wait(&rsSem);

    sleep(rand()%RS_PROCESS_TIME_MAX + RS_PROCESS_TIME_MIN);

	pthread_mutex_lock(&rsMutex);
    char event[80];
    if(isImpatient(rsQueue[rsHead],getTime() ) ) {  // is impatient
		pthread_mutex_lock(&SECTION_IMPATIENT_MUTEX);

        rsQueue[rsHead].finishTime = getTime();
        SECTION_IMPATIENT[sectionImpatientCounter] = rsQueue[rsHead];
        sectionImpatientCounter++;

		sprintf(event,"Student %d from %s is impatient and left", rsQueue[rsHead].id, rsQueue[rsHead].priority);
        print(event);

		pthread_mutex_unlock(&SECTION_IMPATIENT_MUTEX);

        goto end;
    }

    sprintf(event,"RS queue begins processing Student %d.", rsQueue[rsHead].id);
    if(!timesUp)
    {   print(event);   }

	// section 1
    if(canEnroll(rsQueue[rsHead], 1))
	{
		pthread_mutex_lock(&SECTION_1_MUTEX);
		if(section1Counter < SECTION_CAPACITY) { // srs if there is space in the section
			rsQueue[rsHead].finishTime = getTime();
			SECTION_1[section1Counter] = rsQueue[rsHead];
			section1Counter++;

			char event[80];
			sprintf(event,"Student %d enrolls into Section 1", rsQueue[rsHead].id);
			if(!timesUp)
			{   print(event);   }

			pthread_mutex_unlock(&SECTION_1_MUTEX);

			goto end;
		}

		pthread_mutex_unlock(&SECTION_1_MUTEX);
    }

	// section 2
    if(canEnroll(rsQueue[rsHead], 2)) {
		pthread_mutex_lock(&SECTION_2_MUTEX);
		if(section2Counter < SECTION_CAPACITY) { // srs if there is space in the section
			rsQueue[rsHead].finishTime = getTime();
			SECTION_2[section2Counter] = rsQueue[rsHead];
			section2Counter++;

			char event[80];
			sprintf(event,"Student %d enrolls into Section 2", rsQueue[rsHead].id);
			if(!timesUp)
			{   print(event);   }

			pthread_mutex_unlock(&SECTION_2_MUTEX);

			goto end;
		}

		pthread_mutex_unlock(&SECTION_2_MUTEX);
    }

	// section 3
    if(canEnroll(rsQueue[rsHead], 3)) {
		pthread_mutex_lock(&SECTION_3_MUTEX);
		if(section3Counter < SECTION_CAPACITY) { // srs if there is space in the section
			rsQueue[rsHead].finishTime = getTime();
			SECTION_3[section3Counter] = rsQueue[rsHead];
			section3Counter++;

			char event[80];
			sprintf(event,"Student %d enrolls into Section 3", rsQueue[rsHead].id);
			if(!timesUp)
			{   print(event);   }

			pthread_mutex_unlock(&SECTION_3_MUTEX);

			goto end;
		}

		pthread_mutex_unlock(&SECTION_3_MUTEX);
    }

    // dropped section
    if(!timesUp)
    {   dropStudent(rsQueue[rsHead]);   }

    // goto necessary to break out and stop section 0 students adding every section
    end:

    rsHead++;
	pthread_mutex_unlock(&rsMutex);
}

void dropStudent(STUDENT s){
    pthread_mutex_lock(&SECTION_DROPPED_MUTEX);

    s.finishTime = getTime();
    SECTION_DROPPED[sectionDropperCounter] = s;
    sectionDropperCounter++;

    char event2[80];
    sprintf(event2,"Dropped Student %d.", s.id);
    print(event2);

    pthread_mutex_unlock(&SECTION_DROPPED_MUTEX);
}

/**
  * Prints out the students in each section.
  * @param section the section
  * @param sectionType the section type
  * @param indexSelectionLast the last used index in the section
  */
void printSection(STUDENT section[], char *sectionType, int indexSelectionLast) {
    printf("Students %s:\n", sectionType);

    int totalTurnAroundTime = 0;
    int i = 0;
    for(; i < indexSelectionLast; i++) {
        printf("Index: %2d --> ", i);
        printStudent(section[i]);

        int turnAroundTime = getTurnAroundTime(section[i]);
        printf(", turnaround time: %d\n", turnAroundTime);

        totalTurnAroundTime += turnAroundTime;
    }

    if(indexSelectionLast != 0) { // make sure we have something in the section
        printf("Average turnaround time: %f\n\n", totalTurnAroundTime / (indexSelectionLast + 0.4f));
    }
    else {
        printf("Average turnaround time: N/A\n\n");
    }
}
