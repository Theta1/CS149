#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <fcntl.h>
#include <sys/mman.h>
#include <sys/stat.h>

int main(int argc, char *args[])
{
    const int   SIZE = 4096;  // size of shared memory object
    const char *NAME = "OS";  // name of shared memory object
    
    int shm_fd;  // shared memory file descriptor
    void *ptr;   // pointer to shared memory object
    
    // Create shared memory object and configure its size.
    shm_fd = shm_open(NAME, O_CREAT | O_RDWR, 0666);
    ftruncate(shm_fd, SIZE);
    
    // Memory map the shared memory object.
    ptr = mmap(0, SIZE, PROT_WRITE, MAP_SHARED, shm_fd, 0);
    
    // Write to the shared memory object.
    sprintf(ptr, "%s", args[1]);
    
    return 0;
}
