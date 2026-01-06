
/* Pet enakih procesov sočasno polni strukturo vrste HashMap, prvi z elementom z vrednostjo A,  naslednji z B, nato s C, ... (10t)  vsak izvede polnjenje največ 10000x. 
Procesi imajo vgrajeno varovalko; v vsaki iteraciji (pri vsakem izpisu) lahko z verjetnostjo 1.5% zaustavijo izvajanje vseh (takih) procesov.

Spišite mehanizem, ki ugotovi koliko elementov je bilo dejansko zgeneriranih (5t) 

Spišite mehanizem, ki za vsako vrednost ugotovi, kolikokrat se je zapisala v HashMap(5t)  
*/
import java.util.HashMap;

public class naloga2 {
    public static void main(String[] args) {
        HashMap<Integer, Character> map = new HashMap<>();
        Nit n1 = new Nit('A', map);
        Nit n2 = new Nit('B', map);
        Nit n3 = new Nit('C', map);
        Nit n4 = new Nit('D', map);
        Nit n5 = new Nit('E', map);

        public static int volatile izpisanih = 0;

        n1.start();
        n2.start();
        n3.start();
        n4.start();
        n5.start();

        for (HashMap.Entry<Integer, Character> entry : map.entrySet()) {
            System.out.println("Ključ: " + entry.getKey() + ", Vrednost: " + entry.getValue());
        }
    }

}

class Nit extends Thread {
    char znak;
    HashMap map;

    public Nit(char znak, HashMap map) {
        this.znak = znak;
        this.map = map;
    }

    @Override
    public void run() {
        filajHashMap(znak, map);
    }

    void filajHashMap(char znak, HashMap map) {
        for (int i = 0; i < 10000; i++) {
            map.put(i, znak);
            izpisanih++;
            if (Math.random() * 66.6 == 1) {
                break;
            }
        }
    }
}
