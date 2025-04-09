import java.util.*;
 public class soma {    
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
    
    public static int somaDigitos(String entrada, int i, int soma){
        if(i < entrada.length()){
            soma += entrada.charAt(i) - '0';
            return somaDigitos(entrada, i + 1, soma);
        }

        return soma;
    }

   public static void main(String[] args) {
        boolean stop = false;
        Scanner rc = new Scanner (System.in);
        while (!stop) {
            String entrada = rc.nextLine();
            if (!comparaArray(entrada, "FIM")) {
                int resultado = somaDigitos(entrada, 0, 0);
                System.out.println(resultado);
            } else {
                stop = true;
            }
        }
    }

    
}
