/* Bi znali enako kot v naloga 3 narediti s stavki ? */
import java.util.*;
import java.io.*;

public class naloga4 {
    File file = new File("./test.txt");
    ArrayList<String> listStavki = new ArrayList<>();

    public void preberiStavke() {
        try {
            Scanner sc = new Scanner(file);
            // Razcep na "stavke" (deli povedi) glede na vejice, podpične in veznike in/ali/ter
            sc.useDelimiter("(?i)\\s*(,|;|\\b(?:in|ali|ter)\\b)\\s*");
            while (sc.hasNext()) {
                String stavek = sc.next().trim();
                listStavki.add(stavek);
            }
            sc.close();
            for (String s : listStavki) {
                System.out.println(s); // Za preverjanje vsebine
            }
            System.out.println("\nSkupno število stavkov: " + listStavki.size() + "\n");
        } catch (FileNotFoundException e) {
            System.out.println("Datoteka ni bila najdena.");
        }
    }

    public static void main(String[] args) {
        naloga4 naloga = new naloga4();
        naloga.preberiStavke();
    }
}
