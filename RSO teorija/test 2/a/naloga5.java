
/* Javanska metoda napolni objekta tipa Naloga5 zapiše v strukturo osnovano na LinkedList 100 različnih naključno (zgeneriranih števil). 
Metoda napolni()/0 (nima argumentov) ne vrača nobene vrednosti.
a) Spišite ustrezen javanski program z opisano metodo.
b) Spišite metodo, ki bo na zaslon izpisala celotno generirano zaporedje števil v obratnem vrstnem redu, kot bi bila ustvarjena. 
*/
import java.util.LinkedList;

public class naloga5 {
    LinkedList<Integer> seznam = new LinkedList<Integer>();

    public void napolni() {
        while (seznam.size() < 100) {
            int st = (int) (Math.random() * 100);
            if (!seznam.contains(st)) {
                seznam.add(st);
            }
        }
    }

    public void izpisi() {
        for (int i = seznam.size() - 1; i >= 0; i--) {
            System.out.println(seznam.get(i));
        }
    }

    public static void main(String[] args) {
        naloga5 n5 = new naloga5();
        n5.napolni();
        n5.izpisi();
    }
}
