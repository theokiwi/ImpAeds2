#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <ctype.h>
// Estrutura de Data
typedef struct MyDate
{
    char *month;
    int day;
    int year;
} MyDate;

MyDate *initDate(const char *month, int day, int year);
char *showDate(MyDate *myDate);

// Estrutura de Fila
typedef struct MyQueue
{
    int n;
    int max;
    char **items;
} MyQueue;

MyQueue *initQueue(int max);
void insertQueue(MyQueue *myQueue, const char *elem);
void sortQueue(MyQueue *myQueue);
char *showQueue(MyQueue *queue);

// Estrutura de Show
typedef struct show
{
    char *show_id;
    char *title;
    char *type;
    char *director;
    MyQueue *cast;
    char *country;
    MyDate *date_added;
    int release_year;
    char *rating;
    char *duration;
    MyQueue *listed_in;
} Show;

Show *initShow();
void freeShow(Show *show);

// Estrutura de Lista
typedef struct MyList
{
    int n;
    int max;
    Show **items;
} MyList;

MyList *initList(int max);
void freeList(MyList *list);
void insertList(MyList *myList, Show *elem);
Show *findByShowId(MyList *myList, const char *showID);

// Implementação das funções

MyDate *initDate(const char *month, int day, int year)
{
    MyDate *myDate = (MyDate *)malloc(sizeof(MyDate));
    myDate->month = strdup(month);
    myDate->day = day;
    myDate->year = year;
    return myDate;
}

char *showDate(MyDate *myDate)
{
    char *buffer;
    asprintf(&buffer, "%s %d, %d", myDate->month, myDate->day, myDate->year);
    return buffer;
}

MyQueue *initQueue(int max)
{
    MyQueue *myQueue = (MyQueue *)malloc(sizeof(MyQueue));
    myQueue->max = max;
    myQueue->n = 0;
    myQueue->items = (char **)malloc(max * sizeof(char *));
    return myQueue;
}

void freeQueue(MyQueue *queue)
{
    for (int i = 0; i < queue->n; i++)
    {
        free(queue->items[i]);
    }
    free(queue->items);
    free(queue);
}

void insertQueue(MyQueue *myQueue, const char *elem)
{
    if (myQueue->n >= myQueue->max)
        return;
    myQueue->items[myQueue->n] = strdup(elem);
    myQueue->n++;
}

int compareCase(const char *a, const char *b)
{
    return strcasecmp(a, b);
}

void sortQueue(MyQueue *myQueue)
{
    for (int i = 0; i < myQueue->n - 1; i++)
    {
        for (int j = 0; j < myQueue->n - i - 1; j++)
        {
            if (compareCase(myQueue->items[j], myQueue->items[j + 1]) > 0)
            {
                char *temp = myQueue->items[j];
                myQueue->items[j] = myQueue->items[j + 1];
                myQueue->items[j + 1] = temp;
            }
        }
    }
}
char *trim(char *str)
{
    while (isspace((unsigned char)*str))
        str++;
    if (*str == 0)
        return str;

    char *end = str + strlen(str) - 1;
    while (end > str && isspace((unsigned char)*end))
        end--;
    end[1] = '\0';
    return str;
}

