/* Tipizirajte lokacijo (opredelitev v besedilu): generirajte vsaj 100 lokacij in jih urejene po poštnih
številkah zapišite na datoteko. Uporabite datoteko z naključnim dostopom. */

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class naloga1 {
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

		try (RandomAccessFile raf = new RandomAccessFile("lokacije.dat", "rw")) {
			raf.writeInt(lokacije.size());

			for (Lokacija lok : lokacije) {
				lok.zapisiNaDatoteko(raf);
			}
			System.out.println("Lokacije zapisane v datoteko 'lokacije.dat'");
		} catch (Exception e) {
			System.out.println("Napaka pri pisanju: " + e.getMessage());
		}

		// demo branja iz datoteke
		System.out.println("\nBranje iz datoteke:");
		System.out.println("-----------------------------------------------------------");
		try (RandomAccessFile raf = new RandomAccessFile("lokacije.dat", "r")) {
			int stLokacij = raf.readInt();

			for (int i = 0; i < stLokacij; i++) {
				Lokacija lok = Lokacija.preberiIzDatoteke(raf);
				System.out.printf("%3d. %s%n", i + 1, lok);
			}

			System.out.println("\nNaključni dostop - 5. zapis:");
			int pozicija = 4 + 4 * Lokacija.RECORD_SIZE; // 4 bajti za int + 4 zapisi
			raf.seek(pozicija);
			Lokacija lok5 = Lokacija.preberiIzDatoteke(raf);
			System.out.println("5. lokacija: " + lok5);

		} catch (Exception e) {
			System.out.println("Napaka pri branju: " + e.getMessage());
		}
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
