#include "lpc_string.h"

lpc_string *lpc_make_string(const char *s, int size){
    lpc_string *str = NULL;
    if(size > 0 && s == NULL){
        str = calloc(1,sizeof(lpc_string) + size);
        CHECK((str == NULL), "malloc", errno, QUIT);
        memset(str->string, '\0', sizeof(size));
        str->slen = size;
        return str;
    }

    if(size <= 0 && s!= NULL){
        str = calloc(1,sizeof(lpc_string) + strlen(s) + 1);
        CHECK((str == NULL), "malloc", errno, QUIT);
        strcpy(str->string, s);
        str->slen = strlen(s) + 1;
        return str;
    }

    if(size >= strlen(s) + 1){
        str = calloc(1,sizeof(lpc_string) + size);
        CHECK((str == NULL), "malloc", errno, QUIT);
        strcpy(str->string, s);
        str->slen = size;
        return str;
    }

    return NULL;
}

int update_string(lpc_string* s,char * s2,int *len){
    if(s->slen < strlen(s2)){
        s->slen=-1;
        *len = -1;
        errno=ENOMEM;
        return -1;
    }
    strcpy(s->string,s2);
    return 0;
}

