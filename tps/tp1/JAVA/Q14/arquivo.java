import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class arquivo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        int n = scanner.nextInt();
        
        String caminhoArquivo = "pub.in";

        try (RandomAccessFile arquivo = new RandomAccessFile(caminhoArquivo, "rw")) {
            for (int i = 0; i < n; i++) {
                double valor = scanner.nextDouble();
                arquivo.writeDouble(valor);  
            }

            arquivo.close();

            try (RandomAccessFile arquivoLeitura = new RandomAccessFile(caminhoArquivo, "r")) {
                long tamanhoArquivo = arquivoLeitura.length();
                

                for(int i = n - 1; i >=0; i--){
                    arquivoLeitura.seek(i * 8);  
                    double valor = arquivoLeitura.readDouble();  
                    int inteiro = (int)valor;
                    if(valor==inteiro){
                        MyIO.println(inteiro);  
                    } 
				    else MyIO.println(valor);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
