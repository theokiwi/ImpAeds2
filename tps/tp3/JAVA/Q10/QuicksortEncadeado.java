import java.io.*;
import java.util.Scanner;
import java.util.Iterator;
import java.util.NoSuchElementException;

class Show{

    private String show_id;
    private String title;
    private String type;
    private String director;
    private myQueue cast;
    private String country;
    private customDate date_added;
    private int release_year;
    private String rating;
    private String duration;
    private myQueue listed_in;

    public Show(){

    }

    public Show(String show_id, String type, String title, String director, myQueue cast, String country, customDate date_added, int release_year, String rating, String duration, myQueue listed_in) {
        this.show_id = show_id;
        this.title = title;
        this.type = type;
        this.director = director;
        this.cast = cast;
        this.country = country;
        this.date_added = date_added;
        this.release_year = release_year;
        this.rating = rating;
        this.duration = duration;
        this.listed_in = listed_in;
    }


    public static Show readShow(String ogLine) {
        String[] collection = split(ogLine);

        collection = nullTreatment(collection);
        collection = removeQuotesFromAll(collection);

        myQueue cast = treatCast(collection[4]);
        myQueue listed_in = treatListedIn(collection[10]);
        customDate date = dateTreatment(collection[6]);
        Show builtShow = new Show();
        builtShow = showBuild(collection, cast, listed_in, date);

        return builtShow;
    }

    public static Show showBuild(String[] collection, myQueue cast, myQueue listed_in, customDate date_added) {
        Show newShow = new Show(
                collection[0],  // show_id
                collection[1],  // title
                collection[2],  // type
                collection[3],  // director
                cast,           // cast
                collection[5],  // country
                date_added,     // date_added
                Integer.parseInt(collection[7]), // release_year
                collection[8],  // rating
                collection[9],  // duration
                listed_in       // listed_in
        );
        return newShow;
    }

    public static String[] split(String ogLine) {
        String[] collection = new String[12];
        int cCount = 0;
        int pieceStart = 0;
        boolean inQuotes = false;

        for (int i = 0; i < ogLine.length(); i++) {
            char currentChar = ogLine.charAt(i);

            //Se a virgula não estiver dentro de " ele impoe que ela é separadora de campo e separa o campo
            if (currentChar == '"') {
                inQuotes = !inQuotes;
            } else if (currentChar == ',' && !inQuotes) {
                String toAdd = pieceString(ogLine, pieceStart, i);
                collection[cCount] = toAdd;
                cCount++;
                pieceStart = i + 1;
            }
        }

        if (pieceStart < ogLine.length()) {
            String toAdd = pieceString(ogLine, pieceStart, ogLine.length());
            collection[cCount] = toAdd;
        }

        return collection;
    }

    public static String[] removeQuotesFromAll(String[] collection) {
        for (int i = 0; i < collection.length; i++) {
            if (collection[i] != null) {
                collection[i] = collection[i].trim();
                String replacement = "";
                for (int j = 0; j < collection[i].length(); j++) {
                    if (collection[i].charAt(j) != '\"') {
                        replacement += collection[i].charAt(j);
                    }
                }
                collection[i] = replacement;
            }
        }
        return collection;
    }


    public static String pieceString(String ogLine, int start, int end){
        String answer = "";
        for(int i = start; i < end; i++){
            char currentChar = ogLine.charAt(i);
            answer += currentChar;
        }
        return answer;
    }

    public static String[] nullTreatment(String[] collection) {
        for (int i = 0; i < collection.length; i++) {
            if (collection[i] == null || collection[i].equals("")) {
                collection[i] = "NaN";
            }
        }

        return collection;
    }

