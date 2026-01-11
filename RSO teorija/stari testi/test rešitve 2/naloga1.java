/*
 * Dan je javanski razred:
 * 
 * class Mr implement Runnable { public Mr(int a){this.a=a; } }
 * 
 * Odpravite napake v definiciji razreda oz. ga dopolnite, da bo mogoče
 * narediti primerek takega razreda kot:
 * 
 * Mr obj = new Mr(3);
 */

class Mr implements Runnable {
    private int a;

    public Mr(int a) {
        this.a = a;
    }

    public void run() {
        System.out.println("Vrednost a je: " + a);
    }
}