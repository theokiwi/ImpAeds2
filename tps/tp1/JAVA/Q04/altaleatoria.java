import java.util.*;

public class altaleatoria {
    //Converte a string em array de char para permitir as operações
    //Converte o array de char em string de volta   
    //Essas conversões estão sendo realizadas para permitir tratar cada char da String individualmente

    public static boolean comparaArray(String array1, String array2){
        for(int i = 0; i < array1.length(); i++){
            if(array1.charAt(i) != array2.charAt(i)){
                return false;
            }
        }
        
        return true;
    }

    public static char[] paraChar(String entrada){
        char[] charArray = new char[entrada.length()];
        for(int i = 0; i < entrada.length(); i++){
            charArray[i] = entrada.charAt(i);
        }
        return charArray;
    }

    public static String paraString(char[] entrada){
        String result = "";
        for (char c : entrada) {
            result += c; 
        }
        return result;
    }


    //Sorteia letra
    public static char sorteiaLetra(){
        Random gerador = new Random();
        gerador.setSeed(4);
        return ((char) + (Math.abs(gerador.nextInt() % 26)));
    }
    //Substitui Letra
    public static String substituiLetras(char[] entrada, char a, char b){
        for(int i = 0; i < entrada.length; i++){
            if(entrada[i] == a){
                entrada[i] = b;
            }
        }

        return paraString(entrada);
    }

    //Metodo para Encapsulamento
    public static String altAleatoria(String entrada){
        char a = sorteiaLetra();
        char b = sorteiaLetra();
        char[] linha = paraChar(entrada);
        return substituiLetras(linha, a, b);
    }

    public static void main(String[] args) {
        boolean stop = false;
        while(!stop){ 
            String entrada = MyIO.readLine();
            if(!comparaArray(entrada, "FIM")){
                MyIO.println(altAleatoria(entrada));
            }
            else{
                stop = true;
            }
        }
    }
    
}
