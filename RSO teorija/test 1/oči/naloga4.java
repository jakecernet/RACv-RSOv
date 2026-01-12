/* Metoda izpisi100 objekta vrste Naloga04 pri zagonu izpiše naključnih 100 zaporednih naravnih števil, nato proži izjemo. 
Naključnih pomeni, da je prvo število zaporedja pseudo-naključno izbrano iz intervala [7,49]. 
Če je zadnje izpisano število tako, da je njegova zadnja števka manjša od 5, proži izjemo vrste Izjema04Navzdol, sicer Izjema04Navzgor. 
Izjema04Navzdol naj bo obravnavane vrste (managed), Izjema04Navzgor pa neobravnavane (unmanaged). Zagonska metoda razreda Naloga04 izvede metodo izpisi 100. 
Spišite:
    a) potrebne deklaracije/definicije (1t),
    b) opisan program zz vsemi zahtevanimi metodami (1+1).
*/

public class naloga4 {
    public void izpisi100() throws Izjema04Navzdol, Izjema04Navzgor {
        int stevilo = (int) (Math.random() * (49 - 7 + 1)) + 7;
        System.out.println(stevilo);

        for (int i = 1; i < 100; i++) {
            stevilo++;
            System.out.println(stevilo);
        }

        if (stevilo % 10 < 5) {
            throw new Izjema04Navzdol();
        } else {
            throw new Izjema04Navzgor();
        }
    }

    public static void main(String[] args) {
        naloga4 naloga = new naloga4();
        try {
            naloga.izpisi100();
        } catch (Izjema04Navzdol e) {
        }
    }
}

class Izjema04Navzdol extends Exception {
    public Izjema04Navzdol() {
        System.out.println("Zadnja števka je manjša od 5.");
    }
}

class Izjema04Navzgor extends RuntimeException {
    public Izjema04Navzgor() {
        System.out.println("Zadnja števka je večja ali enaka 5.");
    }
}