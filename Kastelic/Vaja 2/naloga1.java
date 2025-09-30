/* 
    Napišite rekurzivno metodo, ki izpiše vsa števila med podanima dvema številoma, od večjega podanega do manjšega podanega. 
    Obe podani števili naj bosta parametra metode.
*/

public class naloga1 {
    public static void main(String[] args) {
        izpisiStevila(10, 5);
    }

    public static void izpisiStevila(int a, int b) {
        if (a < b) {
            return;
        }
        System.out.println(a);
        izpisiStevila(a - 1, b);
    }
}