char *showQueue(MyQueue *queue)
{
    if (queue->n == 0)
        return strdup("[]");

    int totalLength = 2;
    for (int i = 0; i < queue->n; i++)
    {
        totalLength += strlen(queue->items[i]) + 2;
    }

    char *result = (char *)malloc(totalLength);
    char *ptr = result;
    *ptr++ = '[';
    for (int i = 0; i < queue->n; i++)
    {
        if (i > 0)
        {
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

Show *initShow()
{
    Show *myShow = (Show *)malloc(sizeof(Show));
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

void freeShow(Show *show)
{
    if (!show)
        return;

    free(show->show_id);
    free(show->title);
    free(show->type);
    free(show->director);
    freeQueue(show->cast);
    free(show->country);
    if (show->date_added)
    {
        free(show->date_added->month);
        free(show->date_added);
    }
    free(show->rating);
    free(show->duration);
    freeQueue(show->listed_in);
    free(show);
}

MyList *initList(int max)
{
    MyList *myList = (MyList *)malloc(sizeof(MyList));
    myList->max = max;
    myList->n = 0;
    myList->items = (Show **)malloc(max * sizeof(Show *));
    return myList;
}

int listSize(MyList *myList)
{
    return myList->n;
}

void freeList(MyList *list)
{
    for (int i = 0; i < list->n; i++)
    {
        freeShow(list->items[i]);
    }
    free(list->items);
    free(list);
}

void insertList(MyList *myList, Show *elem)
{
    if (myList->n >= myList->max)
    {
        myList->max *= 2;
        myList->items = realloc(myList->items, myList->max * sizeof(Show *));
    }
    myList->items[myList->n++] = elem;
}

Show *findByShowId(MyList *myList, const char *showID)
{
    for (int i = 0; i < myList->n; i++)
    {
        if (strcmp(myList->items[i]->show_id, showID) == 0)
        {
            return myList->items[i];
        }
    }
    return NULL;
}

char **split(const char *ogLine)
{
    char **collection = (char **)calloc(12, sizeof(char *));
    int indexes[12] = {0};
    int field = 0;
    bool inQuotes = false;

    for (int i = 0; ogLine[i] && field < 12; i++)
    {
        if (ogLine[i] == '"')
        {
            inQuotes = !inQuotes;
            continue;
        }

        if (ogLine[i] == ',' && !inQuotes)
        {
            field++;
            continue;
        }

        if (!collection[field])
        {
            collection[field] = (char *)malloc(256);
            indexes[field] = 0;
        }

        if (indexes[field] < 255)
        {
            collection[field][indexes[field]++] = ogLine[i];
        }
    }

    for (int i = 0; i < 12; i++)
    {
        if (collection[i])
        {
            collection[i][indexes[i]] = '\0';
        }
        else
        {
            collection[i] = strdup("NaN");
        }
    }
    return collection;
}

MyDate *dateTreatment(const char *dateStr)
{
    if (strcmp(dateStr, "NaN") == 0)
        return NULL;

    char month[20];
    int day, year;
    if (sscanf(dateStr, "%s %d, %d", month, &day, &year) == 3)
    {
        return initDate(month, day, year);
    }
    return NULL;
}

MyQueue *treatList(const char *blockLine)
{
    MyQueue *queue = initQueue(50);
    if (!blockLine || strcmp(blockLine, "NaN") == 0)
    {
        insertQueue(queue, "NaN");
        return queue;
    }

    char *copy = strdup(blockLine);
    char *token = strtok(copy, ",");
    while (token)
    {
        char *trimmed = trim(token); // Aplica trim no token
        insertQueue(queue, trimmed);
        token = strtok(NULL, ",");
    }
    free(copy);
    return queue;
}

Show *readShow(const char *ogLine)
{
    char **collection = split(ogLine);

    Show *show = initShow();
    show->show_id = strdup(collection[0]);
    show->type = strdup(collection[1]);  // Corrigido: agora type vem da coluna 2
    show->title = strdup(collection[2]); // Corrigido: title vem da coluna 3
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

    for (int i = 0; i < 12; i++)
        free(collection[i]);
    free(collection);

    return show;
}

static const char *safe(const char *s)
{
    return s ? s : "NaN";
}

void printShow(Show *show)
{
    char *cast = showQueue(show->cast);
    char *listed = showQueue(show->listed_in);
    char *date = show->date_added ? showDate(show->date_added) : strdup("NaN");

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

MyList *insercaoPorCor(MyList *choosenList, int cor, int h)
{
    for (int i = (h + cor); i < choosenList->n; i += h)
    {
        Show *tmp = choosenList->items[i];
        int j = i - h;
        while (j >= 0 && (strcmp(choosenList->items[j]->type, tmp->type) > 0 ||
                          (strcmp(choosenList->items[j]->type, tmp->type) == 0 &&
                           strcmp(choosenList->items[j]->title, tmp->title) > 0)))
        {
            choosenList->items[j + h] = choosenList->items[j];
            j -= h;
        }

        choosenList->items[j + h] = tmp;
    }

    return choosenList;
}

MyList *shellsort(MyList *choosenList)
{
    int h = 1;
    do
    {
        h = (h * 3) + 1;
    } while (h < choosenList->n);

    do
    {
        h /= 3;
        for (int cor = 0; cor < h; cor++)
        {
            insercaoPorCor(choosenList, cor, h);
        }
    } while (h != 1);

    return choosenList;
}

int main()
{
    MyList *showList = initList(1000);
    FILE *file = fopen("/tmp/disneyplus.csv", "r");
    // Insere todos os shows no arquivo
    if (file)
    {
        char line[2000];
        fgets(line, sizeof(line), file);
        while (fgets(line, sizeof(line), file))
        {
            line[strcspn(line, "\n")] = '\0';
            insertList(showList, readShow(line));
        }
        fclose(file);
    }
    else
    {
        fprintf(stderr, "Erro ao abrir o arquivo CSV.\n");
        return 1;
    }

    // Recebe todos os ids
    char **idList = (char **)malloc(1000 * sizeof(char *));
    int idCount = 0;
    char id[100];
    while (scanf("%99s", id) == 1 && strcmp(id, "FIM") != 0)
    {
        idList[idCount++] = strdup(id);
    }

    // Cria uma lista só com os shows selecionados
    MyList *choosenShows = initList(1000);
    for (int i = 0; i < idCount; i++)
    {
        Show *s = findByShowId(showList, idList[i]);
        if (s)
        {
            insertList(choosenShows, s);
        }
        else
        {
            printf("Show com id %s não encontrado na hora de inserir nos shows com id selecionado.\n", idList[i]);
        }
        free(idList[i]);
    }
    free(idList);

    // Ordena a lista de titles em ordem alfabetica
    choosenShows = shellsort(choosenShows);

    // Faz a pesquisa binaria de titles na lista de items com o id escolhido
    for (int f = 0; f < choosenShows->n; f++)
    {
        printShow(choosenShows->items[f]);
    }

    // Liberar titleList
    free(choosenShows->items);
    free(choosenShows);
    freeList(showList);

    return 0;
}
