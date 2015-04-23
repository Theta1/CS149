#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/time.h>
#include <sys/ioctl.h>
#include <time.h>
#include <string.h>

#define PROGRAM_DURATION 30
#define SLEEP_DURATION 3
#define NUM_CHILDREN 2
#define READ_END 0
#define WRITE_END 1
#define BUFFER_SIZE 32

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
CHILD children[NUM_CHILDREN];

/**
Creates a random wait time
**/
int sleepTime() {	
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
	pid_t pid;// creates children
	int pipes;
	fd_set inputs, inputfds;  // sets of file descriptors
    struct timeval timeout, start; // time structs
	//for pipe
	char write_msg[BUFFER_SIZE];
	char read_msg[BUFFER_SIZE];
	
	int fd[NUM_CHILDREN][2]; // file desciptors for the pipe
	
	
    FD_ZERO(&inputs);    // initialize inputs to the empty set
	
	//create the pipe
	for (pipes = 0; pipes < NUM_CHILDREN; pipes++) {
		if(pipe (fd[pipes]) == -1) {
			fprintf(stderr, "pipe %d failed", pipes);
			return 1;
		}
		FD_SET(pipes, &inputs);
	}	

	// start the timer
	gettimeofday(&start, NULL);
	startTime = (start.tv_sec) * 1000 + (start.tv_usec) / 1000;
	
	//createChild();
	int i, j;
	for (i = 0; i < NUM_CHILDREN; i++) {
        pid = fork();
        // error handling
		if (pid == -1)
		{	
			perror("select");
			exit(1);
		}
        if (pid == 0)
		{
			srand(time(NULL)*i);
			CHILD child;
			child.id = i+1;
			child.messageCount = 1;
			children[i] = child;
			break;
        }

    }
	
	
    //  Wait for input on stdin for a maximum of 2.5 seconds.    
    while (PROGRAM_DURATION >= getElapsedTime())  {
/*        inputfds = inputs;

		timeout.tv_sec = 2;
        timeout.tv_usec = 500000;

        // Get select() results.
        result = select(FD_SETSIZE, &inputfds, (fd_set *) 0, (fd_set *) 0, &timeout);
		
		if (result == 0) {
			printf("result 0 \n");
		}
		else if (result == -1) {
			perror("select");
			exit(1);
		}
		else {
			for (j = 0;j < NUM_CHILDREN-1;j++) { 
				if(FD_ISSET(j, &inputfds))
				{
					sleep(sleepTime());
					close(fd[READ_END]);
					char event[80];
					sprintf(event,"Child %d message %d",children[i].id ,children[i].messageCount);
					printf(event);
					printf("\n");
					fflush(stdout);
					children[i].messageCount++;
					write(fd[WRITE_END], event, strlen(event)+1); //Possible problem writing to pipe here
					close(fd[WRITE_END]);
					ioctl(0, FIONREAD, &nread);
					if (nread == 0) 
					{
                        printf("Keyboard input done.\n");
                        exit(0);
                    }
                    
                    nread = read(0,buffer,nread);
                    buffer[nread] = 0;
                    printf("Read %d characters from the keyboard: %s", nread, buffer);
				}
			}
		}
	*/		
		//parent process
		if(pid > 0) {
			char event[80];
			int k;
			for(k = 0; k < NUM_CHILDREN; k++) {
				close(fd[k][WRITE_END]);
				read(fd[k][READ_END], event, 81);
				printEvent(event);
				fflush(stdout);
				close(fd[k][READ_END]);
			}
		}
		
		//child process
		if(pid == 0) {
			//standard input
			if(i == 0) {
				/*if (FD_ISSET(0, &inmanputfds)) 
				{
                    ioctl(0,FIONREAD,&nread);
                    
                    if (nread == 0) 
					{
                        printf("Keyboard input done.\n");
                        exit(0);
                    }
                    
                    nread = read(0,buffer,nread);
                    buffer[nread] = 0;
                    printf("Read %d characters from the keyboard: %s", nread, buffer);
                }
                break;
				*/
			}
			//child process write to pipe
			else
			{
				printf("child: %d\n", i);
				close(fd[i][READ_END]);
				int a = sleepTime();
				//printf("%d: %d\n",i,a);
				sleep(a);
				
				char event[80];
				sprintf(event,"Child %d message %d",children[i].id ,children[i].messageCount);
				//printf(event);
				//printf("\n");
				//fflush(stdout);
				children[i].messageCount++;
				write(fd[i][WRITE_END], event, strlen(event)+1); //Possible problem writing to pipe here
				close(fd[i][WRITE_END]);
			}
		}
    }
	
	//wait for children to end
	waitpid(-1, &status, 0);
}

