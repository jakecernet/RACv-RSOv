/* Realizirajte registracijo uporabnika. Mehanizem zahteva 5 argumentov. Prvi je 'registriraj', potem pa v poljubnem vrstnem redu sledijo opcije s pripadajočimi vrednostmi opcij.
 Da se uporabnik registrira, mora biti podanih vseh 5 argumentov. V primeru, da uporabnik že obstaja (enaka vrednost polja uporabnik) registracijo novega uporabnika zavrnemo. 
Potrditve registracije oz. zavrnitve ne oglašujemo. */

import java.io.*;

public class naloga1 {
    private static final String REZULTATI_FILE = "rezultati.csv";
    private static final String HEADER = "uporabnik;email;rezultat;dosežen_dne;število_igranj";

    public static void main(String[] args) {
        if (args.length < 5 || !args[0].equals("registriraj")) {
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
}
