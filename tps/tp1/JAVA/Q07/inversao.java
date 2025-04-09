public class inversao {
    public static boolean comparaArray(String array1, String array2) {
        for (int i = 0; i < array1.length(); i++) {
            if (array1.charAt(i) != array2.charAt(i)) {
                return false;
            }
        }

        return true;
    }
    
    public static String paraString(char[] entrada){
        String result = "";
        for (char c : entrada) {
            result += c; 
        }
        return result;
    }

    public static char[] inverteArray(String entrada){
        char[] invertida = new char[entrada.length()];
        int j = 0;
        for(int i = entrada.length() - 1; i >= 0; i--){
            invertida[j] = entrada.charAt(i);
            j++;
        }

        return invertida;
    }

    public static void main(String[] args) {
        boolean stop = false;
        while(!stop){
            String entrada = MyIO.readLine();
            if(!(comparaArray(entrada, "FIM"))){
               String resultado = paraString(inverteArray(entrada));
               MyIO.println(resultado);
            }
            else{
                stop = true;
            }
        }
    }
}
