#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>

int worker1_id;
int worker2_id;

void *worker1(void *param);
void *worker2(void *param);

pthread_mutex_t mtx1;
pthread_mutex_t mtx2;

int main(int argc, char *argv[])
{
    srand(time(0));
    
    pthread_mutex_init(&mtx1, NULL);
    pthread_mutex_init(&mtx2, NULL);
    
    printf("main: Create worker 1 thread.\n");
    worker1_id = 1;
    pthread_t worker1_tid;
    pthread_attr_t worker1_attr;
    pthread_attr_init(&worker1_attr);
    pthread_create(&worker1_tid, &worker1_attr, worker1, &worker1_id);
    
    printf("main: Create worker 2 thread.\n");
    worker2_id = 2;
    pthread_t worker2_tid;
    pthread_attr_t worker2_attr;
    pthread_attr_init(&worker2_attr);
    pthread_create(&worker2_tid, &worker2_attr, worker2, &worker2_id);

    printf("main: Wait for workers to finish.\n");
    pthread_join(worker1_tid, NULL);
    pthread_join(worker2_tid, NULL);
    printf("main: Done!\n");
}

void *worker1(void *param)
{
    printf("Worker1: Locking mutex 1.\n");
    pthread_mutex_lock(&mtx1);
    
    sleep(rand()%3);
    
    printf("Worker1: Locking mutex 2.\n");
    pthread_mutex_lock(&mtx2);
    
    printf("Worker1: Working.\n");
    sleep(rand()%5);
    
    printf("Worker1: Unlocking mutex 2.\n");
    pthread_mutex_unlock(&mtx2);
    
    sleep(rand()%3);
    
    printf("Worker1: Unlocking mutex 1.\n");
    pthread_mutex_unlock(&mtx1);
}

void *worker2(void *param)
{
    printf("Worker2: Locking mutex 2.\n");
    pthread_mutex_lock(&mtx2);
    
    sleep(rand()%3);
    
    printf("Worker2: Locking mutex 1.\n");
    pthread_mutex_lock(&mtx1);
    
    printf("Worker2: Working.\n");
    sleep(rand()%5);
    
    printf("Worker2: Unlocking mutex 1.\n");
    pthread_mutex_unlock(&mtx1);
    
    sleep(rand()%3);
    
    printf("Worker2: Unlocking mutex 2.\n");
    pthread_mutex_unlock(&mtx2);
}
