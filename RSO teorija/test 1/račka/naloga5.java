/* Rekurzivna metoda vrne palindrom sestavljen iz števk. Velikost palindroma podamo z argumentom metodi. 
Izbira števk pri sestavljanju palindroma naj bo naključna. */

public class naloga5 {
    public long vrniPalindrom(int n) {
        if (n > 0) {
            int stevka = (int) (Math.random() * 10);
            if (n == 1) {
                return stevka;
            } else {
                long manjsiPalindrom = vrniPalindrom(n - 2);
                long mnozitelj = (long) Math.pow(10, n - 1);
                return stevka * mnozitelj + manjsiPalindrom * 10 + stevka;
            }
        } else {
            return 0;
        }
    }

    public static void main(String[] args) {
        naloga5 naloga = new naloga5();
        long palindrom = naloga.vrniPalindrom(25);
        System.out.println("Palindrom velikosti 25: " + palindrom);
    }
}
