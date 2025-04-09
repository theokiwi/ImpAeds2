#include <stdio.h>
#include <stdbool.h>
#include <string.h>
#include <stdlib.h>
typedef char* Char;

int somaDigitos(Char entrada, int i, int soma){
    int tam = strlen(entrada);
    if(i < tam){
        soma += entrada[i] - '0';
        return somaDigitos(entrada, i + 1, soma);
    }

    return soma;
}

int main(){
    bool stop = false;
    Char entrada;
    while(!stop){
        entrada = malloc(sizeof(char) * 100);
        scanf("%99[^\n]", entrada);
        getchar();
        if(strcmp(entrada, "FIM") != 0){
            int resultado = somaDigitos(entrada, 0, 0);
            printf("%d\n", resultado);
        }
        else{
            stop = true;
        }

        free(entrada);

    }

}