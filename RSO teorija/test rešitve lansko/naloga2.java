/* Program Naloga2 kreira in zažene 100 procesov izpisovanja, katerih telo izvajanja je enako telesu izpisovanja v metodi izpisi100 
četrte naloge tega dokumenta. Proces/niti pri izvajanju torej izpisujejo števila. Ob koncu vsakega procesa pa naj se izpiše še število preostalih
(še nedokončanih, aktivnih, izvajanih) procesov. Spišite tak program.
*/

import java.util.Random;

public class naloga2 {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    izpisi100 izpisi = new izpisi100();
                    try {
                        izpisi.izpisi100();
                    } catch (izjema03Liho e) {
                        System.out.println("Ulovljena izjema03Liho: " + e.getMessage());
                    }
                    System.out.println("Preostali aktivni procesi: " + Thread.activeCount());
                }
            });
            thread.start();
        }
    }
}

class izpisi100{
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