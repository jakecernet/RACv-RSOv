
/* V nalogi 4 izvedemo vizualizacijo največ najboljših 10 igralcev (po rezultatu). V primeru, da opcija -o ni podana, se teh 10 izpiše na zaslon v padajočem vrstnem redu njihovega rezultata (od top do desetega). Opcija -o določa, da se izpis izvrši v datoteko s podanim imenom, privzeto je to besedilna (txt), z opcijo -t opredelite še vsebino izhodne datoteke : oblikovana kot html tabela ali xml datoteka.

!- potrebno bo preverjanje formatov ukazov in zagotavljanje potrebnih opcij !

! – struktura XML naj bo kot spodaj,

<?xml version="1.0" encoding="UTF-8"?>
<rezultati>
<igralec>
<uporabnik>Jole</uporabnik>
<rezultat>12345</rezultat>
<datum_cas>2022-11-31 8:00:01</datum_cas>
<st_igranj>7312</st_igranj>
</igralec>
</rezultati>

dobljeno datoteko validirajte z validatorjem npr.

https://www.liquid-technologies.com/online-xml-validator -! */
import java.io.*;
import java.util.*;

public class naloga4 {
    private static final String REZULTATI_FILE = "rezultati.csv";
    private static final String HEADER = "uporabnik;email;rezultat;dosežen_dne;število_igranj";

    public static void main(String[] args) {
        if (args.length < 1) {
            return;
        }

        String command = args[0];

        if (command.equals("registriraj")) {
            if (args.length < 5) {
                return;
            }

            String username = null;
            String email = null;

            for (int i = 1; i < args.length; i++) {
                if (args[i].equals("-u") && i + 1 < args.length) {
                    username = args[i + 1];
                    i++;
                } else if (args[i].equals("-email") && i + 1 < args.length) {
                    email = args[i + 1];
                    i++;
                }
            }

            if (username == null || email == null) {
                return;
            }

            initializeFile();

            if (userExists(username)) {
                return;
            }

            addUser(username, email);
        } else if (command.equals("zbrisi_email")) {
            String emailToDelete = null;
            for (int i = 1; i < args.length; i++) {
                if (args[i].equals("-email") && i + 1 < args.length) {
                    emailToDelete = args[i + 1];
                    break;
                }
            }
            if (emailToDelete != null) {
                removeUserByEmail(emailToDelete);
                replaceOriginalFile(new File(REZULTATI_FILE), new File("temp_rezultati.csv"));
            }
        } else if (command.equals("dodaj")) {
            if (args.length < 7) {
                return;
            }

            String username = null;
            String email = null;
            Integer rezultat = null;

            for (int i = 1; i < args.length; i++) {
                if (args[i].equals("-u") && i + 1 < args.length) {
                    username = args[i + 1];
                    i++;
                } else if (args[i].equals("-email") && i + 1 < args.length) {
                    email = args[i + 1];
                    i++;
                } else if (args[i].equals("-rezultat") && i + 1 < args.length) {
                    try {
                        rezultat = Integer.parseInt(args[i + 1]);
                    } catch (NumberFormatException e) {
                        return;
                    }
                    i++;
                }
            }

            if (username == null || email == null || rezultat == null) {
                return;
            }

            dodajRezultat(username, email, rezultat);
        } else if (command.equals("izpis")) {
            String outputFile = null;
            String format = "txt";

            for (int i = 1; i < args.length; i++) {
                if (args[i].equals("-o") && i + 1 < args.length) {
                    outputFile = args[i + 1];
                    i++;
                } else if (args[i].equals("-t") && i + 1 < args.length) {
                    format = args[i + 1];
                    i++;
                }
            }

            if (outputFile == null) {
                izpisiNajboljsih10Console();
            } else {
                izpisiNajboljsih10File(outputFile, format);
            }
        }
    }

