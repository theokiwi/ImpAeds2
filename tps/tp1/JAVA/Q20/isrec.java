public class isrec {
    
    public static boolean comparaArray(String array1, String array2) {
        for (int i = 0; i < array1.length(); i++) {
            if (array1.charAt(i) != array2.charAt(i)) {
                return false;
            }
        }

        return true;
    }

    public static boolean charLetra(char entrada) {

        if (!(entrada >= 'a' && entrada <= 'z')
                || !(entrada >= 'A' && entrada <= 'Z')) {
            return false;
        }

        return true;
    }

    //eh letra
    public static boolean ehLetra(String entrada, int i) {
        if(i < entrada.length()){
            if (!(entrada.charAt(i) >= 'a' && entrada.charAt(i) <= 'z')
                    || !(entrada.charAt(i) >= 'A' && entrada.charAt(i) <= 'Z')) {
                return false;
            }
            return ehLetra(entrada, i + 1);
        }
        return true;
    }

    //NAO NAO NAO NAO -> formato saida
    //eh vogal
    public static boolean ehVogal(String entrada, int i) {
        if(i < entrada.length()){
            if (entrada.charAt(i) != 'a' && entrada.charAt(i) != 'A'
                    && entrada.charAt(i) != 'e' && entrada.charAt(i) != 'E'
                    && entrada.charAt(i) != 'i' && entrada.charAt(i) != 'I'
                    && entrada.charAt(i) != 'o' && entrada.charAt(i) != 'O'
                    && entrada.charAt(i) != 'u' && entrada.charAt(i) != 'U') {
                return false;
            }
            return ehVogal(entrada, i + 1);
        }
        return true;
    }

    //eh consoantes -> se for letra e não for vogal é consoante
    public static boolean ehConsoante(String entrada) {
        if (ehLetra(entrada, 0) == true && ehVogal(entrada, 0) == false) {
            return true;
        }
        return false;
    }

    //numero real-> olha se tem virgula ou ponto
    public static boolean numReal(String entrada, int i) {
        int contador = 0;

        if(i < entrada.length()){
            if (charLetra(entrada.charAt(i))) {
                return false;
            }
            if (entrada.charAt(i) == '.' || entrada.charAt(i) == ',') {
                contador++;
            }
            return numReal(entrada, i + 1);
        }

        if (contador == 1) {
            return true;
        } else {
            return false;
        }
    }

    //numero inteiro -> olha se o o char ta entre 0 e 9
    public static boolean numInt(String entrada, int i) {
        if(i < entrada.length()){
            if (!(entrada.charAt(i) >= '0' && entrada.charAt(i) <= '9')) {
                return false;
            }
            return numInt(entrada, i + 1);
        }
        return true;
    }

    public static void main(String[] args) {
        boolean stop = false;
        while (!stop) {
            String entrada = MyIO.readLine();
            if (!comparaArray(entrada, "FIM")) {
                if (ehVogal(entrada, 0) == true) {
                    System.out.print("SIM ");
                } else {
                    System.out.print("NAO ");
                }

                if (ehConsoante(entrada) == true) {
                    System.out.print("SIM ");
                } else {
                    System.out.print("NAO ");
                }

                if (numInt(entrada, 0) == true) {
                    System.out.print("SIM ");
                } else {
                    System.out.print("NAO ");
                }

                if (numReal(entrada, 0) == true) {
                    System.out.println("SIM");
                } else {
                    System.out.println("NAO");
                }

            } else {
                stop = true;
            }
        }
    }
}
