/* Izvedite sistem 'iskanja' po datoteki; metoda iskalnega objekta, ki bo uporabljal datoteko, 
naj v skladu s podano poštno številko vrne ime kraja. */

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

// Iskalni objekt za iskanje po datoteki
class IskalnikLokacij {
    private String imeDatoteke;

    public IskalnikLokacij(String imeDatoteke) {
        this.imeDatoteke = imeDatoteke;
    }

    // Metoda, ki vrne ime kraja (mesto) glede na poštno številko
    public String najdiKraj(int postnaStevilka) {
        try (RandomAccessFile raf = new RandomAccessFile(imeDatoteke, "r")) {
            int stLokacij = raf.readInt();

            // Ker je datoteka urejena po poštni številki, lahko uporabimo binarno iskanje
            int levo = 0;
            int desno = stLokacij - 1;

            while (levo <= desno) {
                int sredina = (levo + desno) / 2;

                // Skoči na sredinski zapis
                long pozicija = 4 + (long) sredina * Lokacija.RECORD_SIZE;
                raf.seek(pozicija);

                Lokacija lok = Lokacija.preberiIzDatoteke(raf);

                if (lok.getPostnaStevilka() == postnaStevilka) {
                    return lok.getMesto();
                } else if (lok.getPostnaStevilka() < postnaStevilka) {
                    levo = sredina + 1;
                } else {
                    desno = sredina - 1;
                }
            }

            return null; // Ni najdeno
        } catch (Exception e) {
            System.out.println("Napaka pri iskanju: " + e.getMessage());
            return null;
        }
    }

    // Linerano iskanje - vrne vse lokacije s podano poštno številko
    public ArrayList<Lokacija> najdiVseLokacije(int postnaStevilka) {
        ArrayList<Lokacija> najdene = new ArrayList<>();
        try (RandomAccessFile raf = new RandomAccessFile(imeDatoteke, "r")) {
            int stLokacij = raf.readInt();

            for (int i = 0; i < stLokacij; i++) {
                Lokacija lok = Lokacija.preberiIzDatoteke(raf);
                if (lok.getPostnaStevilka() == postnaStevilka) {
                    najdene.add(lok);
                }
            }
        } catch (Exception e) {
            System.out.println("Napaka pri iskanju: " + e.getMessage());
        }
        return najdene;
    }

    // Vrne lokacijo na določenem indeksu (naključni dostop)
    public Lokacija getLokacija(int indeks) {
        try (RandomAccessFile raf = new RandomAccessFile(imeDatoteke, "r")) {
            int stLokacij = raf.readInt();

            if (indeks < 0 || indeks >= stLokacij) {
                return null;
            }

            long pozicija = 4 + (long) indeks * Lokacija.RECORD_SIZE;
            raf.seek(pozicija);
            return Lokacija.preberiIzDatoteke(raf);
        } catch (Exception e) {
            System.out.println("Napaka pri branju: " + e.getMessage());
            return null;
        }
    }
}

public class naloga2 {
    // Tabele za generiranje naključnih lokacij
    static String[] drzave = { "Slovenija", "Avstrija", "Italija", "Hrvaška", "Madžarska" };
    static String[] mesta = { "Ljubljana", "Maribor", "Celje", "Kranj", "Koper", "Novo mesto",
            "Velenje", "Nova Gorica", "Ptuj", "Murska Sobota" };
    static String[] ulice = { "Glavna ulica", "Šolska ulica", "Prešernova cesta", "Cankarjeva ulica",
            "Titova cesta", "Ljubljanska cesta", "Mariborska cesta", "Tržaška cesta",
            "Dunajska cesta", "Celovška cesta" };

