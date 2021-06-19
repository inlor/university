#include "lpc_server.h"

void *lpc_create(const char *name, size_t capacity){
    char *path = concat(SHM_FOLDER, name);
    if(access(path, F_OK) == 0) shm_unlink(name);
    free(path);

    int fd = shm_open(name, O_TRUNC | O_EXCL | O_CREAT | O_RDWR, S_IRUSR | S_IWUSR | S_IRGRP | S_IWGRP);
    CHECK((fd == -1), "shm_open", errno, STAY);
    if(fd == -1) return NULL;
    
    long page_size = sysconf(_SC_PAGE_SIZE);
    size_t length = capacity * page_size;
    
    int status = ftruncate(fd, length);
    CHECK((status == -1), "ftruncate", errno, STAY);
    if(status == -1) return NULL;

    void *addr = mmap(NULL, length, PROT_READ | PROT_WRITE, MAP_SHARED, fd, 0);
    CHECK((addr == MAP_FAILED), "mmap", errno, STAY);
    if(addr == MAP_FAILED) return NULL;
    memset(addr, 0, length);

    status = close(fd);
    CHECK((status == -1), "close", errno, QUIT);

    header *head = (header *) addr;
    head->capacity = capacity;
    head->available = 0;
    head->data1.args = calloc(1,SIZE_ARGS);

    int code = init_mutex(&head->mutex);
    CHECK((code != 0), "init_mutex", code, QUIT);

    code = init_cond(&head->rcond);
    CHECK((code != 0), "init_rcond", code, QUIT);

    code = init_cond(&head->wcond);
    CHECK((code != 0), "init_wcond", code, QUIT);
        
    return addr;
}

int lpc_add_function(lpc_server_funs *funs, char *fun_name, int (*fun)(void *)){
    if(funs->lfuns + 1 >= FUNS_SIZE) {
        errno = ENOBUFS;
        return -1;
    }

    lpc_function f = {.fun = fun};
    strcpy(f.fun_name, fun_name);

    funs->funs[funs->lfuns] = f;
    funs->lfuns++;
    return 0;
}

int lpc_launch_function(lpc_server_funs *funs, char *fun_name, void *args){
    for(size_t i = 0; i < funs->lfuns; i++){
        if(strcmp(fun_name, funs->funs[i].fun_name) == 0){
            return (funs->funs[i].fun)(args);
        }else if(i + 1 == funs->lfuns){
            errno = ENOSYS;
            printf("Fun: '%s'\n", fun_name);
            CHECK((1), "function not found", errno, STAY);
        }
    }
    return -1;
}
