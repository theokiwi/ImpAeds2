import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class palindromorec {
    public static boolean comparaArray(String array1, String array2){
        for(int i = 0; i < array1.length(); i++){
            if(array1.charAt(i) != array2.charAt(i)){
                return false;
            }
        }
        
        return true;
    }

    public static boolean isPalindromo(String palavra, int i){
        int tamPalavra = palavra.length();

        if(i < tamPalavra){
            if(palavra.charAt(i) != palavra.charAt(tamPalavra - i - 1)){
                return false;
            }
            return isPalindromo(palavra, i + 1);
        }

        
        return true;
    }
    //testa se é um palindromo comparando as duas pontas da string como uma array de char

    public static void main(String[] args) {
        try{
        Scanner rc = new Scanner(new InputStreamReader(System.in, "UTF-8"));
        while(rc.hasNext()){
            String entrada = rc.nextLine();
            if(comparaArray(entrada, "FIM") == false){
                if(isPalindromo(entrada, 0) == true){
                    System.out.println("SIM");
                }
                else{
                    System.out.println("NAO");
                }
            }
        }
        }
        catch(UnsupportedEncodingException e){
            System.err.println("Problema no encoding " + e.getMessage());
        }
    }
}
