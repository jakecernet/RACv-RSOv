/* Recimo da se sočasno izvaja 20 takih procesov, kot so opredeljeni v nalogi 4. Želeli bi lepo oblikovan izpis, pri čemer bi se v vsaki zaslonski vrstici, 
izpisalo natanko po 80 znakov. (uporabite sinhronizacijo bloka, argument povzemite po prvi nalogi te vaje). */

public class naloga6 {
    private static final Object lock = new Object();
    private static int currentLineLength = 0;
    private static final int maxLineLength = 80;

    public static void main(String[] args) {
        final int maxNiti = 20;
        final int znakiNaNit = 5555;
        Thread[] niti = new Thread[maxNiti];
        char[] znaki = new char[maxNiti];

        for (int i = 0; i < maxNiti; i++) {
            znaki[i] = (char) ('A' + Math.random() * 26);
            final char znak = znaki[i];
            niti[i] = new Thread(() -> {
                for (int j = 0; j < znakiNaNit; j++) {
                    synchronized (lock) {
                        if (currentLineLength >= maxLineLength) {
                            System.out.println();
                            currentLineLength = 0;
                        }
                        System.out.print(znak);
                        currentLineLength++;
                    }
                }
            }, String.valueOf(i + 1));
            niti[i].start();
        }

        for (Thread nit : niti) {
            try {
                nit.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("\nSkupno število niti: " + maxNiti);
    }
}