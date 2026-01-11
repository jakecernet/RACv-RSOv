/*
 * Recimo, da v javanskem programu ustvarite in zaženete 2 niti vrste Nit.
 * Kako dosežete, da prva izmed obeh zagnanih niti z izvajanjem počaka na konec
 * izvajanja druge? Spišite ustrezno sekvenco javanske kode.
 */

class Nit extends Thread {
    public void run() {
        System.out.println("Nit " + Thread.currentThread().getName() + " se izvaja.");
    }
}

public class naloga4 {
    public static void main(String[] args) {
        Nit nit1 = new Nit();
        Nit nit2 = new Nit();

        nit1.start();
        nit2.start();
        try {
            nit1.join(); // Čakanje na konec izvajanja nit1
            nit2.join();
        } catch (InterruptedException e) {
        }
    }
}