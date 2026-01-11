
/* Javanska nit vrste Generiraj med svojim izvajanjem generira števila v obsegu med 0 in Long.MAX_VALUE. 
Ko prvič generira duplikat (enako vrednost, kot jo je predhodno že generirala), je to za nit znak, 
da mora generirati še natanko 10 vrednosti in jih izpisati na zaslon. S tem se njeno izvajanje zaključi. 
Spišite javansko definicijo take niti. */
import java.util.LinkedList;

// varianta 1
class Generiraj extends Thread {
    public void run() {
        LinkedList<Long> generiranaStevila = new LinkedList<>();
        int duplikati = 0;

        while (duplikati < 11) {
            long stevilo = (long) (Math.random() * Long.MAX_VALUE);

            if (generiranaStevila.contains(stevilo)) {
                duplikati++;
                System.out.println("Duplikat " + duplikati + ": " + stevilo);
            } else {
                generiranaStevila.add(stevilo);
            }
        }
    }
}

// varianta 2
class Generiraj2 extends Thread {
    public void run() {
        LinkedList<Long> generiranaStevila = new LinkedList<>();

        while (true) {
            long stevilo = (long) (Math.random() * Long.MAX_VALUE);

            if (generiranaStevila.contains(stevilo)) {
                for (int i = 0; i < 10; i++) {
                    long novoStevilo = (long) (Math.random() * Long.MAX_VALUE);
                    System.out.println("Generirano število po duplikatu: " + novoStevilo);
                }
                break;
            } else {
                generiranaStevila.add(stevilo);
            }
        }
    }
}

public class naloga3 {
    public static void main(String[] args) {
        Generiraj nit = new Generiraj();
        nit.start();

        Generiraj2 nit2 = new Generiraj2();
        nit2.start();
    }
}
