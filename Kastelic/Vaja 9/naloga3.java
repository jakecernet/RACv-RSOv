/* Razcepite vsebino datoteke na posamezne povedi. 
Povedi izpišete na zaslon tako, da se izpis vsake povedi prične v novi vrstici, ki je od prejšnje povedi ločna z vrstico s fiksno vsebino 
/** ---------------- **/
import java.util.*;
import java.io.*;

public class naloga3 {
    File file = new File("./test.txt");

    public void izpisiPovedi() {
        try {
            Scanner sc = new Scanner(file);
            sc.useDelimiter("(?<=[.!?])\\s*"); // Razcep na povedi glede na ločila
            while (sc.hasNext()) {
                String poved = sc.next().trim();
                System.out.println("/** ---------------- **/");
                System.out.println(poved);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Datoteka ni bila najdena.");
        }
    }

    public static void main(String[] args) {
        naloga3 naloga = new naloga3();
        naloga.izpisiPovedi();
    }
}
