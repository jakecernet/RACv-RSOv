/* Napišite rekurzivno metodo, ki ugotovi, na katerem mestu v tabeli celih števil se nahaja element z najmanjšo vrednostjo. */

public class naloga3 {
    int tab[] = new int[20];

    public static void main(String[] args) {
        naloga3 n = new naloga3();
        n.tab = new int[]{5, 3, 8, 1, 4, 2};
        System.out.println(n.mestoNajmanjsega(0, n.tab.length - 1)); // Izpis: 3 (indeks najmanjšega elementa)
    }

    public int mestoNajmanjsega(int start, int end) {
        if (start == end) {
            return start;
        }
        int mid = (start + end) / 2;
        int leftMinIndex = mestoNajmanjsega(start, mid);
        int rightMinIndex = mestoNajmanjsega(mid + 1, end);
        return (tab[leftMinIndex] < tab[rightMinIndex]) ? leftMinIndex : rightMinIndex;
    }
}
