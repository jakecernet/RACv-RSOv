import java.util.HashMap;

// Primer uporabe HashMap iz Java Collections Framework
public class HashMapUsage {
    public static void main(String[] args) {
        // Ustvarjanje HashMap, ki uporablja String kot ključ in Integer kot vrednost
        HashMap<String, Integer> map = new HashMap<>();

        // Dodajanje elementov v HashMap
        map.put("Ena", 1);
        map.put("Dva", 2);
        map.put("Tri", 3);

        // Pridobivanje vrednosti na podlagi ključa
        int value = map.get("Dva");
        System.out.println("Vrednost za ključ 'Dva': " + value);

        // Preverjanje prisotnosti ključa
        if (map.containsKey("Tri")) {
            System.out.println("HashMap vsebuje ključ 'Tri'");
        }

        // Odstranjevanje elementa iz HashMap
        map.remove("Ena");
        System.out.println("Po odstranitvi ključa 'Ena': " + map);

        // Iteracija skozi vse vnose v HashMap
        for (HashMap.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println("Ključ: " + entry.getKey() + ", Vrednost: " + entry.getValue());
        }
    }  
}
