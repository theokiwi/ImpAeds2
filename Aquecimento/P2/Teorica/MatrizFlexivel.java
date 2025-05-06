class CelulaEncadeada{
    private int elemento;
    private CelulaEncadeada prox;
    
    public CelulaEncadeada(){
        this.elemento = 0;
        this.prox = null;
    }
    
    public CelulaEncadeada(int x){
        this.elemento = x;
        this.prox = null;
    }
    }
    
    class ListaEncadeada{
        private CelulaEncadeada cabeca;
        private CelulaEncadeada ultimo;
    }
    
    
    class Celula{
        private ListaEncadeada lista;
        int elemento;
        public Celula inf, sup, esq, dir;
    
        public Celula(){
            inf = sup = esq = dir = null;
        }
    }
    
    public class MatrizFlexivel{
        private Celula inicio;
        private int linhas;
        private int colunas;
    
        public MatrizFlexivel(int linhas, int colunas){
            this.linhas = linhas;
            this.colunas = colunas;
            this.inicio = gerarLinha();
    
            /*
             * Eu estou gerando duas linhas separadas, a linha de cima começa no inicio
             * Um for correspondente ao numero de linhas - 1 (-1 porque já tem o inicio)
             * Conecta a nova linha criada com a antiga
             * Passa a antiga pra atual 
             */
            Celula linhaAcima = this.inicio;
            for(int i = 1; i < linhas; i++){
                Celula tmp = gerarLinha();
                conectarLinhas(linhaAcima, tmp);
                linhaAcima = tmp;
            }
        }
    
        /*
         * Gera uma linha normalmente, conectando horizontalmente e o limite do for é o de colunas - 1
         */
        public Celula gerarLinha(){
            Celula tmp = new Celula();
            Celula i = tmp;
    
            for(int j = 0; j < colunas - 1; j++){
                Celula nova = new Celula();
                i.dir = nova;
                nova.esq = i;
                i = nova;
            }
    
            return tmp;
        }

        public Celula gerarColuna(){
            Celula tmp = new Celula();
            Celula i = tmp;

            for(int j = 0; j < linhas - 1; j++){
                Celula nova = new Celula();
                i.inf = nova;
                nova.sup = i;
                i = nova;
            }

            return tmp;
        }
        /*
         * Conecta duas linhas verticalmente
         */
        public void conectarLinhas(Celula acima, Celula abaixo){
            Celula c1 = acima;
            Celula c2 = abaixo;
            
            while(c1 != null && c2 != null){
                while(c1.inf != null){
                    c1 = c1.inf;
                }
                c1.inf = c2;
                c2.sup = c1;
                c1 = c1.dir;
                c2 = c2.dir;
            }
        }
    
        public void addColuna(){
            Celula nova = gerarColuna();
            Celula tmp = inicio;

            while(tmp != null && nova != null){
                while(tmp.dir != null){
                    tmp = tmp.dir;
                }

                tmp.dir = nova;
                nova.esq = tmp;
                nova = nova.inf;
                tmp = tmp.inf;
            }

            colunas++;
        }
    
        public void addLinha(){
            Celula nova = gerarLinha();
            Celula tmp = inicio;

            while(tmp != null & nova != null){
                while(tmp.inf != null){
                    tmp = tmp.inf;
                }

                tmp.inf = nova;
                nova.sup = tmp;
                nova = nova.inf;
                tmp = tmp.inf;
            }

            linhas++;
        }

        public void mostrar(){
        for(Celula i = inicio; i != null; i = i.inf){
            for(Celula j = i; j != null; j = j.dir){
                System.out.print(j.elemento + " ");
            }
            System.out.println();
        }
        System.out.println();
        }
        public static void main(String[] args) {
            MatrizFlexivel m = new MatrizFlexivel(3,3);
            m.mostrar();
            m.addColuna();
            m.addLinha();
            m.mostrar();
        }
    }