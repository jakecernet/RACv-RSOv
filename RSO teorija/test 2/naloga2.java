/*  Javanski program Naredi med svojim izvajanjem kreira 3 niti vrste(tipa) MNit in jih zažene.
    Primerek vrste MNIt v svoji življenjski dobi izpiše na zaslon 20000 enakih znakov. 
    Znak se niti določi ob njenem kreiranju. Spišite tak (javanski) program.
*/

class MNIt extends Thread {
    char znak;

    public MNIt(char znak) {
        this.znak = znak;
    }

    public void run() {
        for (int i = 0; i < 20000; i++) {
            System.out.print(znak);
        }
    }
}

// kle je ime razreda dejansko Naredi, sam ker mora bit ime fajla isto k ime razreda
public class naloga2 {
    public static void main(String[] args) {
        MNIt nit1 = new MNIt('*');
        MNIt nit2 = new MNIt('#');
        MNIt nit3 = new MNIt('@');

        nit1.start();
        nit2.start();
        nit3.start();
    }
}