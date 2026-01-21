/* - serija enakovrstnih številk vrste Long
- 6 000 000 000 takih naključnih zapisov z vrednostmi (6G zapisov)
  med Long.najmanjša in Long.največja

* koliko časa približno traja, da se taka datoteka kreira ?
* ali jo lahko prepišete v ArrayList ali LinkedList in vrednosti
  razvrstite po velikosti ?
* koliko časa traja tak postopek razvrščanja ?

Recimo, da po kreiranju datoteke skušate ugotoviti, katera po vrsti je
bila vpisana najmanjša vrednost in katera vrednost je to.

opomba: če imate problem z resursi, lahko dano število zapisov
zmanjšate v toliko, da v zapisu števila odstranite 3 ničle. */

import java.io.*;
import java.util.*;
import java.util.HashMap;
import java.util.Map;

public class naloga1 {
    private static final String FILE_NAME = "long_values.txt";
    private static final long TOTAL_VALUES = 6000000;

    public static void main(String[] args) {
        try {
            generateRandomLongs(FILE_NAME, TOTAL_VALUES);
            Map<Long, Integer> minInfo = readLongsAndFindMin(FILE_NAME);
            for (Map.Entry<Long, Integer> entry : minInfo.entrySet()) {
                System.out.println("Smallest value: " + entry.getKey());
                System.out.println("Position (order): " + entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void generateRandomLongs(String fileName, long totalValues) throws IOException {
        Random random = new Random();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (long i = 0; i < totalValues; i++) {
                long randomValue = random.nextLong();
                writer.write(Long.toString(randomValue));
                writer.newLine();
            }
        }
    }

    private static List<Long> readLongsFromFile(String fileName) throws IOException {
        List<Long> values = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                values.add(Long.parseLong(line));
            }
        }
        return values;
    }

    private static Map<Long, Integer> readLongsAndFindMin(String fileName) throws IOException {
        Map<Long, Integer> result = new HashMap<>();
        Long minValue = Long.MAX_VALUE;
        int minPosition = 0;
        int position = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Long value = Long.parseLong(line);
                position++;
                if (value < minValue) {
                    minValue = value;
                    minPosition = position;
                }
            }
        }

        result.put(minValue, minPosition);
        return result;
    }
}