/* Napišite rekurzivno metodo, realizira računanje N!  */

public class naloga9 {
    public static void main(String[] args) {
        int n = 5;
        System.out.println("Faktoriel števila " + n + " je: " + factorial(n)); // Izpis: 120
    }

    public static int factorial(int n) {
        if (n == 0 || n == 1) {
            return 1;
        }
        return n * factorial(n - 1);
    }
}
