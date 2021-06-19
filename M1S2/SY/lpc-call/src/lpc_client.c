#include "lpc_client.h"

static int lpc_client_init_args(header *head, va_list params);
static int lpc_client_update_args(header *head, va_list params);

char *lpc_init_connection(){
    int fd = socket(AF_UNIX, SOCK_DGRAM, 0);
    CHECK((fd == -1), "socket", errno, QUIT);

	struct sockaddr_un server = {.sun_family = AF_UNIX};

	strcpy(server.sun_path, SOCKET_PATH);
	socklen_t lserver = offsetof(struct sockaddr_un, sun_path) + strlen(server.sun_path);

	struct sockaddr_un client = {.sun_family = AF_UNIX};

	snprintf(
		client.sun_path, 
		sizeof(client.sun_path),
		"/tmp/client.%ld", 
		(long)getpid()
	);
	socklen_t lclient = offsetof(struct sockaddr_un, sun_path) + strlen(client.sun_path);

	int status = bind(fd, (struct sockaddr *)&client, lclient);
    CHECK((status == -1), "bind", errno, QUIT);

    pid_t pid = getpid();
	status = sendto(fd, &pid, sizeof(pid_t),  0, (struct sockaddr *)&server, lserver);
    CHECK((status == -1), "sendto", errno, QUIT);

    int result;
	status = recvfrom(fd, &result, sizeof(int), 0, (struct sockaddr *)&server, &lserver);
	CHECK((status == -1), "recvfrom", errno, QUIT);
	CHECK((result != 1), "code unknown", 19, QUIT);

	unlink(client.sun_path);
    status = close(fd);
    CHECK((status == -1), "close", errno, QUIT);

    char pid_str[10];
    sprintf(pid_str, "%d", getpid());
    return concat(NAME_SHARED_MEMORY, pid_str);
}

void *lpc_open(const char *name){
    int file = shm_open(name, O_RDWR, S_IRUSR | S_IWUSR);
    CHECK((file == -1), "shm_open", errno, STAY);
    if(file == -1) return NULL;

    struct stat file_stat;
    fstat(file, &file_stat);

    void *mem = mmap(NULL, file_stat.st_size, PROT_READ | PROT_WRITE, MAP_SHARED, file, 0);
    CHECK((mem == MAP_FAILED), "mmap", errno, STAY);
    if(mem == MAP_FAILED) return NULL;

    int status = close(file);
    CHECK((status == -1), "close", errno, QUIT);

    return mem;
}

int lpc_close(void *mem){
    header *head = (header *) mem;
    int pid_server = head->pid_server;

    int status = munmap(mem, head->capacity * sysconf(_SC_PAGE_SIZE));
    CHECK((status == -1), "munmap", errno, STAY);

    kill(pid_server, SIGQUIT);
    return 0;
}

int lpc_call(void *memory, const char *fun_name, ...){
    printf("\n");
    printf("Client pid: %i %s\n", getpid(), fun_name);
    header *head = (header *) memory;
    memset(&head->data1.args, 0, SIZE_ARGS);

    int code = pthread_mutex_lock(&head->mutex);
    CHECK((code != 0), "mutex_lock", code, QUIT);

    while(head->available){
        code = pthread_cond_wait(&head->rcond, &head->mutex);
        CHECK((code != 0), "cond_wait", code, QUIT);
    }
    
    strcpy(head->data1.fun_name, fun_name);

    va_list params;
    va_start(params, fun_name);
    lpc_client_init_args(head, params);
    va_end(params);

    head->available = 1;
    head->pid_client = getpid();
    
    code = pthread_mutex_unlock(&head->mutex);
    CHECK((code != 0), "mutex_unlock", code, QUIT);
    
    code = pthread_cond_signal(&head->wcond);
    CHECK((code != 0), "cond_signal", code, QUIT);

    // Wainting for response
    code = pthread_mutex_lock(&head->mutex);
    CHECK((code != 0), "mutex_lock", code, QUIT);

    while(head->available){
        code = pthread_cond_wait(&head->rcond, &head->mutex);
        CHECK((code != 0), "cond_wait", code, QUIT);
    }

    // Update clients variables
    va_start(params, fun_name);
    lpc_client_update_args(head, params);
    va_end(params);

    code = pthread_mutex_unlock(&head->mutex);
    CHECK((code != 0), "mutex_unlock", code, QUIT);
    
    printf("From server: %s %i\n", head->data1.fun_name, head->result);
    errno = head->error;
    return head->result;
}

static int lpc_client_init_args(header *head, va_list params){
    lpc_type current ;
    off_t offset = 0;
    while((current = va_arg(params, lpc_type)) != NOP){
        switch (current){
            case INT:
                memcpy(&head->data1.args + offset, va_arg(params, int*), sizeof(int));
                offset += sizeof(int);
                break;

            case DOUBLE:
                memcpy(&head->data1.args + offset, va_arg(params, double*), sizeof(double));
                offset += sizeof(double);
                break;

            case STRING:{
                lpc_string *str = va_arg(params, lpc_string*);
                memcpy(&head->data1.args + offset, &str->slen, sizeof(int));
                offset += sizeof(int);
                memcpy(&head->data1.args + offset, str, str->slen + sizeof(lpc_string));
                offset += str->slen;
                break;
            }

            case NOP:
                break;

            default:
                break;
        }
    }
    return 0;
}

static int lpc_client_update_args(header *head, va_list params){
    lpc_type current;
    off_t offset = 0;
    while((current = va_arg(params, lpc_type)) != NOP){
        switch (current){
            case INT:
                memcpy(va_arg(params, int*), (int *) (&head->data1.args + offset), sizeof(int));
                offset += sizeof(int);
                break;

            case DOUBLE:
                memcpy(va_arg(params, double*), (double *) (&head->data1.args + offset), sizeof(double));
                offset += sizeof(double);
                break;
            
            case STRING:
                offset += sizeof(int);
                int size = *(int *)(&head->data1.args + offset);
                if(size==-1)
                    return -1;
                memcpy(va_arg(params, lpc_string*), (lpc_string*) (&head->data1.args + offset), size);
                offset += size;
                break;

            case NOP:
                break;

            default:
                CHECK(1, "lpc_call", 79, QUIT);
                break;
        }
    }
    return 0;
}
