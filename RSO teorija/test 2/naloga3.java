/*  Javanska nit vrste Generiraj med svojim izvajanjem generira števila v obsegu med 0 in Long.MAX_VALUE.
    Ko prvič zgenerira duplikat (enako vrednost, kot je bila predhodno že zgenerirana), se izvajanje niti ustavi in nit izpiše, 
    koliko števil je do tedaj zgenerirala. Spišite opisu ustrezen javanski program.
*/

import java.util.LinkedList;

class Generiraj extends Thread {
    LinkedList<Long> seznam = new LinkedList<Long>();
    int st = 0;

    public void run() {
        while (true) {
            long cifra = (long) (Math.random() * Long.MAX_VALUE);
            if (!seznam.contains(cifra)) {
                seznam.add(cifra);
                st++;
            } else {
                break;
            }
        }
        System.out.println("Število generiranih števil pred duplikatom: " + st);
    }
}

public class naloga3 {
    public static void main(String[] args) {
        Generiraj nit = new Generiraj();
        nit.start();
    }
}