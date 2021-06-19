#include "fun_client_4.h"

/**
 * Function signature: fun_client(lpc_string *, lpc_string *)
 * Takes the 2 strings and concat the content for both 
 */
int fun_client_4(void *args){
    printf("%s\n", __FILE__);
    sleep(3);
    header *head = (header *) args;
    int *size1 = (int *)(&head->data1.args);
    lpc_string *str1 = (lpc_string *)(&head->data1.args + sizeof(int));
    int *size2 = (int *)(&head->data1.args);
    lpc_string *str2 = (lpc_string *)(&head->data1.args + *size1 + (sizeof(int)*2));

    char *s1 = concat(str1->string,str1->string);
    char *s2 = concat(str2->string,str2->string);
    int result1 = update_string(str1,s1,size1);
    int result2 = update_string(str2,s2,size2);

    free(s1);
    free(s2);
    return (result1 == -1 || result2 == -1) ? -1 : 0;
}

