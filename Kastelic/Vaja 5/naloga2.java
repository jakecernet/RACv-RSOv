/* 'Kačo' predstavimo z vrsto. Na začetku je ta vrsta dolga natanko 1 element, ki vsebuje ravninsko pozicijo in 'smer gibanja'. 
Premik kače izvedemo tako, da v glavo kače (rep vrste) dodamo nov element, katerega pozicijo izračunamo iz pozicije prejšnjega repa, 
ki mu 'prištejemo' smer, hkrati pa odstranimo element iz repa kače (glave vrste). Razmislite o uporabi double-ended strukture. 
Izvedite programsko simulacijo premika kače (premik 3 korake v smeri, spremenite smer, spet izvedete 3 korake. Vmes opazujete 'premikanje' kače
*/

import java.util.Deque;
import java.util.LinkedList; 

public class naloga2 {
    public static void main(String[] args) {
        Kaca kaca = new Kaca(new Pozicija(0, 0), new Smer());

        for (int i = 0; i < 3; i++) {
            kaca.premakni();
            System.out.println(kaca);
        }

        kaca.obrniLevo();

        for (int i = 0; i < 3; i++) {
            kaca.premakni();
            System.out.println(kaca);
        }

        kaca.obrniLevo();

        for (int i = 0; i < 3; i++) {
            kaca.premakni();
            System.out.println(kaca);
        }
    }
}

class Pozicija {
    int x;
    int y;

    public Pozicija(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Pozicija premakni(int dx, int dy) {
        return new Pozicija(this.x + dx, this.y + dy);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}

class Kaca {
    private Deque<Pozicija> telo;
    private Smer smer;

    public Kaca(Pozicija zacetnaPozicija, Smer zacetnaSmer) {
        telo = new LinkedList<>();
        telo.addFirst(zacetnaPozicija);
        this.smer = zacetnaSmer;
    }

    public void premakni() {
        Pozicija glava = telo.peekFirst();
        Pozicija novaPozicija = glava.premakni(smer.x, smer.y);
        telo.addFirst(novaPozicija);
        telo.removeLast();
    }

    public void obrniLevo() {
        smer.levo();
    }

    public void obrniDesno() {
        smer.desno();
    }

    @Override
    public String toString() {
        return "Kaca: " + telo.toString();
    }
}

class Smer {
    protected int x; // smer po x osi
    protected int y; // smer po y osi

    public Smer() {
        this.x = 1; // začetna smer desno
        this.y = 0;
    }

    public void levo() {
        int temp = x;
        x = -y;
        y = temp;
    }

    public void desno() {
        int temp = x;
        x = y;
        y = -temp;
    }

    @Override
    public String toString() {
        return "Smer: (" + x + ", " + y + ")";
    }
}