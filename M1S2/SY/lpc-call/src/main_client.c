#include "main_client.h"

int client() {
    int status = 0;
    char *shm_name = lpc_init_connection(); 

    // LPC implementation
    void *mem = lpc_open(shm_name);
    CHECK((mem == NULL), "lpc_open", errno, QUIT);
    free(shm_name);
    
    int type = 1;
    switch (type){
        case 0:{
            // Generate error not enough memory
            lpc_string *s1 = lpc_make_string("bonjour", 100);
            lpc_string *s2 = lpc_make_string("hello", 0);
            status = lpc_call(mem, "fun_client_4", STRING, s1, STRING, s2, NOP);
            CHECK((status == -1), "lpc_call", ((header *)(mem))->error, STAY);
            free(s1);
            free(s2);
            break;
        }
    
        case 1:{
            // Concat string of two lpc_string succesully
            lpc_string *s1 = lpc_make_string("bonjour", 100);
            lpc_string *s2 = lpc_make_string("hello", 100);
            status = lpc_call(mem, "fun_client_4", STRING, s1, STRING, s2, NOP);
            CHECK((status == -1), "lpc_call", ((header *)(mem))->error, STAY);
            printf("s1: %.*s %i\n",s1->slen, s1->string, s1->slen);
            printf("s2: %.*s %i\n",s2->slen, s2->string, s2->slen);
            free(s1);
            free(s2);
            break;
        }

        case 2:{
            // Get factorial of 8
            int value = 8;
            status = lpc_call(mem, "fun_client_3", INT, &value, NOP);
            CHECK((status == -1), "lpc_call", ((header *)(mem))->error, STAY);
            printf("value %i\n", value);
            break;
        }

        case 3:{
            // Get a*b => c
            int a = 8; int b = 8; int c = 0;
            status = lpc_call(mem, "fun_client_1", INT, &a, INT, &b, INT, &c, NOP);
            CHECK((status == -1), "lpc_call", ((header *)(mem))->error, STAY);
            printf("c %i\n", c);
            break;
        }

        case 4:{
            // Increment a and b by 1 & update string of lcp_string
            int a = 8; double b = 8;
            lpc_string *s1 = lpc_make_string("bonjour", 100);
            status = lpc_call(mem, "fun_client_2", INT, &a, DOUBLE, &b, STRING, s1, NOP);
            CHECK((status == -1), "lpc_call", ((header *)(mem))->error, STAY);
            printf("str: %s %i\n", s1->string, s1->slen);
            printf("a: %i\n", a);
            printf("b: %f\n", b);
            break;
        }

        default:
            break;
    }
 
    status = lpc_close(mem);
    CHECK((status == -1), "lpc_close", ((header *)(mem))->error, QUIT);

    return EXIT_SUCCESS;
}

