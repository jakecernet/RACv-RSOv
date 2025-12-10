/* Predpostavite da na vsakem koncu prometnega pasu teče ločen sočasen proces: na +vhodu+ v prometni pas proces generira vozila in jih potiska v vrsto, 
na +izhodu+ drug proces jemlje vozila iz vrste (in jih v tej realizaciji zavrže). Recimo, da vhodni proces generira vozilo v vrsto vsako sekundo 
(kreiramo, ga z argumentom z vrednostjo 1), izhodni vzame vozilo iz vrste vsaki dve sekundi(kreiramo z argumentom vrednosti 2). 
Ti vrednosti naj bosta privzeti za določitev hitrosti izvajanja obeh procesov, naj jih bo pa možno nastaviti v intervalu od 0,1 do 4,5 , vsakega posebej.
Spišite program, ki se bo izvajal do trenutka, ko vhodni proces ne bo mogel več vriniti vozila v vrsto. Tedaj naj se izvajanje obeh niti zaključi.
Izpis (poročilo) o izvajanju programa naj bo enako kot v nalogi 1, dodatno naj se izpiše še seznam vseh vozil v vrsti (tablica+zap.št.+dolžina)*/

public class naloga3 {
    public static void main(String[] args) {
        double vhodHitrost = 1.0; // privzeta hitrost vhodnega procesa
        double izhodHitrost = 2.0; // privzeta hitrost izhodnega procesa

        // Preveri, če so podani argumenti za hitrosti
        if (args.length >= 2) {
            try {
                double vh = Double.parseDouble(args[0]);
                double izh = Double.parseDouble(args[1]);
                if (vh >= 0.1 && vh <= 4.5) {
                    vhodHitrost = vh;
                }
                if (izh >= 0.1 && izh <= 4.5) {
                    izhodHitrost = izh;
                }
            } catch (NumberFormatException e) {
                System.out.println("Neveljavni argumenti za hitrosti, uporabljene privzete vrednosti.");
            }
        }

        PrometniPas prometniPas = new PrometniPas(10); // nastavimo kapaciteto vrste na 10 vozil

        VhodniProces vhodniProces = new VhodniProces(prometniPas, vhodHitrost);
        IzhodniProces izhodniProces = new IzhodniProces(prometniPas, izhodHitrost);

        Thread vhodThread = new Thread(vhodniProces);
        Thread izhodThread = new Thread(izhodniProces);

        vhodThread.start();
        izhodThread.start();

        try {
            vhodThread.join();
            izhodThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Izpis vozil v vrsti:");
        prometniPas.izpisiVozila();
    }

    private static class PrometniPas {
        private final int kapaciteta;
        private final java.util.Queue<Vozilo> vrsta;
        private int zapStVozila = 0;

        public PrometniPas(int kapaciteta) {
            this.kapaciteta = kapaciteta;
            this.vrsta = new java.util.LinkedList<>();
        }

        public synchronized boolean dodajVozilo() {
            if (vrsta.size() < kapaciteta) {
                zapStVozila++;
                Vozilo vozilo = new Vozilo(zapStVozila, (int)(Math.random() * 5000 + 1000));
                vrsta.add(vozilo);
                System.out.println("Dodano vozilo: " + vozilo);
                notifyAll();
                return true;
            }
            return false;
        }

        public synchronized Vozilo odstraniVozilo() throws InterruptedException {
            while (vrsta.isEmpty()) {
                wait();
            }
            Vozilo vozilo = vrsta.poll();
            System.out.println("Odstranjeno vozilo: " + vozilo);
            return vozilo;
        }

        public void izpisiVozila() {
            System.out.printf("%-10s %-10s%n", "Zap.št.", "Dolžina");
            for (Vozilo v : vrsta) {
                System.out.printf("%-10d %-10d%n", v.zapSt, v.dolzina);
            }
        }
    }

    private static class Vozilo {
        public final int zapSt;
        public final int dolzina;

        public Vozilo(int zapSt, int dolzina) {
            this.zapSt = zapSt;
            this.dolzina = dolzina;
        }

        @Override
        public String toString() {
            return "Vozilo{" + "zapSt=" + zapSt + ", dolzina=" + dolzina + '}';
        }
    }

    private static class VhodniProces implements Runnable {
        private final PrometniPas prometniPas;
        private final double hitrost;

        public VhodniProces(PrometniPas prometniPas, double hitrost) {
            this.prometniPas = prometniPas;
            this.hitrost = hitrost;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    boolean dodano = prometniPas.dodajVozilo();
                    if (!dodano) {
                        System.out.println("Vrsta je polna, vhodni proces se ustavi.");
                        break;
                    }
                    Thread.sleep((long)(1000 / hitrost));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static class IzhodniProces implements Runnable {
        private final PrometniPas prometniPas;
        private final double hitrost;

        public IzhodniProces(PrometniPas prometniPas, double hitrost) {
            this.prometniPas = prometniPas;
            this.hitrost = hitrost;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    prometniPas.odstraniVozilo();
                    Thread.sleep((long)(2000 / hitrost));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}