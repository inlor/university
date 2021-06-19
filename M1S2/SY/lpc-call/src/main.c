#include "main_server.h"
#include "main_client.h"

int main(int argc, const char **argv) {
    CHECK((argc < 2), "One argument is required!", errno, QUIT);
    switch (argv[1][0]) {
        case 's': return server();
        case 'c': return client();
        default:
            printf("Unknown argument!\n");
            return EXIT_FAILURE;
    }
}