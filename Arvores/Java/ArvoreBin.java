class No{
    public int elemento;
    public No esq, dir;

    public No(int elemento){
        this.elemento = elemento;
        esq = dir = null;
    }
}

public class ArvoreBin{
    public No raiz;

    public ArvoreBin(){
        raiz = null;
    }

    public void inserir(int elemento) throws Exception{
        raiz = inserirRec(raiz, elemento);
    }

    private No inserirRec(No i, int elemento) throws Exception{
        if(i == null){
            i = new No(elemento);
            System.out.println("Elemento " + i.elemento + " inserido");
        }else if(i.elemento < elemento){
            i.dir = inserirRec(i.dir, elemento);
        }else if(i.elemento > elemento){
            i.esq = inserirRec(i.esq, elemento);
        }else if(i.elemento == elemento){
            throw new Exception("Erro ao inserir - Elemento repetido");
        }

        return i;
    }

    public Boolean pesquisar(int elemento){
       return pesquisarRec(raiz, elemento);
    }

    public Boolean pesquisarRec(No raiz, int elemento){
        Boolean resp = false;
        if(raiz == null){
            resp = false;
        }else if(raiz.elemento < elemento){
            resp = pesquisarRec(raiz.dir, elemento);
        }else if(raiz.elemento > elemento){
            resp = pesquisarRec(raiz.esq, elemento);
        }else if(raiz.elemento == elemento){
            resp = true;
        }

        return resp;
    }

    public void caminharCentral(){
        caminharCentral(raiz);
        System.out.println();
    }

    public void caminharCentral(No raiz){
        if (raiz != null) {
			caminharCentral(raiz.esq); 
			System.out.print(raiz.elemento + " "); 
			caminharCentral(raiz.dir); 
		}
    }

    public void caminharPre(){
        caminharPre(raiz);
        System.out.println();
    }

    public void caminharPre(No raiz){
        if(raiz != null){
             System.out.print(raiz.elemento + " ");   
             caminharPre(raiz.esq);
             caminharPre(raiz.dir);
        }
    }

    public void caminharPos(){
        caminharPos(raiz);
        System.out.println();
    }

    public void caminharPos(No raiz){
        if(raiz != null){
            caminharPos(raiz.esq);
            caminharPos(raiz.dir);
            System.out.print(raiz.elemento + " ");
        }
    }

    public void getAltura(){
        System.out.println(getAltura(raiz, 0));
    }

    public int getAltura(No i, int altura){
        if(i == null){
            altura--;
        }else{
           int alturaEsq = getAltura(i.esq, altura++);
           int alturaDir = getAltura(i.dir, altura++);
           altura = (alturaEsq > alturaDir) ? alturaEsq : alturaDir;
        }
        return altura;

    }
    public static void main(String[] args) {
        ArvoreBin arv = new ArvoreBin();
        try{
            arv.inserir(7);
            arv.inserir(4);
            arv.inserir(1);
            arv.inserir(5);
            arv.inserir(9);
            arv.inserir(13);
            arv.caminharCentral();
            arv.caminharPre();
            arv.caminharPos();
            System.out.println(arv.pesquisar(5));
            System.out.println(arv.pesquisar(6));
            arv.getAltura();
        } catch(Exception e){
            System.out.println(e);
        }
       
    }

}

