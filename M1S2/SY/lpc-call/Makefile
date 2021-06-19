CC = gcc
CFLAGS = -Wall -Werror -g -std=c99 -pedantic -D_XOPEN_SOURCE=500

LDFLAGS += -lrt -pthread

TARGET_EXEC ?= main

BUILD_DIR ?= ./build
SRC_DIRS ?= ./src
INC_DIRS ?= ./include

SRCS := $(shell find $(SRC_DIRS)  -name *.c )
OBJS := $(SRCS:%=$(BUILD_DIR)/%.o)
DEPS := $(OBJS:.o=.d)

INC_FLAGS := $(addprefix -I,$(INC_DIRS))

$(BUILD_DIR)/$(TARGET_EXEC): $(OBJS)
	$(CC) $(OBJS) -o $@ $(LDFLAGS)

CFLAGS += -I $(INC_DIRS)

$(BUILD_DIR)/%.c.o: %.c
	$(MKDIR_P) $(dir $@)
	$(CC) $(CFLAGS) -c $< -o $@


.PHONY: clean

clean:
	$(RM) -r $(BUILD_DIR)/*

-include $(DEPS)

MKDIR_P ?= mkdir -p

server:
	./build/$(TARGET_EXEC) s

client:
	./build/$(TARGET_EXEC) c

memcheck:
	valgrind --tool=memcheck --leak-check=yes ./build/main $(id)

# Testing
LIBSTEST= -lm -lcmocka
TEST=test

ifeq ($(shell uname),Linux)
    LIBSTEST += -lrt -pthread
endif

compile-tests:
	make
	$(CC) $(CFLAGS) $(TEST)/$(TEST).c -o $(BUILD_DIR)/$(TEST) $(LIBSTEST) 

run-tests:
	$(BUILD_DIR)/$(TEST)

check:
	make clean compile-tests run-tests

install-cmocka:
	mkdir cmocka; cd cmocka; \
	wget https://cmocka.org/files/1.1/cmocka-1.1.3.tar.xz; \
	tar -xJf cmocka-1.1.3.tar.xz; \
	cmake cmocka-1.1.3; \
	sudo make install; \
	cd ..; rm -rf cmocka

