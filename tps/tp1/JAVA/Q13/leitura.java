import java.io.*;
import java.net.*;

public class leitura {

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

    public static String obterHtml(String endereco)
    {
       URL url;
       InputStream entrada = null;
       BufferedReader leitor;
       String resposta = "", linha;
 
       try 
       {
          url = new URL(endereco);
          entrada = url.openStream();
          leitor = new BufferedReader(new InputStreamReader(entrada));
 
          while ((linha = leitor.readLine()) != null) {
             resposta += linha + "\n";
          }
       } catch (MalformedURLException e) {
          e.printStackTrace();
       } catch (IOException e) {
          e.printStackTrace();
       } 
 
       try {
          entrada.close();
       } catch (IOException e) {
       }
 
       return resposta;
    }
 
 
    public static void contarOcorrencias(String texto, int comprimento, String identificador)
    {
 
       int contadorA = 0;
       int contadorE = 0;
       int contadorI = 0;
       int contadorO = 0;
       int contadorU = 0;
       int contadorAComAcento = 0;
       int contadorEComAcento = 0;
       int contadorIComAcento = 0;
       int contadorOComAcento = 0;
       int contadorUComAcento = 0;
       int contadorAComGrave = 0;
       int contadorEComGrave = 0;
       int contadorIComGrave = 0;
       int contadorOComGrave = 0;
       int contadorUComGrave = 0;
       int contadorTil = 0;
       int contadorTio = 0;
       int contadorCircunflexoA = 0;
       int contadorCircunflexoE = 0;
       int contadorCircunflexoO = 0;
       int contadorCircunflexoI = 0;
       int contadorCircunflexoU = 0;
       int contadorConsoante = 0;
       int contadorQuebraLinha = 0;
       int contadorTabela = 0;
 
       for(int i = 0; i < comprimento; i++)
       {
          if(texto.charAt(i) == '<')
          {
             if(texto.charAt(i + 1) == 'b' && texto.charAt(i + 2) == 'r' && texto.charAt(i + 3) == '>')
             {
                contadorQuebraLinha++;
                i += 3;
             }
             else if(texto.charAt(i + 1) == 't' && texto.charAt(i + 2) == 'a' && texto.charAt(i + 3) == 'b' && texto.charAt(i + 4) == 'l' && texto.charAt(i + 5) == 'e' && texto.charAt(i + 6) == '>') 
             {
                contadorTabela++;
                i += 6;
             }
          }
          else if(texto.charAt(i) >= 'a' && texto.charAt(i) <= 'z')
          {
             if(texto.charAt(i) == 'a') contadorA++;
             else if(texto.charAt(i) == 'e') contadorE++;
             else if(texto.charAt(i) == 'i') contadorI++;
             else if(texto.charAt(i) == 'o') contadorO++;
             else if(texto.charAt(i) == 'u') contadorU++;
             else
             {
                contadorConsoante++;
             }
          }
          else if(texto.charAt(i) == 225) contadorAComAcento++;
          else if(texto.charAt(i) == 233) contadorEComAcento++;
          else if(texto.charAt(i) == 237) contadorIComAcento++;
          else if(texto.charAt(i) == 243) contadorOComAcento++;
          else if(texto.charAt(i) == 250) contadorUComAcento++;
          else if(texto.charAt(i) == 224) contadorAComGrave++;
          else if(texto.charAt(i) == 232) contadorEComGrave++;
          else if(texto.charAt(i) == 236) contadorIComGrave++;
          else if(texto.charAt(i) == 242) contadorOComGrave++;
          else if(texto.charAt(i) == 249) contadorUComGrave++;
          else if(texto.charAt(i) == 227) contadorTil++;
          else if(texto.charAt(i) == 245) contadorTio++;
          else if(texto.charAt(i) == 226) contadorCircunflexoA++;
          else if(texto.charAt(i) == 234) contadorCircunflexoE++;
          else if(texto.charAt(i) == 238) contadorCircunflexoO++;
          else if(texto.charAt(i) == 244) contadorCircunflexoI++;
          else if(texto.charAt(i) == 251) contadorCircunflexoU++;
       }
       
       MyIO.println("a(" + contadorA + ") e(" + contadorE + ") i(" + contadorI + ") o(" + contadorO + ") u(" + contadorU + ") á(" + contadorAComAcento + ") é(" + contadorEComAcento + ") í(" + contadorIComAcento + ") ó(" + contadorOComAcento + ") ú(" + contadorUComAcento + ") à(" + contadorAComGrave + ") è(" + contadorEComGrave + ") ì(" + contadorIComGrave + ") ò(" + contadorOComGrave + ") ù(" + contadorUComGrave + ") ã(" + contadorTil+ ") õ("+contadorTio+") â("+contadorCircunflexoA+") ê("+contadorCircunflexoE+") î("+contadorCircunflexoO+") ô("+contadorCircunflexoI+") û("+contadorCircunflexoU+") consoante("+contadorConsoante+") <br>("+contadorQuebraLinha+") <table>("+contadorTabela+") "+ identificador );
    }
     
 

    public static void main(String[] args) {
        boolean parar = false;
        while (!parar) {
            String nome = MyIO.readLine();
            if (!(compararStrings(nome, "FIM"))) {
                String url = MyIO.readLine();
                String conteudo = obterHtml(url);
                int tamanho = conteudo.length();
                contarOcorrencias(conteudo, tamanho, nome);
            } else {
                parar = true;
            }
        }
    }
}
