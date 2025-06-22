#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <ctype.h>

typedef struct MyDate {
    char* month;
    int day;
    int year;
} MyDate;

MyDate* initDate(const char* month, int day, int year) {
    MyDate* myDate = (MyDate*)malloc(sizeof(MyDate));
    myDate->month = strdup(month);
    myDate->day = day;
    myDate->year = year;
    return myDate;
}

char* showDate(MyDate* myDate) {
    char* buffer;
    asprintf(&buffer, "%s %d, %d", myDate->month, myDate->day, myDate->year);
    return buffer;
}

typedef struct MyQueue {
    int n;
    int max;
    char** items;
} MyQueue;

MyQueue* initQueue(int max) {
    MyQueue* myQueue = (MyQueue*)malloc(sizeof(MyQueue));
    myQueue->max = max;
    myQueue->n = 0;
    myQueue->items = (char**)malloc(max * sizeof(char*));
    return myQueue;
}

void freeQueue(MyQueue* queue) {
    if (!queue) return;
    for (int i = 0; i < queue->n; i++) {
        free(queue->items[i]);
    }
    free(queue->items);
    free(queue);
}

void insertQueue(MyQueue* myQueue, const char* elem) {
    if (myQueue->n >= myQueue->max) return;
    myQueue->items[myQueue->n] = strdup(elem);
    myQueue->n++;
}

int compareCase(const char* a, const char* b) {
    return strcasecmp(a, b);
}

void sortQueue(MyQueue* myQueue) {
    for (int i = 0; i < myQueue->n - 1; i++) {
        for (int j = 0; j < myQueue->n - i - 1; j++) {
            if (compareCase(myQueue->items[j], myQueue->items[j + 1]) > 0) {
                char* temp = myQueue->items[j];
                myQueue->items[j] = myQueue->items[j + 1];
                myQueue->items[j + 1] = temp;
            }
        }
    }
}

char* trim(char* str) {
    while (isspace((unsigned char)*str)) str++;
    if (*str == 0) return str;
    char* end = str + strlen(str) - 1;
    while (end > str && isspace((unsigned char)*end)) end--;
    end[1] = '\0';
    return str;
}

char* showQueue(MyQueue* queue) {
    if (queue->n == 0) return strdup("[]");
    int totalLength = 2; 
    for (int i = 0; i < queue->n; i++) {
        totalLength += strlen(queue->items[i]) + 2; 
    }
    char* result = (char*)malloc(totalLength);
    char* ptr = result;
    *ptr++ = '[';
    for (int i = 0; i < queue->n; i++) {
        if (i > 0) {
            *ptr++ = ',';
            *ptr++ = ' '; 
        }
        strcpy(ptr, queue->items[i]);
        ptr += strlen(queue->items[i]);
    }
    *ptr++ = ']';
    *ptr = '\0';
    return result;
}

typedef struct show {
    char* show_id;
    char* title;
    char* type;
    char* director;
    MyQueue* cast;
    char* country;
    MyDate* date_added;
    int release_year;
    char* rating;
    char* duration;
    MyQueue* listed_in;
} Show;

Show* initShow() {
    Show* myShow = (Show*)malloc(sizeof(Show));
    myShow->show_id = NULL;
    myShow->title = NULL;
    myShow->type = NULL;
    myShow->director = NULL;
    myShow->cast = initQueue(50);
    myShow->country = NULL;
    myShow->date_added = NULL;
    myShow->release_year = 0;
    myShow->rating = NULL;
    myShow->duration = NULL;
    myShow->listed_in = initQueue(50);
    return myShow;
}

void freeShow(Show* show) {
    if (!show) return;
    free(show->show_id);
    free(show->title);
    free(show->type);
    free(show->director);
    freeQueue(show->cast);
    free(show->country);
    if (show->date_added) {
        free(show->date_added->month);
        free(show->date_added);
    }
    free(show->rating);
    free(show->duration);
    freeQueue(show->listed_in);
    free(show);
}

typedef struct MyList {
    int n;
    int max;
    Show** items;
} MyList;

MyList* initList(int max) {
    MyList* myList = (MyList*)malloc(sizeof(MyList));
    myList->max = max;
    myList->n = 0;
    myList->items = (Show**)malloc(max * sizeof(Show*));
    return myList;
}

int listSize(MyList* myList){
    return myList->n;
}

void freeList(MyList* list) {
    for (int i = 0; i < list->n; i++) {
        freeShow(list->items[i]);
    }
    free(list->items);
    free(list);
}

