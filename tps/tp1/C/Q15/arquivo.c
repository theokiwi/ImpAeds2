#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>
#include<string.h>
int main(){
    char path[10] = "file.txt";
    FILE *arquivo;
    arquivo = fopen(path, "wb");

    int quantidade = 0;
    scanf("%d", &quantidade);

    for (int i = 0; i < quantidade; i++) {
        double valor = 0.0;
        scanf("%lf", &valor);
        fwrite(&valor, sizeof(double), 1 , arquivo);
    }

    fclose(arquivo);
    arquivo = NULL;
    arquivo = fopen(path, "rb");

    for (int j = quantidade - 1; j >= 0; j--) {
        fseek(arquivo, j * 8, SEEK_SET);
        double valorAtual = 0.0;
        fread(&valorAtual, sizeof(double), 1, arquivo);
        int inteiro = (int)valorAtual;
        if (inteiro == valorAtual) {
            printf("%d\n", inteiro);  
        } else {
            printf("%g\n", valorAtual);  
        }
    }

    fclose(arquivo);

    return 0;
}
