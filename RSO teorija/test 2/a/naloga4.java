/* Recimo, da v javanskem programu ustvarite in zaženete poljubno število niti vrste MNit. 
Kako dosežete, da se izvajanje vseh niti konča, ko prva izmed njih doseže 7000 tisoč iteracij? 
Spišite ustrezno javansko kodo. */

// idk to ne vem čist kako narest

class MNIt extends Thread {
    char znak;

    public MNIt(char znak) {
        this.znak = znak;
    }

    public void run() {
        for (int i = 1; i <= 10000; i++) {
            System.out.println("Nit " + znak + " - Iteracija: " + i);
            if (i == 7000) {
                System.out.println("Nit " + znak + " je dosegla 7000 iteracij. Končujem vse niti.");
                System.exit(0); // Ustavi celoten program, kar konča vse niti
            }
        }
    }
}

public class naloga4 {
    public static void main(String[] args) {
        MNIt nitA = new MNIt('A');
        MNIt nitB = new MNIt('B');
        MNIt nitC = new MNIt('C');

        nitA.start();
        nitB.start();
        nitC.start();
    }
}
