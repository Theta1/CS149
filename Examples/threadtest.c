#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <pthread.h>

char *greeting;            // shared data
void *runner(void *parm);  // the thread

int main(int argc, char *argv[])
{
    pthread_t tid;
    pthread_attr_t attr;
    
    printf("Parent: Creating child thread.\n");
    pthread_attr_init(&attr);
    pthread_create(&tid, &attr, runner, argv[1]);
    
    printf("Parent: Waiting for child to complete.\n");
    pthread_join(tid, NULL);
    
    printf("Parent: Child has completed, set greeting '%s'.\n", greeting);
    printf("Parent: Terminating.\n");
    exit(0);
}

void *runner(void *parm)
{
    printf("Child: Started with parm '%s'.\n", parm);
    printf("Child: Creating greeting ... ");
    
    greeting = malloc(strlen(parm) + 8);
    sprintf(greeting, "Hello, %s!", parm);
    
    printf("done!\n");
    printf("Child: Terminating.\n");
    pthread_exit(0);
}
