/*  Primer enostavne implementacije LinkedLista v Javi  */
class LinkedList {
    Node head;

    // Vozlišče seznama
    static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    // Konstruktor
    public LinkedList() {
        this.head = null;
    }

    /**
     * Doda nov element na konec povezanega seznama.
     * 
     * @param data Podatki, ki jih želimo dodati v seznam.
     */
    public void add(int data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    /**
     * Izpiše vse elemente povezanega seznama.
     */
    public void printList() {
        Node current = head;
        while (current != null) {
            System.out.print(current.data + " -> ");
            current = current.next;
        }
        System.out.println("null");
    }

    /**
     * Preveri, ali seznam vsebuje določen element.
     * 
     * @param data Podatki, ki jih iščemo v seznamu.
     * @return true, če seznam vsebuje element, sicer false.
     */
    public boolean contains(int data) {
        Node current = head;
        while (current != null) {
            if (current.data == data) {
                return true;
            }
            current = current.next;
        }
        return false;
    }
}
