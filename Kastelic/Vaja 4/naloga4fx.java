import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;

public class naloga4fx extends Application {
	// Record RTocka z JavaFX barvo
	public record RTocka(int x, int y, Color barva) {}

	private final LinkedList<RTocka> ll = new LinkedList<>();
	private Timeline timeline;

	private Canvas canvas;
	private GraphicsContext gc;
	private Label status;

	private final double scale = 30.0;
	private final double margin = 30.0;
	private final int initialXStart = 5;
	private final int initialXEndExclusive = 12; // 5..11
	private final int initialY = 10;

	@Override
	public void start(Stage stage) {
		var root = new BorderPane();
		canvas = new Canvas(800, 400);
		gc = canvas.getGraphicsContext2D();

		status = new Label("Pripravljeno");
		var btnStart = new Button("Start");
		var btnReset = new Button("Reset");

		btnStart.setOnAction(e -> runAnimation());
		btnReset.setOnAction(e -> {
			if (timeline != null) timeline.stop();
			initList();
			status.setText("Začetni seznam");
			draw();
			printList("Začetni seznam:");
		});

		var top = new HBox(10, btnStart, btnReset, status);
		top.setPadding(new Insets(10));
		root.setTop(top);
		root.setCenter(canvas);

		// Začetno stanje
		initList();
		draw();
		printList("Začetni seznam:");

		stage.setTitle("Vizualizacija: krožen premik elementov (a) in (b)");
		stage.setScene(new Scene(root));
		stage.show();
	}

	private void initList() {
		ll.clear();
		for (int i = initialXStart; i < initialXEndExclusive; i++) {
			ll.add(new RTocka(i, initialY, Color.YELLOW));
		}
	}

	private void runAnimation() {
		if (timeline != null) timeline.stop();

		List<Runnable> steps = new ArrayList<>();

		// Prikaz začetnega seznama
		steps.add(() -> {
			status.setText("Začetni seznam");
			draw();
			printList("Začetni seznam:");
		});

		// (a) Krožen premik za en cikel (toliko korakov kot elementov)
		final int sizeA = ll.size();
		for (int i = 0; i < sizeA; i++) {
			steps.add(() -> {
				status.setText("Krožen premik elementov (a)");
				var first = ll.removeFirst();
				ll.addLast(first);
				draw();
				printList("(a) korak:");
			});
		}

		// Ponovna inicializacija pred (b)
		steps.add(() -> {
			initList();
			status.setText("Ponovna inicializacija za (b)");
			draw();
			printList("Ponovna inicializacija:");
		});

		// (b) Krožen premik z dodajanjem nove rdeče točke, originalSize korakov
		final int originalSize = initialXEndExclusive - initialXStart; // 7
		for (int i = 0; i < originalSize; i++) {
			steps.add(() -> {
				status.setText("Krožen premik z dodajanjem (b)");
				var first = ll.removeFirst();
				var last = ll.getLast();
				var newPoint = new RTocka(last.x() + 1, last.y(), Color.RED);
				ll.addLast(newPoint);
				ll.addLast(first);
				draw();
				printList("(b) korak:");
			});
		}

		timeline = new Timeline();
		double msPerStep = 600;
		for (int i = 0; i < steps.size(); i++) {
			var step = steps.get(i);
			timeline.getKeyFrames().add(new KeyFrame(Duration.millis(i * msPerStep), e -> step.run()));
		}
		timeline.setOnFinished(e -> status.setText("Končano"));
		timeline.playFromStart();
	}

	private void draw() {
		gc.setFill(Color.web("#202225"));
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

		// Koordinatna os (preprosta vodila)
		gc.setStroke(Color.web("#2f3136"));
		gc.setLineWidth(1);
		for (int x = 0; x <= 30; x++) {
			double X = margin + x * scale;
			gc.strokeLine(X, margin, X, canvas.getHeight() - margin);
		}
		for (int y = 0; y <= 15; y++) {
			double Y = margin + y * scale;
			gc.strokeLine(margin, Y, canvas.getWidth() - margin, Y);
		}

		// Oznake osi
		gc.setFill(Color.LIGHTGRAY);
		gc.fillText("x", canvas.getWidth() - margin + 10, canvas.getHeight() - margin + 5);
		gc.fillText("y", margin - 10, margin - 10);

		// Nariši točke iz seznama
		int idx = 0;
		for (RTocka p : ll) {
			double cx = margin + p.x() * scale;
			double cy = margin + p.y() * scale;

			// krog
			double r = 10;
			gc.setFill(p.barva());
			gc.fillOval(cx - r, cy - r, r * 2, r * 2);
			gc.setStroke(Color.BLACK);
			gc.setLineWidth(1.2);
			gc.strokeOval(cx - r, cy - r, r * 2, r * 2);

			// indeks in koordinate
			gc.setFill(Color.WHITE);
			gc.fillText("#" + idx + " (" + p.x() + "," + p.y() + ")", cx + 12, cy - 12);
			idx++;
		}
	}

	private void printList(String title) {
		System.out.println(title);
		System.out.println(ll);
	}

	public static void main(String[] args) {
		launch(args);
	}
}


