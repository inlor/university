#include "fun_client_2.h"


/**
 * Function signature: fun_client(int *, double *, lpc_string *)
 * Increment by 1 the int & double
 */
int fun_client_2(void *args){
    printf("%s\n", __FILE__);

    header *head = (header *) args;
    int *a = (int *)&head->data1.args;
    double *b = (double *)(&head->data1.args + sizeof(int));
    int *size = (int *)(&head->data1.args + sizeof(int) + sizeof(double));
    lpc_string *str = (lpc_string *)(&head->data1.args + (sizeof(int) * 2) + sizeof(double));
   
    int result = update_string(str, "Update okay!", size);
    (*a)++;
    (*b)++;
    return result;
}

