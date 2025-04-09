import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

//TODO: Implementar lista manualmente
//TODO: Criar separação de Aspas
//TODO: Substituir campos vazios por NaN

public class Show{
    private String show_id;
    private String type;
    private String director;
    private String[] cast;
    private String country;
    private Date date_added;
    private int release_year;
    private String rating;
    private String duration;
    private String[] listed_in;

    public static void main(String[] args){
        Show show = new Show();
       show = show.readShow();
    }
    //show_id,type,title,director,cast,country,date_added,release_year,rating,duration,listed_in,description
    //Lembrar de substituir valores vazios por NaN
    public Show readShow(){
        String file = "/tmp/disneyplus.csv";
        BufferedReader reader = null;
        String ogLine = "";
        try {
            reader = new BufferedReader(new FileReader(file));
            reader.readLine();
            while((ogLine = reader.readLine()) != null){
               char[] charLine = new char [5000];
               charLine = ogLine.toCharArray();
                List<char[]> collection = new ArrayList<char[]>();
                collection = split(charLine);
               for(char[] c : collection){
                   System.out.println(c);
               }
            }

        } catch (Exception e) {

        }
        finally{

        }
        //inserir elementos para o construtor
        Show show = new Show();
        return show;
    }

    //s1,Movie,A Spark Story,"Jason Sterman, Leanne Dare","Apthon Corbin,September 24, 2021,,"September 24, 2021",2021,TV-PG,88 min,Documentary,desc.
    public List<char[]> split(char[] ogLine) {
        List<char[]> collection = new ArrayList<>();
        int pieceStart = 0;
        boolean inPiece = false;

        for (int i = 0; i < ogLine.length; i++) {
            if (ogLine[i] == ',') {
                if (!inPiece) {
                    pieceStart = i + 1;
                    inPiece = true;
                } else {
                    int pieceEnd = i;
                    char[] toAdd = Arrays.copyOfRange(ogLine, pieceStart, pieceEnd);
                    collection.add(toAdd);
                    inPiece = false;
                }
            } else if (ogLine[i] == '.' && inPiece) {
                int pieceEnd = i;
                char[] toAdd = pieceArray(ogLine, pieceStart, pieceEnd);
                collection.add(toAdd);
                inPiece = false;
            }
        }

        return collection;
    }

    public char[] pieceArray(char[] ogLine, int start, int end){
        char[] answer = new char[ogLine.length];
        int j = 0;
        for(int i = start; i < end; i++){
            answer[j] = ogLine[i];
            j++;
        }

        return answer;
    }
    // => id ## type ## title ## director ## [cast] ## country ## date added ##
    // release year ## rating ## duration ## [listed in]
    //tem que resolver uma forma de printar coisas que tem lista
    public void printShow(){
        
    }

    //Gets e Setter abaixo
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

    public String[] getCast() {
        return cast;
    }

    public void setCast(String[] cast) {
        this.cast = cast;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getDate_added() {
        return date_added;
    }

    public void setDate_added(Date date_added) {
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

    public String[] getListed_in() {
        return listed_in;
    }

    public void setListed_in(String[] listed_in) {
        this.listed_in = listed_in;
    }
}

//Formato Data: September 24, 2021
class Date{
    private String month;
    private int dia;
    private int ano;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }
}