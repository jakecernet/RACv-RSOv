/* Izvedite sistem za shranjevanje in restavriranje opisane strukture.
Organizacija datoteke/zbirke naj bo objektna - uporabite ObjektInput/Output stream. */

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

// Lokacija - Serializable za shranjevanje
class Lokacija4 implements Serializable {
    private static final long serialVersionUID = 1L;

    private String drzava;
    private String mesto;
    private String ulica;
    private int hisnaStevilka;
    private int postnaStevilka;

    public Lokacija4(String drzava, String mesto, String ulica, int hisnaStevilka, int postnaStevilka) {
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
        return String.format("%s, %s, %s %d, %d", drzava, mesto, ulica, hisnaStevilka, postnaStevilka);
    }
}

// Obiskovalec - Serializable
class Obiskovalec4 implements Serializable {
    private static final long serialVersionUID = 1L;

    private String ime;
    private String priimek;
    private Lokacija4 lokacija;

    public Obiskovalec4(String ime, String priimek, Lokacija4 lokacija) {
        this.ime = ime;
        this.priimek = priimek;
        this.lokacija = lokacija;
    }

    public String getIme() {
        return ime;
    }

    public String getPriimek() {
        return priimek;
    }

    public Lokacija4 getLokacija() {
        return lokacija;
    }

    public String getPolnoIme() {
        return ime + " " + priimek;
    }

    @Override
    public String toString() {
        return String.format("%s %s, %s", ime, priimek, lokacija);
    }
}

// Sedež - Serializable
class Sedez4 implements Serializable {
    private static final long serialVersionUID = 1L;

    private int stevilkaSedeza;
    private Obiskovalec4 lastnik;

    public Sedez4(int stevilkaSedeza) {
        this.stevilkaSedeza = stevilkaSedeza;
        this.lastnik = null;
    }

    public int getStevilkaSedeza() {
        return stevilkaSedeza;
    }

    public Obiskovalec4 getLastnik() {
        return lastnik;
    }

    public boolean jeZaseden() {
        return lastnik != null;
    }

    public void rezerviraj(Obiskovalec4 obiskovalec) {
        this.lastnik = obiskovalec;
    }

    public void sprosti() {
        this.lastnik = null;
    }

    @Override
    public String toString() {
        if (lastnik != null) {
            return String.format("Sedež %d: %s", stevilkaSedeza, lastnik.getPolnoIme());
        }
        return String.format("Sedež %d: prost", stevilkaSedeza);
    }
}

// Dogodek - Serializable
class Dogodek4 implements Serializable {
    private static final long serialVersionUID = 1L;

    private String tema;
    private Date datumInUra;
    private int trajanje;
    private String imePrizorisce; // shranimo samo ime, ne celega objekta
    private ArrayList<Sedez4> sedezi;

    public Dogodek4(String tema, Date datumInUra, int trajanje, Prizorisce4 prizorisce) {
        this.tema = tema;
        this.datumInUra = datumInUra;
        this.trajanje = trajanje;
        this.imePrizorisce = prizorisce.getIme();
        this.sedezi = new ArrayList<>();
        for (int i = 1; i <= prizorisce.getSteviloMest(); i++) {
            sedezi.add(new Sedez4(i));
        }
    }

    public String getTema() {
        return tema;
    }

    public Date getDatumInUra() {
        return datumInUra;
    }

    public int getTrajanje() {
        return trajanje;
    }

    public String getImePrizorisce() {
        return imePrizorisce;
    }

    public ArrayList<Sedez4> getSedezi() {
        return sedezi;
    }

    public int getSteviloProstihMest() {
        int count = 0;
        for (Sedez4 s : sedezi) {
            if (!s.jeZaseden())
                count++;
        }
        return count;
    }

    public int getSteviloZasedenihMest() {
        return sedezi.size() - getSteviloProstihMest();
    }

