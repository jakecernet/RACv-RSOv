/* Recimo, da je element seznama (LinkedList) obarvana ravninska točka. Ker vrednosti v enkrat definirani točki ne bomo spreminjali, jo definiramo kot record(Java 17+). 
Test rabe recorda:
    public record RTocka(int x, int y, java.awt.Color barva) {}
       
    RTocka r1= new RTocka(3,4,java.awt.Color.red); // konstruktor se generira sam
    System.out.println(r1.x);                      // lastnosti so finalne in javne                       
    System.out.println(r1);                        // je Serializable !

Seznam naredimo kot : var ll = new LinkedList<RTocka>();
V seznam vstavimo 7 točk, vse naj imajo enako y koordinato, x koordinate naj bodo zaporedne od 5 naprej, barva vseh naj bo rumena. Spišite sekvenci a) in b), ki bosta:
a)	izvede krožen premik elementov za en cel 'krog/cikel' tako da: vzame 1. element iz seznama, ga vstavi na konec istega seznama in izpiše seznam. 
    Nato postopek ponovi, dokler ne prestavimo vseh elementov.
b)	Izvede krožni premik elementov za cel cikel; vzame 1. iz seznama, na konec doda element, ki ima enak y, kot pred dodajanjem zadnji element, 
    x je za 1 večji od x-a prej zadnjega elementa, barva novega pa je rdeča; no barva njegovega predhodnika je rumena. 
    Izpišite seznam, ponavljajte čez celoten cikel.

Če ste b) izvedli prav, ste dejansko zaporedje premaknili po pozicijah desno !*/

public class naloga4 {
    public record RTocka(int x, int y, java.awt.Color barva) {}

    public static void main(String[] args) {
        var ll = new java.util.LinkedList<RTocka>();

        // Vstavimo 7 točk z enako y koordinato, zaporednimi x koordinatami in rumeno barvo
        for (int i = 5; i < 12; i++) {
            ll.add(new RTocka(i, 10, java.awt.Color.yellow));
        }

        System.out.println("Začetni seznam:");
        System.out.println(ll);

        // a) Krožen premik elementov za en cikel
        System.out.println("\nKrožen premik elementov (a):");
        for (int i = 0; i < ll.size(); i++) {
            RTocka first = ll.removeFirst();
            ll.addLast(first);
            System.out.println(ll);
        }

        // Ponovno inicializiramo seznam
        ll.clear();
        for (int i = 5; i < 12; i++) {
            ll.add(new RTocka(i, 10, java.awt.Color.yellow));
        }

        // b) Krožen premik elementov z dodajanjem nove točke
        System.out.println("\nKrožen premik elementov z dodajanjem nove točke (b):");
        // Shrani velikost seznama pred začetkom, ker se med iteracijami spreminja
        int originalSize = ll.size();
        for (int i = 0; i < originalSize; i++) {
            RTocka first = ll.removeFirst();
            RTocka last = ll.getLast();
            RTocka newPoint = new RTocka(last.x() + 1, last.y(), java.awt.Color.red);
            ll.addLast(newPoint);
            ll.addLast(first);
            System.out.println(ll);
        }
    }
}
