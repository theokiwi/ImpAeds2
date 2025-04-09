typedef struct{
  int n;
  int array[];
} Fila;

void iniciarFila(Fila *fila){
  fila->n = 0;
}