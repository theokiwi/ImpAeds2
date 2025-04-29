
import java.io.*;
import java.util.Scanner;
import java.util.Iterator;
import java.util.NoSuchElementException;

class Show {

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

    public Show() {

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
                collection[0], // show_id
                collection[1], // title
                collection[2], // type
                collection[3], // director
                cast, // cast 
                collection[5], // country
                date_added, // date_added
                Integer.parseInt(collection[7]), // release_year
                collection[8], // rating
                collection[9], // duration
                listed_in // listed_in
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

    public static String pieceString(String ogLine, int start, int end) {
        String answer = "";
        for (int i = start; i < end; i++) {
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
        if (content.equals("[]")) {
            return "[NaN]";
        }
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

    public customDate() {

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

    public String show() {
        return (" " + month + " " + day + ", " + year);
    }
}

class myQueue {

    private int n;
    private int max;
    private String[] items;

    public myQueue(int max) {
        this.max = max;
        this.n = 0;
        this.items = new String[max];
    }

    public void insert(String elem) {
        if (n >= max) {
            System.out.println("Fila Cheia no Inserir de " + elem);
        }
        items[n++] = elem;
    }

    public String remove() {
        if (n == 0) {
            System.out.println("Fila Vazia no Remover Fim");
        }
        String removed = items[0];
        for (int i = 0; i < n; i++) {
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
                if (compareCase(items[j], items[j + 1]) > 0) {
                    String temp = items[j];
                    items[j] = items[j + 1];
                    items[j + 1] = temp;
                }
            }
        }
    }

    public int compareCase(String a, String b) {
        int min = 0;
        int answer = 0;
        if (a.length() < b.length()) {
            min = a.length();
        } else {
            min = b.length();
        }

        for (int i = 0; i < min; i++) {
            char charA = Character.toLowerCase(a.charAt(i));
            char charB = Character.toLowerCase(b.charAt(i));
            if (charA != charB) {
                return charA - charB;
            }
        }

        return a.length() - b.length();

    }

}

//To usando object para dar pra converter para show, porque fosse string não ia dar
class MyList implements Iterable<Object> {

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

    public void changeElements(int index1, int index2) {
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

public class Insercao {

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
            if (!(line.equals("FIM"))) {
                if (line.isEmpty()) {
                    break;
                }
                ids.add(line);
            } else {
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
                showsList.add(newShow);
            }
            rc.close();

            MyList choosenShows = new MyList();
            //Adiciona os shows especificos em uma outra lista
            for (int i = 0; i < ids.size(); i++) {
                String id = (String) ids.get(i);
                Show show = showsList.findByShowId(id);
                if (show != null) {
                    choosenShows.add(show);
                }
            }

            choosenShows = ordenacaoInsercao(choosenShows);
            for (Object obj : choosenShows) {
                Show s = (Show) obj;
                s.printShow();
            }

            long fim = System.nanoTime();
            long duracao = fim - inicio;
            //System.out.println( (duracao / 1_000_000.0));

        } catch (FileNotFoundException e) {
            System.err.println("Arquivo não encontrado: " + e.getMessage());
        }
    }

    public static MyList ordenacaoInsercao(MyList lista) {
        for (int i = 1; i < lista.size(); i++) {
            int j = i;
    
            Show atual = (Show) lista.get(j);
            Show anterior = (Show) lista.get(j - 1);
    
            int compTipo = anterior.getType().compareTo(atual.getType());
    
            while (j > 0 && (compTipo > 0 || compTipo == 0)) {
                if(compTipo == 0){
                    int comp2 = anterior.getTitle().compareTo(atual.getTitle());
                    if(comp2 > 0){
                        lista.changeElements(j, j - 1);
                    }
                }else{
                    lista.changeElements(j, j - 1);
                }
                j--;
    
                if (j > 0) {
                    atual = (Show) lista.get(j);
                    anterior = (Show) lista.get(j - 1);
                    compTipo = anterior.getType().compareTo(atual.getType());
                }
            }
        }
    
        return lista;
    }
    

}
