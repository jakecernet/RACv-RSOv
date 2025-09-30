/* Element dinamične strukture je bil definiran kot:

    Class ElementI nt{
        Int vrednost;
        ElementInt naslednji;
    }

Obstoječo definicijo bi preoblikovali kot v skladu z navodili v naslednjih točkah.
a.	Odpravite sintaktične napake iz definicije.
b.	Vse lastnosti znotraj elementa naredite privatne.
c.	Dodajte nastavljalec in vračalec zgolj za lastnost naslednji.
d.	Dodajte konstruktor, ki bo s parametrom nastavil vrednost, lastnost naslednji pa nastavil na vrednost null.  */

class ElementInt{
    private int vrednost;
    private ElementInt naslednji;

    public ElementInt(int vrednost) {
        this.vrednost = vrednost;
        this.naslednji = null;
    }

    public ElementInt getNaslednji() {
        return naslednji;
    }

    public void setNaslednji(ElementInt naslednji) {
        this.naslednji = naslednji;
    }
}