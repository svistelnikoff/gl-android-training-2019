OBJ_DIR=out

CC=gcc
CFLAGS=-I.
DEPS =

CSRC = ${wildcard *.c}
OBJECTS = ${addprefix ${OBJ_DIR}/, ${patsubst %.c, %.o, ${CSRC}}}

${OBJ_DIR}/%.o: %.c ${DEPS}
	${CC} -c $< ${CFLAGS} -o $@

all: folders ${OBJECTS}
	${CC} ${OBJECTS} -o ${OBJ_DIR}/guess

folders:
	mkdir -p ${OBJ_DIR}

clean: folders
	rm -rf ${OBJ_DIR}/*

.PHONY: clean all folders

