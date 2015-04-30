
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/time.h>
#include <unistd.h>
#include <sys/ioctl.h>

#define SLEEP 3
#define CHILDREN 5 
#define DURATION 30
#define SEC_IN_MSEC 1000
#define READER_PROCESS_INDEX 4
#define INPUT_SIZE  256      //Max number of allowable input characters at stdin.
#define BUFFER_SIZE 256 + 80 //Max number of allowable characters in a message.
#define READ_END     0
#define WRITE_END    1
#define READER_TIMEOUT_SECS 3
#define READER_TIMEOUT_USECS 0

struct timeval start;
int fd[CHILDREN][2];
char timestamp[20] = "";
char buffer[BUFFER_SIZE] = "";

int get_elapsed_msec()
{
	struct timeval current, result;
	gettimeofday(&current, NULL);
	timersub(&current, &start, &result);
	return 1000*result.tv_sec + result.tv_usec/1000;
}

//Fills a buffer with the timestamp string.
//Returns the number of characters printed.
//If output is null, returns the number of characters it would print.
int get_timestamp(char *output)
{
	int elapsed = get_elapsed_msec();
	return sprintf(output, "%d:%02d:%03d", elapsed/(60*1000), (elapsed/1000)%60, elapsed%1000);
}

void child_reader_process(int process_index)
{
	struct timeval timeout = {.tv_sec = READER_TIMEOUT_SECS, .tv_usec = READER_TIMEOUT_USECS};
	fd_set inputs, inputfds;
	int nread, result;
    FD_ZERO(&inputs);    // initialize inputs to the empty set
    FD_SET(0, &inputs);  // set file descriptor 0 (stdin)

	char input[INPUT_SIZE];
	while (get_elapsed_msec() < DURATION * SEC_IN_MSEC) {
		inputfds = inputs;
		result = select(FD_SETSIZE, &inputfds, NULL, NULL, &timeout);
		if (result == 0) {
			fflush(stdout);
		} else if (result == -1) {
			perror("select");
			exit(1);
		} else {
			if (FD_ISSET(0, &inputfds)) {
				ioctl(0,FIONREAD,&nread);
				nread = read(0, input, nread);
				input[nread-1] = '\0';
				get_timestamp(timestamp);
				sprintf(buffer, "%s: %s", timestamp, input);
				write(fd[process_index][WRITE_END], buffer, strlen(buffer) + 1);
			}
		}
	}
}

void child_process(int process_index)
{
	close(fd[process_index][READ_END]); //We don't need the read end.
	if (process_index == READER_PROCESS_INDEX) {
		child_reader_process(process_index);
	} else {
		srand(process_index); //Seed with the process index so children don't sleep the same amount.
		int message_num;
		for (message_num = 0; get_elapsed_msec() < DURATION * SEC_IN_MSEC; message_num++) {
			get_timestamp(timestamp);
			sprintf(buffer, "%s: Child %d message %2d", timestamp, process_index, message_num);
			write(fd[process_index][WRITE_END], buffer, strlen(buffer) + 1);
			sleep(rand()%SLEEP);
		}
	}
	close(fd[process_index][WRITE_END]); //Done with the write end now.
}

int main()
{
	int i, result;
	fd_set in, incopy;
	FD_ZERO(&in);
	struct timeval timeout = {.tv_sec = 3, .tv_usec = 5000}; //Timeout for select, later on.
	struct timeval timeoutcopy = timeout; //Make a copy of timeout, since select modifies it.

	gettimeofday(&start, NULL); //Set our start clock.

	for(i = 0; i < CHILDREN; i++) {
		if (pipe(fd[i]) == -1) { //Create pipes.
			perror("pipe");
			return 1;
		}
		if ((result = fork()) == 0) { //This means we're a child process.
			child_process(i);
			exit(0);
		} else if (result == -1) { //Error case.
			perror("fork");
			return 1;
		}
		close(fd[i][WRITE_END]); //Parent process, close the write ends.
		FD_SET(fd[i][READ_END], &in); //Add the read end of this file descriptor to the fd_set.
	}

	//Parent process continues here.
	incopy = in; //Get a copy of the fd_set, since select modifies it.
	while (get_elapsed_msec() < DURATION * SEC_IN_MSEC) { //Breaks after a timeout of 3.5 seconds.
		in = incopy; //Reset in from our copy.
		timeout = timeoutcopy; //Reset timeout from our copy.
		result = select(FD_SETSIZE, &in, NULL, NULL, &timeout);
		if (result == -1) { //Error occured.
			return 1;
		} else if (result == 0) { //Timeout reached.
			break;
		} else { //One or more file descriptors are ready for read.
			for (i = 0; i < CHILDREN; i++) //Loop through the pipes and check each one.
			{
				if (FD_ISSET(fd[i][READ_END], &in)) {                     //For each that is ready...
					if (read(fd[i][READ_END], buffer, BUFFER_SIZE) > 0) { //...read the input...
						get_timestamp(timestamp);                         //...give it a timestamp...
						printf("%s %s\n", buffer, timestamp);             //...and print.
					}
				}
			}
		}
	}

	for (i = 0; i < CHILDREN; i++)
		close(fd[i][READ_END]); //Close all the pipes (the write ends were closed previously).
}