    public Sedez4 rezervirajSedez(int stevilka, Obiskovalec4 obiskovalec) {
        if (stevilka > 0 && stevilka <= sedezi.size()) {
            Sedez4 sedez = sedezi.get(stevilka - 1);
            if (!sedez.jeZaseden()) {
                sedez.rezerviraj(obiskovalec);
                return sedez;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return String.format("Dogodek: %s\nPrizorišče: %s\nDatum: %s\nTrajanje: %d min\nProsta mesta: %d/%d",
                tema, imePrizorisce, datumInUra, trajanje, getSteviloProstihMest(), sedezi.size());
    }

    public void izpisiSedeze() {
        System.out.println("Seznam sedežev za dogodek '" + tema + "':");
        for (Sedez4 s : sedezi) {
            System.out.println("  " + s);
        }
    }
}

// Prizorišče - Serializable
class Prizorisce4 implements Serializable {
    private static final long serialVersionUID = 1L;

    private String ime;
    private Lokacija4 lokacija;
    private int steviloMest;
    private ArrayList<Dogodek4> dogodki;

    public Prizorisce4(String ime, Lokacija4 lokacija, int steviloMest) {
        this.ime = ime;
        this.lokacija = lokacija;
        this.steviloMest = steviloMest;
        this.dogodki = new ArrayList<>();
    }

    public String getIme() {
        return ime;
    }

    public Lokacija4 getLokacija() {
        return lokacija;
    }

    public int getSteviloMest() {
        return steviloMest;
    }

    public ArrayList<Dogodek4> getDogodki() {
        return dogodki;
    }

    public Dogodek4 dodajDogodek(String tema, Date datumInUra, int trajanje) {
        Dogodek4 dogodek = new Dogodek4(tema, datumInUra, trajanje, this);
        dogodki.add(dogodek);
        return dogodek;
    }

    @Override
    public String toString() {
        return String.format("Prizorišče: %s\nLokacija: %s\nŠtevilo mest: %d\nŠtevilo dogodkov: %d",
                ime, lokacija, steviloMest, dogodki.size());
    }
}

// Glavna podatkovna struktura za shranjevanje
class SistemPrizorisc implements Serializable {
    private static final long serialVersionUID = 1L;

    private ArrayList<Prizorisce4> prizorisce;
    private ArrayList<Obiskovalec4> obiskovalci;

    public SistemPrizorisc() {
        this.prizorisce = new ArrayList<>();
        this.obiskovalci = new ArrayList<>();
    }

    public void dodajPrizorisce(Prizorisce4 p) {
        prizorisce.add(p);
    }

    public void dodajObiskovalca(Obiskovalec4 o) {
        obiskovalci.add(o);
    }

    public ArrayList<Prizorisce4> getPrizorisce() {
        return prizorisce;
    }

    public ArrayList<Obiskovalec4> getObiskovalci() {
        return obiskovalci;
    }
}

public class naloga4 {

    static String[] drzave = { "Slovenija", "Avstrija", "Italija", "Hrvaška", "Madžarska" };
    static String[] mesta = { "Ljubljana", "Maribor", "Celje", "Kranj", "Koper" };
    static String[] ulice = { "Glavna ulica", "Šolska ulica", "Prešernova cesta", "Cankarjeva ulica", "Titova cesta" };
    static String[] imena = { "Janez", "Marko", "Ana", "Maja", "Peter", "Luka", "Nina", "Eva", "Tomaž", "Anja" };
    static String[] priimki = { "Novak", "Horvat", "Kovač", "Krajnc", "Zupan", "Potočnik", "Vidmar", "Bizjak" };

    public static void main(String[] args) {
        String imeDatoteke = "sistem_prizorisc.dat";

        System.out.println("=================================================================");
        System.out.println("SHRANJEVANJE IN RESTAVRIRANJE Z ObjectInputStream/OutputStream");
        System.out.println("=================================================================\n");

        // Ustvari in shrani podatke
        SistemPrizorisc sistem = ustvariTestnePodatke();
        shraniNaDatoteko(sistem, imeDatoteke);

        System.out.println("\n-----------------------------------------------------------------\n");

        // Preberi iz datoteke
        SistemPrizorisc obnovljenSistem = preberiIzDatoteke(imeDatoteke);

        if (obnovljenSistem != null) {
            izpisiSistem(obnovljenSistem);
        }
    }

    public static SistemPrizorisc ustvariTestnePodatke() {
        Random rand = new Random();
        SistemPrizorisc sistem = new SistemPrizorisc();

        System.out.println("USTVARJANJE TESTNIH PODATKOV...");
        System.out.println("-----------------------------------------------------------------");

        // Ustvari prizorišče
        Lokacija4 lokPrizorisce = new Lokacija4("Slovenija", "Ljubljana", "Erjavčeva cesta", 1, 1000);
        Prizorisce4 prizorisce = new Prizorisce4("Cankarjev dom", lokPrizorisce, 30);
        sistem.dodajPrizorisce(prizorisce);

        // Ustvari obiskovalce
        for (int i = 0; i < 15; i++) {
            String ime = imena[rand.nextInt(imena.length)];
            String priimek = priimki[rand.nextInt(priimki.length)];
            Lokacija4 lok = new Lokacija4(
                    drzave[rand.nextInt(drzave.length)],
                    mesta[rand.nextInt(mesta.length)],
                    ulice[rand.nextInt(ulice.length)],
                    rand.nextInt(100) + 1,
                    rand.nextInt(9000) + 1000);
            sistem.dodajObiskovalca(new Obiskovalec4(ime, priimek, lok));
        }

        System.out.printf("Ustvarjeno %d obiskovalcev%n", sistem.getObiskovalci().size());

        // Ustvari dogodek
        Date datum = new Date(2026 - 1900, 1, 15, 19, 0);
        Dogodek4 dogodek = prizorisce.dodajDogodek("Koncert rock skupine", datum, 120);

        System.out.println("Ustvarjen dogodek: " + dogodek.getTema());

        // Rezerviraj sedeže
        for (int i = 0; i < 10; i++) {
            Obiskovalec4 obiskovalec = sistem.getObiskovalci().get(i);
            dogodek.rezervirajSedez(i + 1, obiskovalec);
        }

        System.out.printf("Rezerviranih %d sedežev%n", dogodek.getSteviloZasedenihMest());

        return sistem;
    }

    public static void shraniNaDatoteko(SistemPrizorisc sistem, String imeDatoteke) {
        System.out.println("\nSHRANJEVANJE NA DATOTEKO: " + imeDatoteke);
        System.out.println("-----------------------------------------------------------------");

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(imeDatoteke))) {
            oos.writeObject(sistem);
            System.out.println("Podatki uspešno shranjeni!");

            File file = new File(imeDatoteke);
            System.out.printf("Velikost datoteke: %d bajtov%n", file.length());
        } catch (IOException e) {
            System.out.println("Napaka pri shranjevanju: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static SistemPrizorisc preberiIzDatoteke(String imeDatoteke) {
        System.out.println("BRANJE IZ DATOTEKE: " + imeDatoteke);
        System.out.println("-----------------------------------------------------------------");

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(imeDatoteke))) {
            SistemPrizorisc sistem = (SistemPrizorisc) ois.readObject();
            System.out.println("Podatki uspešno prebrani!\n");
            return sistem;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Napaka pri branju: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static void izpisiSistem(SistemPrizorisc sistem) {
        System.out.println("=================================================================");
        System.out.println("OBNOVLJENI PODATKI IZ DATOTEKE");
        System.out.println("=================================================================\n");

        // Izpiši prizorišča in dogodke
        System.out.println("PRIZORIŠČA:");
        System.out.println("-----------------------------------------------------------------");
        for (Prizorisce4 p : sistem.getPrizorisce()) {
            System.out.println(p);
            System.out.println();

            // Izpiši dogodke
            for (Dogodek4 d : p.getDogodki()) {
                System.out.println("  " + d.toString().replace("\n", "\n  "));
                System.out.println();

                // Izpiši zasedene sedeže
                System.out.println("  Zasedeni sedeži:");
                for (Sedez4 s : d.getSedezi()) {
                    if (s.jeZaseden()) {
                        System.out.println("    " + s);
                    }
                }
                System.out.println();
            }
        }

        // Izpiši obiskovalce
        System.out.println("-----------------------------------------------------------------");
        System.out.println("OBISKOVALCI:");
        System.out.println("-----------------------------------------------------------------");
        int i = 1;
        for (Obiskovalec4 o : sistem.getObiskovalci()) {
            System.out.printf("%2d. %s%n", i++, o);
        }
    }
}