    public static void main(String[] args) {
        ArrayList<Lokacija> lokacije = generirajLokacije(100);

        Collections.sort(lokacije, Comparator.comparingInt(Lokacija::getPostnaStevilka));

        System.out.println("Generirano " + lokacije.size() + " lokacij, urejenih po poštni številki:");
        System.out.println("-----------------------------------------------------------");

        // Zapiši na datoteko
        try (RandomAccessFile raf = new RandomAccessFile("lokacije.dat", "rw")) {
            raf.writeInt(lokacije.size());

            for (Lokacija lok : lokacije) {
                lok.zapisiNaDatoteko(raf);
            }
            System.out.println("Lokacije zapisane v datoteko 'lokacije.dat'");
        } catch (Exception e) {
            System.out.println("Napaka pri pisanju: " + e.getMessage());
        }

        // Izpiši vse lokacije
        System.out.println("\nVse lokacije v datoteki:");
        System.out.println("-----------------------------------------------------------");
        try (RandomAccessFile raf = new RandomAccessFile("lokacije.dat", "r")) {
            int stLokacij = raf.readInt();
            for (int i = 0; i < stLokacij; i++) {
                Lokacija lok = Lokacija.preberiIzDatoteke(raf);
                System.out.printf("%3d. %s%n", i + 1, lok);
            }
        } catch (Exception e) {
            System.out.println("Napaka pri branju: " + e.getMessage());
        }

        // Demonstracija iskalnika
        System.out.println("\n===========================================================");
        System.out.println("DEMONSTRACIJA ISKALNIKA LOKACIJ");
        System.out.println("===========================================================");

        IskalnikLokacij iskalnik = new IskalnikLokacij("lokacije.dat");

        // Vzemi naključno poštno številko iz seznama za demonstracijo
        int testPostna = lokacije.get(50).getPostnaStevilka();
        System.out.println("\nIskanje kraja za poštno številko " + testPostna + ":");
        String kraj = iskalnik.najdiKraj(testPostna);
        if (kraj != null) {
            System.out.println("Najden kraj: " + kraj);
        } else {
            System.out.println("Kraj ni najden.");
        }

        // Iskanje vseh lokacij z isto poštno številko
        System.out.println("\nVse lokacije s poštno številko " + testPostna + ":");
        ArrayList<Lokacija> najdene = iskalnik.najdiVseLokacije(testPostna);
        for (Lokacija lok : najdene) {
            System.out.println("  - " + lok);
        }

        // Naključni dostop do lokacije
        System.out.println("\nNaključni dostop - 10. lokacija:");
        Lokacija lok10 = iskalnik.getLokacija(9);
        if (lok10 != null) {
            System.out.println("  " + lok10);
        }

        // Interaktivno iskanje
        System.out.println("\n-----------------------------------------------------------");
        System.out.println("Interaktivno iskanje (vnesite 0 za izhod):");
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Vnesite poštno številko: ");
            int postna = sc.nextInt();
            if (postna == 0)
                break;

            String najdenKraj = iskalnik.najdiKraj(postna);
            if (najdenKraj != null) {
                System.out.println("Kraj: " + najdenKraj);
            } else {
                System.out.println("Poštna številka ni najdena v datoteki.");
            }
        }
        sc.close();
    }

    public static ArrayList<Lokacija> generirajLokacije(int n) {
        ArrayList<Lokacija> seznam = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < n; i++) {
            String drzava = drzave[rand.nextInt(drzave.length)];
            String mesto = mesta[rand.nextInt(mesta.length)];
            String ulica = ulice[rand.nextInt(ulice.length)];
            int hisnaStevilka = rand.nextInt(200) + 1;
            int postnaStevilka = rand.nextInt(9000) + 1000; // 1000-9999

            seznam.add(new Lokacija(drzava, mesto, ulica, hisnaStevilka, postnaStevilka));
        }
        return seznam;
    }
}

class Lokacija {
    private String drzava;
    private String mesto;
    private String ulica;
    private int hisnaStevilka;
    private int postnaStevilka;

    public static final int DRZAVA_LEN = 20;
    public static final int MESTO_LEN = 30;
    public static final int ULICA_LEN = 40;
    public static final int RECORD_SIZE = DRZAVA_LEN * 2 + MESTO_LEN * 2 + ULICA_LEN * 2 + 4 + 4; // chars * 2 + 2 ints

    public Lokacija(String drzava, String mesto, String ulica, int hisnaStevilka, int postnaStevilka) {
        this.drzava = drzava;
        this.mesto = mesto;
        this.ulica = ulica;
        this.hisnaStevilka = hisnaStevilka;
        this.postnaStevilka = postnaStevilka;
    }

    public String getDrzava() {
        return drzava;
    }

    public String getMesto() {
        return mesto;
    }

    public String getUlica() {
        return ulica;
    }

    public int getHisnaStevilka() {
        return hisnaStevilka;
    }

    public int getPostnaStevilka() {
        return postnaStevilka;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s %d, %d", drzava.trim(), mesto.trim(), ulica.trim(), hisnaStevilka,
                postnaStevilka);
    }

    public void zapisiNaDatoteko(RandomAccessFile raf) throws Exception {
        raf.writeChars(paddString(drzava, DRZAVA_LEN));
        raf.writeChars(paddString(mesto, MESTO_LEN));
        raf.writeChars(paddString(ulica, ULICA_LEN));
        raf.writeInt(hisnaStevilka);
        raf.writeInt(postnaStevilka);
    }

    // Preberi iz RandomAccessFile
    public static Lokacija preberiIzDatoteke(RandomAccessFile raf) throws Exception {
        String drzava = readFixedString(raf, DRZAVA_LEN);
        String mesto = readFixedString(raf, MESTO_LEN);
        String ulica = readFixedString(raf, ULICA_LEN);
        int hisnaStevilka = raf.readInt();
        int postnaStevilka = raf.readInt();
        return new Lokacija(drzava, mesto, ulica, hisnaStevilka, postnaStevilka);
    }

    private static String paddString(String s, int len) {
        if (s.length() > len) {
            return s.substring(0, len);
        }
        StringBuilder sb = new StringBuilder(s);
        while (sb.length() < len) {
            sb.append(' ');
        }
        return sb.toString();
    }

    private static String readFixedString(RandomAccessFile raf, int len) throws Exception {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(raf.readChar());
        }
        return sb.toString().trim();
    }
}
