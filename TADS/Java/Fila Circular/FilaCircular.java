public class FilaCircular {
    private int primeiro;
    private int ultimo;
    private int array[];

    public FilaCircular(int max){
        this.primeiro = 0;
        this.ultimo = 0;
        this.array = new int[max];
    }
    /*
    As diferenças para a fila normal são:
    Na fila circular eu sempre insiro no final e removo no ínicio. Não posso inserir no ínicio e remover no final.
    Para a contagem circular funcionar é importante usar (ultimo + 1) % array.length
     */
    public void inserir(int elem) throws Exception{
        //Verificando se o seguinte do último é o primeiro
        if((ultimo + 1) % array.length == primeiro){
            throw new Exception("Fila Cheia no Inserir");
        }
        array[ultimo] = elem;
        ultimo = (ultimo + 1) % array.length;
    }

    public int remover() throws Exception{
        if(primeiro == ultimo){
            throw new Exception("Fila Vazia no Remover");
        }
        int elem = array[primeiro];
        primeiro = (primeiro + 1) % array.length;
        return elem;
    }

    //No mostrar e pesquisar o avanço também é circular.
    public void mostrar() throws Exception{
        if(primeiro == ultimo){
            throw new Exception("Fila Vazia no Mostrar");
        }
        System.out.println("[ ");
        for(int i = ultimo; i != primeiro; i = (i + 1) % array.length){
            System.out.print(array[i] + ", ");
        }
        System.out.println(" ]");
    }

    public Boolean pesquisar(int elem) throws Exception{
        if(primeiro == ultimo){
            throw new Exception("Fila Vazia no Pesquisar");
        }
        Boolean retorno = false;
        for(int i = primeiro; i != ultimo; i = (i + 1) % array.length){
            if(array[i] == elem){
                retorno = true;
            }
        }
        return retorno;
    }
}
