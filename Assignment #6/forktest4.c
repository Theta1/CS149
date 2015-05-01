/**
 * A demonstration of piping, selection, forking and file writing.
 * Using 5 child processes this program uses 4 to create messages (increasing consecutive integers) and passes
 * them to the parent via pipes. The 5th process recieves input from STNDIN.
 * The parent monitors 5 data pipes for content via selection.
 * When 1 or more pipes have content the parents prints this to a root dir file: Theta1.txt
 *
 * Note: Special appreciation and thanks to the folks in Section 8, we owe you a pint.
 *
 * @authors Theta(1)
 *
 */
#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/time.h>
#include <sys/ioctl.h>
#include <time.h>
#include <string.h>

#define CHILDREN 5
#define BUFFER_SIZE 48
#define READ_END 0
#define WRITE_END 1
#define SLEEP_DURATION 3
#define PROGRAM_DURATION 30

/**global start time variable**/
double startTime; // the time the forking starts

//write buffer for pipe
char write_msg[BUFFER_SIZE] = "";
char read_msg[BUFFER_SIZE] = "";
char buffer[BUFFER_SIZE];

/**
 * Creates a random wait time.
 * @return int representing the random sleep time, limmited by SLEEP_DURATION
 */
int sleepTime() {
	return rand()%SLEEP_DURATION;
}

/**
 * Calculates the time since the forking
 * @returns double representing slapsed time
 */
double getElapsedTime() {
	struct timeval now;
    gettimeofday(&now, NULL);
	double currentTime = (now.tv_sec) * 1000 + (now.tv_usec) / 1000;
	return (currentTime - startTime)/1000;
}



/**
 * Main thread. Creates the pipes, processes, main loop.
 * Outputs to file Theta1.txt in root dir.
 * @returns int 1 for a success or 2 for a failure state.
 */
int main() {
    struct timeval start;
	FILE *fp; // file pointer for opening file
    // start the timer
	gettimeofday(&start, NULL);
	startTime = (start.tv_sec) * 1000 + (start.tv_usec) / 1000;

    //parent and child process ID's
    pid_t pid, wpid;

    //keep track of which child number
    int i, j, k;
    int seedtime;
    int x = -1;

    //array of file descriptors for all Pipes and select
    int fd[CHILDREN][2];

    // Create the pipe for each process
    for (i=0; i< CHILDREN; i++)
    {
        if (pipe(fd[i]) == -1) {
        fprintf(stderr,"pipe() failed");
        return 1;
        }
    }

    //set array of file descriptors
    fd_set inputs, inputfds;
    struct timeval timeout;

    FD_ZERO(&inputs); //initialize to empty set
	fp = fopen("theta1.txt","w");

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




    //if pid is > 0, then it is parent; otherwise if pid == 0, then it is child
    if (pid > 0)
	{
        // Parent


        while(PROGRAM_DURATION > getElapsedTime())
		{
            inputfds = inputs; //reset the set of fd's to the correct fd's

            int result; //holds result of select

            // 2.5 seconds.
            timeout.tv_sec = 3;
            timeout.tv_usec =000000;

            result = select(FD_SETSIZE, &inputfds, NULL, NULL, &timeout);

            switch(result)
			{
				case 0: {
					//printf("Empty Set\n");
					fflush(stdout);
					break;
				}

				case -1: {
					perror("select fails");
					return 1;
				}
				//set is not empty
				default: {
					//loop through children
					for (k=0; k<CHILDREN; k++)
					{
						//if a file descriptor is in the array
						if (FD_ISSET(fd[k][READ_END], &inputfds))
						{

							//read from read end to read_msg buffer
							if(read(fd[k][READ_END], read_msg, BUFFER_SIZE)) {

								double p_sec = getElapsedTime();
								double p_min = 0;

								while (p_sec >=60){
									p_min++;
									p_sec -=60;
								}
								//print out read_msg buffer
								char message[BUFFER_SIZE*2];
								sprintf(message,"%02.0f:%06.3lf | Parent Read: [%s]\n", p_min, p_sec, read_msg);
								fputs(message, fp);
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

		fclose(fp); // closes the file
    }
    else {
        // Child

        //close read end
        for (i = 0; i<CHILDREN; i++)
        {
             close(fd[i][READ_END]);
        }

        //seed differently for each child sleep time;
        srand(x);

        //Child 5, Stdin
        if (x == 5){
            //second fds for stdin
            fd_set input2, input2fds;
            struct timeval timeout2;
            int result2, nread;

            //initialize with only stdin
            FD_ZERO(&input2);
            FD_SET(0, &input2);

            while (PROGRAM_DURATION > getElapsedTime()) {
                //reset fds stdin set
                input2fds = input2;

                timeout2.tv_sec = 2;
                timeout2.tv_usec = 500000;

                result2 = select(FD_SETSIZE, &input2fds, NULL, NULL, &timeout2);

                switch(result2) {
                    case 0: {
                        printf("Enter a message:\n");
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
                        if (FD_ISSET(0, &input2fds)) {
                            ioctl(0,FIONREAD,&nread);

                            if (nread == 0) {
                                printf("Keyboard input done.\n");
                                exit(0);
                            }

                            nread = read(0,buffer,nread);
                            buffer[nread] = 0;

                            double stdin_sec = getElapsedTime();
                            double stdin_min = 0;

                            while (stdin_sec >=60){
                                stdin_min++;
                                stdin_sec -=60;
                            }

                            sprintf(write_msg, "%02.0f:%06.3lf | Child %d stdin message: %s", stdin_min, stdin_sec, x, buffer);

                            //write a message to pipe
                            write(fd[x-1][WRITE_END], write_msg, BUFFER_SIZE);
                        }
                        break;
                    }
                }

            }

        } else{
            //Children 1- 4
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
        }

        //close read end
        close(fd[x-1][WRITE_END]);

    }
}
