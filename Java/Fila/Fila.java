public class Fila{
    private int n;
    private int[] array;

    public Fila(int max){
        this.n = 0;
        this.array = new int[max];
    }

    public void inserirInicio(int elem) throws Exception{
        if(n >= array.length){
            throw new Exception("Fila Cheia no II");
        }
        for(int i = array.length; i > 0; i--){
            array[i] = array[i - 1];
        }
        array[0] = elem;
        n++;
    }
    
    public int removerFim() throws Exception{
        if(n == 0){
            throw new Exception("Fila Vazia no RI");

        }
        return array[--n];  
    }

    public void mostrar() throws Exception{
        if(n == 0){
            throw new Exception("Fila Vazia no mostrar");
        }
        System.out.println("[ ");
        for(int i = 0; i < array.length; i++){
            System.out.println(array[i] + ",");
        }
        System.out.println(" ]");

    }

    public Boolean pesquisar(int elem) throws Exception{
        if(n == 0){
            throw new Exception("Fila Vazia no Pesquisar");
        }
        for(int i = 0; i < array.length; i++){
            if(array[i] == elem){
                return true;
            }
        }
        return false;
    }    

}