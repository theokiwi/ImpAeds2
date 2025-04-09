import java.util.*;
public class algebrabool {
    public static boolean compararStrings(String str1, String str2) {
        if (str1.length() != str2.length()) {
            return false;
        }
        for (int i = 0; i < str1.length(); i++) {
            if (str1.charAt(i) != str2.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public static boolean tem(String entrada, char c){
        for(int i = 0; i < entrada.length(); i++){
            if(entrada.charAt(i) == c){
                return true;
            }
        }

        return false;
    }
    public static String subString(String entrada, int comeco, int fim)
    {
        String resultado = "";
        for(int i = comeco; i < entrada.length() && i < fim; i++){
            resultado += entrada.charAt(i);
        }

        return (resultado);
    }

    public static String removeBranco(String entrada){
        String saida = "";

        for(int i = 0; i < entrada.length(); i++){
            if(entrada.charAt(i) != ' '){
                saida += entrada.charAt(i);
            }
        }

        return saida;
    }

    public static String substitui(String opcao, char substituido, String entrada){
        int tambase = opcao.length();
        String saida = "";

        for(int i = 0; i < entrada.length(); i++){
            String sub = subString(entrada, i, i +tambase);
            if(compararStrings(sub, opcao)) {
                saida += substituido;
                i = i + tambase - 1;
            }
            else{
                saida += entrada.charAt(i);
            }
        }

        return saida;

    }


    public static int quantVal(String entrada){
        return entrada.charAt(0) - 48;
    }
    //2 0 0 and(not(A), not(B))

    //200a(n(A),n(B))
    public static String substituiChar(char c, char newC, String entrada) {
        String saida = "";
        for (int i = 0; i < entrada.length(); i++) {
            if (entrada.charAt(i) == c) {
                saida += newC;  
            } else {
                saida += entrada.charAt(i); 
            }
        }
        return saida;  
    }
    

    public static String tratamento(String entrada) {
        String saida = removeBranco(entrada);
        
        saida = substitui("and", 'a', saida);  
        saida = substitui("or", 'o', saida);   
        saida = substitui("not", 'n', saida);  
    
        int quantVal = quantVal(saida);  
        for (int x = 0; x < quantVal; x++) {
            char c = (char) ('A' + x);
            char newC = saida.charAt(x + 1); 
            saida = substituiChar(c, newC, saida);
        }
    
    
        saida = subString(saida, quantVal + 1, saida.length()); 
    
        return saida; 
    }
    

    //n(0),n(0)
    public static String resolveExp(String input) {
        int inputLen = input.length();
        char operation = input.charAt(0);
        boolean flag = true;
        
        switch (operation) {
            case 'a':  
                flag = true;
                for (int y = 2; y < inputLen && flag; y = y + 1) {
                    if (input.charAt(y) == '0') {
                        flag = false;
                        return ("0");
                    } 
                } 
                return ("1");

            case 'o':  
                flag = false;
                for (int y = 2; y < inputLen && !flag; y = y + 1) {
                    if (input.charAt(y) == '1') {
                        flag = true;
                        return ("1");
                    } 
                } 
                return ("0");

            case 'n':  
                if (input.charAt(2) == '0') {
                    return ("1");
                } else {
                    return ("0");
                } 

            default:
                return ("");  
        } 
    }
    
    public static int resolveEquacao(String entrada){
        String expressao = entrada;
        while(tem(expressao, '(')){
        int comecaParenteses = expressao.lastIndexOf('(');
        int terminaParenteses = expressao.indexOf(')', comecaParenteses);
        String pedaco = subString(expressao, comecaParenteses - 1, terminaParenteses + 1); //(n(0),n(0))
        String resolucao = resolveExp(pedaco); 
        expressao = subString(expressao, 0, comecaParenteses - 1) + resolucao + subString(expressao,terminaParenteses + 1, expressao.length());
        }       
        
        return Integer.parseInt(expressao);
    }
    public static void main(String[] args) {
        Scanner rc = new Scanner(System.in);
        int contador = 0;
        while(rc.hasNext()){
            String entrada = rc.nextLine();
            if(!(compararStrings(entrada, "0"))){
                String saida = tratamento(entrada);
                int resposta = resolveEquacao(saida);
                System.out.println(resposta);
                contador++;
            }
        }
       
    }
}