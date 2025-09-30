/* 
    Element iz naloge 1 smo rahlo preoblikovali za realizacijo naloge 5; (prilagoditev tipom?)

    class Element {
       private int vrednost;
       private Element naslednji;
       public Element(int vrednost){naslednji=null; this.vrednost=vrednost;}
    }
       
Več ali manj gre za 3 enake definicije, zgolj mastno označeni kvalifikatorji se razlikujejo. Z uporabo generikov (generičnih tipov) lahko zadevo poenostavimo:

    class Element<T> {
        private T vrednost;
        private Element naslednji;
        public Element(T vrednost){naslednji=null; this.vrednost=vrednost;}
        public T getVrednost(){ return vrednost; }
    }

Tip T je lahko 'karkoli', se določi ob kreiranju elementa (z rezervacijo pomnilnika):

    Element<Integer> e1 = new Element<Integer>(12); System.out.println( e1.getVrednost() );
    Element<Double> e2 = new Element(13.33);
    Element e3 = new Element("eckapecka");

-	Tip je lahko zgolj referenčni(objektni), nikoli primitiv
-	Kje vse je potrebno pisati 'tip' je rahlo odvisno od različice Java, preskusite. Načeloma se določi avtomatično (od uvedbe var dalje)
-	Problem :   =new Element<String>(82.3); evaluira tip in vrednost argumenta, ter ugotovi, da ne ustrezata !

Realizirajte sezname iz naloge 5 z uporabo generikov .
*/

public class naloga6 {
    public static void main(String[] args) {
        Seznam<Integer> intSeznam = new Seznam<>();
        intSeznam.dodajNaKonec(10);
        intSeznam.dodajNaKonec(20);
        intSeznam.dodajNaKonec(30);
        intSeznam.prikaziVse();
        System.out.println("Dolzina: " + intSeznam.length());
        System.out.println("Zadnji element: " + intSeznam.vrniZadnji().getVrednost());

        Seznam<String> stringSeznam = new Seznam<>();
        stringSeznam.dodajNaKonec("Prvi");
        stringSeznam.dodajNaKonec("Drugi");
        stringSeznam.dodajNaKonec("Tretji");
        stringSeznam.prikaziVse();
        System.out.println("Dolzina: " + stringSeznam.length());
        System.out.println("Zadnji element: " + stringSeznam.vrniZadnji().getVrednost());

        Seznam<Double> doubleSeznam = new Seznam<>();
        doubleSeznam.dodajNaKonec(1.1);
        doubleSeznam.dodajNaKonec(2.2);
        doubleSeznam.dodajNaKonec(3.3);
        doubleSeznam.prikaziVse();
        System.out.println("Dolzina: " + doubleSeznam.length());
        System.out.println("Zadnji element: " + doubleSeznam.vrniZadnji().getVrednost());
    }
}

class Element<T> {
    private T vrednost;
    private Element<T> naslednji = null;

    public Element(T vrednost) {
        this.vrednost = vrednost;
    }

    public T getVrednost() {
        return vrednost;
    }

    public Element<T> getNaslednji() {
        return naslednji;
    }

    public void setNaslednji(Element<T> naslednji) {
        this.naslednji = naslednji;
    }
}

class Seznam<T> {
    private Element<T> zacetek = null;

    void dodajNaKonec(T vrednost) {
        Element<T> novElement = new Element<T>(vrednost);
        if (zacetek == null) {
            zacetek = novElement;
        } else {
            Element<T> trenutni = zacetek;
            while (trenutni.getNaslednji() != null) {
                trenutni = trenutni.getNaslednji();
            }
            trenutni.setNaslednji(novElement);
        }
    }

    Element<T> vrniZadnji() {
        if (zacetek == null) {
            return null;
        }
        Element<T> trenutni = zacetek;
        while (trenutni.getNaslednji() != null) {
            trenutni = trenutni.getNaslednji();
        }
        return trenutni;
    }

    void prikaziVse() {
        Element<T> trenutni = zacetek;
        while (trenutni != null) {
            System.out.print(trenutni.getVrednost() + " ");
            trenutni = trenutni.getNaslednji();
        }
        System.out.println();
    }

    int length() {
        int count = 0;
        Element<T> trenutni = zacetek;
        while (trenutni != null) {
            count++;
            trenutni = trenutni.getNaslednji();
        }
        return count;
    }
}