#include "fun_client_3.h"

/**
 * Function signature: fun_client(int *)
 * Get the factorial of n
 */
int fun_client_3(void *args){
    printf("%s\n", __FILE__);
    header *head = (header *) args;
    int *value = (int *)(&head->data1.args);

    if(*value > 16) {
        errno = EDOM;
        return -1;
    }

    int result = 1;
    for (size_t i = 1; i < *value; i++)
        result *= i;

    memcpy(value, &result, sizeof(int));
    
    return 1;
}

