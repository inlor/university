#include <stdarg.h>
#include <stddef.h>
#include <setjmp.h>
#include <string.h>
#include <cmocka.h>

#include "headers.h"

int main(void) {
    const struct CMUnitTest tests[] = {
            /* lpc_make_string */
            cmocka_unit_test(check_lpc_make_string_null),
            cmocka_unit_test(check_lpc_make_string_negative_size),
            cmocka_unit_test(check_lpc_make_string_positive_size),
            /* server */
            cmocka_unit_test(check_lpc_create),
            /* client */
    };
    return cmocka_run_group_tests(tests, NULL, NULL);
}
