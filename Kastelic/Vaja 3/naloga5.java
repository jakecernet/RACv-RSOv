/* Predpostavite, da imate vertikalno črto (pravokotno na osnovnico koordinatnega sistema). 
Na tretjini višine od osnovnice požene (hm) drevo novo vejo v obliki daljice, od osnovnice proti najvišji 
točki višine drevesa, vendar je nagnjena od osnovnice v levo za npr. 50. Vsaka veja se deli na isti način. 
Delitve ni več, ko je veja krajša od 5 pik. Dve iteraciji sta dani na spodnji sliki. 
Sestavite ustrezen JavaFX program, ki bo 'drevo' vizualiziral. */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import java.util.Scanner;

public class naloga5 extends Application {
    private double angle = Math.toRadians(30); // Kot nagnjenja vej
    private double lengthFactor = 0.67; // Faktor skrajšanja vej
    private int minLength = 5; // Minimalna dolžina veje za nadaljevanje delitve

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Pane pane = new Pane();
        Scene scene = new Scene(pane, 800, 600);
        primaryStage.setTitle("Fraktalno Drevo");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Začetna točka in dolžina glavne veje
        double startX = scene.getWidth() / 2;
        double startY = scene.getHeight();
        double length = scene.getHeight() / 3;

        // Nariši drevo
        drawTree(pane, startX, startY, length, -Math.PI / 2); // -Math.PI/2 za vertikalno gor
    }

    private void drawTree(Pane pane, double x1, double y1, double length, double angle) {
        if (length < minLength) return; // Pogoji za končanje rekurzije

        // Izračun končne točke veje
        double x2 = x1 + length * Math.cos(angle);
        double y2 = y1 + length * Math.sin(angle);

        // Nariši vejo
        Line line = new Line(x1, y1, x2, y2);
        line.setStroke(Color.BROWN);
        pane.getChildren().add(line);

        // Rekurzivno nariši levi in desni del veje
        drawTree(pane, x2, y2, length * lengthFactor, angle + this.angle); // Levi del
        drawTree(pane, x2, y2, length * lengthFactor, angle - this.angle); // Desni del
    }
}