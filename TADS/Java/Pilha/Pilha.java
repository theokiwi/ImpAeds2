public class Pilha{
    private int topo;
    private int array[];

    public Pilha(int max){
        topo = -1;
        array = new int[max];
    }

   /*
   Nesse exemplo vamos usar IF e RF, que é o mais eficiente,
   No entanto também funcionaria II e RI mas seria muito custoso.
   */
    public void inserir(int elem) throws Exception{
        if(topo >= array.length){
            throw new Exception("Pilha Cheia no I");
        }
        array[++topo] = elem;
        /*
          No inserir o ++ vem antes do topo porque é necessário primeiro aumentar o topo em 1
          para alcançar a nova posição e depois colocar o elemento
        */
    }

    public int remover() throws Exception{
        if(topo < 0){
            throw new Exception("Pilha Vazia no R");
        }
        return array[topo--];
        /*
            Nesse caso o -- vem depois do topo porque eu preciso primeiro retornar o topo atual
            e depois decrementar o topo para sumir com o valor anterior.
        */
    }

    public void mostrar() throws Exception{
        if(topo < 0){
            throw new Exception("Pilha Vazia no Mostrar");
        }
        System.out.println("[ ");
        for(int i = topo; i >= 0; i--){
            System.out.println(array[i] + ", ");
        }
        System.out.println(" ]");
    }

    //Lembre que só pode ter um return por função para o código se manter legível
    public Boolean pesquisar(int elem) throws Exception{
        if(topo < 0){
            throw new Exception("Pilha Vazia no Pesquisar");
        }
        Boolean retorno = false;
        for(int i = topo; i >= 0; i--){
            if(array[i] == elem){
                retorno = true;
            }
        }
        return retorno;
    }
}