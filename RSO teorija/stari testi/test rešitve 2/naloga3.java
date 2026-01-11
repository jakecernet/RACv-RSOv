
/*
 * Javanska nit vrste Generiraj med svojim izvajanjem generira števila v obsegu
 * med 0 in Long.MAX_VALUE. Ko prvič zgenerira duplikat (enako vrednost, kot je
 * bli predhodno že zgenerirana), se izvajanje ustavi in nit izpiše, koliko
 * števil je do tedaj zgenerirala.
 * 
 * Bonus: recimo, da tovrstno operacijo sočasno opravlja 100 niti.
 * Izvajanje vseh se konča ob prvem duplikatu.
 */
import java.util.LinkedList;

class Generiraj extends Thread {
    int zgeneriranih = 0;

    public void run() {
        var tabela = new LinkedList<Long>();
        while (1 > 0) {
            long stevilo = (long) (Math.random() * Long.MAX_VALUE);
            zgeneriranih++;
            if (tabela.contains(stevilo)) {
                System.out.println("Duplikat: " + stevilo + ", zgeneriranih: " + zgeneriranih);
                break;
            } else {
                tabela.add(stevilo);
            }
        }
    }
}

public class naloga3 {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            Generiraj g = new Generiraj();
            g.start();
        }
    }
}