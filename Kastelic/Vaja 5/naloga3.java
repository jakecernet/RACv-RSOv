/* Generirajte 100 zaslonskih koordinat elementov, ki bodo služili za hranjenje kače; vsakič, ko glava kače trči ob ta element, 
naj se ji dolžina za en element podaljša ( pri premiku naprej, repa kače pač ne (od)brišete ). Za hitro detekcijo trka uporabite 
preverjanje obstoja koordinate v kolekciji vrste HashMap (glej realizacije z učnih ur).
Izvedite programsko simulacijo premikanja in prehranjevanja kača, kjer bodo vidni tako spremembe v smeri, kot premikanje, kot posledice prehranjevanja.
*/

public class naloga3 {
    public static void main(String[] args) {
        Kaca kaca = new Kaca(new Pozicija(0, 0), new Smer());

        java.util.HashMap<String, Pozicija> hrana = new java.util.HashMap<>();
        java.util.Random rnd = new java.util.Random(42);

        int min = -25, max = 25;
        while (hrana.size() < 100) {
            int x = rnd.nextInt(max - min + 1) + min;
            int y = rnd.nextInt(max - min + 1) + min;
            // ne dodaj hrane na začetno pozicijo glave
            if (x == 0 && y == 0) continue;

            Pozicija p = new Pozicija(x, y);
            String k = key(p);
            if (!hrana.containsKey(k)) {
                hrana.put(k, p);
            }
        }

        int koraki = 60;
        for (int i = 1; i <= koraki; i++) {
            Pozicija naslednja = kaca.naslednjaGlava();
            String k = key(naslednja);
            boolean boJedla = hrana.containsKey(k);

            kaca.premakni(boJedla);
            if (boJedla) {
                hrana.remove(k);
                System.out.println("POJE na " + naslednja);
            }

            // spremembe smeri za prikaz
            if (i % 10 == 0) {
                kaca.obrniLevo();
                System.out.println("OBRNI LEVO");
            } else if (i % 15 == 0) {
                kaca.obrniDesno();
                System.out.println("OBRNI DESNO");
            }

            System.out.println("Korak " + i + " | dolzina: " + kaca.dolzina() + " | preostala hrana: " + hrana.size());
            System.out.println(kaca);
        }
    }

    private static String key(Pozicija p) {
        return p.x + "," + p.y;
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
    private DvojnaVrsta<Pozicija> telo;
    private Smer smer;

    public Kaca(Pozicija zacetnaPozicija, Smer zacetnaSmer) {
        telo = new DvojnaVrsta<>();
        telo.addFirst(zacetnaPozicija);
        this.smer = zacetnaSmer;
    }

    public void premakni(boolean podaljsaj) {
        Pozicija glava = telo.peekFirst();
        Pozicija novaPozicija = glava.premakni(smer.x, smer.y);
        telo.addFirst(novaPozicija);
        if (!podaljsaj) {
            telo.removeLast();
        }
    }

    public Pozicija naslednjaGlava() {
        Pozicija glava = telo.peekFirst();
        return glava.premakni(smer.x, smer.y);
    }

    public void obrniLevo() {
        smer.levo();
    }

    public void obrniDesno() {
        smer.desno();
    }

    public int dolzina() {
        return telo.size();
    }

    @Override
    public String toString() {
        return "Kaca: " + telo;
    }
}

class Smer {
    public int x; // smer po x osi
    public int y; // smer po y osi

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

class DvojnaVrsta<T> {
    private class Vozel {
        T podatek;
        Vozel naslednji;
        Vozel prejsnji;

        Vozel(T podatek) {
            this.podatek = podatek;
        }
    }

    private Vozel glava;
    private Vozel rep;
    private int velikost;

    public DvojnaVrsta() {
        glava = null;
        rep = null;
        velikost = 0;
    }

    public void addFirst(T podatek) {
        Vozel novVozel = new Vozel(podatek);
        if (velikost == 0) {
            glava = novVozel;
            rep = novVozel;
        } else {
            novVozel.naslednji = glava;
            glava.prejsnji = novVozel;
            glava = novVozel;
        }
        velikost++;
    }

    public void removeLast() {
        if (velikost == 0) return;
        if (velikost == 1) {
            glava = null;
            rep = null;
        } else {
            rep = rep.prejsnji;
            rep.naslednji = null;
        }
        velikost--;
    }

    public T peekFirst() {
        if (velikost == 0) return null;
        return glava.podatek;
    }

    public int size() {
        return velikost;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Vozel trenutni = glava;
        while (trenutni != null) {
            sb.append(trenutni.podatek);
            if (trenutni.naslednji != null) {
                sb.append(", ");
            }
            trenutni = trenutni.naslednji;
        }
        sb.append("]");
        return sb.toString();
    }
}