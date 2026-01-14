
/* Spišite javanski program, ki rezultat naloge 3 zapiše v vrsto (npr. LinkedList) tako, da je vsaka poved
predstavljena kot element-string v tej vrsti. Program ob vsakem zagonu naključno izbere 3 povedi iz strukture in jih izpiše na zaslon. */
import java.util.*;
import java.util.LinkedList;
import java.io.*;

public class naloga6 {
    File file = new File("./test.txt");
    LinkedList<String> listPovedi = new LinkedList<>();

    public void preberiPovedi() {
        try {
            Scanner sc = new Scanner(file);
            sc.useDelimiter("(?<=[.!?])\\s*"); // Razcep na povedi glede na ločila
            while (sc.hasNext()) {
                String poved = sc.next().trim();
                listPovedi.add(poved);
            }
            sc.close();
            for (String p : listPovedi) {
                System.out.println(p); // Za preverjanje vsebine
            }
            System.out.println("\nSkupno število povedi: " + listPovedi.size() + "\n");
        } catch (FileNotFoundException e) {
            System.out.println("Datoteka ni bila najdena.");
        }
    }

    public static void main(String[] args) {
        naloga6 naloga = new naloga6();
        naloga.preberiPovedi();
        Random rand = new Random();
        int size = naloga.listPovedi.size();
        for (int i = 0; i < 3; i++) {
            int randomIndex = rand.nextInt(size);
            System.out.println(naloga.listPovedi.get(randomIndex));
        }
    }
}
