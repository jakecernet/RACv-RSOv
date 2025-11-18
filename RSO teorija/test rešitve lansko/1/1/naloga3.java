/* 
Dani del javanskega programa izpiše korena levega in desnega poddrevesa danega BST(binarnega iskalnega drevesa) :

System.out.println(this.koren.l.v + " " + this.koren.d.v);

Spišite javanski podprogram, ki določi višino tega drevesa.
*/

public class naloga3 {
    VozlisceBST koren;

    public naloga3(VozlisceBST koren) {
      this.koren = koren;
    }  

    public int visina(VozlisceBST vozlisce) {
        if (vozlisce == null) {
            return -1; // Višina praznega drevesa je -1
        } else {
            int visinaLevo = visina(vozlisce.l);
            int visinaDesno = visina(vozlisce.d);
            return Math.max(visinaLevo, visinaDesno) + 1;
        }
    }

    public static void main(String[] args) {
        // Primer uporabe
        VozlisceBST levo = new VozlisceBST(2);
        VozlisceBST desno = new VozlisceBST(3);
        VozlisceBST koren = new VozlisceBST(1, levo, desno);

        naloga3 drevo = new naloga3(koren);
        System.out.println("Višina drevesa: " + drevo.visina(drevo.koren));
    }
}

class VozlisceBST {
    int v;
    VozlisceBST l; // levo poddrevo
    VozlisceBST d; // desno poddrevo

    public VozlisceBST(int v) {
        this.v = v;
        this.l = null;
        this.d = null;
    }

    public VozlisceBST(int v, VozlisceBST l, VozlisceBST d) {
        this.v = v;
        this.l = l;
        this.d = d;
    }
}