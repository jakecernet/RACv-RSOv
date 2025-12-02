/*
a)	Spišite metodo, ki kot argument lahko dobi celo število do obsega vrste 'long'. Nato izračuna vsoto vseh naravnih števil do vključno tega števila in vsoto vrne. 
Če vsota pri izračunu preseže največjo možno vrednost, se kot vsota vrne največja možna vrednost (max value?). Testen argument naj bo 4 (za vsoto 10).
b)	Program izračuna tri take vsote z naključnimi argumenti z vrednostmi nad 10000, vsako v lastni programski izvajalni niti in vse tri izračunane vsote izpiše na zaslon. 
Izpis naj se izvrši taki, da program za vsak proces v lastni vrstici izpiše ime niti, identifikator niti, število za katerega se je računala vsota in izračunana vsota. 
Uporabite minimalno število niti.

V pomoč vam je (ali pa tudi ne) lahko
    Thread t = Thread.currentThread();
    System.out.println("Ime niti: " + t.getName());
    System.out.println("ID niti: " + t.getId());
 */

public class naloga2 {
    public static long izracunajVsoto(long n) {
        long vsota = 0;
        for (long i = 1; i <= n; i++) {
            vsota += i;
            if (vsota < 0) {
                return Long.MAX_VALUE;
            }
        }
        return vsota;
    }

    public static void main(String[] args) {
        Runnable task = () -> {
            long randomNum = (long) (Math.random() * 10000) + 10001;
            long vsota = izracunajVsoto(randomNum);
            Thread t = Thread.currentThread();
            System.out.println("Ime niti: " + t.getName());
            System.out.println("ID niti: " + t.getId());
            System.out.println("Število: " + randomNum);
            System.out.println("Izračunana vsota: " + vsota);
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        Thread t3 = new Thread(task);

        t1.start();
        t2.start();
        t3.start();
    }
}
