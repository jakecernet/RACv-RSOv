/* Javanski proces z imenom '1' na zaslon izpisuje znake 'A' , tisti z imenom '2' znake 'Z', … Imena procesov so enolična, znaki pa so naključno generirani s konstrukcijo procesa. 
Vsak proces na zaslon izpiše 5555 znakov, nato se zaključi. Program niti (in s tem izpise) generira toliko časa, dokler se jih sočasno ne izvaja natanko 100.
Ob koncu izvajanja bi želeli tudi izvedeti, koliko niti smo dejansko sprožili.*/

public class naloga4 {
    public static void main(String[] args) {
        final int maxNiti = 100;
        final int znakiNaNit = 5555;
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
