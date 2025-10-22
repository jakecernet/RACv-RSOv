/* Izvedimo par testov/poizvedovanj: 
a)	Ugotovite, ali LinkedList implementira Serializable. Če ga implementira, je mogoče celotno strukturo predstaviti kot niz, torej tudi izpisati s preprostim print
b)	Izvedite test:
       LinkedList l1= new LinkedList<Integer>();
       var l2 = new LinkedList<Integer>();
       
       System.out.println(l1.getClass().getName());
       System.out.println(l2.getClass().getName());
       
       l1.add(3);  System.out.println(l1);
       l1.add(new Integer(6)); System.out.println(l1);
       System.out.println( l1.remove() ); System.out.println(l1);

In ugotovite:
•	katerega izmed paketov je potrebno vključiti, da lahko uporabite zgornjo kodo;
•	ali sta l1 in l2 strukturi enakega tipa;
•	kako lahko dodajate na začetek/konec oziroma jemljete element z začetka/konca seznama (spišite demonstracijo!) */

public class naloga3 {
    @SuppressWarnings("removal")
    
    public static void main(String[] args) {
        // a) ALi LinkedList implementira Serializable?
        System.out.println("Ali linkedlist implementera seriazable? " + java.io.Serializable.class.isAssignableFrom(java.util.LinkedList.class));

        System.out.println();

        // b) Testiramo linkedlist
        java.util.LinkedList<Integer> l1 = new java.util.LinkedList<Integer>();
        var l2 = new java.util.LinkedList<Integer>();

        System.out.println("Razred l1: " + l1.getClass().getName());
        System.out.println("Razred l2: " + l2.getClass().getName());

        System.out.println();

        l1.add(3);
        System.out.println("Po dodajanju 3: " + l1);
        l1.add(new Integer(6));
        System.out.println("Po dodajanju 6: " + l1);
        System.out.println("Odstranjen element: " + l1.remove());
        System.out.println("Po odstranitvi: " + l1);

        // Demonstracija dodajanja/odstranjevanja na začetek/konec
        l1.addFirst(1);
        System.out.println("Po dodajanju 1 na začetek: " + l1);
        l1.addLast(10);
        System.out.println("Po dodajanju 10 na konec: " + l1);
        System.out.println("Odstranjen prvi element: " + l1.removeFirst());
        System.out.println("Po odstranitvi prvega elementa: " + l1);
        System.out.println("Odstranjen zadnji element: " + l1.removeLast());
        System.out.println("Po odstranitvi zadnjega elementa: " + l1);
    }
}
