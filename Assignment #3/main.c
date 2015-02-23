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

/**Circular buffers*/
 STUDENT ALL_STUDENTS[STUDENT_COUNT];
 STUDENT GS_QUEUE[STUDENT_COUNT];
 STUDENT RS_QUEUE[STUDENT_COUNT];
 STUDENT EE_QUEUE[STUDENT_COUNT];
 STUDENT SECTION1[SECTION_CAPACITY];
 STUDENT SECTION2[SECTION_CAPACITY];
 STUDENT SECTION3[SECTION_CAPACITY];
 STUDENT DROPPED[SECTION_CAPACITY];
 STUDENT GAVE_UP[SECTION_CAPACITY];

/**Mutexes protecting queues and sections and print*/
 pthread_mutex_t GS_QUEUE_MUTEX;
 pthread_mutex_t RS_QUEUE_MUTEX;
 pthread_mutex_t EE_QUEUE_MUTEX;
 pthread_mutex_t SECTION1_MUTEX;
 pthread_mutex_t SECTION2_MUTEX;
 pthread_mutex_t SECTION3_MUTEX;
 pthread_mutex_t PRINT_MUTEX;

 int firstPrint = 1;

/**Semaphore for busy section*/
 sem_t FILLINGSECTION;

/**Regestration timer*/
 struct itimerval regestrationTimer;
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

/**
* Main method
*/
int main(void) {

    int indexGS = 0;
    int indexRS = 0;
    int indexEE = 0;
    int indexDrop = 0;
    int indexImpatient = 0;
    int cnt = 0;
    int temp = 0;
    int gs = 0;
    int rs = 0;
    int ee = 0;

    printme();
    srand(time(NULL));

    //create 75 students randomly
    for( cnt=0; cnt<75; cnt++)
    {
        STUDENT x;//new student
        x.arriveTime = (rand() % 50 ) + 1;
        x.finishTime = (rand() % 30 ) + x.arriveTime;
        x.section = rand() % 4;
        x.id = rand() % 1000;
        temp = (rand()% 3) + 1;
        if (temp > 2) {   strcpy(x.priority, "GS");  }
        else if (temp < 2) {    strcpy(x.priority, "EE");    }
        else {   strcpy(x.priority, "RS");   }
        ALL_STUDENTS[cnt] = x;
    }

    printf("\nStud\tpri\tarrive\tfinish\tsec\tid\n");
    for ( cnt = 0; cnt<75; cnt++) {
        printf("%d\t %s\t%d\t%d\t%d\t%d\n",cnt,ALL_STUDENTS[cnt].priority,ALL_STUDENTS[cnt].arriveTime, ALL_STUDENTS[cnt].finishTime, ALL_STUDENTS[cnt].section, ALL_STUDENTS[cnt].id);
    }

    //put students into their perspective queue
    for (cnt = 0; cnt<75; cnt++) {
        if (ALL_STUDENTS[cnt].priority == "GS")
        {
            GS_QUEUE[gs] = ALL_STUDENTS[cnt];
            gs++;
        }
        else if (ALL_STUDENTS[cnt].priority == "RS")
        {
            RS_QUEUE[rs] = ALL_STUDENTS[cnt];
            rs++;
        }
        else
        {
            EE_QUEUE[ee] = ALL_STUDENTS[cnt];
            ee++;
        }
    }

//create 5 class arrays, the 3 classes, 1 dropped array, 1 impatient array
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

}

