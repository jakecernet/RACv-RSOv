/*
    Metoda izpisi100 objekta vrste Naloga05 pri zagonu izpiše naključnih 100 zaporednih naravnih števil, nato proži izjemo.
    Naključnih pomeni, da je prvo število zaporedja pseudo-naključno izbrano iz intervala [7, 49]. Če je zadnje izpisano število liho, 
    proži izjemo vrste izjema03Liho, sicer izjema03Sodo. Izjema03Liho je obravnavane vrste (managed), izjema03Sodo pa neobravnavane vrste (unmanaged).
    Zagonska metoda razreda Naloga04 izvede metodo izpisi100. Spišite:
        - potrebne deklaracije / definicije
        - opisan program z vsemi zahtevanimi metodami
*/

import java.util.Random;

public class naloga4 {
    public static void main(String[] args) {
        Naloga05 naloga = new Naloga05();
        try {
            naloga.izpisi100();
        } catch (izjema03Liho e) {
            System.out.println("Ulovljena izjema03Liho: " + e.getMessage());
        }
    }
}

class Naloga05 {
    public void izpisi100() throws izjema03Liho {
        Random rand = new Random();
        int start = rand.nextInt(43) + 7; // Naključno število iz [7, 49]
        int current = start;

        for (int i = 0; i < 100; i++) {
            System.out.println(current);
            current++;
        }

        if (current % 2 == 0) {
            throw new izjema03Sodo("Zadnje število je sodo: " + current);
        } else {
            throw new izjema03Liho("Zadnje število je liho: " + current);
        }
    }
}

class izjema03Liho extends Exception {
    public izjema03Liho(String message) {
        super(message);
    }
}

class izjema03Sodo extends RuntimeException {
    public izjema03Sodo(String message) {
        super(message);
    }
}