import java.io.*;
import java.util.*;
import java.util.Scanner;

public class Show{

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

    public static void main(String[] args) {
        String path = "/tmp/disneyplus.csv";
        Scanner scanner = new Scanner(System.in);
        MyList ids = new MyList();

        // Ler IDs de entrada
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) break;
            ids.add(line);
        }
        scanner.close();

        try {
            MyList showsList = new MyList();
            Scanner rc = new Scanner(new File(path));
            rc.nextLine(); // Pular cabeçalho
            while (rc.hasNextLine()) {
                String ogLine = rc.nextLine();
                Show newShow = readShow(ogLine);
                showsList.add(newShow);
            }
            rc.close();

            // Buscar cada ID na lista de shows
            for (int i = 0; i < ids.size(); i++) {
                String id = (String) ids.get(i);
                Show show = showsList.findByShowId(id);
                if (show != null) {
                    show.printShow();
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("Arquivo não encontrado: " + e.getMessage());
        }
    }
    public static Show readShow(String ogLine) {
        String[] collection = split(ogLine);

        collection = nullTreatment(collection);
        collection = removeQuotesFromAll(collection); // <-- Adicione isso aqui

        myQueue cast = treatCast(collection[4]); // era collection[3], mas cast é o campo 4
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
                cast,           // cast --> DEVERIA vir de collection[4]
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
                if (collection[i].startsWith("\"") && collection[i].endsWith("\"")) {
                    collection[i] = collection[i].substring(1, collection[i].length() - 1);
                }
            }
        }
        return collection;
    }


    public static String pieceString(String ogLine, int start, int end){
        String answer = "";
        int j = 0;
        for(int i = start; i < end; i++){
            char currentChar = ogLine.charAt(i);
            answer += currentChar;
        }
        return answer;
    }

    public static String[] nullTreatment(String[] collection){
        for(int i = 0; i < collection.length; i++){
            if(collection[i] == null){
                collection[i] = "NaN";
            }
        }

        return collection;
    }

    public static myQueue treatQuotes(String blockLine) {
        myQueue queue = new myQueue(20);

        if (!blockLine.contains(",") && !blockLine.contains("\"")) {
            queue.insert(blockLine);
            return queue;
        }

        boolean group = false;
        int pieceStart = 0;
        for (int i = 0; i < blockLine.length(); i++) {
            char currentChar = blockLine.charAt(i);

            if ((currentChar == '"' || currentChar == ',') && !group) {
                pieceStart = i + 1;
                group = true;
            }
            else if ((currentChar == '"' || currentChar == ',') && group) {
                String toAdd = pieceString(blockLine, pieceStart, i);
                queue.insert(toAdd);
                group = false;
            }
        }
        return queue;
    }


    //"September 24, 2021"
    public static customDate dateTreatment(String blockLine) {
        if (blockLine == null || blockLine.isEmpty() || blockLine.equals("\"\"")) {
            return null;
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
        myQueue queue = new myQueue(20);
        if (blockLine == null || blockLine.isEmpty() || blockLine.equals("NaN")) {
            return queue;
        }

        String cleaned = blockLine.replaceAll("\"", "").trim();

        String[] actors = cleaned.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

        for (String actor : actors) {
            queue.insert(actor.trim());
        }

        queue.sort();
        return queue;
    }

    public static myQueue treatListedIn(String blockLine) {
        myQueue queue = new myQueue(1);
        String cleaned = blockLine.trim();

        if (cleaned.startsWith("\"") && cleaned.endsWith("\"")) {
            cleaned = cleaned.substring(1, cleaned.length() - 1);
        }

        queue.insert(cleaned);
        queue.sort();
        return queue;
    }

    public void printShow() {
        // Verificar campos String e substituir vazios/NaN
        String id = safeString(show_id);
        id = removeQuotes(id);
        String titulo = safeString(title);
        titulo = removeQuotes(titulo);
        String tipo = safeString(type);
        tipo = removeQuotes(tipo);
        String diretor = safeString(director);
        diretor = removeQuotes(diretor);
        String pais = safeString(country);
        pais = removeQuotes(pais);
        String classificacao = safeString(rating);
        classificacao = removeQuotes(classificacao);
        String duracao = safeString(duration);
        duracao = removeQuotes(duracao);

        // Verificar filas e datas
        String elenco = safeQueueCast(cast); // sem colchetes
        elenco = removeQuotes(elenco);
        String categorias = safeQueueListedIn(listed_in); // com colchetes
        categorias = removeQuotes(categorias);

        String data = (date_added == null) ? "NaN" : date_added.show();

        System.out.print("=> " + id + " ## " + titulo + " ## " + tipo + " ## " + diretor + " ## "
                + elenco + " ## " + pais + " ##" + data + " ## "
                + release_year + " ## " + classificacao + " ## " + duracao + " ## "
                + categorias + " ##");
        System.out.println();
    }

    public String removeQuotes(String value){
        String copy = "";
        for(int i = 0; i < value.length(); i++){
            if(value.charAt(i) != '"'){
                copy += value.charAt(i);
            }
        }
        return copy;
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


    public String safeQueue(myQueue queue) {
        if (queue == null || queue.isEmpty()) {
            return "NaN";
        }

        // Verificar se todos os elementos são vazios
        String content = queue.show();
        return content.equals("[]") ? "NaN" : content;
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

    //Gets e Setter abaixo


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

    //=> s441 ## Maggie Simpson in The Longest Daycare ## Movie ## NaN ## [NaN] ## United States ## May 29, 2020 ## 2012 ## PG ## 5 min ## [Animation, Comedy] ##
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

    public void sort() {
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (items[j].compareToIgnoreCase(items[j + 1]) > 0) {
                    String temp = items[j];
                    items[j] = items[j + 1];
                    items[j + 1] = temp;
                }
            }
        }
    }


}

class MyList {
    private Object[] elements;
    private int size;
    private int max = 10;

    public MyList() {
        elements = new Object[max];
        size = 0;
    }

    public void add(Object element) {
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
}