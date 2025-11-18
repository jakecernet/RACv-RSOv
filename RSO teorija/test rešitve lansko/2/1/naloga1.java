/*  Vozlišče dvojiškega drevesa vsebuje zgolj znak in povezavi na poddrevesi. Listi drevesa so števke, ostali koreni pa predstavljajo binarne aritmetične operacije, 
kot je podano na sliki desno od tega besedila (*, +, /, -) 
    a) podajte ustrezno strukturo za vozlišče takega drevesa
    b) spišite metodo, ki bo vsebino drevesa izpisala na tak način, da se s sliko dano drevo izpiše kot : /-823
    c) spišite metodo za izpis drevesa, kjer se dano drevo izpiše kot: ((8-2)/3)
*/

public class naloga1 {
    public static String ispisiPreOrder(Vozlisce v) {
        if (v == null)
            return "";
        String s = String.valueOf(v.znak);
        s += ispisiPreOrder(v.levo);
        s += ispisiPreOrder(v.desno);
        return s;
    }

    public static String ispisiInOrder(Vozlisce v) {
        if (v == null)
            return "";
        // če je list (ni otrok), vrnemo samo znak (števko)
        if (v.levo == null && v.desno == null)
            return String.valueOf(v.znak);
        // sicer obdamo z oklepaji
        return "(" + ispisiInOrder(v.levo) + v.znak + ispisiInOrder(v.desno) + ")";
    }

    public static void main(String[] args) {
        // Sestavimo drevo za primer: ((8-2)/3)
        Vozlisce n8 = new Vozlisce('8', null, null);
        Vozlisce n2 = new Vozlisce('2', null, null);
        Vozlisce minus = new Vozlisce('-', n8, n2);
        Vozlisce n3 = new Vozlisce('3', null, null);
        Vozlisce root = new Vozlisce('/', minus, n3);

        // (b) izpis v prefiksni notaciji brez presledkov: "/-823"
        System.out.println(ispisiPreOrder(root));

        // (c) izpis z oklepaji kot infiksni izraz: "((8-2)/3)"
        System.out.println(ispisiInOrder(root));
    }

}

class Vozlisce {
    char znak;
    Vozlisce levo;
    Vozlisce desno;

    public Vozlisce(char znak, Vozlisce levo, Vozlisce desno) {
        this.znak = znak;
        this.levo = levo;
        this.desno = desno;
    }
}
