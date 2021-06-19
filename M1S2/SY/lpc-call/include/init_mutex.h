#pragma once

#include <pthread.h>

int init_mutex(pthread_mutex_t *pmutex);
int init_cond(pthread_cond_t *pcond);
