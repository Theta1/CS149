/**************************************
A structure to create a student
Student functions
print functions
*************************************/
#include "Student.h"


/**
  * Prints out the student.
  * @param student the student
  */
void printStudent(STUDENT student) {
    printf("#%3d\.%s", student.id, student.priority);
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
  * Checks if a student can enroll in a section.
  * @param student the student
  * @param section the section the student is trying to add
  * @return 1 if the student can enroll in that section
  *         0 if the student cannot enroll in that section
  */
int canEnroll(STUDENT student, int section) {
    if(student.section == 0) {
        return 1;
    }

    return student.section == section;
}

/**
  * Checks if a student is impatient (waited 10s in the queue) or not.
  * @param student the student
  * @param processStartTime the time the student is about to be processed in
  * @return 1 if the student is impatient
  *         0 if the student is not impatient
*/
int isImpatient(STUDENT student, int processStartTime) {
    return processStartTime - student.arriveTime >= 10;
}