    private static void initializeFile() {
        File file = new File(REZULTATI_FILE);
        if (!file.exists()) {
            try (FileWriter writer = new FileWriter(REZULTATI_FILE)) {
                writer.write(HEADER + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean userExists(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(REZULTATI_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals(HEADER))
                    continue;

                String[] parts = line.split(";");
                if (parts.length > 0 && parts[0].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void addUser(String username, String email) {
        try (FileWriter writer = new FileWriter(REZULTATI_FILE, true)) {
            String newRecord = username + ";" + email + ";0;;0\n";
            writer.write(newRecord);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void removeUserByEmail(String email) {
        File inputFile = new File(REZULTATI_FILE);
        File tempFile = new File("temp_rezultati.csv");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                FileWriter writer = new FileWriter(tempFile)) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals(HEADER)) {
                    writer.write(line + "\n");
                    continue;
                }

                String[] parts = line.split(";");
                if (parts.length > 1 && parts[1].equals(email)) {
                    continue; // Skip the line with the matching email
                }
                writer.write(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void replaceOriginalFile(File inputFile, File tempFile) {
        if (!inputFile.delete()) {
            System.out.println("Could not delete original file");
            return;
        }
        if (!tempFile.renameTo(inputFile)) {
            System.out.println("Could not rename temp file to original file name");
        }
    }

    private static void dodajRezultat(String username, String email, int rezultat) {
        File inputFile = new File(REZULTATI_FILE);
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                FileWriter writer = new FileWriter("temp_rezultati.csv")) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals(HEADER)) {
                    writer.write(line + "\n");
                    continue;
                }

                String[] parts = line.split(";");
                if (parts.length > 0 && parts[0].equals(username)) {
                    int existingRezultat = Integer.parseInt(parts[2]);
                    int steviloIgranj = Integer.parseInt(parts[4]);

                    steviloIgranj++;

                    if (rezultat > existingRezultat) {
                        String currentDate = java.time.LocalDate.now().toString();
                        String currentTime = java.time.LocalTime.now().toString();
                        String newLine = username + ";" + email + ";" + rezultat + ";" + currentDate + " " + currentTime
                                + ";" + steviloIgranj + "\n";
                        writer.write(newLine);
                    } else {
                        String newLine = username + ";" + email + ";" + existingRezultat + ";" + parts[3] + ";"
                                + steviloIgranj + "\n";
                        writer.write(newLine);
                    }
                } else {
                    writer.write(line + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        replaceOriginalFile(new File(REZULTATI_FILE), new File("temp_rezultati.csv"));
    }

    private static class Player implements Comparable<Player> {
        String username;
        int rezultat;
        String datumCas;
        int steviloIgranj;

        Player(String username, int rezultat, String datumCas, int steviloIgranj) {
            this.username = username;
            this.rezultat = rezultat;
            this.datumCas = datumCas;
            this.steviloIgranj = steviloIgranj;
        }

        @Override
        public int compareTo(Player other) {
            return Integer.compare(other.rezultat, this.rezultat);
        }
    }

    private static List<Player> getTop10Players() {
        List<Player> players = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(REZULTATI_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals(HEADER))
                    continue;

                String[] parts = line.split(";");
                if (parts.length >= 5 && !parts[2].isEmpty()) {
                    String username = parts[0];
                    int rezultat = Integer.parseInt(parts[2]);
                    String datumCas = parts[3];
                    int steviloIgranj = Integer.parseInt(parts[4]);

                    players.add(new Player(username, rezultat, datumCas, steviloIgranj));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Collections.sort(players);
        return players.size() > 10 ? players.subList(0, 10) : players;
    }

    private static void izpisiNajboljsih10Console() {
        List<Player> players = getTop10Players();

        System.out.println("Top 10 Najboljših Igralcev:");
        System.out.println("============================");
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            System.out.println((i + 1) + ". " + p.username + " - " + p.rezultat + " točk (Igral: "
                    + p.steviloIgranj + "x, Dosežen: " + p.datumCas + ")");
        }
    }

    private static void izpisiNajboljsih10File(String outputFile, String format) {
        List<Player> players = getTop10Players();

        if (format.equals("xml")) {
            writeXML(outputFile, players);
        } else if (format.equals("html")) {
            writeHTML(outputFile, players);
        } else {
            writeTXT(outputFile, players);
        }
    }

    private static void writeXML(String filename, List<Player> players) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write("<rezultati>\n");

            for (Player p : players) {
                writer.write("  <igralec>\n");
                writer.write("    <uporabnik>" + escapeXML(p.username) + "</uporabnik>\n");
                writer.write("    <rezultat>" + p.rezultat + "</rezultat>\n");
                writer.write("    <datum_cas>" + escapeXML(p.datumCas) + "</datum_cas>\n");
                writer.write("    <st_igranj>" + p.steviloIgranj + "</st_igranj>\n");
                writer.write("  </igralec>\n");
            }

            writer.write("</rezultati>\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeHTML(String filename, List<Player> players) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("<!DOCTYPE html>\n");
            writer.write("<html>\n");
            writer.write("<head>\n");
            writer.write("  <meta charset=\"UTF-8\">\n");
            writer.write("  <title>Top 10 Najboljših Igralcev</title>\n");
            writer.write("  <style>\n");
            writer.write("    table { border-collapse: collapse; width: 100%; }\n");
            writer.write("    th, td { border: 1px solid black; padding: 8px; text-align: left; }\n");
            writer.write("    th { background-color: #4CAF50; color: white; }\n");
            writer.write("  </style>\n");
            writer.write("</head>\n");
            writer.write("<body>\n");
            writer.write("  <h1>Top 10 Najboljših Igralcev</h1>\n");
            writer.write("  <table>\n");
            writer.write("    <tr>\n");
            writer.write("      <th>Mesto</th>\n");
            writer.write("      <th>Uporabnik</th>\n");
            writer.write("      <th>Rezultat</th>\n");
            writer.write("      <th>Datum in Čas</th>\n");
            writer.write("      <th>Število Iger</th>\n");
            writer.write("    </tr>\n");

            for (int i = 0; i < players.size(); i++) {
                Player p = players.get(i);
                writer.write("    <tr>\n");
                writer.write("      <td>" + (i + 1) + "</td>\n");
                writer.write("      <td>" + escapeHTML(p.username) + "</td>\n");
                writer.write("      <td>" + p.rezultat + "</td>\n");
                writer.write("      <td>" + escapeHTML(p.datumCas) + "</td>\n");
                writer.write("      <td>" + p.steviloIgranj + "</td>\n");
                writer.write("    </tr>\n");
            }

            writer.write("  </table>\n");
            writer.write("</body>\n");
            writer.write("</html>\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeTXT(String filename, List<Player> players) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("Top 10 Najboljših Igralcev\n");
            writer.write("============================\n\n");

            for (int i = 0; i < players.size(); i++) {
                Player p = players.get(i);
                writer.write((i + 1) + ". " + p.username + " - " + p.rezultat + " točk\n");
                writer.write("   Igral: " + p.steviloIgranj + "x\n");
                writer.write("   Dosežen: " + p.datumCas + "\n\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String escapeXML(String text) {
        return text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&apos;");
    }

    private static String escapeHTML(String text) {
        return text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;");
    }
}