    //"September 24, 2021"
    public static customDate dateTreatment(String blockLine) {
        if (blockLine == null || blockLine.isEmpty() || blockLine.equals("\"\"") || blockLine.equals("NaN")) {
            return new customDate("March", 1, 1900);
        }

        int start = 0;
        int end = blockLine.length();

        if (blockLine.charAt(0) == '"') {
            start = 1;
        }
        if (blockLine.charAt(blockLine.length() - 1) == '"') {
            end = blockLine.length() - 1;
        }

        String trimmed = blockLine.substring(start, end);

        if (trimmed.isEmpty()) {
            return null;
        }

        int spaceIndex = trimmed.indexOf(' ');
        if (spaceIndex == -1) {
            return null;
        }

        int commaIndex = trimmed.indexOf(',');
        if (commaIndex == -1) {
            return null;
        }

        try {
            String monthS = trimmed.substring(0, spaceIndex);
            String dayStr = trimmed.substring(spaceIndex + 1, commaIndex).trim();
            String yearStr = trimmed.substring(commaIndex + 2).trim();

            int dayN = Integer.parseInt(dayStr);
            int yearN = Integer.parseInt(yearStr);

            return new customDate(monthS, dayN, yearN);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    public static myQueue treatCast(String blockLine) {
        myQueue queue = new myQueue(25);
        if (blockLine == null || blockLine.isEmpty() || blockLine.equals("NaN")) {
            return queue;
        }

        String[] actors = blockLine.split(",");
        for (String actor : actors) {
            queue.insert(actor.trim());
        }

        queue.sort();
        return queue;
    }

    public static myQueue treatListedIn(String blockLine) {
        myQueue queue = new myQueue(25);
        if (blockLine == null || blockLine.isEmpty() || blockLine.equals("NaN")) {
            return queue;
        }

        String[] genres = blockLine.split(",");
        for (String genre : genres) {
            queue.insert(genre.trim());
        }

        return queue;
    }

    public void printShow() {
        // Verificar campos String e substituir vazios/NaN
        String id = safeString(show_id);
        String titulo = safeString(title);
        String tipo = safeString(type);
        String diretor = safeString(director);
        String pais = safeString(country);
        String classificacao = safeString(rating);
        String duracao = safeString(duration);

        // Verificar filas e datas
        String elenco = safeQueueCast(cast); // sem colchetes
        String categorias = safeQueueListedIn(listed_in); // com colchetes

        String data = (date_added == null) ? "NaN" : date_added.show();

        System.out.print("=> " + id + " ## " + titulo + " ## " + tipo + " ## " + diretor + " ## "
                + elenco + " ## " + pais + " ##" + data + " ## "
                + release_year + " ## " + classificacao + " ## " + duracao + " ## "
                + categorias + " ##");
        System.out.println();
    }

    public String safeString(String value) {
        return (value == null || value.isEmpty() || value.equals("NaN") || value.equals("[NaN]")) ? "NaN" : value;
    }

    public String safeQueueCast(myQueue queue) {
        if (queue == null || queue.isEmpty()) {
            return "[NaN]";
        }

        String content = queue.show();
        if (content.equals("[]")) return "[NaN]";
        return "[" + content.substring(1, content.length() - 1) + "]";
    }


    public String safeQueueListedIn(myQueue queue) {
        if (queue == null || queue.isEmpty()) {
            return "[NaN]";
        }

        String content = queue.show();
        return content.equals("[]") ? "[NaN]" : content;
    }

    public String getShow_id() {
        return show_id;
    }

    public void setShow_id(String show_id) {
        this.show_id = show_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public myQueue getCast() {
        return cast;
    }

    public void setCast(myQueue cast) {
        this.cast = cast;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public customDate getDate_added() {
        return date_added;
    }

    public void setDate_added(customDate date_added) {
        this.date_added = date_added;
    }

    public int getRelease_year() {
        return release_year;
    }

    public void setRelease_year(int release_year) {
        this.release_year = release_year;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public myQueue getListed_in() {
        return listed_in;
    }

    public void setListed_in(myQueue listed_in) {
        this.listed_in = listed_in;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



}

//Formato Data: September 24, 2021
class customDate {
    private String month;
    private int day;
    private int year;

    public customDate(){

    }

    public customDate(String month, int day, int year) {
        this.month = month;
        this.day = day;
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getDia() {
        return day;
    }

    public void setDia(int day) {
        this.day = day;
    }

    public int getAno() {
        return year;
    }

    public void setAno(int year) {
        this.year = year;
    }

    public String show(){
        return (" " + month + " " + day + ", " + year);
    }
}

class myQueue{
    private int n;
    private int max;
    private String[] items;

    public myQueue(int max){
        this.max = max;
        this.n = 0;
        this.items = new String[max];
    }

    public void insert(String elem){
        if(n >= max) {
            System.out.println("Fila Cheia no Inserir de " + elem);
        }
        items[n++] = elem;
    }

    public String remove(){
        if(n == 0){
            System.out.println("Fila Vazia no Remover Fim");
        }
        String removed = items[0];
        for(int i = 0; i < n; i++){
            items[i] = items[i++];
        }
        n--;
        return removed;
    }

    //[Kamran Lucas, Nathaniel Potvin, Pearce Joza, Raymond Cham]
    public String show() {
        String result = "";
        if (n == 0) {
            result += "Fila vazia no show";
        }
        result += "[";
        for (int i = 0; i < n; i++) { // inicia do índice 0
            if (i == n - 1) {
                result += items[i];
            } else {
                result += items[i] + ", ";
            }
        }
        result += "]";
        return result;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    //bubble sort
    //a função positivo se items[j] é maior que items[j + 1]
    public void sort() {
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (compareCase(items[j], items [j + 1])> 0) {
                    String temp = items[j];
                    items[j] = items[j + 1];
                    items[j + 1] = temp;
                }
            }
        }
    }

    public int compareCase(String a, String b){
        int min = 0;
        if(a.length() < b.length()){
            min = a.length();
        }else{
            min = b.length();
        }

        for(int i = 0; i < min; i++){
            char charA = Character.toLowerCase(a.charAt(i));
            char charB = Character.toLowerCase(b.charAt(i));
            if(charA != charB){
                return charA - charB;
            }
        }

        return a.length() - b.length();

    }


}

//To usando object para dar pra converter para show, porque fosse string não ia dar
class MyList implements Iterable<Object>{
    private Object[] elements;
    private int size;
    private int max = 10;

    public MyList() {
        elements = new Object[max];
        size = 0;
    }

    public void inserirFim(Object element) {
        if (size == elements.length) {
            expandCapacity();
        }
        elements[size++] = element;
    }

    private void expandCapacity() {
        int newCapacity = elements.length * 2;
        Object[] newElements = new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }
        elements = newElements;
    }

    public Object get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return elements[index];
    }

    public int size() {
        return size;
    }

    public Show findByShowId(String showId) {
        for (int i = 0; i < size; i++) {
            Show show = (Show) elements[i];
            if (show.getShow_id().equals(showId)) {
                return show;
            }
        }
        return null;
    }

    public void changeElements(int index1, int index2){
        Object temp = elements[index1];
        elements[index1] = elements[index2];
        elements[index2] = temp;
    }

    //Gambiarra pra ele me deixar usar o foreach
    public Iterator<Object> iterator() {
        return new Iterator<Object>() {
            private int current = 0;

            @Override
            public boolean hasNext() {
                return current < size;
            }

            @Override
            public Object next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return elements[current++];
            }
        };
    }


}

public class QuicksortEncadeado{

    public static void main(String[] args) {
        long inicio = System.nanoTime();
        Show showObj = new Show();
        String path = "/tmp/disneyplus.csv";
        Scanner scanner = new Scanner(System.in);
        MyList ids = new MyList();
        boolean stop = false;
        // le todos os ids até o FIM
        while (!stop) {
            String line = scanner.nextLine().trim();
            if(!(line.equals("FIM"))){
                if (line.isEmpty()) break;
                ids.inserirFim(line);
            }
            else{
                stop = true;
            }
        }


        try {
            //Coloca todos os shows existentes em uma lista
            MyList showsList = new MyList();
            Scanner rc = new Scanner(new File(path));
            rc.nextLine();
            while (rc.hasNextLine()) {
                String ogLine = rc.nextLine();
                Show newShow = showObj.readShow(ogLine);
                showsList.inserirFim(newShow);
            }

            ListaEncadeada choosenShows = new ListaEncadeada();
            //Adiciona os shows especificos em uma outra lista
            for (int i = 0; i < ids.size(); i++) {
                String id = (String) ids.get(i);
                Show show = showsList.findByShowId(id);
                if (show != null) {
                    choosenShows.inserir(show);
                }
            }

            //chamar quicksort
            choosenShows.quicksort();
            choosenShows.printaPilha();

            long fim = System.nanoTime();
            long duracao = fim - inicio;
            // System.out.println( (duracao / 1_000_000.0));

            rc.close();

        } catch (FileNotFoundException e) {
            System.err.println("Arquivo não encontrado: " + e.getMessage());
        }
    }


    public static String[] trataString(String linha){
        String[] tratada = new String[3];

        tratada = linha.split(" ");

        return tratada;
    }
}

class Celula{
    Show elemento;
    Celula prox;
    Celula ant;

    public Celula(Show elemento){
        this.elemento = elemento;
        this.prox = null;
        this.ant = null;
    }
}

class ListaEncadeada{
    Celula cabeca; 

    public ListaEncadeada(){
        this.cabeca = new Celula(null);
    }

    public void inserir(Show elemento){
        if (elemento == null) return; 
        Celula novaCelula = new Celula(elemento);
        Celula atual = cabeca;
        while(atual.prox != null){
            atual = atual.prox;
        }
        atual.prox = novaCelula;
        novaCelula.ant = atual;
    }

    public void printaPilha(){ 
        Celula temp = cabeca.prox;
        while(temp != null){
           if (temp.elemento != null) { 
               temp.elemento.printShow();
           }
           temp = temp.prox;
        }
    }

    private void trocarDados(Celula c1, Celula c2) {
        if (c1 == null || c2 == null || c1.elemento == null || c2.elemento == null) {
            return; 
        }
        Show temp = c1.elemento;
        c1.elemento = c2.elemento;
        c2.elemento = temp;
    }

    private int obterValorMes(String nomeMes) {
        if (nomeMes == null) return 0; 
        switch (nomeMes.trim().toLowerCase()) { 
            case "january": return 1;
            case "february": return 2;
            case "march": return 3;
            case "april": return 4;
            case "may": return 5;
            case "june": return 6;
            case "july": return 7;
            case "august": return 8;
            case "september": return 9;
            case "october": return 10;
            case "november": return 11;
            case "december": return 12;
            default: return 0; 
        }
    }

    private int compararDatasCustomizadas(customDate d1, customDate d2) {
        if (d1 == null && d2 == null) return 0; 
        if (d1 == null) return -1; 
        if (d2 == null) return 1;  

        if (d1.getAno() != d2.getAno()) {
            return d1.getAno() - d2.getAno();
        }

        int valorMes1 = obterValorMes(d1.getMonth());
        int valorMes2 = obterValorMes(d2.getMonth());
        if (valorMes1 != valorMes2) {
            return valorMes1 - valorMes2;
        }

        return d1.getDia() - d2.getDia();
    }

    private int compararShows(Show s1, Show s2) {
        if (s1 == null && s2 == null) return 0;
        if (s1 == null) return -1; 
        if (s2 == null) return 1;  

        int comparacaoData = compararDatasCustomizadas(s1.getDate_added(), s2.getDate_added());
        if (comparacaoData != 0) {
            return comparacaoData;
        }

        String titulo1 = (s1.getTitle() == null || s1.getTitle().equals("NaN")) ? "" : s1.getTitle();
        String titulo2 = (s2.getTitle() == null || s2.getTitle().equals("NaN")) ? "" : s2.getTitle();
        return titulo1.compareToIgnoreCase(titulo2);
    }

    private Celula particionar(Celula inicio, Celula fim) {
        Show pivo = fim.elemento; 
        Celula i = inicio.ant;    

        for (Celula j = inicio; j != fim; j = j.prox) {

            if (compararShows(j.elemento, pivo) <= 0) {

                i = (i == null || i == cabeca) ? inicio : i.prox;
                trocarDados(i, j); 
            }
        }

        i = (i == null || i == cabeca) ? inicio : i.prox;
        trocarDados(i, fim); 
        return i; 
    }

    private void quicksortRecursivo(Celula inicio, Celula fim) {

        if (inicio != null && fim != null && inicio != fim && inicio.ant != fim) { 
            Celula noPivo = particionar(inicio, fim);
            if (noPivo != null) {
                quicksortRecursivo(inicio, noPivo.ant);  
                quicksortRecursivo(noPivo.prox, fim); 
            }
        }
    }

    public void quicksort() {
        Celula primeiroNo = cabeca.prox; 

        if (primeiroNo == null || primeiroNo.prox == null) {
            return;
        }

        Celula ultimoNo = primeiroNo;
        while (ultimoNo.prox != null) {
            ultimoNo = ultimoNo.prox;
        }

        if (primeiroNo != ultimoNo) { 
            quicksortRecursivo(primeiroNo, ultimoNo);
        }
    }

    private Celula obterNoPorIndice(int indice) {
        if (indice < 0) return null; 
        Celula atual = cabeca.prox;
        int contador = 0;
        while (atual != null) {
            if (contador == indice) {
                return atual;
            }
            contador++;
            atual = atual.prox;
        }
        return null; 
    }

    public void quicksort(int esq, int dir) {

        if (esq < 0 || dir < 0 || esq >= dir) { 
            return;
        }

        Celula noEsq = obterNoPorIndice(esq);
        Celula noDir = obterNoPorIndice(dir);

        if (noEsq != null && noDir != null && noEsq != noDir) {

            quicksortRecursivo(noEsq, noDir);
        }
    }
}