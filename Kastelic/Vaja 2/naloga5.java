/* 
Napišite metode, ki vrnejo generiran palindrom izbrane dolžine. Pseudoprimeri metod:
•	(String) s1 = genPalindr(String niz,boolean sod);
o	(Abc,true) -> AbccbA, (Abc,false)->AbcbA
•	(int) ii1 = genPalindr(int stevilo, boolean sod);
Sod: določa sodost,lihost dolžine, stevilo dolzino v števkah
*/

public class naloga5 {
    public static void main(String[] args) {
        System.out.println(genPalindr("Abc", true));  // Izpis: AbccbA
        System.out.println(genPalindr("Abc", false)); // Izpis: AbcbA
        System.out.println(genPalindr(123, true));    // Izpis: 123321
        System.out.println(genPalindr(123, false));   // Izpis: 12321
    }

    public static String genPalindr(String niz, boolean sod) {
        if (sod) {
            return niz + new StringBuilder(niz).reverse().toString();
        } else {
            return niz + new StringBuilder(niz.substring(0, niz.length() - 1)).reverse().toString();
        }
    }

    public static int genPalindr(int stevilo, boolean sod) {
        String strStevilo = Integer.toString(stevilo);
        String palindrom;
        if (sod) {
            palindrom = strStevilo + new StringBuilder(strStevilo).reverse().toString();
        } else {
            palindrom = strStevilo + new StringBuilder(strStevilo.substring(0, strStevilo.length() - 1)).reverse().toString();
        }
        return Integer.parseInt(palindrom);
    }
}
