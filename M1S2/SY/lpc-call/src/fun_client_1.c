#include "fun_client_1.h"

/**
 * Function signature: fun_client(int *, int *, int*)
 * Get product of a * b => c
 */
int fun_client_1(void *args){
    printf("%s\n", __FILE__);
    header *head = (header *) args;
    int a = *(int *)(&head->data1.args);
    int b = *(int *)(&head->data1.args + sizeof(int));
    int *c = (int *)(&head->data1.args + (sizeof(int) * 2));

    int d = a * b; 

    memcpy(c, &d, sizeof(int));
    
    return 1;
}

