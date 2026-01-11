/* Splošni členi zaporedja A se določi kot an = an-1+2*an-2. Robna pogoja a0 in a1 se določita z argumentoma metode, ki računa in vrne 
vrednost n-tega člen opisanega zaporedja. Spišite zahtevano kot metodo rekurzivne narave. 
*/

public class naloga5 {
    public static void main(String[] args) {
        System.out.println(clenZaporedja(0, 1, 4)); // primer klica metode
    }

    public static int clenZaporedja(int a0, int a1, int n) {
        if (n == 0) {
            return a0;
        } else if (n == 1) {
            return a1;
        } else {
            return clenZaporedja(a0, a1, n - 1) + 2 * clenZaporedja(a0, a1, n - 2);
        }
    }
}