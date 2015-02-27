/*********************************************
 * Runs the class enrollment algorithms
 * using multithreading.
 * Calculates statistics form runtime
 * Borrowed heavily from format of Professor Mak offichour program
 * @author Team Theta 1: David-Eric Thorpe, Nathan Kong, Luke Sieben, Dennis Hsu
 * CS 149
 **********************************************/

 #include "Student.h"

/**Major variables of this simulation*/
 #define STUDENT_COUNT 75
 #define REGISTRATION_DURATION 20
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
void processStudent(processTime, student);
void *gsThread(void *param);
void *rsThread(void *param);
void *eeThread(void *param);
void *student(void *param);
void studentArrives(STUDENT student);
void eeAdd();
void gsAdd();
void rsAdd();

/**Circular buffers*/
 //STUDENT ALL_STUDENTS[STUDENT_COUNT];
 int ALL_STUDENTS[STUDENT_COUNT];
 STUDENT gsQueue[STUDENT_COUNT];
 STUDENT rsQueue[STUDENT_COUNT];
 STUDENT eeQueue[STUDENT_COUNT];
 STUDENT SECTION_1[SECTION_CAPACITY];
 STUDENT SECTION_2[SECTION_CAPACITY];
 STUDENT SECTION_3[SECTION_CAPACITY];
 STUDENT SECTION_DROPPED[SECTION_CAPACITY];
 STUDENT SECTION_IMPATIENT[SECTION_CAPACITY];

 /**Circular buffer counters*/
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


/**Mutexes protecting queues and sections and print*/
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

/**Semaphore for busy section*/
 sem_t gsSem;
 sem_t rsSem;
 sem_t eeSem;


/**Registration timers*/
 struct itimerval timer;
 time_t startTime;





/**
* Main method
*/
int main(void) {
    int indexSection1 = 0;
    int indexSection2 = 0;
    int indexSection3 = 0;
    int indexSectionDropped = 0;
    int indexSectionImpatient = 0;
    int cnt = 0;
    int temp = 0;
    int gs = 0;
    int rs = 0;
    int ee = 0;

    sem_init(&eeSem, 0, 0);

    // set up rand
    srand(time(NULL));

    time(&startTime);

    pthread_t ee_t;
    pthread_attr_t eeAttr;
    pthread_attr_init(&eeAttr);
    pthread_create(&ee_t, &eeAttr, eeThread, NULL);

    sleep(1);

    pthread_t gs_t;
    pthread_attr_t gsAttr;
    pthread_attr_init(&gsAttr);
    pthread_create(&gs_t, &gsAttr, gsThread, NULL);

    pthread_t rs_t;
    pthread_attr_t rsAttr;
    pthread_attr_init(&rsAttr);
    pthread_create(&rs_t, &rsAttr, rsThread, NULL);

    /**
    creates 75 student threads
    */
    for (cnt = 0; cnt < STUDENT_COUNT; cnt++)
    {
        ALL_STUDENTS[cnt] = ID_BASE + cnt;
        pthread_t studentThreadId;
        pthread_attr_t studentAttr;
        pthread_attr_init(&studentAttr);
        pthread_create(&studentThreadId, &studentAttr, student, &ALL_STUDENTS[cnt]);

    }

    // Set the timer signal handler.
    signal(SIGALRM, timerHandler);

    pthread_join(ee_t, NULL);
    pthread_join(gs_t, NULL);
    pthread_join(rs_t, NULL);


    for(cnt = gsHead; cnt < gsTail; cnt++){
        dropStudent(gsQueue[cnt]);
    }
    for(cnt = rsHead; cnt < rsTail; cnt++){
        dropStudent(rsQueue[cnt]);
    }
    for(cnt = eeHead; cnt < eeTail; cnt++){
        dropStudent(eeQueue[cnt]);
    }
}





/**
* Print a line for each event:
* elapsed time
* who is registering from what queue
* who is waiting in what queue
* what action they take: Register/drop/gaveup and where
//   what event occurred
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
  print("Registration is closed");
  timesUp = 1;  // Registration is closed
}

/**
* Creates a Student thread
*/
void *student(void *param)
{
    int num = *((int *) param);

    // Students will arrive at random times during the office hour.
    int arriveTime = rand()%REGISTRATION_DURATION;

    //create student
    STUDENT x;//new student
    x.arriveTime = arriveTime;
    x.finishTime = (rand() % 30 ) + x.arriveTime;
    x.section = rand() % 4;
    x.id = num; // id must be unique
    int temp = (rand()% 3) + 1;
    if (temp > 2) {   strcpy(x.priority, "GS");  }
    else if (temp < 2) {    strcpy(x.priority, "EE");    }
    else {   strcpy(x.priority, "RS");   }

    sleep(arriveTime);

    //function putting the student in line
    studentArrives(x);

    return NULL;
}

