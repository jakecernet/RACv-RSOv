/* 2. Dvostransko povezan linearen seznam celoštevilskih vrednosti vsebuje vrednosti urejene po velikosti od najmanjše do največje. 
Spišite metodo, ki na konec seznama doda nov element z vrednostjo (kakorkoli) večjo od največje izmed vseh vrednosti elementov v obstoječem seznamu. 
Podajte opis strukture elementa seznama v meri, da se omogoči sledenje vaši realizaciji. 

Opis: Razred seznam ima lastnosti n tipa int ter 2 lastnosti tipa Element, prejšnji in naslednji. 
      Ima tudi konstruktor, ki nastavi v na vrednost celotevilskega argumenta in prej in naslednji na null.
*/

public class naloga2 {
    void dodajElement(Element glava, int vrednost) {
        Element novElement = new Element(vrednost);
        Element trenutni = glava;

        while (trenutni.naslednji != null) {
            trenutni = trenutni.naslednji;
        }

        trenutni.naslednji = novElement;
        novElement.prej = trenutni;
    }
}

class Element {
    int vrednost;
    Element prej, naslednji;

    public Element(int v) {
        vrednost = v;
        prej = null;
        naslednji = null;
    }
}
