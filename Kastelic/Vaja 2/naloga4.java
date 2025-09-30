/* Napišite rekurzivno metodo, ki napolni vsebino tabele celih števil velikost 5x5 z vrednostjo 1. */

public class naloga4 {
    int tab[][] = new int[5][5];

    public static void main(String[] args) {
        naloga4 n = new naloga4();
        n.napolniTabelo(0, 0);
        n.izpisiTabelo();
    }

    public void napolniTabelo(int i, int j) {
        if (i >= tab.length) {
            return;
        }
        if (j >= tab[i].length) {
            napolniTabelo(i + 1, 0);
            return;
        }
        tab[i][j] = 1;
        napolniTabelo(i, j + 1);
    }

    public void izpisiTabelo() {
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                System.out.print(tab[i][j] + " ");
            }
            System.out.println();
        }
    }
}
