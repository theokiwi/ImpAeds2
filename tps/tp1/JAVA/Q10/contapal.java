import java.util.*;
public class contapal {
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

    public static int contaEspaco(String entrada){
        int cont = 0;
        for(int i = 0; i < entrada.length(); i++){
            if(entrada.charAt(i) == ' '){
                cont++;
            }
        }

        return cont;
    }
    public static void main(String[] args) {
        boolean stop = false;
        Scanner rc = new Scanner(System.in);
        while(!stop){
            String entrada = rc.nextLine();
            if(!(comparaArray(entrada, "FIM"))){
                System.out.println(contaEspaco(entrada) + 1);
            }
            else{
                stop = true;
            }
        }
    }
}