void studentArrives(STUDENT student) {

    if(isPriority(student, "EE"))
    {
        pthread_mutex_lock(&eeMutex);

        eeQueue[eeTail] = student;
        eeTail++;

        pthread_mutex_unlock(&eeMutex);

        char event[80];
        sprintf(event,"Student %d arrives to EE line (Section: %d, %s)", student.id, student.section, student.priority);
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
        sprintf(event,"Student %d arrives to GS line (Section: %d, %s)", student.id, student.section, student.priority);
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
        sprintf(event,"Student %d arrives to RS line (Section: %d, %s)", student.id, student.section, student.priority);
        print(event);

        sem_post(&rsSem);
    }

}

void *eeThread(void *param) {
    print("Registration begins");

    //set the timer
    timer.it_value.tv_sec = REGISTRATION_DURATION;
    setitimer(ITIMER_REAL, &timer, NULL);

    //time to register for classes
    while(!timesUp) {
        eeAdd();
    }

    return NULL;
}

void eeAdd() {
    if(!timesUp)
    {
        sem_wait(&eeSem);

        char event[80];
        sprintf(event,"EE queue begins processing Student %d.", eeQueue[eeHead].id);
        print(event);

        sleep(rand()%EE_PROCESS_TIME_MAX + EE_PROCESS_TIME_MIN);

        pthread_mutex_lock(&SECTION_1_MUTEX);
        if(canEnroll(eeQueue[eeHead], 1) && section1Counter < SECTION_CAPACITY)
        {
            SECTION_1[section1Counter] = eeQueue[eeHead];
            section1Counter++;

            char event[80];
            sprintf(event,"Student %d enrolls into Section 1", eeQueue[eeHead].id);
            print(event);

            pthread_mutex_unlock(&SECTION_1_MUTEX);

            goto end;
        }
        pthread_mutex_unlock(&SECTION_1_MUTEX);

        pthread_mutex_lock(&SECTION_2_MUTEX);
        if(canEnroll(eeQueue[eeHead], 2) && section2Counter < SECTION_CAPACITY)
        {
            SECTION_2[section2Counter] = eeQueue[eeHead];
            section2Counter++;

            char event[80];
            sprintf(event,"Student %d enrolls into Section 2", eeQueue[eeHead].id);
            print(event);

            pthread_mutex_unlock(&SECTION_2_MUTEX);

            goto end;
        }
        pthread_mutex_unlock(&SECTION_2_MUTEX);

        pthread_mutex_lock(&SECTION_3_MUTEX);
        if(canEnroll(eeQueue[eeHead], 3) && section3Counter < SECTION_CAPACITY)
        {
            SECTION_3[section3Counter] = eeQueue[eeHead];
            section3Counter++;

            char event[80];
            sprintf(event,"Student %d enrolls into Section 3", eeQueue[eeHead].id);
            print(event);

            pthread_mutex_unlock(&SECTION_3_MUTEX);

            goto end;
        }
        pthread_mutex_unlock(&SECTION_3_MUTEX);

        // dropped section
        dropStudent(eeQueue[eeHead]);
        /* TBD moved into custom fuction
        pthread_mutex_lock(&SECTION_DROPPED_MUTEX);

        SECTION_DROPPED[sectionDropperCounter] = eeQueue[eeHead];
        sectionDropperCounter++;

        char event2[80];
        sprintf(event2,"Dropped Student %d.", eeQueue[eeHead].id);
        print(event2);

        pthread_mutex_unlock(&SECTION_DROPPED_MUTEX);*/


        // goto necessary to break out and stop section 0 students adding every section
        end:

        eeHead++;
    }
}

void dropStudent(STUDENT s){
    pthread_mutex_lock(&SECTION_DROPPED_MUTEX);

    SECTION_DROPPED[sectionDropperCounter] = s;
    sectionDropperCounter++;

    char event2[80];
    sprintf(event2,"Dropped Student %d.", s.id);
    print(event2);

    pthread_mutex_unlock(&SECTION_DROPPED_MUTEX);
}

void *gsThread(void *param) {
    //time to register for classes
    while(!timesUp) {
        gsAdd();
    }

    return NULL;
}

