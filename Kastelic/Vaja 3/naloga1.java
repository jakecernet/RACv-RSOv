/* Napišite rekurzivno realizacijo izvedbe postopka množenja velikih števil. Postopek je opisan v relevantnem dokumentu, lahko pa si ga ogledate tudi na internetu. 
Predpostavite, da boste med seboj množili 2 vsaj 2 mestni števili. Predlagam, da ugotovite, kako velika števila lahko zmnožite z vgrajenimi tipi. 
Zapis osnovnega števila je konec koncev lahko tudi (zelo dolg) niz. */

import java.util.Scanner;

public class naloga1 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String a, b;
        System.out.println("Vnesi dve števili za množenje:");
		if (sc.hasNext()) {
			a = sc.next();
		} else {
			sc.close();
			return;
		}
		if (sc.hasNext()) {
			b = sc.next();
		} else {
			sc.close();
			return;
		}
		sc.close();

		String result = multiplyBig(a, b);
		System.out.println(result);
	}

	static String multiplyBig(String a, String b) {
		a = a.trim();
		b = b.trim();
		if (a.isEmpty() || b.isEmpty()) return "0";

		boolean negA = false, negB = false;
		if (a.charAt(0) == '+') a = a.substring(1);
		if (b.charAt(0) == '+') b = b.substring(1);
		if (a.charAt(0) == '-') { negA = true; a = a.substring(1); }
		if (b.charAt(0) == '-') { negB = true; b = b.substring(1); }

		a = stripLeadingZeros(a);
		b = stripLeadingZeros(b);

		if (a.equals("0") || b.equals("0")) return "0";

		String prod = karatsuba(a, b);
		if (negA ^ negB && !prod.equals("0")) return "-" + prod;
		return prod;
	}

	private static String karatsuba(String x, String y) {
		if (x.length() <= 9 && y.length() <= 9) {
			long a = Long.parseLong(x);
			long b = Long.parseLong(y);
			long c = a * b;
			return Long.toString(c);
		}

		int n = Math.max(x.length(), y.length());
		x = leftPadZeros(x, n);
		y = leftPadZeros(y, n);

		int m = n / 2; // razcep: nizka polovica velikosti m, visoka velikosti n-m
		String xHigh = x.substring(0, n - m);
		String xLow  = x.substring(n - m);
		String yHigh = y.substring(0, n - m);
		String yLow  = y.substring(n - m);

		// rekurzivni izračuni
		String z0 = karatsuba(stripLeadingZeros(xLow), stripLeadingZeros(yLow));
		String z2 = karatsuba(stripLeadingZeros(xHigh), stripLeadingZeros(yHigh));
		String sumX = addStrings(stripLeadingZeros(xLow), stripLeadingZeros(xHigh));
		String sumY = addStrings(stripLeadingZeros(yLow), stripLeadingZeros(yHigh));
		String z1 = karatsuba(sumX, sumY);

		// z1 - z2 - z0
		String mid = subtractStrings(subtractStrings(z1, z2), z0); // vedno nenegativen izraz

		// z2 * 10^(2*m) + mid * 10^m + z0
		String part1 = z2.equals("0") ? "" : z2 + repeatChar('0', 2 * m);
		String part2 = mid.equals("0") ? "" : mid + repeatChar('0', m);
		String res = addStrings(addStrings(part1.isEmpty() ? "0" : part1, part2.isEmpty() ? "0" : part2), z0);
		return stripLeadingZeros(res);
	}

	// Seštevanje dveh nenegativnih števil kot nizov
	private static String addStrings(String a, String b) {
		int i = a.length() - 1, j = b.length() - 1, carry = 0;
		StringBuilder sb = new StringBuilder(Math.max(a.length(), b.length()) + 1);
		while (i >= 0 || j >= 0 || carry > 0) {
			int da = (i >= 0) ? (a.charAt(i) - '0') : 0;
			int db = (j >= 0) ? (b.charAt(j) - '0') : 0;
			int sum = da + db + carry;
			sb.append((char)('0' + (sum % 10)));
			carry = sum / 10;
			i--; j--;
		}
		return sb.reverse().toString();
	}

	private static String subtractStrings(String a, String b) {
		int cmp = compareAbs(a, b);
		if (cmp == 0) return "0";
		if (cmp < 0) throw new IllegalArgumentException("subtractStrings zahteva a >= b (" + a + " < " + b + ")");

		int i = a.length() - 1, j = b.length() - 1, borrow = 0;
		StringBuilder sb = new StringBuilder(a.length());
		while (i >= 0) {
			int da = a.charAt(i) - '0' - borrow;
			int db = (j >= 0) ? (b.charAt(j) - '0') : 0;
			if (da < db) {
				da += 10;
				borrow = 1;
			} else {
				borrow = 0;
			}
			int d = da - db;
			sb.append((char)('0' + d));
			i--; j--;
		}
		return stripLeadingZeros(sb.reverse().toString());
	}

	private static int compareAbs(String a, String b) {
		a = stripLeadingZeros(a);
		b = stripLeadingZeros(b);
		if (a.length() != b.length()) return Integer.compare(a.length(), b.length());
		return a.compareTo(b);
	}

	private static String leftPadZeros(String s, int len) {
		int d = len - s.length();
		if (d <= 0) return s;
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < d; i++) sb.append('0');
		sb.append(s);
		return sb.toString();
	}

	private static String stripLeadingZeros(String s) {
		int i = 0;
		while (i < s.length() - 1 && s.charAt(i) == '0') i++;
		return s.substring(i);
	}

	private static String repeatChar(char c, int times) {
		if (times <= 0) return "";
		StringBuilder sb = new StringBuilder(times);
		for (int i = 0; i < times; i++) sb.append(c);
		return sb.toString();
	}
}