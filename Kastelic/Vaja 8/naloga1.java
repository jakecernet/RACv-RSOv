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
        Queue<Vozilo> cesta1pas1 = new LinkedList<Vozilo>();
        final int dolzinaCeste = 1450;
        final int dolzinaVozila = 5;
        final int maxVozil = dolzinaCeste / dolzinaVozila;

        Thread nit = new Thread(() -> {
            while (true) {
                synchronized (cesta1pas1) {
                    if (cesta1pas1.size() < maxVozil) {
                        Vozilo novoVozilo = new Vozilo();
                        cesta1pas1.add(novoVozilo);
                    } else {
                        break;
                    }
                }
            }
        });
        nit.start();

        try {
            nit.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Čas polnjenja: " + System.currentTimeMillis());
        System.out.println("Število vozil v vrsti: " + cesta1pas1.size());
        System.out.println("Skupna dolžina vozil v vrsti: " + (cesta1pas1.size() * dolzinaVozila));
        System.out.println("Neizrabljen prostor na cesti: " + (dolzinaCeste - (cesta1pas1.size() * dolzinaVozila)));
    }
}

class Vozilo {
    private static int zaporednaStevilka = 0;
    private int dolzina;
    private int id;

    public Vozilo() {
        this.dolzina = 5; // privzeta dolžina vozila
        this.id = ++zaporednaStevilka;
    }

    public int getDolzina() {
        return dolzina;
    }

    public int getId() {
        return id;
    }
}
