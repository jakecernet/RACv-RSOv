import java.util.function.DoubleUnaryOperator;

public class naloga3 {
	// Funkciji iz navodil
	static double f1(double x) { // f1(x) = 1/2 x + 2
		return 0.5 * x + 2.0;
	}

	static double f2(double x) { // f2(x) = 1/6 (x^2 + 5)
		return (x * x + 5.0) / 6.0;
	}

	// Točna integrala na [0,5]
	static double exactF1(double a, double b) { // ∫ (1/2 x + 2) dx = 1/4 x^2 + 2x
		return 0.25 * (b * b - a * a) + 2.0 * (b - a);
	}

	static double exactF2(double a, double b) { // ∫ (x^2+5)/6 dx = 1/18 x^3 + 5/6 x
		return (Math.pow(b, 3) - Math.pow(a, 3)) / 18.0 + (5.0 / 6.0) * (b - a);
	}

	// Kvadratnična (metoda srednjih pravokotnikov)
	static double midpoint(DoubleUnaryOperator f, double a, double b, int n) {
		double h = (b - a) / n;
		double sum = 0.0;
		for (int i = 0; i < n; i++) {
			double mid = a + (i + 0.5) * h;
			sum += f.applyAsDouble(mid);
		}
		return sum * h;
	}

	// Trapezna metoda
	static double trapezoid(DoubleUnaryOperator f, double a, double b, int n) {
		double h = (b - a) / n;
		double sum = 0.5 * (f.applyAsDouble(a) + f.applyAsDouble(b));
		for (int i = 1; i < n; i++) {
			double x = a + i * h;
			sum += f.applyAsDouble(x);
		}
		return sum * h;
	}

	// Poenostavljena Simpsonova: S = 1/3 T + 2/3 M
	static double simpsonSimplified(DoubleUnaryOperator f, double a, double b, int n) {
		double T = trapezoid(f, a, b, n);
		double M = midpoint(f, a, b, n);
		return (T + 2.0 * M) / 3.0;
	}

	public static void main(String[] args) {
		double a = 0.0, b = 5.0;
		int[] ns = {2, 4, 10, 50, 100};

		// Tabela za F1
		System.out.println("F1");
		System.out.println("Intervalov\tKvadratnična(mid)\ttrapezna\tsimpsonova\tizračunano");
		for (int n : ns) {
			double mid = midpoint(naloga3::f1, a, b, n);
			double tra = trapezoid(naloga3::f1, a, b, n);
			double sim = simpsonSimplified(naloga3::f1, a, b, n);
			double ex = exactF1(a, b);
			System.out.printf("%d\t\t%.6f\t\t%.6f\t%.6f\t%.6f%n", n, mid, tra, sim, ex);
		}

		System.out.println();

		// Tabela za F2
		System.out.println("F2");
		System.out.println("Intervalov\tKvadratnična(mid)\ttrapezna\tsimpsonova\tizračunano");
		for (int n : ns) {
			double mid = midpoint(naloga3::f2, a, b, n);
			double tra = trapezoid(naloga3::f2, a, b, n);
			double sim = simpsonSimplified(naloga3::f2, a, b, n);
			double ex = exactF2(a, b);
			System.out.printf("%d\t\t%.6f\t\t%.6f\t%.6f\t%.6f%n", n, mid, tra, sim, ex);
		}
	}
}
