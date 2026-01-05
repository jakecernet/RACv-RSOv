// Primer implementacije preproste vrste (queue) v Javi
public class Queue<T> { // Generični tip T za elemente (torej lahko shranjujemo katerikoli tip podatkov)
    private Node<T> front;
    private Node<T> rear;
    private int size;

    // Notranja razred za vozlišča vrste
    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    public Queue() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }

    /**
     * Dodajanje elementa v vrsto
     * 
     * @param item Element, ki ga želimo dodati v vrsto
     */
    public void enqueue(T item) {
        Node<T> newNode = new Node<>(item);
        if (rear != null) {
            rear.next = newNode;
        }
        rear = newNode;
        if (front == null) {
            front = newNode;
        }
        size++;
    }

    /**
     * Odstranjevanje elementa iz vrste
     * 
     * @return Odstranjeni element iz vrste
     * @throws IllegalStateException Če je vrsta prazna
     */
    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Vrsta je prazna");
        }
        T item = front.data;
        front = front.next;
        if (front == null) {
            rear = null;
        }
        size--;
        return item;
    }

    // Preverjanje, ali je vrsta prazna
    public boolean isEmpty() {
        return size == 0;
    }

    // Pridobivanje velikosti vrste
    public int size() {
        return size;
    }

    /**
     * Pogled na prvi element vrste brez odstranjevanja
     * 
     * @return Prvi element vrste
     * @throws IllegalStateException Če je vrsta prazna
     */
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Vrsta je prazna");
        }
        return front.data;
    }

    /**
     * Izpiše vse elemente vrste
     * 
     * @return String predstavitev vrste
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node<T> current = front;
        sb.append("Front -> ");
        while (current != null) {
            sb.append(current.data).append(" -> ");
            current = current.next;
        }
        sb.append("Rear");
        return sb.toString();
    }

    // Glavna metoda za testiranje vrste
    public static void main(String[] args) {
        Queue<Integer> queue = new Queue<>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);

        System.out.println("Velikost vrste: " + queue.size());

        System.out.println("Element na vrhu vrste: " + queue.peek());

        System.out.println("Vsebina vrste: " + queue);

        while (!queue.isEmpty()) {
            System.out.println("Odstranjeni element: " + queue.dequeue());
        }
    }
}