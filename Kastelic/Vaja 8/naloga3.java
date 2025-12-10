/* Predpostavite da na vsakem koncu prometnega pasu teče ločen sočasen proces: na +vhodu+ v prometni pas proces generira vozila in jih potiska v vrsto, 
na +izhodu+ drug proces jemlje vozila iz vrste (in jih v tej realizaciji zavrže). Recimo, da vhodni proces generira vozilo v vrsto vsako sekundo 
(kreiramo, ga z argumentom z vrednostjo 1), izhodni vzame vozilo iz vrste vsaki dve sekundi(kreiramo z argumentom vrednosti 2). 
Ti vrednosti naj bosta privzeti za določitev hitrosti izvajanja obeh procesov, naj jih bo pa možno nastaviti v intervalu od 0,1 do 4,5 , vsakega posebej.
Spišite program, ki se bo izvajal do trenutka, ko vhodni proces ne bo mogel več vriniti vozila v vrsto. Tedaj naj se izvajanje obeh niti zaključi.
Izpis (poročilo) o izvajanju programa naj bo enako kot v nalogi 1, dodatno naj se izpiše še seznam vseh vozil v vrsti (tablica+zap.št.+dolžina)*/

import java.util.LinkedList;
import java.util.Queue;

public class naloga3 {
    public static void main(String[] args) {
        final int dolzinaCeste = 1450;
        Cesta cesta = new Cesta(dolzinaCeste);

        // Nastavitve hitrosti (v sekundah)
        double intervalVhod = 1.0;
        double intervalIzhod = 2.0;

        // Preverjanje argumentov (opcijsko, če bi želeli spreminjati preko args)
        if (args.length > 0) {
            try {
                intervalVhod = Double.parseDouble(args[0]);
                if (args.length > 1) {
                    intervalIzhod = Double.parseDouble(args[1]);
                }
            } catch (NumberFormatException e) {
                System.out.println("Napaka pri branju argumentov, uporabljam privzete vrednosti.");
            }
        }

        // Omejitev intervalov na 0.1 do 4.5
        intervalVhod = Math.max(0.1, Math.min(4.5, intervalVhod));
        intervalIzhod = Math.max(0.1, Math.min(4.5, intervalIzhod));

        VhodniProces vhod = new VhodniProces(cesta, intervalVhod);
        IzhodniProces izhod = new IzhodniProces(cesta, intervalIzhod);

        System.out.println("Začenjam simulacijo...");
        cesta.startTimer();
        vhod.start();
        izhod.start();

        try {
            vhod.join(); // Čakamo, da se vhodni proces ustavi (ko je cesta polna)
            izhod.ustavi(); // Ustavimo še izhodni proces
            izhod.interrupt(); // Prekinemo spanje če spi
            izhod.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        cesta.izpisPoročila();
    }
}

class Cesta {
    private Queue<Vozilo> vrsta = new LinkedList<>();
    private int maxDolzina;
    private int skupnaDolzinaVozil = 0;
    private boolean polna = false;
    private long startTime;
    private long endTime;

    public Cesta(int maxDolzina) {
        this.maxDolzina = maxDolzina;
    }

    public void startTimer() {
        this.startTime = System.currentTimeMillis();
    }

    public synchronized boolean dodaj(Vozilo v) {
        if (polna) return false;

        // Logika iz naloge 1:
        // if (skupnaDolzinaVozil + dolzinaVozila + (cesta.size() * 0.75) > dolzinaCeste)
        if (skupnaDolzinaVozil + v.dolzina + (vrsta.size() * 0.75) > maxDolzina) {
            polna = true;
            endTime = System.currentTimeMillis();
            return false;
        }

        vrsta.add(v);
        skupnaDolzinaVozil += v.dolzina;
        // System.out.println("Dodano vozilo: " + v.registracija + " (Dolžina: " + v.dolzina + ")");
        return true;
    }

    public synchronized Vozilo vzemi() {
        Vozilo v = vrsta.poll();
        if (v != null) {
            skupnaDolzinaVozil -= v.dolzina;
            // System.out.println("Odstranjeno vozilo: " + v.registracija);
        }
        return v;
    }

    public synchronized boolean jePolna() {
        return polna;
    }

    public synchronized void izpisPoročila() {
        long casPolnjenja = endTime - startTime;
        
        System.out.println("\n--- POROČILO ---");
        System.out.println("Čas polnjenja ceste: " + casPolnjenja + " ms");
        System.out.println("Število vozil na cesti: " + vrsta.size());
        System.out.println("Skupna dolžina vozil: " + skupnaDolzinaVozil + " m");
        System.out.println("Neizrabljen prostor na cesti: " + (maxDolzina - skupnaDolzinaVozil - (vrsta.size() * 0.75)) + " m");
        
        System.out.println("\n--- SEZNAM VOZIL V VRSTI ---");
        System.out.println("Tablica\t\tZap.št.\tDolžina");
        for (Vozilo v : vrsta) {
            System.out.println(v.registracija + "\t\t" + v.zaporednaStevilka + "\t" + v.dolzina + "m");
        }
    }
}

class VhodniProces extends Thread {
    private Cesta cesta;
    private double interval; // v sekundah
    private int zaporednaStevilka = 1;

    public VhodniProces(Cesta cesta, double interval) {
        this.cesta = cesta;
        this.interval = interval;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep((long) (interval * 1000));
            } catch (InterruptedException e) {
                break;
            }

            int dolzina = generirajDolzinoVozila();
            Vozilo v = new Vozilo("REG" + zaporednaStevilka, zaporednaStevilka, dolzina);
            
            if (!cesta.dodaj(v)) {
                // Cesta je polna, končamo
                break;
            }
            zaporednaStevilka++;
        }
    }

    private int generirajDolzinoVozila() {
        // Logika iz naloge 1
        double rand = Math.random();
        if (rand < 0.998) {
            return 5 + (int)(Math.random() * 7); // 5m - 11m (naloga 1 pravi 5-12, ampak koda je 5 + rand*7 kar je 5..11.99)
        } else {
            return 1 + (int)(Math.random() * 4); // 1m - 4m
        }
    }
}

class IzhodniProces extends Thread {
    private Cesta cesta;
    private double interval; // v sekundah
    private volatile boolean running = true;

    public IzhodniProces(Cesta cesta, double interval) {
        this.cesta = cesta;
        this.interval = interval;
    }

    public void ustavi() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep((long) (interval * 1000));
            } catch (InterruptedException e) {
                // Če smo prekinjeni in running je false, končamo
                if (!running) break;
            }

            if (!running) break;

            // Če je cesta polna (vhodni proces se je ustavil), bi morali tudi mi nehati?
            // Navodilo: "Tedaj naj se izvajanje obeh niti zaključi."
            // To urejamo v main z ustavi() in interrupt().
            
            cesta.vzemi();
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