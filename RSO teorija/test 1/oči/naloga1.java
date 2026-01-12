/* Vozlišče dvostransko povezanega linearnega seznama določajo: celoštevilska vrednost v, povezava na predhodno vozlišče seznama p in povezana na naslednje vozlišče n. 
Seznam referencira(naslovi) povezava z. V primeru, da na povezavo ni vezano nobeno vozlišce, ima taka povezava vrednost null.
    a) Podajte vse potrebne javanske deklaracije, da je mogoče ustvariti seznam.
    b) Predpostavite, da je seznam urejen, ter da bi želeli, da so v seznamu vrednosti 7, 1 in 9. Spišite javanski program, ki ustvari tak seznam.
    c) Izrišite razredni diagram v predhodnem besedilu omenjenega. 

    Diagram:

    +----------------+
    |    Vozlisce    |
    +----------------+
    | - v: int       |  
    | - p: Vozlisce  |
    | - n: Vozlisce  |
    +----------------+
    | + Vozlisce(v)  |
    +----------------+  
*/

class Vozlisce {
    int v;
    Vozlisce p;
    Vozlisce n;

    public Vozlisce(int v) {
        this.v = v;
        this.p = this.n = null;
    }
}

public class naloga1 {
    public static void main(String[] args) {
        Vozlisce z = new Vozlisce(1);

        Vozlisce novoVozlisce7 = new Vozlisce(7);
        novoVozlisce7.n = z;
        z.p = novoVozlisce7;
        z = novoVozlisce7;

        Vozlisce novoVozlisce9 = new Vozlisce(9);
        novoVozlisce9.p = z;
        z.n = novoVozlisce9;
    }
}