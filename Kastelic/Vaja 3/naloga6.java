/* Sestavite javanski program, ki bo izrisoval trikotnik Sierpinskega.
   Postopek (povzetek):
   1) razpolovi vse tri stranice trikotnika,
   2) nariši črte med razpolovišči (nastane manjši notranji trikotnik),
   3) postopek ponovi za tri zunanje trikotnike.

   Rekurzijo prekinemo, ko je najkrajša stranica trenutnega trikotnika krajša od 5 pik.

   Opomba: Program uporablja JavaFX (enako kot ostale naloge v mapi). 
 */

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class naloga6 extends Application {
	private static final double MIN_SIDE = 5.0; // prag za ustavitev rekurzije

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		Pane pane = new Pane();
		Scene scene = new Scene(pane, 800, 700);
		stage.setTitle("Sierpinski trikotnik");
		stage.setScene(scene);
		stage.show();

		// Začetni (približno) enakostranični trikotnik v oknu
		double margin = 40;
		double side = scene.getWidth() - 2 * margin;
		double height = side * Math.sqrt(3) / 2.0;
		double cx = scene.getWidth() / 2.0;
		double baseY = margin + height; // osnovnica spodaj

		Point2D T1 = new Point2D(cx - side / 2.0, baseY);      // levo-spodaj
		Point2D T2 = new Point2D(cx + side / 2.0, baseY);      // desno-spodaj
		Point2D T3 = new Point2D(cx, baseY - height);          // zgoraj

		// (neobvezno) narišemo obris začetnega trikotnika
		drawEdgeTriangle(pane, T1, T2, T3, Color.BLACK, 1.5);

		// Sierpinski rekurzija
		sierpinski(pane, T1, T2, T3);
	}

	// Rekurzivni izris Sierpinskega trikotnika (prek črt med razpolovišči)
	private void sierpinski(Pane pane, Point2D a, Point2D b, Point2D c) {
		double s1 = a.distance(b);
		double s2 = b.distance(c);
		double s3 = c.distance(a);
		double min = Math.min(s1, Math.min(s2, s3));
		if (min < MIN_SIDE) return; // baza rekurzije

		// Razpolovišča stranic
		Point2D ab = a.midpoint(b);
		Point2D bc = b.midpoint(c);
		Point2D ca = c.midpoint(a);

		// Nariši notranji trikotnik, ki povezuje razpolovišča
		drawEdgeTriangle(pane, ab, bc, ca, Color.DARKBLUE, 1.0);

		// Rekurzija na treh zunanjih trikotnikih (brez notranjega)
		sierpinski(pane, a, ab, ca);
		sierpinski(pane, ab, b, bc);
		sierpinski(pane, ca, bc, c);
	}

	// Pomožna: nariše obris trikotnika podanega s tremi točkami
	private void drawEdgeTriangle(Pane pane, Point2D p1, Point2D p2, Point2D p3, Color color, double width) {
		pane.getChildren().add(makeLine(p1, p2, color, width));
		pane.getChildren().add(makeLine(p2, p3, color, width));
		pane.getChildren().add(makeLine(p3, p1, color, width));
	}

	private Line makeLine(Point2D p, Point2D q, Color color, double width) {
		Line l = new Line(p.getX(), p.getY(), q.getX(), q.getY());
		l.setStroke(color);
		l.setStrokeWidth(width);
		return l;
	}
}


