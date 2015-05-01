#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/time.h>
#include <sys/ioctl.h>

//change child number to 5 when you have added in standard input
#define CHILD_NUMBER 5
#define BUFFER_SIZE 32
#define READ_END 0
#define WRITE_END 1
#define PROGRAM_DURATION 10
#define SLEEP_DURATION 3

typedef struct {
    int id;
    int message;
} CHILD;

//global start time variable
double startTime; // the time the forking starts
FILE *fp;
    int fd[5][2];
CHILD aChild;
fd_set inputs, inputfds;

/**
* Gets the elapsed time
*/
double getElapsedTime() {
    struct timeval now;
    gettimeofday(&now, NULL);
    double currentTime = (now.tv_sec) * 1000 + (now.tv_usec) / 1000;
    return (currentTime - startTime)/1000;
}

/**
Adds the time to the childs event
**/
void addTimeToEvent(char *event) {
   double sec = getElapsedTime();
   double min = 0;

   while (sec >=60){
       min++;
       sec -=60;
   }

   // Elapsed time.
   sprintf(event, "%02.0f:%06.3lf | Child %d message %d", min, sec, aChild.id, aChild.message++);
}

/**
Prints the time and the event for the parent
e.g. 00:00.000 | event
**/
void printEvent(char *event) {
    double sec = getElapsedTime();
    double min = 0;

    while (sec >=60){
        min++;
        sec -=60;
    }

    // Elapsed time.
    //printf("%02.0f:%06.3lf | ", min, sec);
    char time[BUFFER_SIZE] = "";
    sprintf(time,"%02.0f:%06.3lf | ", min, sec);
    fputs(time, fp);
    fputs(event, fp);
    fputs("\n", fp);

    //What they are doing
    //printf(event);
    //printf("\n");
}

/**
Creates a random wait time
**/
int sleepTime() {
    return rand()%SLEEP_DURATION;
}

void childMethod(){

    if(aChild.id=!4){//Normal random sleep child
        char write_msg[BUFFER_SIZE] = "";
        sleep(sleepTime());

        addTimeToEvent(write_msg);

        // printf("%s\n", write_msg);
        // Write to the WRITE end of the pipe.
        write(fd[aChild.id-1][WRITE_END], write_msg, BUFFER_SIZE);

         // Close the WRITE end of the pipe.
    }else{//STNDIN
        FD_ZERO(&inputs);
        FD_SET(0, &inputs);

    }
}

parentMethod(){
    char read_msg[BUFFER_SIZE];
    struct timeval timeout;
    int i, status, result;
    inputfds = inputs;
    result = select(FD_SETSIZE, &inputfds, NULL, NULL, &timeout);

    switch(result) {
        case 0: {// Nothing ready
            break;
        }
        case -1: {
           perror("select");
           exit(1);
        }
        default: {//set is not empty
            int readChild;

            for(i=0;i<CHILD_NUMBER;i++){

                if (FD_ISSET(fd[i][READ_END], &inputfds)) {
                    // open(fd[i])
;                    //close write end
                    

                    char event[BUFFER_SIZE] = "";
                    // Read from the READ end of the pipe.

                    //read from read end to read_msg buffer
                    read(fd[i][READ_END], read_msg, BUFFER_SIZE);
                    sprintf(event, "Parent read: ");

                    strcat(event, read_msg);
                    printf("%s\n", event);
                    printEvent(event);


                    close(fd[i][READ_END]); //close pipes for read of parent
                    //print out read_msg buffer
                    // printf("Read Message: %s\n", read_msg);
                }
            }
            break;
        }
    }
}
    
main() {
    //parent and child process ID's
    pid_t pid;

    // time struct to get start time in secs
    struct timeval start; 

    //some useful information for each process

    //keep track of which child number. The first is -1, the parent
    aChild.id = -1;

    //everyone needs me.
    int i;
    int messageNumber = 1;
    
    //initialize to empty set
    FD_ZERO(&inputs); 

    // Create the pipe for each process
    for(i=0;i<CHILD_NUMBER;i++){
        if (pipe(fd[i]) == -1) {
            fprintf(stderr,"pipe() failed");
            return 1;
        }
    }

    // start the timer
    gettimeofday(&start, NULL);
    startTime = (start.tv_sec) * 1000 + (start.tv_usec) / 1000;

    //create child processes up to CHILD_NUMBER times
    for (i = 0; i < CHILD_NUMBER; i++) {
		pid = fork();
		if (pid > 0) {//Parent
            close(fd[i][WRITE_END]);//close write end
            FD_SET(fd[i][READ_END], &inputs);
        }
		else if (pid == 0) {//Child
			aChild.id = i+1;
            close(fd[aChild.id-1][READ_END]);//close read end
			break;
		} else {
			perror("fork error");
			return 1;
		}
	}    
    if(pid != 0) fp = fopen("theta1.txt","w"); // opens the file to write to
    else aChild.message = 1; //Sets the child message to 1.

    while (PROGRAM_DURATION > getElapsedTime()){      
        if (pid != 0) parentMethod();
        else childMethod();
    }
    close(fd[aChild.id-1][WRITE_END]);
    for (i = 0; i < CHILD_NUMBER; i++)
        close(fd[i][READ_END]); 
}
