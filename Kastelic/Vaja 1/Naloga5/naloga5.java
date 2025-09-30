/* 
    Seznam v predhodnih nalogah je (lepo) tipiziran s primitivom vrste int. Spišite še realizaciji, 
    pri katerih je element vrednost: a) vrste double in b) vrste String največje dolžine 10 znakov.
*/

interface NarascajoUrejen {
    public void vstavi(int vrednost); // vstavi vrednost v seznam tako, da je ta urejen
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

class elementDouble {
    double vrednost;
    elementDouble naslednji = null;

    public elementDouble(double vrednost) {
        this.vrednost = vrednost;
    }

    public double getVrednost() {
        return vrednost;
    }

    public elementDouble getNaslednji() {
        return naslednji;
    }

    public void setNaslednji(elementDouble naslednji) {
        this.naslednji = naslednji;
    }
}

class elementString {
    String vrednost;
    elementString naslednji = null;

    public elementString(String vrednost) {
        if (vrednost.length() > 10) {
            throw new IllegalArgumentException("String je daljši od 10 znakov");
        }
        this.vrednost = vrednost;
    }

    public String getVrednost() {
        return vrednost;
    }

    public elementString getNaslednji() {
        return naslednji;
    }

    public void setNaslednji(elementString naslednji) {
        this.naslednji = naslednji;
    }
}

class SeznamDouble {
    private elementDouble zacetek = null;

    void dodajNaKonec(double vrednost) {
        elementDouble novElement = new elementDouble(vrednost);
        if (zacetek == null) {
            zacetek = novElement;
        } else {
            elementDouble trenutni = zacetek;
            while (trenutni.getNaslednji() != null) {
                trenutni = trenutni.getNaslednji();
            }
            trenutni.setNaslednji(novElement);
        }
    }

    elementDouble vrniZadnji() {
        if (zacetek == null) {
            return null;
        }
        elementDouble trenutni = zacetek;
        while (trenutni.getNaslednji() != null) {
            trenutni = trenutni.getNaslednji();
        }
        return trenutni;
    }

    void prikaziVse() {
        elementDouble trenutni = zacetek;
        while (trenutni != null) {
            System.out.print(trenutni.getVrednost() + " ");
            trenutni = trenutni.getNaslednji();
        }
        System.out.println();
    }

    int length() {
        int count = 0;
        elementDouble trenutni = zacetek;
        while (trenutni != null) {
            count++;
            trenutni = trenutni.getNaslednji();
        }
        return count;
    }

    double getMaxValue() {
        if (zacetek == null) {
            throw new IllegalStateException("Seznam je prazen");
        }
        double max = zacetek.getVrednost();
        elementDouble trenutni = zacetek.getNaslednji();
        while (trenutni != null) {
            if (trenutni.getVrednost() > max) {
                max = trenutni.getVrednost();
            }
            trenutni = trenutni.getNaslednji();
        }
        return max;
    }
}

class SeznamString {
    private elementString zacetek = null;

    void dodajNaKonec(String vrednost) {
        elementString novElement = new elementString(vrednost);
        if (zacetek == null) {
            zacetek = novElement;
        } else {
            elementString trenutni = zacetek;
            while (trenutni.getNaslednji() != null) {
                trenutni = trenutni.getNaslednji();
            }
            trenutni.setNaslednji(novElement);
        }
    }

    elementString vrniZadnji() {
        if (zacetek == null) {
            return null;
        }
        elementString trenutni = zacetek;
        while (trenutni.getNaslednji() != null) {
            trenutni = trenutni.getNaslednji();
        }
        return trenutni;
    }

    void prikaziVse() {
        elementString trenutni = zacetek;
        while (trenutni != null) {
            System.out.print(trenutni.getVrednost() + " ");
            trenutni = trenutni.getNaslednji();
        }
        System.out.println();
    }

    int length() {
        int count = 0;
        elementString trenutni = zacetek;
        while (trenutni != null) {
            count++;
            trenutni = trenutni.getNaslednji();
        }
        return count;
    }

    String getMaxValue() {
        if (zacetek == null) {
            throw new IllegalStateException("Seznam je prazen");
        }
        String max = zacetek.getVrednost();
        elementString trenutni = zacetek.getNaslednji();
        while (trenutni != null) {
            if (trenutni.getVrednost().length() > max.length()) {
                max = trenutni.getVrednost();
            }
            trenutni = trenutni.getNaslednji();
        }
        return max;
    }
}