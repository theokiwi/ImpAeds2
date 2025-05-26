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

typedef struct Celula{
    Show* elemento;
    struct Celula* prox;
} Celula;

typedef struct ListaDinamica{
    Celula* primeiro;
    Celula* ultimo;
} ListaDinamica;

Celula* novaCelula(Show* elemento) {
    Celula* nova = malloc(sizeof(Celula));
    nova->elemento = elemento;
    nova->prox = NULL;
    return nova;
}

ListaDinamica* novaLista() {
    ListaDinamica* nova = malloc(sizeof(ListaDinamica));
    nova->primeiro = novaCelula(NULL);
    nova->ultimo = nova->primeiro;
    return nova;
}

int tamanhoLista(ListaDinamica* lista) {
    int tam = 0;
    Celula* aux = lista->primeiro->prox;
    while (aux != NULL) {
        tam++;
        aux = aux->prox;
    }
    return tam;
}

void listaInserirInicio(ListaDinamica* lista, Show* elemento) {
    Celula* tmp = novaCelula(elemento);
    tmp->prox = lista->primeiro->prox;
    lista->primeiro->prox = tmp;
    if (lista->primeiro == lista->ultimo) {
        lista->ultimo = tmp;
    }
    tmp = NULL;
}

void listaInserirFim(ListaDinamica* lista, Show* elemento) {
    Celula* nova = novaCelula(elemento);
    lista->ultimo->prox = nova;
    lista->ultimo = nova;
}


void listaInserirPos(ListaDinamica* lista, Show* elemento, int pos) {
    int tamanho = tamanhoLista(lista);
    if (pos < 0 || pos > tamanho) {
        printf("Erro tentando inserir em posição invalida na lista");
        return;
    }else if (pos == 0) {
        listaInserirInicio(lista, elemento);
    }else if (pos == tamanho) {
        listaInserirFim(lista, elemento);
    }else {
        int j = 0;
        Celula* aux = lista->primeiro;
        for (j = 0; j < pos; j++) {
            aux = aux->prox;
        }
        Celula* tmp = novaCelula(elemento);
        tmp->prox = aux->prox;
        aux->prox = tmp;
        tmp = aux = NULL;
    }
}

Show* listaRemoverInicio(ListaDinamica* lista) {
    if (lista->primeiro == lista->ultimo) {
        printf("Erro - Lista vazia no remover\n");
        return NULL;
    }

    Celula* tmp = lista->primeiro->prox;
    Show* resp = tmp->elemento;
    lista->primeiro->prox = tmp->prox;

    if (tmp == lista->ultimo) {
        lista->ultimo = lista->primeiro;
    }

    free(tmp);
    return resp;
}


Show* listaRemoverFim(ListaDinamica* lista) {
    if (lista->primeiro == lista->ultimo) {
        printf("Erro - Lista vazia no remover\n");
        return NULL;
    }

    Celula* i = lista->primeiro;
    while (i->prox != lista->ultimo) {
        i = i->prox;
    }

    Show* resp = lista->ultimo->elemento;
    free(lista->ultimo);
    lista->ultimo = i;
    lista->ultimo->prox = NULL;

    return resp;
}


Show* listaRemoverPos(ListaDinamica* lista, int pos) {
    Show* resp;
    int tam = tamanhoLista(lista);
    if (lista->primeiro == lista->ultimo) {
        printf("Erro - Lista vazia no remover pos");
        return;
    }else if (pos == 0) {
        listaRemoverInicio(lista);
    }else if (pos == tam-1) {
        listaRemoverFim(lista);
    }else {
        Celula* aux = lista->primeiro;
        int j;
        for (j = 0; j < pos; j++) {
            aux = aux->prox;
        }

        Celula* tmp = aux->prox;
        resp = tmp->elemento;
        aux->prox = tmp->prox;
        tmp->prox = NULL;
        free(tmp);
        aux = tmp = NULL;
    }

    return resp;
}


void operaRegistro(ListaDinamica* choosenShows, MyList* showList, char** entrada){
    if(!entrada[0]) return;
    if(strcmp(entrada[0], "II") == 0 && entrada[1]){
        Show* inserir = findByShowId(showList, entrada[1]);
        listaInserirInicio(choosenShows, inserir);
    }else if(strcmp(entrada[0], "IF") == 0 && entrada[1]){
        Show* inserir = findByShowId(showList, entrada[1]);
        listaInserirFim(choosenShows, inserir);
    }else if(strcmp(entrada[0], "I*") == 0 && entrada[1] && entrada[2]){
        int pos = atoi(entrada[1]);
        Show* inserir = findByShowId(showList, entrada[2]);
        listaInserirPos(choosenShows, inserir, pos);
    }else if(strcmp(entrada[0], "RI") == 0){
        Show* removido = listaRemoverInicio(choosenShows);
        if(removido) printf("(R) %s\n", removido->title);
    }else if(strcmp(entrada[0], "RF") == 0){
        Show* removido = listaRemoverFim(choosenShows);
        if(removido) printf("(R) %s\n", removido->title);
    }else if(strcmp(entrada[0], "R*") == 0 && entrada[1]){
        int pos = atoi(entrada[1]);
        Show* removido = listaRemoverPos(choosenShows, pos);
        if(removido) printf("(R) %s\n", removido->title);
    }
}

void freeLista(ListaDinamica* lista) {
    if (!lista) return;
    Celula* atual = lista->primeiro;
    while (atual != NULL) {
        Celula* tmp = atual;
        atual = atual->prox;
        free(tmp);
    }
    free(lista);
}

void printLista(ListaDinamica* lista) {
    if (!lista) return;
    Celula* atual = lista->primeiro->prox;
    while (atual != NULL) {
        printShow(atual->elemento);
        atual = atual->prox;
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
    ListaDinamica* choosenShows = novaLista(2000);
    for(int i = 0; i < idCount; i++) {
        Show* s = findByShowId(showList, idList[i]);
        if (s) {
            listaInserirFim(choosenShows, s);
        } else {
            printf("Show com id %s não encontrado na hora de inserir nos shows com id selecionado.\n", idList[i]);
        }
        free(idList[i]);
    }
    free(idList);
    int operacoes = 0;
    scanf("%d", &operacoes);
    getchar();
    for(int j = 0; j < operacoes; j++){
        char entrada[100];
        if(!fgets(entrada, sizeof(entrada), stdin)) break;
        entrada[strcspn(entrada, "\n")] = 0;
        if(strlen(entrada) == 0) { j--; continue; }
        char** inputs = trataEntrada(entrada);
        operaRegistro(choosenShows, showList, inputs);
        for(int k=0; k<3; k++) free(inputs[k]);
        free(inputs);
    }
    printLista(choosenShows);

    freeLista(choosenShows);
    freeList(showList);
    return 0;
}