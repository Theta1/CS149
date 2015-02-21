#include <stdio.h>
#include <sys/time.h>
#include <stdlib.h>
#include <semaphore.h>
#include <signal.h>
#include <unistd.h>


typedef struct {
    int arriveTime;
    int finishTime;
    char priority[3];
    int section;
    int id;
} STUDENT;

void printme();
