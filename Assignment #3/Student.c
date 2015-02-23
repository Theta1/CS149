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

/**
  * Gets the turnAroundTime.
  * @param student the student
  * @return the turnAroundTime
  */
int getTurnAroundTime(STUDENT student) {
    return student.finishTime - student.arriveTime + 1;
}

/**
  * Checks if a student is impatient (waited 10s in the queue) or not.
  * @param student the student
  * @param processStartTime the time the student is about to be processed in
  * @return 1 if the student is impatient
            0 if the student is not impatient
*/
int isImpatient(STUDENT student, int processStartTime) {
    return processStartTime - student.arriveTime >= 10;
}
