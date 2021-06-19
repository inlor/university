#pragma once

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "check.h"

typedef enum {STRING, DOUBLE, INT, NOP} lpc_type;

typedef struct {
    int slen;
    char string[];
} lpc_string;

/**
 * Create new lpc_string
 * @param s - string
 * @param size - size of the string
 * @return lpc_string
 */
lpc_string *lpc_make_string(const char *s, int size);

/**
 * Update string of lpc_string
 * @param s lpc_string
 * @param s2 new string
 * @param len pointer for the new len
 * @return lpc_string
 */
int update_string(lpc_string* s,char * s2,int *len);
