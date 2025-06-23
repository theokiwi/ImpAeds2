#include <string.h>
#include <stdio.h>
#include <stdlib.h>

typedef struct Atleta{
    int maxPeso;
    char* nome;
} Atleta;

Atleta* initAtleta(int peso, char* Anome){
    Atleta* atleta = malloc(sizeof(Atleta));
    atleta->maxPeso = peso;
    atleta->nome = malloc(sizeof(char) * strlen(Anome) + 1);
    strcpy(atleta->nome, Anome);

    return atleta;
}

void freeAtletas(Atleta** atletas, int tam){
    for(int i = 0; i < tam; i++){
        free(atletas[i]->nome);
        free(atletas[i]);
    }

    free(atletas);
}

void printAtletas(Atleta** atletas, int entradas){
    for(int i = 0; i < entradas; i++){
        printf("%s %d", atletas[i]->nome, atletas[i]->maxPeso);
        printf("\n");
    }
}

//Retornar -0 se for menor Retornar > 0 se for maior
int comparaAtletas(Atleta* a, Atleta* b) {
    if (a->maxPeso > b->maxPeso) return -1;           
    if (a->maxPeso < b->maxPeso) return 1;            
    return strcmp(a->nome, b->nome);                   
}

Atleta** quicksort(Atleta** atletas, int esq, int dir){
    int i = esq;
    int j = dir;
    int pivo = (esq + dir) / 2;

    //Pra adicionar uma condição ou mudar a condição do quicksort
    //É só mudar os dois primeiros whiles por uma funcao de comparacao
    while(i <= j){
        while(comparaAtletas(atletas[i], atletas[pivo]) < 0) { i++; }              
        while(comparaAtletas(atletas[j], atletas[pivo]) > 0) { j--; }              
        if(i <= j){
            Atleta* temp = atletas[i];
            atletas[i] = atletas[j];
            atletas[j] = temp;
            i++;
            j--;
        }
    }
    if(esq < j) quicksort(atletas, esq, j);
    if(i < dir) quicksort(atletas, i, dir);
    return atletas;
}


Atleta** ordenarAtletas(Atleta** atletas, int entradas){
    atletas = quicksort(atletas, 0, entradas - 1);
    return atletas;
}

int main(){
    int entradas = 0;
    int contador = 0;
    scanf("%d", &entradas);
    Atleta** atletas = malloc(sizeof(Atleta*) * entradas);
    
    while(contador < entradas){
        int pesoAtual = 0;
        char* nomeAtual = malloc(sizeof(char) * 20);
        scanf("%s", nomeAtual);
        scanf("%d", &pesoAtual);
        getchar();

        atletas[contador] = initAtleta(pesoAtual, nomeAtual);
        contador++;
    }

    atletas = ordenarAtletas(atletas, entradas);
    printAtletas(atletas, entradas);
    freeAtletas(atletas, entradas);

}