void inserirFim(MyList* myList, Show* elem) {
    if (myList->n >= myList->max) {
        myList->max *= 2;
        myList->items = realloc(myList->items, myList->max * sizeof(Show*));
    }
    myList->items[myList->n++] = elem;
}

void inserirInicio(MyList* myList, Show* elem){
    if(myList->n >= myList->max){
        myList->max *= 2;
        myList->items = realloc(myList->items, myList->max * sizeof(Show*));
    }
    for(int i = myList->n; i > 0; i--){
        myList->items[i] = myList->items[i - 1]; 
    }
    myList->items[0] = elem;
    myList->n++;
}

void inserirPos(MyList* myList, Show* elem, int pos){
    if(myList->n >= myList->max){
        myList->max *= 2;
        myList->items = realloc(myList->items, myList->max * sizeof(Show*));
    }
    for(int i = myList->n; i > pos; i--){
        myList->items[i] = myList->items[i - 1]; 
    }
    myList->items[pos] = elem;
    myList->n++;
}

Show* removerFim(MyList* myList){
    if(myList->n == 0){
        printf("Erro lista vazia no remover fim\n");
        return NULL;
    }
    return myList->items[--myList->n];
}

Show* removerInicio(MyList* myList){
    if(myList->n == 0){
        printf("Erro lista vazia no remover inicio\n");
        return NULL;
    }
    Show* removido = myList->items[0];
    for(int i = 0; i < myList->n - 1; i++){
        myList->items[i] = myList->items[i + 1];
    }
    myList->n--;
    return removido;
}

Show* removerPos(MyList* myList, int pos){
    if(myList->n == 0 || pos < 0 || pos >= myList->n){
        printf("Erro lista vazia ou posicao invalida no remover pos\n");
        return NULL;
    }
    Show* removido = myList->items[pos];
    for(int i = pos; i < myList->n - 1; i++){
        myList->items[i] = myList->items[i + 1];
    }
    myList->n--;
    return removido;
}

Show* findByShowId(MyList* myList, char* showID) {
    for (int i = 0; i < myList->n; i++) {
        if (strcmp(myList->items[i]->show_id, showID) == 0) {
            return myList->items[i];
        }
    }
    return NULL;
}

char** split(const char* ogLine) {
    char** collection = (char**)calloc(12, sizeof(char*));
    int indexes[12] = {0};
    int field = 0;
    bool inQuotes = false;
    for (int i = 0; ogLine[i] && field < 12; i++) {
        if (ogLine[i] == '"') {
            inQuotes = !inQuotes;
            continue;
        }
        if (ogLine[i] == ',' && !inQuotes) {
            field++;
            continue;
        }
        if (!collection[field]) {
            collection[field] = (char*)malloc(256);
            indexes[field] = 0;
        }
        if (indexes[field] < 255) {
            collection[field][indexes[field]++] = ogLine[i];
        }
    }
    for (int i = 0; i < 12; i++) {
        if (collection[i]) {
            collection[i][indexes[i]] = '\0';
        } else {
            collection[i] = strdup("NaN");
        }
    }
    return collection;
}

MyDate *dateTreatment(const char *dateStr) {
    if (strcmp(dateStr, "NaN") == 0)
        return initDate("March", 1, 1900);
    char month[20];
    int day, year;
    if (sscanf(dateStr, "%s %d, %d", month, &day, &year) == 3) {
        return initDate(month, day, year);
    }
    return NULL;
}

MyQueue* treatList(const char* blockLine) {
    MyQueue* queue = initQueue(50);
    if (!blockLine || strcmp(blockLine, "NaN") == 0) {
        insertQueue(queue, "NaN");
        return queue;
    }
    char* copy = strdup(blockLine);
    char* token = strtok(copy, ",");
    while (token) {
        char* trimmed = trim(token);
        insertQueue(queue, trimmed);
        token = strtok(NULL, ",");
    }
    free(copy);
    return queue;
}

Show* readShow(const char* ogLine) {
    char** collection = split(ogLine);
    Show* show = initShow();
    show->show_id = strdup(collection[0]);
    show->type    = strdup(collection[1]);
    show->title   = strdup(collection[2]);
    show->director = strdup(collection[3]);
    freeQueue(show->cast);
    show->cast = treatList(collection[4]);
    sortQueue(show->cast);
    show->country = strdup(collection[5]);
    show->date_added = dateTreatment(collection[6]);
    show->release_year = atoi(collection[7]);
    show->rating = strdup(collection[8]);
    show->duration = strdup(collection[9]);
    freeQueue(show->listed_in);
    show->listed_in = treatList(collection[10]);
    for (int i = 0; i < 12; i++) free(collection[i]);
    free(collection);
    return show;
}

