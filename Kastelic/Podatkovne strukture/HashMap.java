// Primer implementacije HashMapa v Javi
public class HashMap {
    // Notranji razred za par ključ-vrednost
    class Entry {
        String key; // lahko je tudi drug tip podatka (npr. int, float, itd.)
        String value; // lahko je tudi drug tip podatka
        Entry next;

        public Entry(String key, String value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    private Entry[] table;
    private int capacity;

    // Konstruktor
    public HashMap(int size) {
        table = new Entry[size];
        capacity = size;
    }

    // Hash funkcija
    private int hash(String key) {
        return Math.abs(key.hashCode()) % capacity;
    }

    /**
     * Metoda za vstavljanje para ključ-vrednost
     * 
     * @param key   Ključ
     * @param value Vrednost
     */
    public void put(String key, String value) {
        int index = hash(key);
        Entry newEntry = new Entry(key, value);
        if (table[index] == null) {
            table[index] = newEntry;
        } else {
            Entry current = table[index];
            while (true) {
                if (current.key.equals(key)) {
                    current.value = value; // Posodobi obstoječo vrednost
                    return;
                }
                if (current.next == null) {
                    break;
                }
                current = current.next;
            }
            current.next = newEntry; // Dodaj na konec verige
        }
    }

    /**
     * Metoda za pridobivanje vrednosti glede na ključ
     * 
     * @param key Ključ
     * @return Vrednost ali null, če ključ ni najden
     */
    public String get(String key) {
        int index = hash(key);
        Entry current = table[index];
        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null; // Ključ ni najden
    }

    /**
     * Metoda za odstranjevanje para ključ-vrednost glede na ključ
     * 
     * @param key Ključ
     */
    public void remove(String key) {
        int index = hash(key);
        Entry current = table[index];
        Entry prev = null;
        while (current != null) {
            if (current.key.equals(key)) {
                if (prev == null) {
                    table[index] = current.next; // Odstrani prvo vozlišče v verigi
                } else {
                    prev.next = current.next; // Preskoči trenutno vozlišče
                }
                return;
            }
            prev = current;
            current = current.next;
        }
    }

    // Test
    public static void main(String[] args) {
        HashMap map = new HashMap(10);
        map.put("ime", "Janez");
        map.put("priimek", "Novak");
        System.out.println("Ime: " + map.get("ime")); // Ime: Janez
        System.out.println("Priimek: " + map.get("priimek")); // Priimek: Novak
        map.remove("ime");
        System.out.println("Ime po odstranitvi: " + map.get("ime")); // Ime po odstranitvi: null
    }
}
