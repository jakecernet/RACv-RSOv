/* Recimo, da imamo 10 datotek naključnih dolžin med 1500 in 3000
števil vrste Double. Želeli bi enostavno zlivanje vsebin vseh 
datotek v eno samo in sicer tako, da prve elemente vseh datotek
zlijemo na ciljno datoteko kot prvo urejeno zaporedje, vse druge kot drugo urejeno
in tako do konca.
   
V rezultativni datoteki pričakujemo toliko urejenih zaporedij, kot je
dolžina najdaljše datoteke. Ugotovite ali je temu res tako, in pa zapisov
koliko % se število dejanskih zaporedij razlikuje od pričakovanih.
   
Izvršite 15 izvajanj, podajte posamezne rezultate posameznega, ob
koncu pa določite še povprečja vseh 15-tih izvajanj. Rezultati naj
bodo v obliki, zapisani v besedilni datoteki poskus_XY.txt:
   
poskus 1
20. 01. 2026 ob 8:50
  
                pričakovano      dejanskih      odstopanje[%]
  -----------------------------------------------------------
	1. izvajanje        10              10             0
	
	
	------------------------------------------------------------
  povp:             10              6              40
	  
tipično je poskusov lahko poljubno, vsak naslednji mora biti v
imenu in vsebini označen z naslednjo številko. */

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class naloga2 {
	static final int STEVILO_DATOTEK = 10;
	static final int MIN_DOLZINA = 1500;
	static final int MAX_DOLZINA = 3000;
	static final int STEVILO_IZVAJANJ = 15;
	static Random random = new Random();

	public static void main(String[] args) {
		// Poišči naslednjo številko poskusa
		int poskusStevilka = najdiNaslednjiPoskus();

		String imeDatoteke = String.format("poskus_%02d.txt", poskusStevilka);

		try (PrintWriter writer = new PrintWriter(new FileWriter(imeDatoteke))) {
			writer.println("poskus " + poskusStevilka);

			LocalDateTime zdaj = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd. MM. yyyy 'ob' H:mm");
			writer.println(zdaj.format(formatter));
			writer.println();

			writer.println("                    pričakovano      dejanskih      odstopanje[%]");
			writer.println("-----------------------------------------------------------");

			int[] pricakovana = new int[STEVILO_IZVAJANJ];
			int[] dejanska = new int[STEVILO_IZVAJANJ];
			double[] odstopanja = new double[STEVILO_IZVAJANJ];

			for (int i = 0; i < STEVILO_IZVAJANJ; i++) {
				// Ustvari 10 datotek z naključnimi dolžinami
				int[] dolzine = ustvariDatoteke();

				// Najdi najdaljšo datoteko
				int maxDolzina = 0;
				for (int d : dolzine) {
					if (d > maxDolzina)
						maxDolzina = d;
				}
				pricakovana[i] = maxDolzina;

				// Zlij vse datoteke v eno
				zlijDatoteke(dolzine, maxDolzina);

				// Preštej dejanska urejena zaporedja
				dejanska[i] = prestejZaporedja("rezultat.dat");

				// Izračunaj odstopanje
				odstopanja[i] = Math.abs(pricakovana[i] - dejanska[i]) * 100.0 / pricakovana[i];

				writer.printf("%2d. izvajanje      %5d           %5d           %.2f%n",
						i + 1, pricakovana[i], dejanska[i], odstopanja[i]);

				// Počisti datoteke
				pocistiDatoteke();
			}

			writer.println("------------------------------------------------------------");

			// Izračunaj povprečja
			double povpPricakovano = Arrays.stream(pricakovana).average().orElse(0);
			double povpDejansko = Arrays.stream(dejanska).average().orElse(0);
			double povpOdstopanje = Arrays.stream(odstopanja).average().orElse(0);

			writer.printf("  povp:            %.1f           %.1f           %.2f%n",
					povpPricakovano, povpDejansko, povpOdstopanje);

			System.out.println("Rezultati zapisani v " + imeDatoteke);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static int najdiNaslednjiPoskus() {
		int max = 0;
		File dir = new File(".");
		File[] datoteke = dir.listFiles((d, name) -> name.matches("poskus_\\d+\\.txt"));
		if (datoteke != null) {
			for (File f : datoteke) {
				String ime = f.getName();
				int stevilka = Integer.parseInt(ime.substring(7, 9));
				if (stevilka > max)
					max = stevilka;
			}
		}
		return max + 1;
	}

	static int[] ustvariDatoteke() throws IOException {
		int[] dolzine = new int[STEVILO_DATOTEK];

		for (int i = 0; i < STEVILO_DATOTEK; i++) {
			dolzine[i] = MIN_DOLZINA + random.nextInt(MAX_DOLZINA - MIN_DOLZINA + 1);

			try (DataOutputStream dos = new DataOutputStream(
					new BufferedOutputStream(new FileOutputStream("dat" + i + ".dat")))) {
				for (int j = 0; j < dolzine[i]; j++) {
					dos.writeDouble(random.nextDouble() * 10000);
				}
			}
		}
		return dolzine;
	}

	static void zlijDatoteke(int[] dolzine, int maxDolzina) throws IOException {
		DataInputStream[] vhodi = new DataInputStream[STEVILO_DATOTEK];

		for (int i = 0; i < STEVILO_DATOTEK; i++) {
			vhodi[i] = new DataInputStream(
					new BufferedInputStream(new FileInputStream("dat" + i + ".dat")));
		}

		try (DataOutputStream izhod = new DataOutputStream(
				new BufferedOutputStream(new FileOutputStream("rezultat.dat")))) {

			// Za vsako pozicijo (od 0 do maxDolzina-1)
			for (int pos = 0; pos < maxDolzina; pos++) {
				ArrayList<Double> elementi = new ArrayList<>();

				// Preberi element na poziciji pos iz vsake datoteke (če obstaja)
				for (int i = 0; i < STEVILO_DATOTEK; i++) {
					if (pos < dolzine[i]) {
						elementi.add(vhodi[i].readDouble());
					}
				}

				// Uredi elemente
				Collections.sort(elementi);

				// Zapiši urejeno zaporedje
				for (Double d : elementi) {
					izhod.writeDouble(d);
				}
			}
		}

		// Zapri vse vhodne tokove
		for (DataInputStream dis : vhodi) {
			dis.close();
		}
	}

	static int prestejZaporedja(String imeDatoteke) throws IOException {
		int stZaporedij = 0;

		try (DataInputStream dis = new DataInputStream(
				new BufferedInputStream(new FileInputStream(imeDatoteke)))) {

			double prejsnji = Double.MIN_VALUE;
			boolean prvoZaporedje = true;

			while (dis.available() > 0) {
				double trenutni = dis.readDouble();

				if (trenutni < prejsnji) {
					// Novo zaporedje se začne
					stZaporedij++;
				} else if (prvoZaporedje) {
					// Prvo zaporedje
					stZaporedij = 1;
					prvoZaporedje = false;
				}

				prejsnji = trenutni;
			}
		}

		return stZaporedij;
	}

	static void pocistiDatoteke() {
		// Izbriši začasne datoteke
		for (int i = 0; i < STEVILO_DATOTEK; i++) {
			new File("dat" + i + ".dat").delete();
		}
		new File("rezultat.dat").delete();
	}
}
