#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/time.h>
#include <sys/ioctl.h>
#include <time.h>

#define PROGRAM_DURATION 30
#define SLEEP_DURATION 3

//global start time variable
double startTime;

typedef struct {
    int id;
    int messageCount;
} CHILD;

// registration timers
//struct itimerval timer;
//time_t startTime;

/**
* Print a line for each event:
* elapsed time
* who is registering from what queue
* who is waiting in what queue
* what action they take: Register/drop/gaveup and where
*/

/**
Creates a random wait time
**/
int sleepTime() {
	// set up rand
    srand(time(NULL));
	
	return rand()%SLEEP_DURATION;
}


/**
Gets the elapsed time
**/
double getElapsedTime() {
	struct timeval now;
    gettimeofday(&now, NULL);
	double currentTime = (now.tv_sec) * 1000 + (now.tv_usec) / 1000;
	return (currentTime - startTime)/1000;
}

/**
Prints the time and the event
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
    printf("%02.0f:%06.3lf | ", min, sec);

    //What they are doing
    printf(event);
    printf("\n");
}

/**
main program
**/
int main() {
    char buffer[128];
    int result, nread;
	pid_t pid;// creates 1st child
	
	int fd[2]; // file desciptors for the pipe
	
	//create the pipe
	if(pipe(fd) ==-1) {
		fprintf(stderr, "pipe() failed");
		return 1;
	}
	
    fd_set inputs, inputfds;  // sets of file descriptors
    struct timeval timeout, start; // time structs
   
    // start the timer
	gettimeofday(&start, NULL);
	startTime = (start.tv_sec) * 1000 + (start.tv_usec) / 1000;

    FD_ZERO(&inputs);    // initialize inputs to the empty set
    FD_SET(0, &inputs);  // set file descriptor 0 (stdin)
	
	pid = fork();

    //  Wait for input on stdin for a maximum of 2.5 seconds.    
    while (PROGRAM_DURATION >= getElapsedTime())  {
        inputfds = inputs;

		timeout.tv_sec = 0;
        timeout.tv_usec = 500000;

        // Get select() results.
        result = select(FD_SETSIZE, &inputfds, (fd_set *) 0, (fd_set *) 0, &timeout);
		
		//main
		if(pid > 0) {
			char event[80];
        	sprintf(event,"parent event1");
			printEvent(event);
            fflush(stdout);
			
		}
		
		if(pid == 0) {
			sleep(sleepTime());
			char event[80];
        	sprintf(event,"event1");
			printEvent(event);
            fflush(stdout);
		}

        // Check the results.
        //   No input:  the program loops again.
        //   Got input: print what was typed, then terminate.
        //   Error:     terminate.
        /*switch(result) {
            case 0: {
        	char event[80];
        	sprintf(event,"event1");
			printEvent(event);
                fflush(stdout);
                break;
            }
            
            case -1: {
                perror("select");
                exit(1);
            }

            // If, during the wait, we have some action on the file descriptor,
            // we read the input on stdin and echo it whenever an 
            // <end of line> character is received, until that input is Ctrl-D.
            default: {
                if (FD_ISSET(0, &inputfds)) {
                    ioctl(0,FIONREAD,&nread);
                    
                    if (nread == 0) {
                        printf("Keyboard input done.\n");
                        exit(0);
                    }
                    
                    nread = read(0,buffer,nread);
                    buffer[nread] = 0;
                    printf("Read %d characters from the keyboard: %s", 
                           nread, buffer);
                }
                break;
            }
        }*/
    }

}

