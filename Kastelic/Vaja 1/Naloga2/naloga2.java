/* 
Razred SeznamInt je specializacija razreda SeznamAbsI :
class SeznamInt extends SeznamAbsI {}

abstract class SeznamAbsI{
       private ElementInt zacetek = null;                	// vedno pokaže začetek seznama
       abstract void dodajNaKonec(int vrednost);   //doda nov element na konec seznama
       public ElementInt vrniPrvi(){ return zacetek; };
       abstract ElementInt vrniZadnji();
       abstract void prikaziVse();                    // izpiše vrednosti vseh el. seznama
       abstract int length();                         // vrne št. elmentov seznama
       abstract int getMaxValue();                    // vrne največjo vrednost iz seznama
}
Implementirajte ga.
*/

abstract class SeznamAbsI {
    private ElementInt zacetek = null; // vedno pokaže začetek seznama

    abstract void dodajNaKonec(int vrednost); // doda nov element na konec seznama

    public ElementInt vrniPrvi() {
        return zacetek;
    };

    abstract ElementInt vrniZadnji();

    abstract void prikaziVse(); // izpiše vrednosti vseh el. seznama

    abstract int length(); // vrne št. elmentov seznam

    abstract int getMaxValue(); // vrne največjo vrednost iz seznama
}

class SeznamInt extends SeznamAbsI {
    private ElementInt zacetek = null;

    @Override
    void dodajNaKonec(int vrednost) {
        ElementInt novElement = new ElementInt(vrednost);
        if (zacetek == null) {
            zacetek = novElement;
        } else {
            ElementInt trenutni = zacetek;
            while (trenutni.getNaslednji() != null) {
                trenutni = trenutni.getNaslednji();
            }
            trenutni.setNaslednji(novElement);
        }
    }

    @Override
    ElementInt vrniZadnji() {
        if (zacetek == null) {
            return null;
        }
        ElementInt trenutni = zacetek;
        while (trenutni.getNaslednji() != null) {
            trenutni = trenutni.getNaslednji();
        }
        return trenutni;
    }

    @Override
    void prikaziVse() {
        ElementInt trenutni = zacetek;
        while (trenutni != null) {
            System.out.print(trenutni.vrednost + " ");
            trenutni = trenutni.getNaslednji();
        }
        System.out.println();
    }

    @Override
    int length() {
        int count = 0;
        ElementInt trenutni = zacetek;
        while (trenutni != null) {
            count++;
            trenutni = trenutni.getNaslednji();
        }
        return count;
    }

    @Override
    int getMaxValue() {
        if (zacetek == null) {
            throw new IllegalStateException("Seznam je prazen");
        }
        int max = zacetek.vrednost;
        ElementInt trenutni = zacetek.getNaslednji();
        while (trenutni != null) {
            if (trenutni.vrednost > max) {
                max = trenutni.vrednost;
            }
            trenutni = trenutni.getNaslednji();
        }
        return max;
    }
}

class ElementInt {
    int vrednost;
    private ElementInt naslednji = null;

    public ElementInt(int vrednost) {
        this.vrednost = vrednost;
    }

    public ElementInt getNaslednji() {
        return naslednji;
    }

    public void setNaslednji(ElementInt naslednji) {
        this.naslednji = naslednji;
    }
}