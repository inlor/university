static void check_lpc_make_string_null(void **state){
    int size = 3;
    lpc_string *str = lpc_make_string(NULL, size);
    assert_int_equal(str->slen, size);
    assert_string_equal(str->string, "\0");
    free(str);
}

static void check_lpc_make_string_negative_size(void **state){
    char *msg = "My super message";
    lpc_string *str = lpc_make_string(msg, -3);
    assert_int_equal(strlen(msg) + 1, str->slen);
    assert_string_equal(msg, str->string);
    free(str);
}

static void check_lpc_make_string_positive_size(void **state){
    char *msg = "My super message";
    int size = 50;
    lpc_string *str = lpc_make_string(msg, size);
    assert_int_equal(size, str->slen);
    assert_string_equal(msg, str->string);
    free(str);
}