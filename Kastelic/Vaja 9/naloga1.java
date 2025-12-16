/* Vsebino datoteke izpišite na zaslon, pri tem naj se vsaka posamezna beseda izpiše v lastno zaslonsko vrstico. */

import java.util.*;
import java.io.*;

public class naloga1 {
    File file = new File("./test.txt");

    public void izpisiBesede() {
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String vrstica = sc.nextLine();
                String[] besede = vrstica.split("\\s+");
                for (String beseda : besede) {
                    System.out.println(beseda);
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Datoteka ni bila najdena.");
        }
    }

    public static void main(String[] args) {
        naloga1 naloga = new naloga1();
        naloga.izpisiBesede();
    }
}
