/* 
    Vmesnik NarascajoceUrejen definira metode, potrebne, da se ustvari naraščajo urejen linearen, enostransko povezan seznam. Recimo, da SeznamInt implementira še ta vmesnik:

    interface NarascajoUrejen{
        public void vstavi(int vrednost);  // vstavi vrednost v seznam tako, da je ta urejen
    }

    … in ponovite realizacijo naloge 3.
*/

interface NarascajoUrejen {
    public void vstavi(int vrednost);  // vstavi vrednost v seznam tako, da je ta urejen
}

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

class SeznamInt extends SeznamAbsI implements NarascajoUrejen {
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
            System.out.print(trenutni.getVrednost() + " ");
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
        int max = zacetek.getVrednost();
        ElementInt trenutni = zacetek.getNaslednji();
        while (trenutni != null) {
            if (trenutni.getVrednost() > max) {
                max = trenutni.getVrednost();
            }
            trenutni = trenutni.getNaslednji();
        }
        return max;
    }

    @Override
    public void vstavi(int vrednost) {
        ElementInt novElement = new ElementInt(vrednost);
        if (zacetek == null || zacetek.getVrednost() >= vrednost) {
            novElement.setNaslednji(zacetek);
            zacetek = novElement;
        } else {
            ElementInt trenutni = zacetek;
            while (trenutni.getNaslednji() != null && trenutni.getNaslednji().getVrednost() < vrednost) {
                trenutni = trenutni.getNaslednji();
            }
            novElement.setNaslednji(trenutni.getNaslednji());
            trenutni.setNaslednji(novElement);
        }
    }   
}

class ElementInt {
    int vrednost;
    ElementInt naslednji = null;

    public ElementInt(int vrednost) {
        this.vrednost = vrednost;
    }

    public int getVrednost() {
        return vrednost;
    }

    public ElementInt getNaslednji() {
        return naslednji;
    }

    public void setNaslednji(ElementInt naslednji) {
        this.naslednji = naslednji;
    }
}