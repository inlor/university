#pragma once

#include <pthread.h>
#include <sys/types.h>
#include <stdbool.h>
#include "lpc_function.h"
#include "data.h"

typedef struct{
  pthread_mutex_t mutex;
  pthread_cond_t rcond;
  pthread_cond_t wcond;
  /* Pid of the client */
  pid_t pid_client;
  /* Pid of the server */
  pid_t pid_server;
  /* Result of the function */
  int result;
  /* Value of errno */
  int error;
  /* Capacity allocated for the mmap */
  size_t capacity;
  /* Data to call function */
  data data1;
  /* Is avaible for modifications */
  bool available;
} header;  
