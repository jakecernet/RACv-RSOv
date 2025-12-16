/* Predpostavite da na vsakem koncu prometnega pasu teče ločen sočasen proces: na +vhodu+ v prometni pas proces generira vozila in jih potiska v vrsto, 
na +izhodu+ drug proces jemlje vozila iz vrste (in jih v tej realizaciji zavrže). Recimo, da vhodni proces generira vozilo v vrsto vsako sekundo 
(kreiramo, ga z argumentom z vrednostjo 1), izhodni vzame vozilo iz vrste vsaki dve sekundi(kreiramo z argumentom vrednosti 2). 
Ti vrednosti naj bosta privzeti za določitev hitrosti izvajanja obeh procesov, naj jih bo pa možno nastaviti v intervalu od 0,1 do 4,5 , vsakega posebej.
Spišite program, ki se bo izvajal do trenutka, ko vhodni proces ne bo mogel več vriniti vozila v vrsto. Tedaj naj se izvajanje obeh niti zaključi.
Izpis (poročilo) o izvajanju programa naj bo enako kot v nalogi 1, dodatno naj se izpiše še seznam vseh vozil v vrsti (tablica+zap.št.+dolžina)*/

import java.util.LinkedList;
import java.util.Queue;
import java.util.Iterator;

public class naloga3 {
    public static final double DOLZINA_CESTE = 1450.0;

    public static void main(String[] args) throws InterruptedException {

        double hitrostVhod = 0.001;
        double hitrostIzhod = 0.003;

        if (args.length >= 1)
            hitrostVhod = Double.parseDouble(args[0]);
        if (args.length >= 2)
            hitrostIzhod = Double.parseDouble(args[1]);

        LinkedGTList cesta = new LinkedGTList(DOLZINA_CESTE);

        Kontrola kontrola = new Kontrola();

        long zacetek = System.currentTimeMillis();

        VhodnaNit vhod = new VhodnaNit(cesta, hitrostVhod, kontrola);
        IzhodnaNit izhod = new IzhodnaNit(cesta, hitrostIzhod, kontrola);

        vhod.start();
        izhod.start();

        vhod.join();
        izhod.join();

        long konec = System.currentTimeMillis();

        System.out.println("\n===== POROČILO =====");
        System.out.println("Čas izvajanja: " + (konec - zacetek) + " ms");
        System.out.println("Število vozil: " + cesta.size());
        System.out.println("Skupna dolžina vozil: " + cesta.getSkupnaDolzina());
        System.out.println("Neizrabljen prostor: " +
                (DOLZINA_CESTE - cesta.getSkupnaDolzina()));

        System.out.println("\nVozila v vrsti:");
        cesta.izpisiVozila();
    }
}

class Kontrola {
    public volatile boolean running = true;
}

class VhodnaNit extends Thread {
    private LinkedGTList cesta;
    private double interval;
    private Kontrola kontrola;

    public VhodnaNit(LinkedGTList cesta, double interval, Kontrola kontrola) {
        this.cesta = cesta;
        this.interval = interval;
        this.kontrola = kontrola;
    }

    public void run() {
        try {
            while (kontrola.running) {
                Vozilo v = Vozilo.generirajNovo();

                synchronized (cesta) {
                    if (!cesta.offer(v)) {
                        kontrola.running = false;
                        break;
                    }
                }

                Thread.sleep((long) (interval * 1000));
            }
        } catch (InterruptedException e) {
        }
    }
}

class IzhodnaNit extends Thread {
    private LinkedGTList cesta;
    private double interval;
    private Kontrola kontrola;

    public IzhodnaNit(LinkedGTList cesta, double interval, Kontrola kontrola) {
        this.cesta = cesta;
        this.interval = interval;
        this.kontrola = kontrola;
    }

    public void run() {
        try {
            while (kontrola.running) {
                synchronized (cesta) {
                    cesta.poll();
                }
                Thread.sleep((long) (interval * 1000));
            }
        } catch (InterruptedException e) {
        }
    }
}

class Vozilo {
    private static int stevec = 0;

    private int zaporedna;
    private String tablica;
    private double dolzina;

    private Vozilo(int zaporedna, String tablica, double dolzina) {
        this.zaporedna = zaporedna;
        this.tablica = tablica;
        this.dolzina = dolzina;
    }

    public static Vozilo generirajNovo() {
        stevec++;
        double dolzina = 1.5 + Math.random() * 10.5;
        String tablica = "LJ-" + (1000 + stevec);
        return new Vozilo(stevec, tablica, dolzina);
    }

    public double getDolzina() {
        return dolzina;
    }

    public String toString() {
        return tablica + " | št=" + zaporedna + " | dolžina=" +
                String.format("%.2f", dolzina);
    }
}

class LinkedGTList {
    private Queue<Vozilo> vrsta = new LinkedList<Vozilo>();
    private double maxDolzina;
    private double trenutnaDolzina = 0.0;

    public LinkedGTList(double maxDolzina) {
        this.maxDolzina = maxDolzina;
    }

    public boolean offer(Vozilo v) {
        double novaDolzina = trenutnaDolzina + v.getDolzina() + 0.75;
        if (novaDolzina > maxDolzina)
            return false;
        vrsta.offer(v);
        trenutnaDolzina += v.getDolzina() + 0.75;
        return true;
    }

    public Vozilo poll() {
        Vozilo v = vrsta.poll();
        if (v != null) {
            trenutnaDolzina -= (v.getDolzina() + 0.75);
        }
        return v;
    }

    public int size() {
        return vrsta.size();
    }

    public double getSkupnaDolzina() {
        return trenutnaDolzina;
    }

    public void izpisiVozila() {
        Iterator<Vozilo> it = vrsta.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
}