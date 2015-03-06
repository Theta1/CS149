#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <fcntl.h>
#include <sys/mman.h>
#include <sys/stat.h>

int main()
{
    const int   SIZE = 4096;  // size of shared memory object
    const char *NAME = "OS";  // name of shared memory object
    
    int shm_fd;  // shared memory file descriptor
    void *ptr;   // pointer to shared memory object
    
    // Open the shared memory object.
    shm_fd = shm_open(NAME, O_RDONLY, 0666);
    
    // Memory map the shared memory object.
    ptr = mmap(0, SIZE, PROT_READ, MAP_SHARED, shm_fd, 0);

    // Read from the shared memory object and then remove it.
    printf("%s\n", (char *) ptr);
    shm_unlink(NAME);
    
    return 0;
}
