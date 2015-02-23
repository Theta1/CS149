/**************************************
A structure to create a student
Student functions
print functions
*************************************/
#include "Student.h"


/*
put print functions in here
*/

void printme() {
    printf("hello world");
}

/*
Gets the turnaround time.
*/
int getTurnAroundTime(STUDENT student) {
    return student.finishTime - student.arriveTime + 1;
}
