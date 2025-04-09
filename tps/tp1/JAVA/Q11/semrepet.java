import java.util.*;

public class semrepet {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean stop = false;

        while (!stop) {
            String entrada = scanner.nextLine();
            if (entrada.equals("FIM")) {
                stop = true;
            } else {
                System.out.println();
            }
        }

        scanner.close();
    }
}
