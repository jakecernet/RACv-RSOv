/* Napišite rekurzivno metodo, ki ugotovi, kolikokrat se v nizu pojavi izbrani podniz */

public class naloga6 {
    public static void main(String[] args) {
        String niz = "ababcabc";
        String podniz = "abc";
        System.out.println(steviloPojavitev(niz, podniz)); // Izpis: 2
    }

    public static int steviloPojavitev(String niz, String podniz) {
        if (niz.length() < podniz.length()) {
            return 0;
        }
        if (niz.startsWith(podniz)) {
            return 1 + steviloPojavitev(niz.substring(1), podniz);
        } else {
            return steviloPojavitev(niz.substring(1), podniz);
        }
    }
}
