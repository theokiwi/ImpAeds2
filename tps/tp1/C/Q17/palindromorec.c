#include <stdio.h>
#include <stdbool.h>
#include <string.h>
#include <stdlib.h>

bool charIgual(char a, char b) {
    return a == b;
}

bool palindromo(char* entrada, int i) {
    int tam = strlen(entrada);

    if (entrada[i] != '\0') {
        if (charIgual(entrada[i], entrada[tam - i - 1]) == false) {
            return false;
        }
        return palindromo(entrada, i + 1);
    }
    return true;
}

int main() {
    char* entrada;
    bool fim = false;
    
    while (!fim) {
        entrada = malloc(100 * sizeof(char));
        scanf("%99[^\n]", entrada);
        getchar(); 
        
        if (strcmp(entrada, "FIM") == 0) {
            fim = true;
        } else {
            if (palindromo(entrada, 0)) {
                printf("SIM\n");
            } else {
                printf("NAO\n");
            }
        }
        free(entrada);
    }
    
    return 0;
}
