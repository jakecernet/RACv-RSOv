/* Nekaj opredelitev iz uvodnega besedila:
import java.util.Queue;
import java.util.LinkedList;

    Queue<Vozilo> cesta1pas1 = new LinkedList<Vozilo>();

Predpostavite, da je odsek ceste dolg 1450m, da je cesta prazna in da je na koncu semafor z lučjo v rdeči barvi. 
Spišite javansko nit, ki bo v vsaki iteraciji v 'cesto' zgenerirala/vstavila novo vozilo, dokler bo vanjo še možno zapeljati. Javanska nit ob polni vrsti neha delovati.
Program nato izpiše čas, ki je bil potreben za polnjenje vrste, število vozil v vrsti, skupno dolžino vozil v vrsti in neizrabljen (dolžinski) prostor na cesti (prostor med vozili).
Predpostavite lahko, da je prehitevanje na opredeljenem odseku ceste prepovedano, lahko pa si zagrenite življenje še s tem; 
v slednjem primeru uporabite PriorityQueue kot osnovno strukturo namesto LinkedList. */
import java.util.Queue;
import java.util.LinkedList;

public class naloga1 {
    public static void main(String[] args) {
        final int dolzinaCeste = 1450; // dolžina ceste v metrih
        Queue<Vozilo> cesta = new LinkedList<>();

        long startTime = System.currentTimeMillis();

        int skupnaDolzinaVozil = 0;
        int zaporednaStevilka = 1;

        while (true) {
            int dolzinaVozila = generirajDolzinoVozila();
            if (skupnaDolzinaVozil + dolzinaVozila + (cesta.size() * 0.75) > dolzinaCeste) {
                break; // Ne moremo več dodati vozila
            }
            Vozilo novoVozilo = new Vozilo("REG" + zaporednaStevilka, zaporednaStevilka, dolzinaVozila);
            cesta.add(novoVozilo);
            skupnaDolzinaVozil += dolzinaVozila;
            zaporednaStevilka++;
        }

        long endTime = System.currentTimeMillis();
        long casPolnjenja = endTime - startTime;

        System.out.println("Čas polnjenja ceste: " + casPolnjenja + " ms");
        System.out.println("Število vozil na cesti: " + cesta.size());
        System.out.println("Skupna dolžina vozil: " + skupnaDolzinaVozil + " m");
        System.out.println("Neizrabljen prostor na cesti: " + (dolzinaCeste - skupnaDolzinaVozil - (cesta.size() * 0.75)) + " m");
    }

    private static int generirajDolzinoVozila() {
        // Generiraj dolžino vozila med 1.5m in 12m, z upoštevanjem verjetnosti
        double rand = Math.random();
        if (rand < 0.998) {
            return 5 + (int)(Math.random() * 7); // Povprečna vozila med 5m in 12m
        } else {
            return 1 + (int)(Math.random() * 4); // Krajša vozila med 1m in 5m
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
