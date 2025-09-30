/* Napišite rekurzivno metodo, za iskanje največjega skupnega delitelja dveh podanih števil. */

public class naloga8 {
    public static void main(String[] args) {
        int a = 48;
        int b = 18;
        System.out.println("Največji skupni delitelj " + a + " in " + b + " je: " + gcd(a, b)); // Izpis: 6
    }

    public static int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }
}
