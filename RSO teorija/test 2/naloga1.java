/* Dan je javanski razred

    class Rnb {
        public Rnb(){this.a=0;}
    }
    
    Odpravite napake v definicija razreda oz. ga smiselno dopolnite, da bo mogoče uspešno izvesti stavek, kot:

    new Thread(new Rnb(3)).start();
*/

// Varianta 1: brez runnable
class Rnb1 extends Thread {
    private int a;

    public Rnb1(int a) {
        this.a = a;
    }

    public void run() {
        System.out.println("Vrednost a je: " + a);
    }
}

// Varianta 2: z runnable (tko kt kastelic hoče)
class Rnb2 implements Runnable {
    private int a;

    public Rnb2(int a) {
        this.a = a;
    }

    public void run() {
        System.out.println("Vrednost a je: " + a);
    }
}

public class naloga1 {
    public static void main(String[] args) {
        // Uporaba variante 1
        new Rnb1(3).start();

        // Uporaba variante 2
        new Thread(new Rnb2(3)).start();
    }
}