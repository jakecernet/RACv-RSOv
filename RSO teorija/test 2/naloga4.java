/*  Recimo, da v javanskem programu ustvarite in zaženete 2 niti vrste Nit.
    Kako dosežete, da prva izmed obeh zgeneriranih niti z izvajanjem počaka na konec izvajanja druge?
    Spišite ustrezno sekvenco javanske kode.
*/

public class naloga4 {
    public static void main(String[] args) {
        Nit nit1 = new Nit("Prva");
        Nit nit2 = new Nit("Druga");

        nit2.start();
        try {
            nit2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        nit1.start();
    }
}

class Nit extends Thread {
    private String ime;

    public Nit(String ime) {
        this.ime = ime;
    }

    public void run() {
        System.out.println("Nit " + ime + " se izvaja.");
    }
}
