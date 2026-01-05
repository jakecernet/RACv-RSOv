import java.util.Queue;

// Primer uporabe vrste (queue iz Java Collections Framework) v Javi
public class QueueUsage {
    public static void main(String[] args) {
        // Ustvarimo vrsto z uporabo LinkedList
        Queue<Integer> queue = new java.util.LinkedList<>();

        // Dodajanje elementov v vrsto
        queue.add(10);
        queue.add(20);
        queue.add(30);

        System.out.println("Vrsta po dodajanju elementov: " + queue);

        System.out.println("Prvi element v vrsti: " + queue.peek());

        // Odstranjevanje elementov iz vrste
        System.out.println("Odstranjeni element: " + queue.poll());
        System.out.println("Vrsta po odstranitvi elementa: " + queue);

        // Pregled prvega elementa brez odstranitve
        System.out.println("Prvi element v vrsti: " + queue.peek());
        System.out.println("Vrsta ostaja nespremenjena: " + queue);

        // Velikost vrste
        System.out.println("Velikost vrste: " + queue.size());

        // Izpis celotne vrste
        System.out.println("Celotna vrsta: " + queue);
    }
}
