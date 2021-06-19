#include "main_server.h"

static int pid_client = 0;

static void before_quit(){
    printf("End of client %i\n\n", pid_client);
    char pid_str[10];
    sprintf(pid_str, "%d", pid_client);
    char *shm_name = concat(NAME_SHARED_MEMORY, pid_str);
    int status = shm_unlink(shm_name);
    CHECK((status == -1), "shm_unlink", errno, QUIT);
    free(shm_name);
}

static void handler_quit(int sig){
    exit(0);
}

int server() {
    // Init functions supported by the server
    lpc_server_funs funs = {.lfuns = 0};
    lpc_add_function(&funs, "fun_client_1", fun_client_1);
    lpc_add_function(&funs, "fun_client_2", fun_client_2);
    lpc_add_function(&funs, "fun_client_3", fun_client_3);
    lpc_add_function(&funs, "fun_client_4", fun_client_4);

    // Init server for first connection to get the client pid
    if(access(SOCKET_PATH, F_OK) == 0) unlink(SOCKET_PATH);
    struct sockaddr_un server = {.sun_family = AF_UNIX};
	strcpy(server.sun_path, SOCKET_PATH);
	socklen_t lserver = offsetof(struct sockaddr_un, sun_path) + strlen(server.sun_path);
    
    int fd = socket(AF_UNIX, SOCK_DGRAM, 0);
    CHECK((fd == -1), "socket", errno, QUIT);

    int status = bind(fd, (struct sockaddr *) &server, lserver);
    CHECK((status == -1), "bind", errno, QUIT);

    int i = 0;
    while(1){
        i++;        
        struct sockaddr_un client;
        memset(&client, 0, sizeof(client));
        socklen_t lclient = sizeof(client);

        pid_t pid;
        
        printf("Parent server waiting for client request... %i\n", i);
        status = recvfrom(fd, &pid, sizeof(pid_t), 0, (struct sockaddr *)&client, &lclient);
        CHECK((status == -1), "recvfrom", errno, QUIT);

        // Create new process to run lpc_server
        int child = fork();
        CHECK((child == -1), "fork", errno, QUIT);
        if(child == 0){
            // Double fork to delete Zombies processes
            int grandchild = fork();
            CHECK((grandchild == -1), "fork", errno, QUIT);
            if(grandchild != 0) exit(0);

            printf("Client: %i\n", pid);

            // Install handler to kill child ps
            struct sigaction sa;
            sa.sa_handler = &handler_quit;
            status = sigaction(SIGQUIT, &sa, NULL);
            CHECK((status == -1), "signal", errno, QUIT);

            // Cleanning before killing the child ps
            int status = atexit(&before_quit);
            CHECK((status == -1), "atexit", errno, QUIT);

            pid_client = pid;
            char pid_str[10];
            sprintf(pid_str, "%d", pid);
            char *shm_name = concat(NAME_SHARED_MEMORY, pid_str);

            void *addr = lpc_create(shm_name, CAPACITY_SHARED_MEMORY);
            CHECK((addr == NULL), "lpc_create", errno, QUIT);
            free(shm_name);

            header *head = (header *) addr;
            head->pid_server = getpid();

            int result = 1;
            status = sendto(fd, &result, sizeof(int), 0, (struct sockaddr *)&client, lclient);
            CHECK((status == -1), "sendto", errno, QUIT);

            status = close(fd);
            CHECK((status == -1), "close", errno, QUIT);

            while(1){
                int code = pthread_mutex_lock(&head->mutex);
                CHECK((code != 0), "mutex_lock", code, QUIT);
                
                // Wait until the memory is available
                while(!head->available){
                    code = pthread_cond_wait(&head->wcond, &head->mutex);
                    CHECK((code != 0), "cond_wait", code, QUIT);
                }
                
                // Critical section
                int result = lpc_launch_function(&funs, head->data1.fun_name, head);
                head->available = 0;
                head->result = result;
                head->error = errno;

                code = pthread_mutex_unlock(&head->mutex);
                CHECK((code != 0), "mutex_unlock", code, QUIT);

                code = pthread_cond_signal(&head->rcond);
                CHECK((code != 0), "cond_signal", code, QUIT);
            }
        }
        // Remove zombie processes
        status = waitpid(child, NULL, 0);
        CHECK((status < 0), "waitpid", errno, STAY);
    }
    return EXIT_SUCCESS;
}
