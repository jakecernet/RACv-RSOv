/* Metoda ponavljajSt/2 objekta vrste(tipa) Naloga03 N krat izpiše vrednost znaka zn. N in zn sta argumenta metodi. 
Ovojna metoda istega objekta ovoj/2 kliče ponavljajSt, ob njenem zaključku pa (s)proži izjemo vrste Izjema03. 
Zagonska(driver) metoda razreda Naloga03 proži ovoj/2. Servis izjeme izpiše besedilo 'konec izpisovanja'. Spišite:
    - definicije vseh potrebnih razredov,
    - metodi ponavljaj in ovoj,
    - zagonsko metodo razreda s prestrezanjem izjeme in njenim servisom. */

public class naloga3 {
    public void ponavljajSt(char zn, int N) {
        if (N == 0)
            return;
        System.out.print(zn);
        ponavljajSt(zn, N - 1);
    }

    public void ovoj(char zn, int N) throws Izjema03 {
        ponavljajSt(zn, N);
        throw new Izjema03();
    }

    public static void main(String[] args) {
        naloga3 naloga = new naloga3();
        try {
            naloga.ovoj('*', 5);
        } catch (Izjema03 e) {
            // Servis izjeme
        }
    }
}

class Izjema03 extends Exception {
    public Izjema03() {
        System.out.println();
        System.out.println("konec izpisovanja");
    }
}
