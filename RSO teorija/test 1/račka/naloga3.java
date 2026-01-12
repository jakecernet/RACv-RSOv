/* Metoda ponavljajSt/2 objekta vrste (tipa) Naloga03 N-krat izpiše vrednost znaka zn. N in zn sta argumenta metodi. 
Ovojn(a) metoda istega objekta ovoj/2 kliče ponavljajSt, ob njenem zaključku pa (sproži) izjemo vrste Naloga03. 
Zagonska (driver) metoda razreda Naloga03 proži ovoj/2. Servis izjeme izpiše besedilo 'konec izpisovanja'. Spišite:
    a) definicije vseh potrebnih razredov,
    b) metodi ponavljaj in ovoj,
    c) zagonsko metodo razreda s prestrezanjem izjeme in njenim servisom. 
*/

public class naloga3 {
    void ponavljajSt(int N, char zn) {
        for (int i = 0; i < N; i++) {
            System.out.print(zn);
        }
        System.out.println();
    }

    void ovoj(int N, char zn) throws Izjema03 {
        ponavljajSt(N, zn);
        throw new Izjema03();
    }

    public static void main(String[] args) {
        naloga3 obj = new naloga3();
        try {
            obj.ovoj(5, '*');
        } catch (Izjema03 e) {
        }
    }
}

class Izjema03 extends Exception {
    public Izjema03() {
        System.out.println("konec izpisovanja");
    }
}