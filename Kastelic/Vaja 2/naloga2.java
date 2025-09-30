/* 
    Napišite rekurzivno metodo, ki ugotovi, ali je vsebina tabele int a[41]; palindromistična.
*/

public class naloga2 {
    int a[] = new int[41];
    public static void main(String[] args) {
        naloga2 n = new naloga2();
        // Primer palindromistične tabele
        n.a = new int[]{1, 2, 3, 4, 5, 4, 3, 2, 1};
        System.out.println(n.jePalindrom(0, n.a.length - 1)); // true

        // Primer ne-palindromistične tabele
        n.a = new int[]{1, 2, 3, 4, 5};
        System.out.println(n.jePalindrom(0, n.a.length - 1)); // false
    }

    public boolean jePalindrom(int start, int end) {
        if (start >= end) {
            return true;
        }
        if (a[start] != a[end]) {
            return false;
        }
        return jePalindrom(start + 1, end - 1);
    }
}
