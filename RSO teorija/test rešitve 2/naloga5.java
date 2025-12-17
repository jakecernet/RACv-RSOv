
/*
 * Javanska metoda napolni objekta tipa Naloga5 zapiše v strukturo osnovano
 * na LinkedList 100 različnih naključno zgeneriranih znakov. Metoda napolni()
 * (nima argumentov) ne vrača nobene vrednosti.
 * 
 * a) Spišite ustrezen javanski program z opisano metodo.
 * b) Spišite metodo, ki bo na zaslon izpisala celotno generirano zaporedje
 * števil v vrstnem redu, kot so bila ustvarjena.
 */
import java.util.LinkedList;

public class naloga5 {
    LinkedList<Character> seznam = new LinkedList<Character>();

    public void napolni() {
        while (seznam.size() < 100) {
            char znak = (char) (Math.random() * 100 + 32);
            if (!seznam.contains(znak)) {
                seznam.add(znak);
            }
        }
    }

    public void izpisi() {
        for (char znak : seznam) {
            System.out.print(znak + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        naloga5 n5 = new naloga5();
        n5.napolni();
        n5.izpisi();
    }
}
