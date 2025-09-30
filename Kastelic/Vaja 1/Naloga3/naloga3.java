/* Spišite demonstracijski program, ki bo ustvaril seznam 20 elementov. */

public class naloga3 {
    public static void main(String[] args) {
        Seznam seznam = new Seznam();
        for (int i = 1; i <= 20; i++) {
            seznam.dodajElement(i);
        }
        seznam.izpisiSeznam();
    }
}

class Seznam {
    private ElementInt prvi;
    private ElementInt zadnji;

    public Seznam() {
        this.prvi = null;
        this.zadnji = null;
    }

    public void dodajElement(int vrednost) {
        ElementInt novElement = new ElementInt(vrednost);
        if (prvi == null) {
            prvi = novElement;
            zadnji = novElement;
        } else {
            zadnji.setNaslednji(novElement);
            zadnji = novElement;
        }
    }

    public void izpisiSeznam() {
        ElementInt trenutni = prvi;
        while (trenutni != null) {
            System.out.print(trenutni.vrednost + " ");
            trenutni = trenutni.getNaslednji();
        }
        System.out.println();
    }
}

class ElementInt {
    int vrednost;
    private ElementInt naslednji;

    public ElementInt(int vrednost) {
        this.vrednost = vrednost;
        this.naslednji = null;
    }

    public void setNaslednji(ElementInt naslednji) {
        this.naslednji = naslednji;
    }

    public ElementInt getNaslednji() {
        return naslednji;
    }
}
