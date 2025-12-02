/* V primeru, da v realizaciji prve naloge še niste pomislili na to, zalo ugodna realizacija bi bila:
    Queue<Vozilo> cesta1pas1 = new LinkedGTList<Vozilo>(1450);
Popravite realizacijo naloge 1 v skladu z dano deklaracijo.
*/
import java.util.Queue;
import java.util.Collection;
import java.util.Iterator;

public class naloga2 {
    public static void main(String[] args) {
        Queue<Vozilo> cesta1pas1 = new LinkedGTList<Vozilo>(1450);
        cesta1pas1.offer(new Vozilo("ABC123", 1, 400));
        cesta1pas1.offer(new Vozilo("DEF456", 2, 500));
        cesta1pas1.offer(new Vozilo("GHI789", 3, 600));

        while (!cesta1pas1.isEmpty()) {
            Vozilo v = cesta1pas1.poll();
            System.out.println("Obdelujemo vozilo: " + v.registracija);
        }
    }
}

class Vozilo {
    String registracija;
    int zaporednaStevilka;
    int dolzina; // v metrih

    public Vozilo(String registracija, int zaporednaStevilka, int dolzina) {
        this.registracija = registracija;
        this.zaporednaStevilka = zaporednaStevilka;
        this.dolzina = dolzina;
    }
}

class LinkedGTList<E> implements Queue<E> {
    private class Node {
        E data;
        Node next;

        Node(E data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head;
    private Node tail;
    private int size;
    private int capacity;

    public LinkedGTList(int capacity) {
        this.head = null;
        this.tail = null;
        this.size = 0;
        this.capacity = capacity;
    }

    @Override
    public boolean offer(E e) {
        if (size >= capacity) {
            return false; // Queue is full
        }
        Node newNode = new Node(e);
        if (tail != null) {
            tail.next = newNode;
        }
        tail = newNode;
        if (head == null) {
            head = newNode;
        }
        size++;
        return true;
    }

    @Override
    public E poll() {
        if (head == null) {
            return null; // Queue is empty
        }
        E data = head.data;
        head = head.next;
        if (head == null) {
            tail = null;
        }
        size--;
        return data;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'size'");
    }

    @Override
    public boolean contains(Object o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'contains'");
    }

    @Override
    public Iterator<E> iterator() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'iterator'");
    }

    @Override
    public Object[] toArray() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toArray'");
    }

    @Override
    public <T> T[] toArray(T[] a) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toArray'");
    }

    @Override
    public boolean remove(Object o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'containsAll'");
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addAll'");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeAll'");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'retainAll'");
    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'clear'");
    }

    @Override
    public boolean add(E e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }

    @Override
    public E remove() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public E element() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'element'");
    }

    @Override
    public E peek() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'peek'");
    }
}