void gsAdd() {
    if(!timesUp)
    {
        sem_wait(&gsSem);

        char event[80];
        sprintf(event,"GS queue begins processing Student %d.", gsQueue[gsHead].id);
        print(event);

        sleep(rand()%GS_PROCESS_TIME_MAX + GS_PROCESS_TIME_MIN);

        pthread_mutex_lock(&SECTION_1_MUTEX);
        if(canEnroll(gsQueue[gsHead], 1) && section1Counter < SECTION_CAPACITY)
        {
            SECTION_1[section1Counter] = gsQueue[gsHead];
            section1Counter++;

            char event[80];
            sprintf(event,"Student %d enrolls into Section 1", gsQueue[gsHead].id);
            print(event);

            pthread_mutex_unlock(&SECTION_1_MUTEX);

            goto end;
        }
        pthread_mutex_unlock(&SECTION_1_MUTEX);

        pthread_mutex_lock(&SECTION_2_MUTEX);
        if(canEnroll(gsQueue[gsHead], 2) && section2Counter < SECTION_CAPACITY)
        {
            SECTION_2[section2Counter] = gsQueue[gsHead];
            section2Counter++;

            char event[80];
            sprintf(event,"Student %d enrolls into Section 2", gsQueue[gsHead].id);
            print(event);

            pthread_mutex_unlock(&SECTION_2_MUTEX);

            goto end;
        }
        pthread_mutex_unlock(&SECTION_2_MUTEX);

        pthread_mutex_lock(&SECTION_3_MUTEX);
        if(canEnroll(gsQueue[gsHead], 3)  && section3Counter < SECTION_CAPACITY)
        {
            SECTION_3[section3Counter] = gsQueue[gsHead];
            section3Counter++;

            char event[80];
            sprintf(event,"Student %d enrolls into Section 3", gsQueue[gsHead].id);
            print(event);

            pthread_mutex_unlock(&SECTION_3_MUTEX);

            goto end;
        }
        pthread_mutex_unlock(&SECTION_3_MUTEX);

        // dropped section
        dropStudent(gsQueue[gsHead]);
        // goto necessary to break out and stop section 0 students adding every section
        end:

        gsHead++;
    }

}

void *rsThread(void *param) {
    //time to register for classes
    while(!timesUp) {
        rsAdd();
    }

    return NULL;
}

void rsAdd() {
    if(!timesUp)
    {
        sem_wait(&rsSem);

        char event[80];
        sprintf(event,"RS queue begins processing Student %d.", rsQueue[rsHead].id);
        print(event);

        sleep(rand()%RS_PROCESS_TIME_MAX + RS_PROCESS_TIME_MIN);

        pthread_mutex_lock(&SECTION_1_MUTEX);
        if(canEnroll(rsQueue[rsHead], 1)  && section1Counter < SECTION_CAPACITY)
        {
            SECTION_1[section1Counter] = rsQueue[rsHead];
            section1Counter++;

            char event[80];
            sprintf(event,"Student %d enrolls into Section 1", rsQueue[rsHead].id);
            print(event);

            pthread_mutex_unlock(&SECTION_1_MUTEX);

            goto end;
        }
        pthread_mutex_unlock(&SECTION_1_MUTEX);

        pthread_mutex_lock(&SECTION_2_MUTEX);
        if(canEnroll(rsQueue[rsHead], 2) && section2Counter < SECTION_CAPACITY)
        {
            SECTION_2[section2Counter] = rsQueue[rsHead];
            section2Counter++;

            char event[80];
            sprintf(event,"Student %d enrolls into Section 2", rsQueue[rsHead].id);
            print(event);

            pthread_mutex_unlock(&SECTION_2_MUTEX);

            goto end;
        }
        pthread_mutex_unlock(&SECTION_2_MUTEX);

        pthread_mutex_lock(&SECTION_3_MUTEX);
        if(canEnroll(rsQueue[rsHead], 3) && section3Counter < SECTION_CAPACITY)
        {
            SECTION_3[section3Counter] = rsQueue[rsHead];
            section3Counter++;

            char event[80];
            sprintf(event,"Student %d enrolls into Section 3", rsQueue[rsHead].id);
            print(event);

            pthread_mutex_unlock(&SECTION_3_MUTEX);

            goto end;
        }
        pthread_mutex_unlock(&SECTION_3_MUTEX);

        // dropped section
        dropStudent(rsQueue[rsHead]);
        // goto necessary to break out and stop section 0 students adding every section
        end:

        rsHead++;
    }

}

/**
  * Prints out the students in each section.
  * @param section the section
  * @param sectionType the section type
  * @param indexSelectionLast the last used index in the section
  */
void printSection(STUDENT section[], char *sectionType, int indexSelectionLast) {
    printf("Students in section %s:\n", sectionType);

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
        printf("Average turnaround time: %f\n\n", totalTurnAroundTime / (indexSelectionLast + 0.0f));
    }
    else {
        printf("Average turnaround time: N/A\n\n");
    }
}
