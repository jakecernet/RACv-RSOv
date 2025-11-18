/* Spišite javansko rekurzivno metodo, ki bo iz podanega besedila izdelala in vrnila palindrom. Npr. iz Koza je bela vrne Koza je bela bela je Koza. 
Za enako število točk je sprejemljiv tudi rezultat kot : Koza je bela aleb ej azoK */

public class naloga4 {
    public static void main(String[] args) {
        naloga4 naloga = new naloga4();
        naloga.obrniHelp("a nigger");
    }

    void obrniHelp(String besedilo) {
        char znaki[] = new char[besedilo.length()];
        for (int i = 0; i < besedilo.length(); i++) {
            znaki[i] = besedilo.charAt(i);
        }
        int levo = 0;
        int desno = znaki.length - 1;
        obrni(znaki, levo, desno);
        String palindrom = besedilo + " " + String.valueOf(znaki);
        System.out.println(palindrom);
    }

    void obrni(char znaki[], int levo, int desno) {
        if (levo >= desno) {
            return;
        }
        char temp = znaki[levo];
        znaki[levo] = znaki[desno];
        znaki[desno] = temp;
        obrni(znaki, levo + 1, desno - 1);

    }
}
