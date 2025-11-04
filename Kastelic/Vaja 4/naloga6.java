/* Ponovite 3, element povezanega seznama (LinkedList) naj bo namesto  zapisa RTocka element seznama objekt vrste Circle. */

public class naloga6 {
    public static class Circle {
        private final int x;
        private final int y;
        private final java.awt.Color color;

        public Circle(int x, int y, java.awt.Color color) {
            this.x = x;
            this.y = y;
            this.color = color;
        }

        public int x() { return x; }
        public int y() { return y; }
        public java.awt.Color color() { return color; }

        @Override
        public String toString() {
            String name;
            if (color == java.awt.Color.YELLOW) name = "yellow";
            else if (color == java.awt.Color.RED) name = "red";
            else name = color.toString();
            return "Circle{x=" + x + ", y=" + y + ", color=" + name + "}";
        }
    }

    public static void main(String[] args) {
        var ll = new java.util.LinkedList<Circle>();

        // vstavi 7 krogov z enako y, zaporednimi x in rumeno barvo
        for (int x = 5; x <= 11; x++) {
            ll.add(new Circle(x, 10, java.awt.Color.YELLOW));
        }

        System.out.println("Začetni seznam:");
        System.out.println(ll);

        // a) krožen premik: prestavi prvi element na konec
        System.out.println("\nKrožen premik elementov (a):");
        for (int i = 0; i < ll.size(); i++) {
            Circle first = ll.removeFirst();
            ll.addLast(first);
            System.out.println(ll);
        }

        // ponovno inicializiramo
        ll.clear();
        for (int x = 5; x <= 11; x++) {
            ll.add(new Circle(x, 10, java.awt.Color.YELLOW));
        }

        // b) krožen premik z dodajanjem novega kroga
        System.out.println("\nKrožen premik elementov z dodajanjem novega kroga (b):");
        int originalSize = ll.size();
        for (int i = 0; i < originalSize; i++) {
            Circle first = ll.removeFirst();
            Circle last = ll.getLast();
            Circle newCircle = new Circle(last.x() + 1, last.y(), java.awt.Color.RED);
            ll.addLast(newCircle);
            ll.addLast(first);
            System.out.println(ll);
        }
    }
}
