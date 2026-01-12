/* Dani del javanskega programa izpiše korena levega in desnega poddrevesa danega BST(binarnega iskalnega drevesa):

    System.out.prinlnt (this.koren.l.v + " "+ this.koren.d.v);

Spišite javanski podprogram, ki določi višino tega drevesa. 
*/

class Vozlisce {
    int v;
    Vozlisce l, d;

    public Vozlisce(int v) {
        this.v = v;
        this.l = this.d = null;
    }
}

public class naloga3 {
    Vozlisce koren;

    public naloga3(Vozlisce koren) {
        this.koren = koren;
    }

    public int visina(Vozlisce vozlisce) {
        if (vozlisce == null) {
            return -1;
        } else {
            int visinaLevo = visina(vozlisce.l);
            int visinaDesno = visina(vozlisce.d);
            return Math.max(visinaLevo, visinaDesno) + 1;
        }
    }

    public static void main(String[] args) {
        Vozlisce koren = new Vozlisce(10);
        koren.l = new Vozlisce(5);
        koren.d = new Vozlisce(15);
        koren.l.l = new Vozlisce(3);
        koren.l.d = new Vozlisce(7);
        koren.d.d = new Vozlisce(20);

        naloga3 drevo = new naloga3(koren);
        System.out.println("Višina drevesa: " + drevo.visina(drevo.koren));
    }
}