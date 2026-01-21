
/* Mehanizem 'dodaj' zapiše rezultat uporabnika: če uporabnik ni registriran, zapis rezultata tiho zavrne. 
V primeru, da uporabnik še nima vpisanega rezultata, ga vpiše, vpiše trenutni datum in čas, števec igranj postavi na 1. 
V primer, da uporabnik že ima vpisan rezultat, poveča števec igranj za 1; če je novi rezultat višji od predhodno vpisanega ga popravi, 
popravi tudi datum dosežka s trenutnim datumom in časom, v nasprotnem primeru rezultata in datuma dosežka ne spreminja. */
import java.io.*;

public class naloga3 {
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
}