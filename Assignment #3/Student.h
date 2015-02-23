#include <stdio.h>
#include <sys/time.h>
#include <stdlib.h>
#include <semaphore.h>
#include <signal.h>
#include <unistd.h>
#include <string.h>
#include <pthread.h>

#define IMPATIENT_TIME 10

typedef struct {
    int arriveTime;
    int finishTime;
    char priority[3];
    int section;
    int id;
} STUDENT;

void printStudent(STUDENT student);
int getTurnAroundTime(STUDENT student);
int canEnroll(STUDENT student, int section);
int isImpatient(STUDENT student, int processStartTime);
int isPriority(STUDENT student, char *priority);
