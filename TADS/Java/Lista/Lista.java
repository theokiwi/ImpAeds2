public class Lista{
    private int n;
    private int array[];

    public Lista(int max){
        this.n = 0;
        array = new int[max];
    }

    public void inserirInicio(int elem) throws Exception{
        if(n >= array.length){
            throw new Exception("Fila Cheia no II");
        }
        for(int i = n; i > 0; i--){
            array[i] = array[i-1];
        }
        array[0] = elem;
        n++;
    }

    public void inserirFim(int elem) throws Exception{
        if(n >= array.length){
            throw new Exception("Fila Cheia no IF");
        }
        array[n++] = elem;
    }

    public void inserir(int elem, int pos) throws Exception{
        if(n >= array.length){
            throw new Exception("Fila Cheia no I");
        }
        for(int i = array.length; i > pos; i--){
            array[i] = array[i - 1];
        }
        array[pos] = elem;
        n++;
    }

    public int removerInicio() throws Exception{
        if(n == 0){
            throw new Exception("Fila Vazia no RI");
        }
        int elem = array[0];
        n--;
        /*
            É preciso fazer a redução de n antes senão ele vai tentar fazer o i + 1 da posição N
            e não tem nada seguinte da posição N
        */
        for(int i = 0; i < n; i++){
            array[i] = array[i + 1];
        }
        return elem;
    }

    public int removerFim() throws Exception{
        if(n == 0){
            throw new Exception("Fila Vazia no RF");
        }
        return array[--n];
    }

    public int remover(int pos) throws Exception{
        if(n == 0 || pos < 0  || pos > n){
            throw new Exception("Erro no Remover em pos: " + pos);
        }
        int elem = array[pos];
        n--;
        for(int i = pos; i < n; i++){
            array[i] = array[i + 1];
        }
        return elem;
    }

    public void mostrar() throws Exception{
        if(n == 0){
            throw new Exception("Fila Vazia no Mostrar");
        }
        System.out.println("[ ");
        for(int i = 0; i < array.length; i++){
            System.out.println(array[i] + ", ");
        }
        System.out.println("] ");
    }

    public Boolean pesquisar(int elem) throws Exception{
        if(n == 0){
            throw new Exception("Fila Vazia no Pesquisar");
        }
        Boolean retorno = false;
        for(int i = 0; i < array.length; i++){
            if(array[i] == elem){
                retorno = true;
            }
        }

        return retorno;
    }
}