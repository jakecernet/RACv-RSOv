/* 1. Dan je javanski razred:

    class Mr implement Runnable { public Mr(int a){ this.a=a; } }

Odpravite napake v definiciji razreda oz. ga dopolnite, da bo mogoče narediti primerke takega razreda kot:

Mr obj = new Mr(3);
*/

class Mr implements Runnable {
    private int a;

    public Mr(int a) {
        this.a = a;
    }

    @Override
    public void run() {
        System.out.println("Vrednost a je: " + a);
    }
}

public class naloga1 {
    public static void main(String[] args) {
        Mr obj = new Mr(3);
        Thread thread = new Thread(obj);
        thread.start();
    }
}