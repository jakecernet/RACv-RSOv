
/* Izvedite štetje besed (kolikokrat se posamezna beseda pojavi). 
Uporabite razprševanje v HashMap (element naj bo ["beseda",števec], pri čemer naj bo beseda ključ). 
Statistka besed naj se po končanem postopku izpiše na zaslon. */
import java.util.*;
import java.io.*;

public class naloga2 {
    File file = new File("./test.txt");
    HashMap<String, Integer> besedeMap = new HashMap<>();

    public void stejeBesede() {
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String vrstica = sc.nextLine();
                String[] besede = vrstica.split("\\s+");
                for (String beseda : besede) {
                    beseda = beseda.toLowerCase();
                    if (besedeMap.containsKey(beseda)) {
                        besedeMap.put(beseda, besedeMap.get(beseda) + 1);
                    } else {
                        besedeMap.put(beseda, 1);
                    }
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Datoteka ni bila najdena.");
        }
    }

    public void izpisiStatistiko() {
        for (Map.Entry<String, Integer> entry : besedeMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public static void main(String[] args) {
        naloga2 naloga = new naloga2();
        naloga.stejeBesede();
        naloga.izpisiStatistiko();
    }
}