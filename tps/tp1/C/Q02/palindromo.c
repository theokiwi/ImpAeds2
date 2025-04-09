#include <stdio.h>
#include <stdbool.h>
#include <string.h>
#include <stdlib.h>

//forma curta e encapsulada de retornar se os char são iguais
bool charIgual(char a, char b){
    return a == b;
}

//compara os chars da array de uma ponta a outra
bool palindromo(char* entrada){
    int tam = strlen(entrada);
    for(int i = 0; entrada[i] != '\0'; i++){
        if(charIgual(entrada[i], entrada[tam - i - 1]) == false){
            return false;
        }
    }
    return true;
}

//um loop simples seguindo os criterios de entrada e
int main(){
    char* entrada;
    bool fim = false;
    while(!fim){
        entrada = malloc(100 * sizeof(char));
        scanf("%1000[^\n]", entrada);
        if(strcmp(entrada, "FIM") == true){
            fim = true;
        }
        else{
            if(palindromo(entrada)){
                printf("SIM\n");
            }
            else{
                printf("NAO\n");
            }
        }
        free(entrada);
    }
}