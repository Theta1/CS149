/*********************************************
 * Runs the class enrollment algorithms
 * using multithreading.
 * Calculates statistics form runtime
 * @author Team Theta 1: David-Eric Thorpe, Nathan Kong, Luke Sieben, Dennis Hsu
 * CS 149
 **********************************************/

#include "Student.h"

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

    STUDENT ALL[75];
    STUDENT GS[75];
    STUDENT RS[75];
    STUDENT EE[75];

    STUDENT impatient[15];
    STUDENT drop[15];
    STUDENT c1[20];
    STUDENT c2[20];
    STUDENT c3[20];

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
        ALL[cnt] = x;
    }

    printf("\nStud\tpri\tarrive\tfinish\tsec\tid\n");
    for ( cnt = 0; cnt<75; cnt++) {
        printf("%d\t %s\t%d\t%d\t%d\t%d\n",cnt,ALL[cnt].priority,ALL[cnt].arriveTime, ALL[cnt].finishTime, ALL[cnt].section, ALL[cnt].id);
    }

    //put students into their perspective queue
    for (cnt = 0; cnt<75; cnt++) {
        if (ALL[cnt].priority == "GS")
        {
            GS[gs] = ALL[cnt];
            gs++;
        }
        else if (ALL[cnt].priority == "RS")
        {
            RS[rs] = ALL[cnt];
            rs++;
        }
        else
        {
            EE[ee] = ALL[cnt];
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
    //print

}

