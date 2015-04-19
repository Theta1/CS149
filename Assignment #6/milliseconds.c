#include <sys/time.h>
#include<stdio.h>
#include<time.h>

int main () {
	struct timeval stop, start;
	gettimeofday(&start, NULL);
	
	sleep(2);
	
	gettimeofday(&stop, NULL);
	double time1 = (start.tv_sec) * 1000 + (start.tv_usec) / 1000;
	double time2 = (stop.tv_sec) * 1000 + (stop.tv_usec) / 1000;
	printf("time1 %lf\n", time1);
	printf("time2 %lf\n", time2);
	printf("took %lf\n", time2 - time1);
	return 0;
}