#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/time.h>
#include <sys/ioctl.h>
#include <time.h>
#include <string.h>

#define CHILDREN 4
#define BUFFER_SIZE 48
#define READ_END 0
#define WRITE_END 1
#define SLEEP_DURATION 3
#define PROGRAM_DURATION 30

/**
Creates a random wait time
**/
int sleepTime() {
	return rand()%SLEEP_DURATION;
}



//global start time variable
double startTime; // the time the forking starts
FILE *fp; // file pointer for opening file

//write buffer for pipe
char write_msg[BUFFER_SIZE];
char read_msg[BUFFER_SIZE] = "";

/**
Gets the elapsed time
**/
double getElapsedTime() {
	struct timeval now;
    gettimeofday(&now, NULL);
	double currentTime = (now.tv_sec) * 1000 + (now.tv_usec) / 1000;
	return (currentTime - startTime)/1000;
}

int main()
{
    struct timeval start;

    // start the timer
	gettimeofday(&start, NULL);
	startTime = (start.tv_sec) * 1000 + (start.tv_usec) / 1000;

    //parent and child process ID's
    pid_t pid, wpid;

    //keep track of which child number
    int i, j, k;
    int seedtime;
    int x = -1;


    //array of file descriptors for all the Child Pipes
    int fd[CHILDREN][2];

    // Create the pipe for each process
    for (i=0; i< CHILDREN; i++)
    {
        if (pipe(fd[i]) == -1) {
        fprintf(stderr,"pipe() failed");
        return 1;
        }
    }

    //set of file descriptors
    fd_set inputs, inputfds;
    struct timeval timeout;

    FD_ZERO(&inputs); //initialize to empty set


    printf("Parent: Process started\n");
    printf("Parent: Forking a child.\n");

    //create child processes up to CHILD_NUMBER times
    for (i = 0; i < CHILDREN; i++) {
		pid = fork();
		if (pid > 0) {

            //parent add read end to fd set
            FD_SET(fd[i][READ_END], &inputs);

            //parent close the write end
            close (fd[i][WRITE_END]);
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


    //message to be passed around


    //if pid is > 0, then it is parent; otherwise if pid == 0, then it is child
    if (pid > 0) {
        // Parent
        while(PROGRAM_DURATION > getElapsedTime()) {
            inputfds = inputs; //reset the set of fd's to the correct fd's

            int result; //holds result of select

            // 2.5 seconds.
            timeout.tv_sec = 3;
            timeout.tv_usec =000000;


            result = select(FD_SETSIZE, &inputfds, NULL, NULL, &timeout);



            switch(result) {
                    case 0: {
                        printf("Empty Set\n");
                        fflush(stdout);
                        break;
                    }

                    case -1: {
                        perror("select fails");
                        return 1;
                    }
                    //set is not empty
                    default: {
                        //printf("result is %d\n", result);

                        for (k=0; k<CHILDREN; k++)
                        {
                            if (FD_ISSET(fd[k][READ_END], &inputfds)) {

                                //read from read end to read_msg buffer
                                if(read(fd[k][READ_END], read_msg, BUFFER_SIZE)) {

                                    double p_sec = getElapsedTime();
                                    double p_min = 0;

                                    while (p_sec >=60){
                                        p_min++;
                                        p_sec -=60;
                                    }
                                    //print out read_msg buffer
                                    printf("%02.0f:%06.3lf Read Message: %s\n", p_min, p_sec, read_msg);
                                }
                            }
                        }

                        break;
                    }

                }
            }

            //parent closes read end after looping
            for (i=0; i<CHILDREN; i++)
            {
                close(fd[i][READ_END]);
            }
    }
    else {
        // Child
        printf("Child %d: Process started.\n", x);


        //close read end
        for (i = 0; i<CHILDREN; i++)
        {
             close(fd[i][READ_END]);
        }

        //seed differently for each child sleep time;
        srand(x);

        for (j=1; PROGRAM_DURATION > getElapsedTime(); j++) {





            double sec = getElapsedTime();
            double min = 0;

            while (sec >=60){
                min++;
                sec -=60;
            }

            //message to be written, which Child, which message
            sprintf(write_msg, "%02.0f:%06.3lf | Child %d message %d", min, sec, x, j);

            //write a message to pipe
            write(fd[x-1][WRITE_END], write_msg, BUFFER_SIZE);

             //sleep 0,1, or 2 seconds
            sleep(sleepTime());

        }

        //close read end
        close(fd[x-1][WRITE_END]);


        printf("Child %d: Terminating.\n", x);
    }

}
