#include <stdlib.h>
#include <stdio.h>
#include <limits.h>
#include <errno.h>
#include <readline/readline.h>

static const int max_number = 10;
static const int min_number = 1;

void promt( char** out_result );
int parser( char* user_input );

int main(int argc, char *argv[] ) {
    char* result;

    promt( &result );
    parser( result );
    return EXIT_SUCCESS;
}

void promt( char** out_result ) {
    printf("Guess the number from %d to %d, dude !\n", min_number, max_number);
    printf("Type it and press enter or type exit to exit:\n");
    *out_result = readline("> ");
}

int parser( char* user_input ) {
    int user_number;
    char* pend;

    if( strcmp(user_input, "exit" ) == 0 ) {
        printf("Exiting...Bye !\n");
        exit( EXIT_SUCCESS );
    }

    errno = 0;
    user_number = strtol( user_input, &pend, 10 );
    if( *pend != '\0' ) {
        printf( "Invalid input: not number\n" );
        exit( EXIT_FAILURE);
    }
    /* Check for various possible errors */
    if( ( errno == ERANGE && ( user_number == LONG_MAX || user_number == LONG_MIN ) )
        || ( errno != 0 && user_number == 0 ) || ( user_number > max_number ) || ( user_number < min_number ) ) {
        printf( "Invalid input: out of range\n" );
        exit( EXIT_FAILURE);
    }
    printf("Nice try: %s\n", user_input);
    return 0;
}

