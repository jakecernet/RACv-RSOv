/* Metoda napolniTabelo/2 z argumentom podano tabelo napolni s celoštevilskimi vrednostmi od podanega števila naprej. 
Metodi ni znana dolžina tabele, niti je ne zna pridobiti (recimo, da length na tabeli ne obstaja). Prav tako v metodi ne bo uporabljen 
nobeden izmed stavkov za ponavljanje (for,do,do-while). Spišite jo.(2). Upoštevajte, da naj bo do uporabnika prijazna/trpežna.(1) */

public class naloga3 {
    public static void main(String[] args) {
        int tab[] = new int[25];
        napolniTabelo(tab, 5, 0);

        for (int i = 0; i < tab.length; i++) {
            System.out.print(tab[i] + " ");
        }
    }

    public static void napolniTabelo(int[] tabela, int zacetnaVrednost, int zacetniIndeks) {
        try {
            tabela[zacetniIndeks] = zacetnaVrednost;
            napolniTabelo(tabela, zacetnaVrednost + 1, zacetniIndeks + 1);
        } catch (ArrayIndexOutOfBoundsException e) {
            // Končamo rekurzijo, ko dosežemo konec tabele
            System.out.println("Prišli smo do konca.");
        }
    }
}
