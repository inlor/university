#pragma once

#include <stdio.h>
#include <stdlib.h>
#include <sys/errno.h>
#include <string.h>
#include <unistd.h>

#define DEFAULT "\033[0m"
#define RED "\033[1;31m"
#define GREEN "\033[1;32m"

#define STAY 0
#define QUIT 1

/**
 * Check if there is an error
 */
#define CHECK(test, str, code, action) do{ \
    if(!test) break; \
    printf("%s%d %s: ", RED, (int) getpid(), str); \
    printf("#%i %s%s\n", code, strerror(code), DEFAULT); \
    printf("%s%s:%d%s\n", GREEN,  __FILE__, __LINE__, DEFAULT); \
    if(test && action == QUIT) exit(code > 0 ? code : EXIT_FAILURE); \
} while(0);
