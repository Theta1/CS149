#include <stdio.h>
#include <time.h>
#include <stdlib.h>

typedef struct {
    int arriveTime;
    int finishTime;
    char priority[3];
    int section;
    int id;
} STUDENT;

void printme();