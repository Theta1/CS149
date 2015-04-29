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
#define PROGRAM_DURATION 1

//global start time variable
double startTime; // the time the forking starts

/**
* Gets the elapsed time
*/
double getElapsedTime() {
    struct timeval now;
    gettimeofday(&now, NULL);
    double currentTime = (now.tv_sec) * 1000 + (now.tv_usec) / 1000;
    return (currentTime - startTime)/1000;
}

main(){
    //parent and child process ID's
    pid_t pid, wpid;

    // time struct to get start time in secs
    struct timeval start; 

    //keep track of which child number
    int i;
    int x = -1;


    //array of file descriptors for all the Child Pipes
    int fd[5][2];

    // Create the pipe for each process
    for(i=0;i<CHILD_NUMBER;i++){
        if (pipe(fd[i]) == -1) {
            fprintf(stderr,"pipe() failed");
            return 1;
        }
    }

    //parent's read buffer from pipe
    char read_msg[BUFFER_SIZE];


    //set of file descriptors
    fd_set inputs, inputfds;
    struct timeval timeout;

    FD_ZERO(&inputs); //initialize to empty set


    printf("Parent: Process started\n");
    printf("Parent: Forking a child.\n");

    // start the timer
    gettimeofday(&start, NULL);
    startTime = (start.tv_sec) * 1000 + (start.tv_usec) / 1000;

    //create child processes up to CHILD_NUMBER times
    for (i = 0; i < CHILD_NUMBER; i++) {
		pid = fork();
		if (pid > 0) {
			continue;
		} else if (pid == 0) {
			x = i+1;
			break;
		} else {
			printf("fork error\n");
			return 1;
		}
	}

	//Parent and Child Processes execute the code from this point onwards

    //write buffer for pipe
    char write_msg[BUFFER_SIZE];

    //message to be passed around
    sprintf(write_msg, "I am Child %d", x);

    //if pid is > 0, then it is parent; otherwise if pid == 0, then it is child
    while (PROGRAM_DURATION > getElapsedTime()){
        //=================Parent Preoces=================
        if (pid > 0) {
            // Parent

            //add read ends to the set
            for(i=0;i<CHILD_NUMBER;i++)
                FD_SET(fd[i][READ_END], &inputs);
            
            //NEED TO ADD fd[4] pipe for STANDARD INPUT

            inputfds = inputs;

            printf("Parent: Wait for child to complete.\n");

            int status, result;

            // while ((wpid = wait(&status)) > 0); //wait for all child processes to finish

            // 2.5 seconds.
            timeout.tv_sec = 1;
            timeout.tv_usec =000000;

            result = select(FD_SETSIZE, &inputfds, NULL, NULL, &timeout);

            printf("result is %d\n", result);

            switch(result) {
                    case 0: {
                        printf("Empty Set\n");
                        fflush(stdout);
                        break;
                    }

                    case -1: {
                        perror("select");
                        return 1;
                    }
                    //set is not empty
                    default: {
                        for(i=0;i<CHILD_NUMBER;i++){

                            if (FD_ISSET(fd[i][READ_END], &inputfds)) {

                                //close write end
                                close(fd[i][WRITE_END]);

                                //read from read end to read_msg buffer
                                read(fd[i][READ_END], read_msg, BUFFER_SIZE);

                                //print out read_msg buffer
                                printf("Read Message: %s\n", read_msg);
                            }
                        }
                        break;
                    }

                }
        	printf("Parent: Terminating.\n");
        }
        //=================Child Preoces=================
        else {
            // Child
            printf("Child %d: Process started.\n", x);

            //close read end
            close(fd[x-1][READ_END]);

            //write a message to
            write(fd[x-1][WRITE_END], write_msg, BUFFER_SIZE);

             // Close the WRITE end of the pipe.
            close(fd[x-1][WRITE_END]);
       
            printf("Child %d has written %s\n", x, write_msg);

            printf("Child %d: Terminating.\n", x);
        }
    }
}
