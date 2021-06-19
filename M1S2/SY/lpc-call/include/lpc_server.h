#pragma once

#include <stdlib.h>
#include <sys/mman.h>
#include <sys/stat.h> 
#include <fcntl.h> 
#include <stdio.h>
#include <unistd.h>
#include <sys/socket.h>
#include <sys/un.h>
#include <stddef.h>
#include <signal.h>
#include <sys/wait.h>

#include "config.h"
#include "check.h"
#include "helper.h"
#include "header.h"
#include "data.h"
#include "init_mutex.h"

#define FUNS_SIZE 25

typedef struct{
  /* Functions of the server */
  lpc_function funs[FUNS_SIZE];
  /* Size of array funs */
  size_t lfuns;
}lpc_server_funs;

/**
 * Create new shared memory
 * @param name
 * @param capacity
 * @return
 */
void *lpc_create(const char *name, size_t capacity);

/**
 * Add an function to server service
 * @param head
 * @param fun_name
 * @param fun
 * @return
 */
int lpc_add_function(lpc_server_funs *funs, char *fun_name, int (*fun)(void *));

/**
 * Launch function from server
 * @param funs
 * @param lfuns
 * @param fun_name
 * @param args
 * @return
 */
int lpc_launch_function(lpc_server_funs *funs, char *fun_name, void *args);
