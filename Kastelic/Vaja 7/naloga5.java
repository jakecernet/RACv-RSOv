/* Javanski program sproži v sočasno izvajanje 30 procesov, kot jih opredeljuje naloga 4, le da je število iteracij vsakega procesa povečano na 55555. 
Izvajanje vseh procesov naj se prekine natanko takrat, ko se izteče prvi izmed zagnanih procesov. */

public class naloga5 {
    public static void main(String[] args) {
        final int maxNiti = 30;
        final int znakiNaNit = 55555;
        Thread[] niti = new Thread[maxNiti];
        char[] znaki = new char[maxNiti];

        for (int i = 0; i < maxNiti; i++) {
            znaki[i] = (char) ('A' + Math.random() * 26);
            final char znak = znaki[i];
            niti[i] = new Thread(() -> {
                for (int j = 0; j < znakiNaNit; j++) {
                    System.out.print(znak);
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
