/* Uporabite JavaFX in seznam iz 4b) izrišite na sceno odra. Točke lahko vizualizirate z majhno krožnico (javafx.scene.shape.Circle), barvo v record nadomestite z javafx.scene.paint.Color.
   Realizirajte gibanje kače (event driven): trije gumbi CW, CCW, NAP.
*/
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.LinkedList;

public class naloga5 extends Application {
	// Record točke z JavaFX barvo
	public record RTocka(int x, int y, Color barva) {}

	private enum Dir {
		RIGHT(1, 0), DOWN(0, 1), LEFT(-1, 0), UP(0, -1);
		final int dx, dy;
		Dir(int dx, int dy) { this.dx = dx; this.dy = dy; }
		Dir cw() { return switch (this) { case RIGHT -> DOWN; case DOWN -> LEFT; case LEFT -> UP; case UP -> RIGHT; }; }
		Dir ccw() { return switch (this) { case RIGHT -> UP; case UP -> LEFT; case LEFT -> DOWN; case DOWN -> RIGHT; }; }
	}

	private final LinkedList<RTocka> ll = new LinkedList<>(); // tail..head (head je zadnji)
	private Dir dir = Dir.RIGHT;

	private Pane board;
	private Label status;

	private final double scale = 30.0;
	private final double margin = 30.0;
	private final double radius = 10.0;

	private final int initialXStart = 5;
	private final int initialXEndExclusive = 12; // 5..11
	private final int initialY = 10;

	@Override
	public void start(Stage stage) {
		var root = new BorderPane();
		board = new Pane();
		board.setPrefSize(800, 400);

		var btnCW = new Button("CW");
		var btnCCW = new Button("CCW");
		var btnNAP = new Button("NAP");
		var btnReset = new Button("Reset");
		status = new Label("Pripravljeno");

		btnCW.setOnAction(e -> { dir = dir.cw(); step(); });
		btnCCW.setOnAction(e -> { dir = dir.ccw(); step(); });
		btnNAP.setOnAction(e -> step());
		btnReset.setOnAction(e -> { initList(); redraw(); status.setText("Reset"); });

		var top = new HBox(10, btnCW, btnCCW, btnNAP, btnReset, status);
		top.setPadding(new Insets(10));

		root.setTop(top);
		root.setCenter(board);

		initList();
		redraw();

		stage.setTitle("Naloga 5 – Kača (CW/CCW/NAP)");
		stage.setScene(new Scene(root));
		stage.show();
	}

	private void initList() {
		ll.clear();
		for (int x = initialXStart; x < initialXEndExclusive; x++) {
			ll.add(new RTocka(x, initialY, Color.YELLOW));
		}
		// Označi glavo kot rdečo (zadnji element)
		var head = ll.removeLast();
		ll.addLast(new RTocka(head.x(), head.y(), Color.RED));
		dir = Dir.RIGHT;
		status.setText("Začetni seznam (glava rdeča)");
	}

	// Naredi en premik v trenutni smeri (fiksna dolžina "kače")
	private void step() {
		if (ll.isEmpty()) return;

		// odstrani rep
		ll.removeFirst();

		// prebarvaj staro glavo v rumeno
		var oldHead = ll.removeLast();
		ll.addLast(new RTocka(oldHead.x(), oldHead.y(), Color.YELLOW));

		// dodaj novo glavo (rdečo) v smeri dir
		int nx = oldHead.x() + dir.dx;
		int ny = oldHead.y() + dir.dy;
		ll.addLast(new RTocka(nx, ny, Color.RED));

		status.setText("Smer: " + dir + " | Glava: (" + nx + "," + ny + ")");
		redraw();
	}

	private void redraw() {
		board.getChildren().clear();
		for (RTocka p : ll) {
			double cx = margin + p.x() * scale;
			double cy = margin + p.y() * scale;
			var c = new Circle(cx, cy, radius, p.barva());
			c.setStroke(Color.BLACK);
			board.getChildren().add(c);
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
