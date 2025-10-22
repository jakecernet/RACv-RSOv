/* Nova programska zbirka priimkov je glede na zapisane priimke abecedno urejena. 
Zagotovite ustrezno dodajanje oseb, da bo zbirka ves čas urejena, prikaze z metodama prikaziNaprej in prikaziNazaj, 
ki priimke zapisov zbirke podata na zaslon od prvega naprej proti koncu ali od zadnjega nazaj do prvega zapisanega. 
Zapis zbirke naj bo tak, da bo omogočil izpis zadnjih 10 dodanih priimkov v obratnem časovnem vrstnem redu dodajanj elementov, metoda naj bo poimenovana zadnjih10. 
Tudi tu uporabite ustrezno spisano metodo dodajPriimek/1 za dodajanje elementa.
Opombi: zbirka naj bo glede priimkov še vedno množica, za določanje zadnjih desetih uvedite indeksiranje dodajanja elementov (oštevilčite jih).
*/

public class naloga2 {
    public static void main(String[] args) {
        ZbirkaPriimkov zbirka = new ZbirkaPriimkov();

        zbirka.dodajPriimek("Novak");
        zbirka.dodajPriimek("Kovač");
        zbirka.dodajPriimek("Horvat");
        zbirka.dodajPriimek("Zupančič");
        zbirka.dodajPriimek("Kralj");
        zbirka.dodajPriimek("Vidmar");
        zbirka.dodajPriimek("Petek");
        zbirka.dodajPriimek("Mali");
        zbirka.dodajPriimek("Veliki");
        zbirka.dodajPriimek("Gorenjak");
        zbirka.dodajPriimek("Dolenec");

        System.out.println("Prikaz zbirke od prvega do zadnjega:");
        zbirka.prikaziNaprej();

        System.out.println("\nPrikaz zbirke od zadnjega do prvega:");
        zbirka.prikaziNazaj();

        System.out.println("\nPrikaz zadnjih 10 priimkov v obratnem vrstnem redu:");
        zbirka.zadnjih10();
    }

    public interface Zbirka {
        void dodajPriimek(String priimek);
        void prikaziNaprej();
        void prikaziNazaj();
        void zadnjih10();
    }

    public static class ZbirkaPriimkov implements Zbirka {
        private Node head;

        private static class Node {
            String priimek;
            Node next;

            Node(String priimek) {
                this.priimek = priimek;
                this.next = null;
            }
        }

        @Override
        public void dodajPriimek(String priimek) {
            if (priimek == null) return;
            priimek = priimek.trim();
            if (priimek.isEmpty()) return;

            Node cur = head;
            while (cur != null) {
                if (cur.priimek.equalsIgnoreCase(priimek)) {
                    return;
                }
                cur = cur.next;
            }

            Node newNode = new Node(priimek);
            if (head == null || head.priimek.compareToIgnoreCase(priimek) > 0) {
                newNode.next = head;
                head = newNode;
                return;
            }

            Node current = head;
            while (current.next != null && current.next.priimek.compareToIgnoreCase(priimek) < 0) {
                current = current.next;
            }
            newNode.next = current.next;
            current.next = newNode;
        }

        @Override
        public void prikaziNaprej() {
            Node current = head;
            while (current != null) {
                System.out.println(current.priimek);
                current = current.next;
            }
        }

        @Override
        public void prikaziNazaj() {
            prikaziNazajHelper(head);
        }

        private void prikaziNazajHelper(Node node) {
            if (node == null) {
                return;
            }
            prikaziNazajHelper(node.next);
            System.out.println(node.priimek);
        }

        @Override
        public void zadnjih10() {
            String[] lastTen = new String[10];
            int count = 0;

            Node current = head;
            while (current != null) {
                if (count < 10) {
                    lastTen[count] = current.priimek;
                } else {
                    for (int i = 0; i < 9; i++) {
                        lastTen[i] = lastTen[i + 1];
                    }
                    lastTen[9] = current.priimek;
                }
                count++;
                current = current.next;
            }

            for (int i = Math.min(count, 10) - 1; i >= 0; i--) {
                System.out.println(lastTen[i]);
            }
        }
    }
}
