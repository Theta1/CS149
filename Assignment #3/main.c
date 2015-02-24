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
 #define REGESTRATION_DURATION 160
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
void *processGS_QUEUE();
void *processRS_QUEUE();
void *processEE_QUEUE();
void printSection(STUDENT section[], char *sectionType, int indexLast);

/**Circular buffers*/
 STUDENT ALL_STUDENTS[STUDENT_COUNT];
 STUDENT GS_QUEUE[STUDENT_COUNT];
 STUDENT RS_QUEUE[STUDENT_COUNT];
 STUDENT EE_QUEUE[STUDENT_COUNT];
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

/**Mutexes protecting queues and sections and print*/
 pthread_mutex_t GS_QUEUE_MUTEX;
 pthread_mutex_t RS_QUEUE_MUTEX;
 pthread_mutex_t EE_QUEUE_MUTEX;
 pthread_mutex_t SECTION_1_MUTEX;
 pthread_mutex_t SECTION_2_MUTEX;
 pthread_mutex_t SECTION_3_MUTEX;
 pthread_mutex_t SECTION_DROPPED_MUTEX;
 pthread_mutex_t SECTION_IMPATIENT_MUTEX;
 pthread_mutex_t PRINT_MUTEX;

 int firstPrint = 1;

/**Semaphore for busy section*/
 sem_t FILLINGSECTION;

/**Regestration timers*/
 struct itimerval gsRegestrationTimer;
 struct itimerval rsRegestrationTimer;
 struct itimerval eeRegestrationTimer;
 time_t startTime;

/**
* Print a line for each event:
* elapsed time
* who is regestering from what queue
* who is waiting in what queue
* what action they take: Regester/drop/gaveup and where
//   what event occurred
*/
void print(char *event){
    time_t now;
    time(&now);
    double elapsed = difftime(now, startTime);
    int min = 0;
    int sec = (int) elapsed;

    if (sec >=60){
        min++;
        sec -=60;
    }
    //locks the mutex for printing
    pthread_mutex_lock(&PRINT_MUTEX);

    if (firstPrint) {
        printf("TIME | STUDENT ID | WAITING     | EVENT\n");
        firstPrint = 0;
    }

    // Elapsed time.
    printf("%1d:%02d | ", min, sec);

    //What they are doing

}

int timesUp = 0;

// Timer signal handler.
void timerHandler(int signal)
{
  timesUp = 1;  // Registration is closed
}

//Shared function for processing student
//Didn't workout as shared as planned. LOOKS AWFUL! -DT
void processStudent(processTime, student){
    if(!timesUp && !student.isImpatient()){
        pthread_mutex_lock(&SECTION_1_MUTEX);
        if(section1Counter<SECTION_CAPACITY &&
            (student.section==1 || student.section==4)){
            SECTION_1[section1Counter]=student;
            section1Counter++;
        }else if (student.section!=4){
            pthread_mutex_lock(&SECTION_DROPPED_MUTEX);
            SECTION_DROPPED[sectionDropperCounter];
            sectionDropperCounter++
            pthread_mutex_unlock(&SECTION_1_MUTEX);
        }
        pthread_mutex_unlock(&SECTION_1_MUTEX);
        //unlock mutex? Student dropped? Student wait timeout?
        pthread_mutex_lock(&SECTION_2_MUTEX);
        if (section2Counter<SECTION_CAPACITY &&
            (student.section==2 || student.section==4)){
            SECTION_2[section1Counter]=student;
            section2Counter++;
        }
        pthread_mutex_unlock(&SECTION_2_MUTEX);
        //unlock mutex? Student dropped? Student wait timeout?
        pthread_mutex_lock(&SECTION_3_MUTEX);
        if (section3Counter<SECTION_CAPACITY &&
            (student.section==3 || student.section==4)){
            SECTION_3[section3Counter]=student;
            section3Counter++;
        }
        pthread_mutex_unlock(&SECTION_3_MUTEX);
        //unlock mutex? Student dropped? Student wait timeout?
            
        }
        //Critical region: add a student to the section
    }
}

// The graduating senor thread.
void *gsThread(void *param){
  print("Graduating Senor's queue begins processing");

  // Set the timer for regestration queue duration.
  gsRegestrationTimer.it_value.tv_sec = REGESTRATION_DURATION;
  setitimer(ITIMER_REAL, &gsRegestrationTimer, NULL);

  // Processes students until the queue is empty or regestration peroid has ended.
  int processTime;
  do {
    processTime = (rand()%GS_PROCESS_TIME_MAX) + GS_PROCESS_TIME_MIN;
    processStudent(processTime);
  } while (!timesUp); //need queue checking

  print("Gaduating Senor queue is closed");
  return NULL;
}

// The regular senor thread.
void *rsThread(void *param){
  print("Regular Senor's queue begins processing");

  // Set the timer for regestration queue duration.
  rsRegestrationTimer.it_value.tv_sec = REGESTRATION_DURATION;
  setitimer(ITIMER_REAL, &rsRegestrationTimer, NULL);

  // Processes students until the queue is empty or regestration peroid has ended.
  int processTime;
  do {
    processTime = (rand()%RS_PROCESS_TIME_MAX) + RS_PROCESS_TIME_MIN;
    processStudent(processTime);
  } while (!timesUp);

  print("Regular Senor queue is closed");
  return NULL;
}

