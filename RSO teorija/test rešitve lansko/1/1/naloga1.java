/* Vozlišče dvostransko povezanega linearnega seznama določajo: celoštevilska vrednost v, povezava na predhodno vozlišče seznama p 
in povezana na naslednje vozlišče n. Seznam referencira(naslovi) povezava z. V primeru, da na povezavo ni vezano nobeno vozlišče, 
ima taka povezava vrednost null.
    a)	Podajte vse potrebne javanske deklaracije, da je mogoče ustvariti seznam.
    b)	Predpostavite, da je seznam urejen, ter da bi želeli, da so v seznamu vrednosti 7, 1 in 9. Spišite javanski program, ki ustvari tak seznam.
    c)	Izrišite razredni diagram v predhodnem besedilu omenjenega. 
    

Diagram:
            -----------------------------
            |        Vozlišče           |
            -----------------------------
            | - v: int                  |
            | - p: Vozlišče             |
            | - n: Vozlišče             |
            -----------------------------
            | + Vozlišče(v: int)        |
            -----------------------------
*/

public class naloga1 {
    public static void main(String[] args) {
        Vozlisce z = null;

        // Vstavljanje vrednosti 1
        Vozlisce novo1 = new Vozlisce(1);
        z = novo1;

        // Vstavljanje vrednosti 7
        Vozlisce novo7 = new Vozlisce(7);
        novo7.p = novo1;
        novo1.n = novo7;

        // Vstavljanje vrednosti 9 na konec
        Vozlisce novo9 = new Vozlisce(9);
        Vozlisce trenutni = z;
        while (trenutni.n != null) {
            trenutni = trenutni.n;
        }
        trenutni.n = novo9;
        novo9.p = trenutni;

        // Izpis seznama za preverjanje
        trenutni = z;
        while (trenutni != null) {
            System.out.print(trenutni.v + " ");
            trenutni = trenutni.n;
        }
    }
}

class Vozlisce {
    int v;
    Vozlisce p; // povezava na predhodno vozlišče
    Vozlisce n; // povezava na naslednje vozlišče

    public Vozlisce(int v) {
        this.v = v;
        this.p = null;
        this.n = null;
    }
}