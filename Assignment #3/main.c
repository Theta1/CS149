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
void processStudent(processTime, student);
void *gsThread(void *param);
void *rsThread(void *param);
void *eeThread(void *param);
void *student(void *param);
void studentArrives(STUDENT student);
void eeAdd();

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

    if (sec >=60){
        min++;
        sec -=60;
    }
    //locks the mutex for printing
    pthread_mutex_lock(&PRINT_MUTEX);

    if (firstPrint) {
        printf("TIME | EVENT\n");
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
        sprintf(event,"Student %d arrives to EE line", student.id);
        print(event);

        sem_post(&eeSem);
    }

}

void *eeThread(void *param) {
    print("Registration begins");

    //set the timer
    timer.it_value.tv_sec = REGISTRATION_DURATION;
    setitimer(ITIMER_REAL, &timer, NULL);

    //time to register for classes
    do
    {
        eeAdd();
    }while( !timesUp );

    print("Registration is closed");
    return NULL;
}

void eeAdd() {
    if(!timesUp)
    {
        sem_wait(&eeSem);

        sleep(rand()%EE_PROCESS_TIME_MAX + EE_PROCESS_TIME_MIN);

        if(canEnroll(eeQueue[eeHead], 1))
        {
            pthread_mutex_lock(&SECTION_1_MUTEX);

            SECTION_1[section1Counter] = eeQueue[eeHead];
            section1Counter++;

            char event[80];
            sprintf(event,"Student %d enrolls into Section 1", eeQueue[eeHead].id);
            print(event);

            pthread_mutex_unlock(&SECTION_1_MUTEX);
        }



        eeHead++;
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
