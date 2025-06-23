import java.util.*;
class Atleta{
    int maxPeso;
    String nome;

    public Atleta(String nome, int maxPeso){
        this.maxPeso = maxPeso;
        this.nome = nome;
    }
}

public class Peso{
    public static void ordenarAlfabetico(Atleta[] atletas){
        for(int i = 0; i < atletas.length; i++){
            int min = i;
            for(int j = i; j < atletas.length; j++){
                if(atletas[min].maxPeso == atletas[j].maxPeso){
                    if(atletas[min].nome.compareTo(atletas[j].nome) > 0){
                        min = j;
                    }
                }else if(atletas[min].maxPeso < atletas[j].maxPeso){
                    min = j;
                }
            }
            Atleta temp = atletas[i];
            atletas[i] = atletas[min];
            atletas[min] = temp;
        }
    }

    public static void printAtleta(Atleta[] atletas){
        for(int i = 0; i < atletas.length; i++){
            System.out.println(atletas[i].nome + " " + atletas[i].maxPeso);
        }
    }

    public static void main(String[] args) {
        Scanner rc = new Scanner (System.in);
        int entradas = rc.nextInt();
        int cont = 0;
        Atleta[] atletas = new Atleta[entradas];
    
        while(cont < entradas){
            //Coleta todos os atletas e coloca em um array.
            String nomeAtual = "";
            nomeAtual = rc.next();
            int pesoAtual = 0;
            pesoAtual = rc.nextInt();
            Atleta atletaAtual = new Atleta(nomeAtual, pesoAtual);
            atletas[cont] = atletaAtual;
            cont++;
        }   

        ordenarAlfabetico(atletas);
        printAtleta(atletas);
        rc.close();
    }
}