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

typedef struct FilaCircular{
    int primeiro;
    int ultimo;
    int n;
    int max;
    Show** items;
}FilaCircular;

FilaCircular* initFila(int max){
    FilaCircular* fila = (FilaCircular*)malloc(sizeof(FilaCircular));
    fila->max = max;
    fila->primeiro = 0;
    fila->ultimo = 0;
    fila->n = 0;
    fila->items = malloc(max * sizeof(Show*));
    return fila;
}

void freeFila(FilaCircular* fila){
    if(fila == NULL){
        printf("Erro - Tentando liberar fila inexistente\n");
        return;
    }

    free(fila->items);
    free(fila);
}

void calcularMedia(FilaCircular* fila){
    if(fila == NULL){
        printf("Erro - Fila Inexistente no calcular media");
        return;
    }
    if(fila->primeiro == fila->ultimo){
        printf("Erro - Fila Vazia no calcular media");
        return;
    }
    int total = 0;
    int count = 0; 
    for(int i = fila->primeiro; i != fila->ultimo; i = (i + 1) % fila->max){
        total += fila->items[i]->release_year;
        count++;
    }

    if(count > 0) {
        printf("[Media] %d\n", total / count);
    }
}

Show* removerFila(FilaCircular* fila){
    if(fila == NULL || fila->n == 0){
        printf("Erro - Fila inexistente ou vazia no remover Fila\n");
        return NULL;
    }

    Show* elemento = fila->items[fila->primeiro];
    fila->primeiro = (fila->primeiro + 1) % fila->max;
    fila->n--;
    return elemento;
}

void inserirFila(FilaCircular* fila, Show* elemento){
    if(fila == NULL){
        printf("Erro - Fila inexistente no inserir Fila\n");
        return;
    }
    if((fila->ultimo + 1) % fila->max == fila->primeiro){
        removerFila(fila);
        fila->n--;
    } else {
        fila->n++;
    }
    

    fila->items[fila->ultimo] = elemento;
    fila->ultimo = (fila->ultimo + 1) % fila->max;
    calcularMedia(fila);
}
void operaRegistro(FilaCircular* choosenShows, MyList* showList, char** entrada){
    if(!entrada[0]) return;
    if(strcmp(entrada[0], "I") == 0 && entrada[1]){
        Show* inserir = findByShowId(showList, entrada[1]);
        inserirFila(choosenShows, inserir);
    }else if(strcmp(entrada[0], "R") == 0){
        Show* removido = removerFila(choosenShows);
        printf("(R) %s\n", removido->title);
    }
}

void printaFila(FilaCircular* choosenShows){
    if(choosenShows == NULL){
        printf("Erro - Fila inexistente no printa fila");
        return;
    }
    if(choosenShows->primeiro == choosenShows->ultimo){
        printf("Erro - Fila vazia no printa fila");
        return;
    }
    for(int i = choosenShows->primeiro; i != choosenShows->ultimo; i = (i + 1) % choosenShows->max){
        printf("[i] ");
        printShow(choosenShows->items[i]);
    }
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
    FilaCircular* choosenShows = initFila(6);
    for(int i = 0; i < idCount; i++) {
        Show* s = findByShowId(showList, idList[i]);
        if (s) {
            inserirFila(choosenShows, s);
        } else {
            printf("Show com id %s não encontrado na hora de inserir nos shows com id selecionado.\n", idList[i]);
        }
        free(idList[i]);
    }
    free(idList);
    int operacoes = 0;
    scanf("%d", &operacoes);
    getchar();
    for(int j = 0; j <= operacoes; j++){
        char entrada[100]; 
        if(!fgets(entrada, sizeof(entrada), stdin)) break;
        entrada[strcspn(entrada, "\n")] = 0; 
        if(strlen(entrada) == 0) { j--; continue; }
        char** inputs = trataEntrada(entrada);
        operaRegistro(choosenShows, showList, inputs);
        for(int k=0; k<3; k++) free(inputs[k]);
        free(inputs);
    }

    printaFila(choosenShows);
    freeFila(choosenShows);
    freeList(showList);
    return 0;
}