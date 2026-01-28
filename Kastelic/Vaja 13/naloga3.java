/* Uporabite Java, modelirajte opisano strukturo. Upoštevajte, da je v besedilu opisanih entitet več, 
ter da so relacije med njimi dobro definirane (besedilo). Generirajte kakšnega izmed dogodkov. */

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

// Lokacija - skupna za Prizorišče in Obiskovalca
class Lokacija3 {
    private String drzava;
    private String mesto;
    private String ulica;
    private int hisnaStevilka;
    private int postnaStevilka;

    public Lokacija3(String drzava, String mesto, String ulica, int hisnaStevilka, int postnaStevilka) {
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

// Obiskovalec - identificiran z imenom, priimkom in lokacijo
class Obiskovalec {
    private String ime;
    private String priimek;
    private Lokacija3 lokacija;

    public Obiskovalec(String ime, String priimek, Lokacija3 lokacija) {
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

    public Lokacija3 getLokacija() {
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

// Sedež - oštevilčen, vstopnica je vedno imenska
class Sedez {
    private int stevilkaSedeza;
    private Obiskovalec lastnik; // vstopnica je imenska

    public Sedez(int stevilkaSedeza) {
        this.stevilkaSedeza = stevilkaSedeza;
        this.lastnik = null;
    }

    public int getStevilkaSedeza() {
        return stevilkaSedeza;
    }

    public Obiskovalec getLastnik() {
        return lastnik;
    }

    public boolean jeZaseden() {
        return lastnik != null;
    }

    public void rezerviraj(Obiskovalec obiskovalec) {
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

// Dogodek - tema, datum, ura, trajanje
class Dogodek {
    private String tema; // naziv dogodka
    private Date datumInUra;
    private int trajanje; // v minutah
    private Prizorisce prizorisce;
    private ArrayList<Sedez> sedezi; // kopija sedežev za ta dogodek

    public Dogodek(String tema, Date datumInUra, int trajanje, Prizorisce prizorisce) {
        this.tema = tema;
        this.datumInUra = datumInUra;
        this.trajanje = trajanje;
        this.prizorisce = prizorisce;
        // Ustvari kopijo sedežev za ta dogodek
        this.sedezi = new ArrayList<>();
        for (int i = 1; i <= prizorisce.getSteviloMest(); i++) {
            sedezi.add(new Sedez(i));
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

    public Prizorisce getPrizorisce() {
        return prizorisce;
    }

    public ArrayList<Sedez> getSedezi() {
        return sedezi;
    }

    public int getSteviloProstihMest() {
        int count = 0;
        for (Sedez s : sedezi) {
            if (!s.jeZaseden())
                count++;
        }
        return count;
    }

    public int getSteviloZasedenihMest() {
        return sedezi.size() - getSteviloProstihMest();
    }

    public Sedez rezervirajSedez(int stevilka, Obiskovalec obiskovalec) {
        if (stevilka > 0 && stevilka <= sedezi.size()) {
            Sedez sedez = sedezi.get(stevilka - 1);
            if (!sedez.jeZaseden()) {
                sedez.rezerviraj(obiskovalec);
                return sedez;
            }
        }
        return null;
    }

    public Sedez rezervirajNaslednjegaProstega(Obiskovalec obiskovalec) {
        for (Sedez s : sedezi) {
            if (!s.jeZaseden()) {
                s.rezerviraj(obiskovalec);
                return s;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return String.format("Dogodek: %s\nPrizorišče: %s\nDatum: %s\nTrajanje: %d min\nProsta mesta: %d/%d",
                tema, prizorisce.getIme(), datumInUra, trajanje, getSteviloProstihMest(), sedezi.size());
    }

    public void izpisiSedeze() {
        System.out.println("Seznam sedežev za dogodek '" + tema + "':");
        for (Sedez s : sedezi) {
            System.out.println("  " + s);
        }
    }
}

// Prizorišče - ima ime, lokacijo, število mest, gosti dogodke
class Prizorisce {
    private String ime;
    private Lokacija3 lokacija;
    private int steviloMest;
    private ArrayList<Dogodek> dogodki;

    public Prizorisce(String ime, Lokacija3 lokacija, int steviloMest) {
        this.ime = ime;
        this.lokacija = lokacija;
        this.steviloMest = steviloMest;
        this.dogodki = new ArrayList<>();
    }

    public String getIme() {
        return ime;
    }

    public Lokacija3 getLokacija() {
        return lokacija;
    }

    public int getSteviloMest() {
        return steviloMest;
    }

    public ArrayList<Dogodek> getDogodki() {
        return dogodki;
    }

    public Dogodek dodajDogodek(String tema, Date datumInUra, int trajanje) {
        Dogodek dogodek = new Dogodek(tema, datumInUra, trajanje, this);
        dogodki.add(dogodek);
        return dogodek;
    }

    @Override
    public String toString() {
        return String.format("Prizorišče: %s\nLokacija: %s\nŠtevilo mest: %d\nŠtevilo dogodkov: %d",
                ime, lokacija, steviloMest, dogodki.size());
    }
}

public class naloga3 {

    // Tabele za generiranje naključnih podatkov
    static String[] drzave = { "Slovenija", "Avstrija", "Italija", "Hrvaška", "Madžarska" };
    static String[] mesta = { "Ljubljana", "Maribor", "Celje", "Kranj", "Koper" };
    static String[] ulice = { "Glavna ulica", "Šolska ulica", "Prešernova cesta", "Cankarjeva ulica", "Titova cesta" };
    static String[] imena = { "Janez", "Marko", "Ana", "Maja", "Peter", "Luka", "Nina", "Eva", "Tomaž", "Anja" };
    static String[] priimki = { "Novak", "Horvat", "Kovač", "Krajnc", "Zupan", "Potočnik", "Vidmar", "Bizjak" };
    static String[] imenaGledalisc = { "SNG Drama", "Cankarjev dom", "Kino Šiška", "Gallusova dvorana",
            "Stadion Stožice" };
    static String[] temeDogokov = { "Koncert rock skupine", "Baletna predstava", "Gledališka igra",
            "Stand-up komedija", "Operna predstava", "Filmska premiera", "Konferenca", "Športna tekma" };

    public static void main(String[] args) {
        Random rand = new Random();

        System.out.println("=================================================================");
        System.out.println("MODELIRANJE STRUKTURE PRIZORIŠČ, OBISKOVALCEV IN DOGODKOV");
        System.out.println("=================================================================\n");

        // Ustvari prizorišče
        Lokacija3 lokacijaPrizorisce = new Lokacija3("Slovenija", "Ljubljana", "Erjavčeva cesta", 1, 1000);
        Prizorisce prizorisce = new Prizorisce("Cankarjev dom", lokacijaPrizorisce, 50);

        System.out.println(prizorisce);
        System.out.println("\n-----------------------------------------------------------------\n");

        // Ustvari nekaj dogodkov
        Date datum1 = new Date(2026 - 1900, 1, 15, 19, 0); // 15. februar 2026, 19:00
        Date datum2 = new Date(2026 - 1900, 1, 20, 20, 30); // 20. februar 2026, 20:30
        Date datum3 = new Date(2026 - 1900, 2, 5, 18, 0); // 5. marec 2026, 18:00

        Dogodek dogodek1 = prizorisce.dodajDogodek("Koncert rock skupine", datum1, 120);
        Dogodek dogodek2 = prizorisce.dodajDogodek("Baletna predstava", datum2, 90);
        Dogodek dogodek3 = prizorisce.dodajDogodek("Stand-up komedija", datum3, 75);

        System.out.println("GENERIRANI DOGODKI:");
        System.out.println("-----------------------------------------------------------------");
        System.out.println(dogodek1);
        System.out.println();
        System.out.println(dogodek2);
        System.out.println();
        System.out.println(dogodek3);
        System.out.println("\n-----------------------------------------------------------------\n");

        // Generiraj obiskovalce
        ArrayList<Obiskovalec> obiskovalci = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            String ime = imena[rand.nextInt(imena.length)];
            String priimek = priimki[rand.nextInt(priimki.length)];
            Lokacija3 lok = new Lokacija3(
                    drzave[rand.nextInt(drzave.length)],
                    mesta[rand.nextInt(mesta.length)],
                    ulice[rand.nextInt(ulice.length)],
                    rand.nextInt(100) + 1,
                    rand.nextInt(9000) + 1000);
            obiskovalci.add(new Obiskovalec(ime, priimek, lok));
        }

        System.out.println("GENERIRANI OBISKOVALCI:");
        System.out.println("-----------------------------------------------------------------");
        for (int i = 0; i < obiskovalci.size(); i++) {
            System.out.printf("%2d. %s%n", i + 1, obiskovalci.get(i));
        }
        System.out.println("\n-----------------------------------------------------------------\n");

        // Rezerviraj sedeže za prvi dogodek
        System.out.println("REZERVACIJA SEDEŽEV ZA DOGODEK: " + dogodek1.getTema());
        System.out.println("-----------------------------------------------------------------");

        // Rezerviraj 15 naključnih sedežev
        for (int i = 0; i < 15; i++) {
            Obiskovalec obiskovalec = obiskovalci.get(i);
            int zeleniSedez = rand.nextInt(50) + 1;
            Sedez rezerviran = dogodek1.rezervirajSedez(zeleniSedez, obiskovalec);

            if (rezerviran != null) {
                System.out.printf("Rezerviran sedež %d za %s%n", zeleniSedez, obiskovalec.getPolnoIme());
            } else {
                // Če je sedež zaseden, rezerviraj naslednjega prostega
                rezerviran = dogodek1.rezervirajNaslednjegaProstega(obiskovalec);
                if (rezerviran != null) {
                    System.out.printf("Sedež %d zaseden, rezerviran naslednji prosti sedež %d za %s%n",
                            zeleniSedez, rezerviran.getStevilkaSedeza(), obiskovalec.getPolnoIme());
                }
            }
        }

        System.out.println("\n-----------------------------------------------------------------\n");

        // Statistika
        System.out.println("STATISTIKA DOGODKA: " + dogodek1.getTema());
        System.out.println("-----------------------------------------------------------------");
        System.out.printf("Zasedenih mest: %d%n", dogodek1.getSteviloZasedenihMest());
        System.out.printf("Prostih mest: %d%n", dogodek1.getSteviloProstihMest());
        System.out.printf("Skupaj mest: %d%n", dogodek1.getSedezi().size());

        System.out.println("\n-----------------------------------------------------------------\n");

        // Izpiši vse sedeže
        dogodek1.izpisiSedeze();

        System.out.println("\n=================================================================");
        System.out.println("PREGLED VSEH DOGODKOV NA PRIZORIŠČU");
        System.out.println("=================================================================");
        for (Dogodek d : prizorisce.getDogodki()) {
            System.out.println("\n" + d);
            System.out.println();
        }
    }
}
