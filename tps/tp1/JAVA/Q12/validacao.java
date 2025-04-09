
import java.util.*;

public class validacao {

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

    //contem 8 caracteres
    public static boolean contemChar(String entrada) {
        return entrada.length() >= 8;
    }

    //tem letra maiuscula
    public static boolean contemMaiuscula(String entrada) {
        for (int i = 0; i < entrada.length(); i++) {
            if (entrada.charAt(i) >= 'A' && entrada.charAt(i) <= 'Z') {
                return true;
            }
        }
        return false;
    }

    //tem letra minuscula
    public static boolean contemMinuscula(String entrada) {
        for (int i = 0; i < entrada.length(); i++) {
            if (entrada.charAt(i) >= 'a' && entrada.charAt(i) <= 'z') {
                return true;
            }
        }
        return false;
    }

    //tem numero
    public static boolean contemNum(String entrada) {
        for (int i = 0; i < entrada.length(); i++) {
            if (entrada.charAt(i) >= '0' && entrada.charAt(i) <= '9') {
                return true;
            }
        }
        return false;
    }

    //tem caractere especial
    public static boolean ehEspecial(String entrada) {
        for(int i = 0; i < entrada.length(); i++){
        if (!(entrada.charAt(i) >= 'A' && entrada.charAt(i) <= 'Z')
                && !(entrada.charAt(i) >= 'a' && entrada.charAt(i) <= 'z')
                && !(entrada.charAt(i) >= '0' && entrada.charAt(i) <= '9')
                && entrada.charAt(i) != ' ') {
            return true;
        }}
        return false;
        
        
    }
    // se nao eh nem letra nem numero eh especial

    //eh valida
    public static boolean ehValida(String entrada){
        if(contemChar(entrada) && contemMaiuscula( entrada) && contemMinuscula(entrada) 
        &&  contemNum(entrada) && ehEspecial(entrada)){
                    return true; 
        }
        return false;
    }
    public static void main(String[] args) {

        boolean stop = false;
        Scanner rc = new Scanner(System.in);
        while(!stop){
            String entrada = rc.nextLine();
            if(!(comparaArray(entrada, "FIM"))){
                 if(ehValida(entrada)){ 
                    MyIO.println("SIM");
                }else{
                    MyIO.println("NAO");
                }
            }else{
                 stop = true; 
            }
        }
    }
}
