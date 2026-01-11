/*
 * Javanski program Naredi med svojim izvajanjem kreira 3 niti vrste (tipa) MNit
 * in jih zažene.
 * Primerek vrste MNit v svoji življenski dobi izpiše na zaslon 20000 enakih
 * znakov.
 * Znak se niti določi ob njenek kreiranju. Spišite tak (javanski) program.
 */

class MNit extends Thread {
    char znak;

    public MNit(char znak) {
        this.znak = znak;
    }

    public void run() {
        for (int i = 0; i < 20000; i++) {
            System.out.print(znak);
        }
    }
}

class Naredi {
    public void zalaufej() {
        MNit nit1 = new MNit('*');
        MNit nit2 = new MNit('#');
        MNit nit3 = new MNit('@');

        nit1.start();
        nit2.start();
        nit3.start();
    }
}

public class naloga2 {
    public static void main(String[] args) {
        Naredi n = new Naredi();
        n.zalaufej();
    }
}