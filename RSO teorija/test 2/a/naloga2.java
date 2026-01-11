/*  
2. Javanski program Naredi med svojim izvajanjem kreira 3 niti iste vrste (tipa) in jih zažene. 
Vsaka izmed niti v svoji življenjski dobi izračuna vsoto 20000 naključnih števil iz obsega od 0 do 10000. 
Vsoto vsaka nit računa po modulu 256. Ob zaključku izvajanja vsoto izpiše. Spišite tak (javanski) program.
*/

class MNIt extends Thread {
    public void run() {
        int vsota = 0;
        for (int i = 0; i < 20000; i++) {
            int nakljucnoStevilo = (int) (Math.random() * 10001); // Naključno število med 0 in 10000
            vsota += nakljucnoStevilo;
        }
        vsota = vsota % 256; // idk, mejb je to mišleno s "po modulu 256"
        System.out.println("Vsota niti " + Thread.currentThread().getName() + " je: " + vsota);
    }
}

// kle je ime dejansko Naredi, sam kr mora bit ime fajla isto k ime razreda
public class naloga2 {
    public static void main(String[] args) {
        MNIt nit1 = new MNIt();
        MNIt nit2 = new MNIt();
        MNIt nit3 = new MNIt();

        nit1.start();
        nit2.start();
        nit3.start();
    }
}
