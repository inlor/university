#pragma once

#include <stdlib.h>
#include <sys/mman.h>
#include <fcntl.h>
#include <unistd.h>
#include <sys/stat.h>
#include <sys/socket.h>
#include <sys/un.h>
#include <stddef.h>
#include <signal.h>

#include "config.h"
#include "check.h"
#include "header.h"
#include "helper.h"
#include "data.h"
#include "lpc_string.h"

/**
 * Init new shared memory name
 * @return
 */
char *lpc_init_connection();

/**
 * Open shared memory
 * @param name
 * @return
 */
void *lpc_open(const char *name);

/**
 * Close shared memory
 * @param mem
 * @return
 */
int lpc_close(void *mem);

/**
 * Call a function from the server
 * @param memory
 * @param fun_name
 * @param ...
 * @return
 */
int lpc_call(void *memory, const char *fun_name, ...);