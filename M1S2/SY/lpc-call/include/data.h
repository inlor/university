#pragma once

#include <stdarg.h>
#include <sys/types.h>
#include "lpc_string.h"

#define NAME_LENGTH 48

typedef struct{
    void *args;
    char fun_name[NAME_LENGTH];
} data;

