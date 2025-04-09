
public class ciframentocesar {
    /*Eu to convertendo entre array de char e string para ficar mais facil de encapsular
    Em java eu não consigo fazer
    coisa.charAt(i) = somaTres(coisa.charAt(i)); 
    Então como eu queria fazer encapsulamento eu tive que fazer as conversões
     */

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

    //soma um numero x ao caracter atual para fazer o ciframento
    public static char somaChar(char c, int soma){
        return c += soma;
    }

    public static String cifraCesar(String entrada){
        char[] linha = paraChar(entrada);
        for(int i = 0; i < entrada.length(); i++){
            linha[i] = somaChar(linha[i], 3);
        }

        return paraString(linha);
    }
    public static void main(String[] args) {
        boolean stop = false;
        while(!stop){
        String entrada = MyIO.readLine();
        if(!comparaArray(entrada, "FIM")){
        MyIO.println(cifraCesar(entrada));
        }
        else{
            stop = true;
        }     
    }
    }
}
