/* 'Kačo' predstavimo z vrsto. Na začetku je ta vrsta dolga natanko 1 element, ki vsebuje ravninsko pozicijo in 'smer gibanja'. 
Premik kače izvedemo tako, da v glavo kače (rep vrste) dodamo nov element, katerega pozicijo izračunamo iz pozicije prejšnjega repa, 
ki mu 'prištejemo' smer, hkrati pa odstranimo element iz repa kače (glave vrste). Razmislite o uporabi double-ended strukture. 
Izvedite programsko simulacijo premika kače (premik 3 korake v smeri, spremenite smer, spet izvedete 3 korake. Vmes opazujete 'premikanje' kače
*/

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
    private DvojnaVrsta<Pozicija> telo;
    private Smer smer;

    public Kaca(Pozicija zacetnaPozicija, Smer zacetnaSmer) {
        telo = new DvojnaVrsta<>();
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

class DvojnaVrsta<T> {
    private static class Vozlisce<E> {
        E v;
        Vozlisce<E> prev, next;

        Vozlisce(E v) {
            this.v = v;
        }
    }

    private Vozlisce<T> head; // first
    private Vozlisce<T> tail; // last
    private int size;

    public void addFirst(T e) {
        Vozlisce<T> n = new Vozlisce<>(e);
        if (head == null) {
            head = tail = n;
        } else {
            n.next = head;
            head.prev = n;
            head = n;
        }
        size++;
    }

    public void addLast(T e) {
        Vozlisce<T> n = new Vozlisce<>(e);
        if (tail == null) {
            head = tail = n;
        } else {
            n.prev = tail;
            tail.next = n;
            tail = n;
        }
        size++;
    }

    public T removeFirst() {
        if (head == null)
            return null;
        T val = head.v;
        head = head.next;
        if (head != null)
            head.prev = null;
        else
            tail = null;
        size--;
        return val;
    }

    public T removeLast() {
        if (tail == null)
            return null;
        T val = tail.v;
        tail = tail.prev;
        if (tail != null)
            tail.next = null;
        else
            head = null;
        size--;
        return val;
    }

    public T peekFirst() {
        return head != null ? head.v : null;
    }

    public T peekLast() {
        return tail != null ? tail.v : null;
    }

    public int size() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Vozlisce<T> cur = head;
        while (cur != null) {
            sb.append(cur.v);
            cur = cur.next;
            if (cur != null)
                sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}