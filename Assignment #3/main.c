/*********************************************
 * Runs the class enrollment algorithms
 * using multithreading.
 * Calculates statistics form runtime
 * @author Team Theta 1: David-Eric Thorpe, Nathan Kong, Luke Sieben, Dennis Hsu
 * CS 149
 **********************************************/


#include <stdio.h>

/**
* Main method
*/
int main(void) {

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