static const char* safe(const char* s) { 
    return s ? s : "NaN"; 
}

void printShow(Show* show) {
    char* cast = showQueue(show->cast);
    char* listed = showQueue(show->listed_in);
    char* date = show->date_added ? showDate(show->date_added) : strdup("NaN");
    printf("=> %s ## %s ## %s ## %s ## %s ## %s ## %s ## %d ## %s ## %s ## %s ##\n",
        safe(show->show_id),
        safe(show->title),    
        safe(show->type),
        safe(show->director),
        cast,
        safe(show->country),
        date,
        show->release_year,
        safe(show->rating),
        safe(show->duration),
        listed);
    free(cast);
    free(listed);
    free(date);
}

char** trataEntrada(char* entrada) {
    char* token;
    char** tokens = malloc(3 * sizeof(char*));
    int i = 0;
    token = strtok(entrada, " ");
    while(token != NULL && i < 3) {
        tokens[i] = malloc(strlen(token)+1); 
        strcpy(tokens[i], token);       
        i++;
        token = strtok(NULL, " ");
    }
    while(i < 3) tokens[i++] = NULL;
    return tokens;
}


typedef struct No{
    char** elemento;
    int nivel;
    struct No *esq;
    struct No *dir;
}No;

No* initNo(char** elemento) {
    No* novo = (No*)malloc(sizeof(No));
    novo->elemento = malloc(sizeof(char*));
    *novo->elemento = strdup(*elemento);  
    novo->nivel = 1;
    novo->esq = novo->dir = NULL;
    return novo;
}

typedef struct AVL{
    No* raiz;
}AVL;

AVL* initAvl(){
    AVL* nova = malloc(sizeof(AVL));
    nova->raiz = NULL;
    return nova;
}

int getNivel(No* no) {
    return (no == NULL) ? 0 : no->nivel;
}

No* rotacionaEsquerda(No* no){
    No* noDir = no->dir;
    No* noDirEsq = (noDir != NULL) ? noDir->esq : NULL;

    noDir->esq = no;
    no->dir = noDirEsq;

    no->nivel = (getNivel(no->esq) > getNivel(no->dir) ? getNivel(no->esq) : getNivel(no->dir)) + 1;
    noDir->nivel = (getNivel(noDir->esq) > getNivel(noDir->dir) ? getNivel(noDir->esq) : getNivel(noDir->dir)) + 1;

    return noDir;
}

No* rotacionaDireita(No* no){
    No* noEsq = no->esq;
    No* noEsqDir = (noEsq != NULL) ? noEsq->dir : NULL;

    noEsq->dir = no;
    no->esq = noEsqDir;

    no->nivel = (getNivel(no->esq) > getNivel(no->dir) ? getNivel(no->esq) : getNivel(no->dir)) + 1;
    noEsq->nivel = (getNivel(noEsq->esq) > getNivel(noEsq->dir) ? getNivel(noEsq->esq) : getNivel(noEsq->dir)) + 1;

    return noEsq;
}

No* balanceamento(No* no){
    int fatorBalanceamento = getNivel(no->dir) - getNivel(no->esq);

    if (abs(fatorBalanceamento) <= 1) {
        no->nivel = (getNivel(no->esq) > getNivel(no->dir) ? getNivel(no->esq) : getNivel(no->dir)) + 1;
    } else if (fatorBalanceamento == 2) {
        int fatorFilho = getNivel(no->dir ? no->dir->dir : NULL) - getNivel(no->dir ? no->dir->esq : NULL);
        if (fatorFilho < 0) {
            no->dir = rotacionaDireita(no->dir);
        }
        no = rotacionaEsquerda(no);
    } else if (fatorBalanceamento == -2) {
        int fatorFilho = getNivel(no->esq ? no->esq->dir : NULL) - getNivel(no->esq ? no->esq->esq : NULL);
        if (fatorFilho > 0) {
            no->esq = rotacionaEsquerda(no->esq);
        }
        no = rotacionaDireita(no);
    } else {
        printf("Deu erro no balanceamento\n");
    }

    return no;
}



