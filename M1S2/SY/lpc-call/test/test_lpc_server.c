#define TEST_NAME_SHARED_MEMORY "/lpc.shm.test.245"
#define TEST_CAPACITY_SHARED_MEMORY 108

static void check_lpc_create(void **state){
    void *addr = lpc_create(TEST_NAME_SHARED_MEMORY, TEST_CAPACITY_SHARED_MEMORY);
    char *path = concat(SHM_FOLDER, TEST_NAME_SHARED_MEMORY);

    assert_true(addr != NULL);
    assert_true((access(path, F_OK) == 0));
    free(path);
}