/*********************************************
 * Runs the class enrollment algorithms
 * using multithreading.
 * Calculates statistics form runtime
 * @author Team Theta 1: David-Eric Thorpe, Nathan Kong, Luke Sieben, Dennis Hsu
 * CS 149
 **********************************************/

 #include "Student.h"

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

    STUDENT ALL[75];
    STUDENT GS[20];
    STUDENT RS[20];
    STUDENT EE[20];

    printme();


    for( cnt=0; cnt<75; cnt++)
    {
        STUDENT x;//new student
        ALL[cnt] = x;
    }

//create 75 students randomly
//create 3 queues
//put students into their perspective queue
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
    //print



}

