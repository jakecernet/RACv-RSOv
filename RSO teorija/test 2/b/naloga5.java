/*  Javanska metoda napolni objekta tipa Naloga5 zapiše v strukturo osnovano na HashMap 100 različnih
    naključno zgeneriranih števil. Metoda napolni/0 (nima argumentov) ne vrača nobene vrednosti.

    a) Spišite ustrezen javanski program z opisano metodo.
    b) Spišite metodo, ki bo na zaslon izpisala celotno generirano zaporedje števil v vrstnem redu, kot so bila ustvarjena.
*/

import java.util.HashMap;

public class naloga5 {
    HashMap<Integer, Integer> seznam = new HashMap<Integer, Integer>();

    public void napolni() {
        int i = 0;
        while (seznam.size() < 100) {
            int st = (int) (Math.random() * 100);
            if (!seznam.containsValue(st)) {
                seznam.put(i, st);
                i++;
            }
        }
    }

    public void izpisi() {
        for (Integer j : seznam.keySet()) {
            System.out.println(seznam.get(j));
        }
    }

    public static void main(String[] args) {
        naloga5 n5 = new naloga5();
        n5.napolni();
        n5.izpisi();
    }
}
