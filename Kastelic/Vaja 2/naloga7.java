/* Napišite rekurzivni metodi, ki:
•	Obrne podani niz znakov
•	Obrne vrstni red števk v številu (ničlo na koncu zanemari: le zakaj?), realizirajte brez rabe nizov
*/

public class naloga7 {
    public static void main(String[] args) {
        String niz = "abcdef";
        int stevilo = 1234500;

        System.out.println("Obrnjen niz: " + obrniNiz(niz));
        System.out.println("Obrnjeno število: " + obrniStevilo(stevilo, 0));
    }

    public static String obrniNiz(String niz) {
        if (niz.isEmpty()) {
            return niz;
        }
        return niz.charAt(niz.length() - 1) + obrniNiz(niz.substring(0, niz.length() - 1));
    }

    public static int obrniStevilo(int stevilo, int obrnjeno) {
        if (stevilo == 0) {
            return obrnjeno;
        }
        return obrniStevilo(stevilo / 10, obrnjeno * 10 + stevilo % 10);
    }
}
