/* Splošni členi zaporedja A se določi kot an = an-1+2*an-2. Robna pogoja a0 in a1 se določita z argumentoma metode, 
ki računa in vrne vrednost n-tega člen opisanega zaporedja. Spišite zahtevano kot metodo rekurzivne narave. 
*/

public class naloga5 {
    public int cleniZaporedja(int n, int a0, int a1) {
        if (n == 0)
            return a0;
        else if (n == 1)
            return a1;
        else {
            return cleniZaporedja(n - 1, a0, a1) + 2 * cleniZaporedja(n - 2, a0, a1);
        }
    }

    public static void main(String[] args) {
        naloga5 naloga = new naloga5();
        int rezultat = naloga.cleniZaporedja(5, 1, 2);
        System.out.println("Vrednost 5. člena zaporedja je: " + rezultat);
    }
}