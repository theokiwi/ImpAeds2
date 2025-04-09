import java.util.*;
public class anagrama {
    public static boolean comparaArray(String array1, String array2) {
        if (array1.length() != array2.length()) {
            return false;
        }
        for (int i = 0; i < array1.length(); i++) {
            if (array1.charAt(i) != array2.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public static String removeEspacos(String entrada){
        String saida = "";
        for (int i = 0; i < entrada.length(); i++) {
            if(entrada.charAt(i) != ' '){
                saida += entrada.charAt(i);
            }
        }

        return saida;
    }
    
    //Escuta-Custa
    public static String[] separaPalavra(String entrada){
        entrada = removeEspacos(entrada);
        
        String palavra1 = "";
        String palavra2 = "";
        boolean troca = false;
        for(int i = 0; i < entrada.length(); i++){
            if(entrada.charAt(i) != '-' && !troca){
                palavra1 += entrada.charAt(i);
            }
            else if(entrada.charAt(i) == '-'){
                troca = true;
            }
            else{
                palavra2 += entrada.charAt(i);
            }
        }
        

        String[] arrayStrings = new String[2];
        arrayStrings[0] = palavra1;
        arrayStrings[1] = palavra2;

        return arrayStrings;
    }

    public static int maiorPalavra(String[] entradas){
        if(entradas[0].length() > entradas[1].length()){
            return 0;
        }
        else{
            return 1;
        }
    }

    public static boolean pesquisaSequencial(String entrada, char c){
        for (int i = 0; i < entrada.length(); i++) {
            if(entrada.charAt(i) == c){
                return true;
            }
        }

        return false;
    }

    public static String paraMinusculo(String entrada) {
    String saida = ""; 
    for (int i = 0; i < entrada.length(); i++) {
        char c = entrada.charAt(i); 
        if (c >= 'A' && c <= 'Z') {
            c = (char) (c + 32);
        }
        saida += c; 
    }
    return saida; 
    }

    public static boolean ehAnagrama(String[] entradas){
     entradas[0] = paraMinusculo(entradas[0]);
     entradas[1] = paraMinusculo(entradas[1]);

        if(maiorPalavra(entradas) == 0){
            for(int i = 0; i < entradas[0].length(); i++){
                if(pesquisaSequencial(entradas[1], entradas[0].charAt(i)) == false){
                    return false;
                }
            }
        }
        else{
            for(int i = 0; i < entradas[1].length(); i++){
                if(pesquisaSequencial(entradas[0], entradas[1].charAt(i)) == false){
                    return false;
                }
            }
        }

        return true;
    }

    public static void main(String[] args) {
        boolean stop = false;
        Scanner rc = new Scanner(System.in);
        while(!stop){
            String entrada = rc.nextLine();
            if(!(comparaArray(entrada, "FIM"))){
                String[] entradas = new String[2];
                entradas = separaPalavra(entrada);
                if(ehAnagrama(entradas) == true){
                    System.out.println("SIM");
                }
                else{
                    MyIO.println("NÃO");
                }
            }
            else{
                stop = true;
            }
        }
    }
}
