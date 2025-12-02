/* Cilj naloge je variacija naloge 2.  Glavni program/glavna izvajalna nit sproži 3 niti za izračun vsot. Ko vse tri vsote dobi, izračuna njihovo vsoto. 
Ponovno: če je prekoračen obseg definiran z obsegom 'long', naj bo vsota največja vrednost, ki jo  'long' še dopušča. 
Izpis naj bo v obliki (fiktiven primer):   721345 + 31234 + 127 = 752706 */

public class naloga3 {
    public static long izracunajVsoto(long n) {
        long vsota = 0;
        for (long i = 1; i <= n; i++) {
            vsota += i;
            if (vsota < 0) {
                return Long.MAX_VALUE;
            }
        }
        return vsota;
    }

    public static void main(String[] args) {
        long[] vsote = new long[3];
        Thread[] niti = new Thread[3];

        for (int i = 0; i < 3; i++) {
            final int index = i;
            niti[i] = new Thread(() -> {
                long randomNum = (long) (Math.random() * 10000) + 10001;
                vsote[index] = izracunajVsoto(randomNum);
            });
            niti[i].start();
        }

        for (Thread nit : niti) {
            try {
                nit.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long skupnaVsota = 0;
        StringBuilder izpis = new StringBuilder();
        for (long vsota : vsote) {
            skupnaVsota += vsota;
            izpis.append(vsota).append(" + ");
        }
        izpis.setLength(izpis.length() - 3); // Odstrani zadnji " + "
        izpis.append(" = ").append(skupnaVsota);

        System.out.println(izpis);
    }
}