// The everybody else thread.
void *eeThread(void *param){
  print("Everyone Else's queue begins processing");

  // Set the timer for regestration queue duration.
  eeRegestrationTimer.it_value.tv_sec = REGESTRATION_DURATION;
  setitimer(ITIMER_REAL, &eeRegestrationTimer, NULL);

  // Processes students until the queue is empty or regestration peroid has ended.
  int processTime;
  do {
  processTime = (rand()%ES_PROCESS_TIME_MAX) + ES_PROCESS_TIME_MIN;
      processStudent(processTime);
  } while (!timesUp);

  print("Everyone Else's queue is closed");
  return NULL;}

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

    // Set the timer signal handler.
    signal(SIGALRM, timerHandler);

    // set up rand
    srand(time(NULL));

    //create 75 students randomly
    for( cnt=0; cnt< STUDENT_COUNT; cnt++)
    {
        STUDENT x;//new student
        x.arriveTime = (rand() % 50 ) + 1;
        x.finishTime = (rand() % 30 ) + x.arriveTime;
        x.section = rand() % 4;
        x.id = 101 + cnt; // id must be unique
        temp = (rand()% 3) + 1;
        if (temp > 2) {   strcpy(x.priority, "GS");  }
        else if (temp < 2) {    strcpy(x.priority, "EE");    }
        else {   strcpy(x.priority, "RS");   }
        ALL_STUDENTS[cnt] = x;
    }

    /*Shows us the list of students
    printf("\nStudent\tPri\tArrival\tFinish\tSection\tId\n");
    for ( cnt = 0; cnt<75; cnt++) {
        printf("%d\t%s\t%d\t%d\t%d\t%d\n", cnt,
            ALL_STUDENTS[cnt].priority,ALL_STUDENTS[cnt].arriveTime, 
            ALL_STUDENTS[cnt].finishTime, ALL_STUDENTS[cnt].section, 
            ALL_STUDENTS[cnt].id);
    }
*/
    //put students into their perspective queue
    for (cnt = 0; cnt < STUDENT_COUNT; cnt++) {
        if(isPriority(ALL_STUDENTS[cnt], "GS")) {
            GS_QUEUE[gs] = ALL_STUDENTS[cnt];
            gs++;
        }
        else if(isPriority(ALL_STUDENTS[cnt], "RS")) {
            RS_QUEUE[rs] = ALL_STUDENTS[cnt];
            rs++;
        }
        else
        {
            EE_QUEUE[ee] = ALL_STUDENTS[cnt];
            ee++;
        }
    }

    //Creates the gs queue thread
    int gsQueueID = 1;
    pthread_t GSThreadId;
    pthread_attr_t GSAttr;
    pthread_attr_init(&GSAttr);
    //Did you get a comp error from two lines below?
    //Make sure to compile with "gcc –pthread main.c Student.c Student.h"
    pthread_create(&GSThreadId, &GSAttr, gsThread, &gsQueueID);

    //Creates the rs queue thread
    int rsQueueID = 2;
    pthread_t RSThreadId;
    pthread_attr_t RSAttr;
    pthread_attr_init(&RSAttr);
    pthread_create(&RSThreadId, &RSAttr, rsThread, &rsQueueID);

    //Creates the ee queue thread
    int eeQueueID = 1;
    pthread_t EEThreadId;
    pthread_attr_t EEAttr;
    pthread_attr_init(&EEAttr);
    pthread_create(&EEThreadId, &EEAttr, eeThread, &eeQueueID);

    printStudent(GS_QUEUE[0]);
    printf("\n");
    printStudent(RS_QUEUE[0]);
    printf("\n");
    printStudent(EE_QUEUE[0]);
    printf("\n");



    // wait for all threads to finish --> requires pthread_t, not pthread_mutex_t struct
    /*pthread_join(GS_QUEUE_MUTEX, NULL);
    pthread_join(RS_QUEUE_MUTEX, NULL);
    pthread_join(EE_QUEUE_MUTEX, NULL);*/

    // print enrolled
    printSection(SECTION_1, "1", indexSection1);
    printSection(SECTION_2, "2", indexSection2);
    printSection(SECTION_3, "3", indexSection3);


    // do stats calculations

    // print dropped
    printSection(SECTION_DROPPED, "Dropped", indexSectionDropped);
    printSection(SECTION_IMPATIENT, "Impatient", indexSectionImpatient);
}

//create 3 threads
    //per thread
    //while loop start
    //check if impatient (if arrival time is > 10 seconds)
        //if impatient lock
        //add to array
        //unlock
    //check to see which class he/she can enroll in -- return section 0 to 3; return 0 means can add to any section
    //lock the class
        //check section, if all sections then
    //check to see if the class is full
    //enroll in class
    //unlock
    //lock
    //if can't enroll push into dropped array -- if it locked wait and try again
    //unlock
    //drop from queue
    //while loop end
//print
    //print each section
    //print turnaround for each student
    //print average turnaround per queue
    //print who could not enroll/was dropped
    // print any other statistics we find interesting

/**
  * Process students in the GS_QUEUE.
  */
void *processGS_QUEUE() {
    // sleep for GS processing time
    sleep(rand() % GS_PROCESS_TIME_MAX + GS_PROCESS_TIME_MIN);
}

/**
  * Process students in the RS_QUEUE.
  */
void *processRS_QUEUE() {
    // sleep for RS processing time
    sleep(rand() % RS_PROCESS_TIME_MAX + RS_PROCESS_TIME_MIN);
}

/**
  * Process students in the EE_QUEUE.
  */
void *processEE_QUEUE() {
    // sleep for EE processing time
    sleep(rand() % EE_PROCESS_TIME_MAX + EE_PROCESS_TIME_MIN);
}

/**
  * Prints out the students in each section.
  * @param section the section
  * @param sectionType the section type
  * @param indexLast the last used index in the section
  */
void printSection(STUDENT section[], char *sectionType, int indexLast) {
    printf("Section: %s\n", sectionType);

    int i = 0;
    for(; i < indexLast; i++) {
        printf("%d: ", i);
        printStudent(section[i]);
        printf("\n");
    }
}
