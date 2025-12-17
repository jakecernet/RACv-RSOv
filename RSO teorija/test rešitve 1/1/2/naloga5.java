/* Javanski program skuša demonstrirati delovanje vrste s pomočjo:

Queue<String> vrsta = new LinkedList<>();

//dodamo 5 elementov

System.out.println(vrsta);

//odstranimo 3 elemente

System.out.println(vrsta);

Nadomestite pseudo-opise komentarjev z dejansko realizacijo iz opisa. */

import java.util.LinkedList;
import java.util.Queue;

public class naloga5 {
    public static void main(String[] args) {

        Queue<String> vrsta = new LinkedList<>();

        // dodamo 5 elementov
        vrsta.add("Element 1");
        vrsta.add("Element 2");
        vrsta.add("Element 3");
        vrsta.add("Element 4");
        vrsta.add("Element 5");

        System.out.println(vrsta);

        // odstranimo 3 elemente
        vrsta.poll();
        vrsta.poll();
        vrsta.poll();

        System.out.println(vrsta);
    }
}
