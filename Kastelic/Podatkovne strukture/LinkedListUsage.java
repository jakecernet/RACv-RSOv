import java.util.LinkedList;

// Uporaba vgrajenega LinkedLista iz Java Collections Framework
public class LinkedListUsage {
    public static void main(String[] args) {
        // Ustvarjanje LinkedLista
        LinkedList<Integer> list = new LinkedList<>();

        // Dodajanje elementov
        list.add(10);
        list.add(20);
        list.add(30);

        // Izpis elementov
        System.out.println("Elementi v LinkedListu: " + list);

        System.out.print("Elementi v LinkedListu: ");
        for (Integer element : list) {
            System.out.print(element + " -> ");
        }
        System.out.println();

        // Preverjanje prisotnosti elementa
        int searchElement = 20;
        if (list.contains(searchElement)) {
            System.out.println("LinkedList vsebuje element: " + searchElement);
        } else {
            System.out.println("LinkedList ne vsebuje elementa: " + searchElement);
        }

        // Odstranjevanje elementa
        list.remove(Integer.valueOf(20));
        System.out.println("Elementi po odstranitvi 20: " + list);
    }
}
