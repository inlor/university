#pragma once

#define NAME_LENGTH 48

typedef struct{
    char fun_name[NAME_LENGTH];
    int (*fun)(void *);
} lpc_function;
