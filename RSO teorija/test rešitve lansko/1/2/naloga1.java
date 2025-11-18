/* Javanski program sestavi tri-nomsko iskalno drevo. Predpostavite urejenost, pri kateri so manjši elementi vedno levo od večjih. 
Podajte ustrezno strukturo vozlišča z zgolj privatnimi lastnostmi in ustrezno konstrukcijsko metodo, ter konstruirajte ustrezno drevo, 
v katerem bodo vrednosti 7,5,12,3,1,4,18. */

public class naloga1 {
    public static void main(String[] args) {
        vozlisce drevo = new vozlisce(7);
        drevo.levo(new vozlisce(5));
        drevo.sredina(new vozlisce(12));
        drevo.levo().levo(new vozlisce(3));
        drevo.levo().levo().levo(new vozlisce(1));
        drevo.levo().levo().desno(new vozlisce(4));
        drevo.desno(new vozlisce(18));

        naloga1 naloga = new naloga1();
        naloga.traverseInOrder(drevo);
        System.out.println();
        naloga.traversePreOrder(drevo);
        System.out.println();
        naloga.traversePostOrder(drevo);
    }

    public void traverseInOrder(vozlisce node) {
        if (node != null) {
            traverseInOrder(node.levo());
            System.out.print(node.cifra() + " ");
            traverseInOrder(node.sredina());
            traverseInOrder(node.desno());
        }
    }

    public void traversePreOrder(vozlisce node) {
        if (node != null) {
            System.out.print(node.cifra() + " ");
            traversePreOrder(node.levo());
            traversePreOrder(node.sredina());
            traversePreOrder(node.desno());
        }
    }

    public void traversePostOrder(vozlisce node) {
        if (node != null) {
            traversePostOrder(node.levo());
            traversePostOrder(node.sredina());
            traversePostOrder(node.desno());
            System.out.print(node.cifra() + " ");
        }
    }
}

class vozlisce {
    private vozlisce levo;
    private vozlisce sredina;
    private vozlisce desno;
    private int cifra;

    public vozlisce(int n) {
        this.cifra = n;
    }

    public void levo(vozlisce levo) {
        this.levo = levo;
    }

    public void sredina(vozlisce sredina) {
        this.sredina = sredina;
    }

    public void desno(vozlisce desno) {
        this.desno = desno;
    }

    public vozlisce levo() {
        return this.levo;
    }

    public vozlisce sredina() {
        return this.sredina;
    }

    public vozlisce desno() {
        return this.desno;
    }

    public int cifra() {
        return this.cifra;
    }
}
