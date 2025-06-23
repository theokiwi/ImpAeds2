#include "string.h"
#include "stdio.h"
int hash(char* string, int contadorTestes){
    int tam = strlen(string);
    int soma = 0;
    for(int i = 0; i < tam; i++){
        soma += string[i] - 'A';
        soma += i;
        soma += contadorTestes;
    }

    return soma;
}

int main(){
    int casos = 0;
    scanf("%d", &casos);
    int contador = 0;

    while(contador < casos){
        int testes = 0;
        scanf("%d", &testes);
        int contadorTestes = 0;
        int resultadoHash = 0;
        while(contadorTestes < testes){
            char string[51];
            scanf("%s", string);
            resultadoHash += hash(string, contadorTestes);
            contadorTestes++;
        }

        printf("%d\n", resultadoHash);
        resultadoHash = 0;
        contador++;
    }
}