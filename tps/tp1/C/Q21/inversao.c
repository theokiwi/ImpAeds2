#include <stdio.h>
#include <stdbool.h>
#include <string.h>
#include <stdlib.h>

typedef char* Char;

Char inverteString(char* String, int i, char* saida){
    int tam = strlen(String);
    
    if(i < tam){
        saida[i] = String[tam - i - 1];
        return inverteString(String, i + 1, saida);
    }

     saida[tam] = '\0'; 
    return saida;
}

int main(){
    bool stop = false;
    char* entrada;
    while(!stop){
        entrada = malloc(sizeof(char) * 100);
        scanf("%99[^\n]", entrada);
        getchar();
        if(strcmp(entrada, "FIM") != 0){
            char* saida;
            saida = malloc(sizeof(char) * 100);
            saida = inverteString(entrada, 0, saida);
            printf("%s\n", saida);
            free(saida);
        }
        else{
            stop = true;
        }
        free(entrada);
    }
}