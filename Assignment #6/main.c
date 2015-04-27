#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/time.h>
#include <sys/ioctl.h>
#include <time.h>
#include <string.h>

#define PROGRAM_DURATION 5
#define SLEEP_DURATION 3
#define NUM_CHILDREN 2
#define READ_END 0
#define WRITE_END 1
#define BUFFER_SIZE 80

/**
* Print a line for each event:
* elapsed time
* message from child process
* user standard input
*/
typedef struct {
    int id;
    int messageCount;
} CHILD;

//global start time variable
int status;
double startTime;
int fd[NUM_CHILDREN][2]; // file descriptors for the pipe
char read_buffer[NUM_CHILDREN][BUFFER_SIZE];
char write_buffer[NUM_CHILDREN][BUFFER_SIZE];
fd_set inputs[NUM_CHILDREN]; // for select

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
 * Do the work of a child who does not use the keyboard.
 */
void doAutoChildWork(int i) {
    srand(time(NULL) * i);

	CHILD child;
	child.id = i + 1;
	child.messageCount = 1;

    while(PROGRAM_DURATION >= getElapsedTime()) {
        int sleepTime = rand() % SLEEP_DURATION;
        sleep(sleepTime);

        close(fd[i][READ_END]);
        sprintf(write_buffer[i],"Child %d message %d", child.id, child.messageCount);
        //printEvent(write_buffer[i]);
        //fflush(stdout);
        child.messageCount++;
        write(fd[i][WRITE_END], write_buffer[i], strlen(write_buffer[i]) + 1);
        close(fd[i][WRITE_END]);

        FD_SET(i, &inputs[i]);
    }
}

/**
main program
**/
int main() {
    int result, nread;
	pid_t pid;// creates children
	fd_set inputfds;  // sets of file descriptors
    struct timeval timeout, start; // time structs
	//for pipe
	char write_msg[BUFFER_SIZE];
	char read_msg[BUFFER_SIZE];
    int i;

	// start the timer
	gettimeofday(&start, NULL);
	startTime = (start.tv_sec) * 1000 + (start.tv_usec) / 1000;

	//create the pipe
	for(i = 0; i < NUM_CHILDREN; i++) {
		if(pipe(fd[i]) == -1) {
			fprintf(stderr, "pipe %d failed", i);
			return 1;
		}

        FD_ZERO(&inputs[i]);
		FD_SET(i, &inputs[i]);
	}

	for(i = 0; i < NUM_CHILDREN; i++) {
        pid = fork();
        // error handling
		if(pid == -1) {
			perror("select");
			exit(1);
		}
        if(pid == 0) {
            doAutoChildWork(i);
            // kill the children after they are done working
			exit(0);
        }
    }

    //  Wait for input on stdin for a maximum of 2.5 seconds.
    // do the parent's work
    while(PROGRAM_DURATION >= getElapsedTime())  {
        for(i = 0; i < NUM_CHILDREN; i++) {
            inputfds = inputs[i];

            timeout.tv_sec = 2;
            timeout.tv_usec = 500000;

            // Get select() results.
            result = select(i + 1, (fd_set *) 0, &inputfds, (fd_set *) 0, &timeout);

            if(result == 0) {
                printf("result was 0 \n");
            }
            else if(result == -1) {
                perror("select");
                exit(1);
            }
            else {
                // add another if here to determine if input should be coming from keyboard child
                if(FD_ISSET(i, &inputfds)) {
                    close(fd[i][WRITE_END]);
                    read(fd[i][READ_END], read_buffer[i], BUFFER_SIZE);
                    printf("Parent sees that Child %d is about to print\n", i + 1);
                    printEvent(read_buffer[i]);
                    fflush(stdout);
                    close(fd[i][READ_END]);

                    FD_CLR(i, &inputs[i]);
                }
            }
        }
    }
}