No* inserirArvore(No* raiz, char** elemento){
    if(raiz == NULL){
        raiz = initNo(elemento);
    }else if(strcmp(*(raiz->elemento), *(elemento)) > 0){
        raiz->esq = inserirArvore(raiz->esq, elemento);
    }else if(strcmp(*(raiz->elemento), *(elemento)) < 0){
        raiz->dir = inserirArvore(raiz->dir, elemento);
    }

    return balanceamento(raiz);
    //era pra ter uma condição dando erro se ja tive inserido
    //na arvore mas eu quero que ele so ignore mesmo;
}

No* pesquisarArvore(No* raiz, char** elemento) {
    if (raiz == NULL) {
        printf(" NAO\n");
    } else if (strcmp(*(raiz->elemento), *elemento) > 0) {
        printf(" esq");
        pesquisarArvore(raiz->esq, elemento);
    } else if (strcmp(*(raiz->elemento), *elemento) < 0) {
        printf(" dir");
        pesquisarArvore(raiz->dir, elemento);
    } else {
        printf(" SIM\n");
    }
    return raiz;
}

void freeArvore(No* raiz) {
    if (raiz == NULL) return;

    freeArvore(raiz->esq);
    freeArvore(raiz->dir);

    free(*(raiz->elemento));
    free(raiz->elemento);
    free(raiz);
}

typedef struct ListaTitulos {
    int n;
    int max;
    char** titulos;
} ListaTitulos;

ListaTitulos* initListaTitulos(int max) {
    ListaTitulos* lista = malloc(sizeof(ListaTitulos));
    lista->n = 0;
    lista->max = max;
    lista->titulos = malloc(max * sizeof(char*));
    return lista;
}

void inserirTitulo(ListaTitulos* lista, const char* titulo) {
    if (lista->n >= lista->max) {
        lista->max *= 2;
        lista->titulos = realloc(lista->titulos, lista->max * sizeof(char*));
    }
    lista->titulos[lista->n++] = strdup(titulo);
}

void freeListaTitulos(ListaTitulos* lista) {
    for (int i = 0; i < lista->n; i++) {
        free(lista->titulos[i]);
    }
    free(lista->titulos);
    free(lista);
}


int main() {
    MyList* showList = initList(1000);
    FILE* file = fopen("/tmp/disneyplus.csv", "r");
    if(file){
        char line[2000];
        fgets(line, sizeof(line), file);
        while (fgets(line, sizeof(line), file)) {
            line[strcspn(line, "\n")] = '\0'; 
            inserirFim(showList, readShow(line));
        }
        fclose(file);
    } else {
        fprintf(stderr, "Erro ao abrir o arquivo CSV.\n");
        return 1;
    }
    char** idList = (char**)malloc(1000 * sizeof(char*));
    int idCount = 0;
    char id[100];
    while(scanf("%99s", id) == 1 && strcmp(id, "FIM") != 0) {
        idList[idCount++] = strdup(id);
    }
    MyList* choosenShows = initList(2000);
    for(int i = 0; i < idCount; i++) {
        Show* s = findByShowId(showList, idList[i]);
        if (s) {
            inserirFim(choosenShows, s);
        } else {
            printf("Show com id %s não encontrado na hora de inserir nos shows com id selecionado.\n", idList[i]);
        }
        free(idList[i]);
    }
    free(idList);


    ListaTitulos* listaTitulos = initListaTitulos(100);
    char tituloLinha[300];

    while (fgets(tituloLinha, sizeof(tituloLinha), stdin)) {
        tituloLinha[strcspn(tituloLinha, "\n")] = '\0';
        if (strlen(tituloLinha) == 0) continue; 
        inserirTitulo(listaTitulos, tituloLinha);
    }

    AVL* arvore = initAvl();

    for (int i = 0; i < idCount; i++) {
        char** ref = malloc(sizeof(char*));
        *ref = strdup(choosenShows->items[i]->title);
        arvore->raiz = inserirArvore(arvore->raiz, ref);
    }

    for (int i = 0; i < listaTitulos->n; i++) {
        char** ref = malloc(sizeof(char*));
        *ref = strdup(listaTitulos->titulos[i]);
        printf("raiz");
        pesquisarArvore(arvore->raiz, ref);
        free(*ref);
        free(ref);
    }


    freeArvore(arvore->raiz);
    free(arvore);

    free(choosenShows->items);
    free(choosenShows);
    freeList(showList);
    return 0;
}
