/* Recimo, da imamo šahovnico (8x8) polj in šahovsko figurico skakača, s principom premikanja 1,2 in 2,1 
(možni skoki skakača). Iščemo sekvenco skokov skakača, iz poljubne izbrane začetne pozicije, ki s skoki 
pokrije celotno šahovnico, pri tem pa posamezno polje šahovnice obišče zgolj enkrat. Spišite JavaFX varianto 
programa, ki bo vizualiziral pozicije skokov skakača. */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;

public class naloga4 extends Application {
    private static final int SIZE = 8; // Velikost šahovnice
    private static final int TILE_SIZE = 60; // Velikost posameznega polja
    private int[][] board = new int[SIZE][SIZE]; // Šahovnica (shrani številko poteze 1..64)
    private final int[] moveX = {1, 2, 2, 1, -1, -2, -2, -1}; // Možni premiki skakača v X smeri
    private final int[] moveY = {2, 1, -1, -2, -2, -1, 1, 2}; // Možni premiki skakača v Y smeri

    private Pane tilesPane;   // plast za polja
    private Pane overlayPane; // plast za poti, številke in skakača
    private Timeline timeline; // animacija

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        tilesPane = new Pane();
        overlayPane = new Pane();
        root.getChildren().addAll(tilesPane, overlayPane);

        Scene scene = new Scene(root, SIZE * TILE_SIZE, SIZE * TILE_SIZE);
        primaryStage.setTitle("Skakač na šahovnici – klikni začetno polje");
        primaryStage.setScene(scene);
        primaryStage.show();

        drawEmptyBoard();

        // Klik na šahovnico določi začetno polje in sproži vizualizacijo
        root.setOnMouseClicked(e -> {
            int x = (int) (e.getX() / TILE_SIZE);
            int y = (int) (e.getY() / TILE_SIZE);
            if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) return;

            // prekini morebitno prejšnjo animacijo in očisti prekrivno plast
            if (timeline != null) {
                timeline.stop();
            }
            overlayPane.getChildren().clear();

            // izračun poti (Warnsdorff)
            boolean ok = computeTourWarnsdorff(x, y);
            if (!ok) {
                System.out.println("Ni rešitve iz te začetne pozicije.");
                return;
            }

            // animiraj pot (številke na poljih + premik skakača + povezovalne črte)
            animateTour();
        });
    }

    // Warnsdorffov algoritem: hitro poišče turnejo iz poljubnega začetnega polja
    private boolean computeTourWarnsdorff(int startX, int startY) {
        // počisti šahovnico
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = 0;
            }
        }

        int x = startX;
        int y = startY;
        board[x][y] = 1;

        for (int move = 2; move <= SIZE * SIZE; move++) {
            int bestNX = -1, bestNY = -1;
            int bestDegree = Integer.MAX_VALUE;

            for (int k = 0; k < 8; k++) {
                int nx = x + moveX[k];
                int ny = y + moveY[k];
                if (isSafe(nx, ny)) {
                    int deg = countOnwardMoves(nx, ny);
                    if (deg < bestDegree) {
                        bestDegree = deg;
                        bestNX = nx;
                        bestNY = ny;
                    }
                }
            }

            if (bestNX == -1) {
                // zatajilo – ni naslednjega koraka
                return false;
            }

            x = bestNX;
            y = bestNY;
            board[x][y] = move;
        }
        return true;
    }

    // Preveri, če je naslednji premik varen
    private boolean isSafe(int x, int y) {
        return x >= 0 && x < SIZE && y >= 0 && y < SIZE && board[x][y] == 0;
    }

    // Prešteje koliko varnih izhodov ima polje (nx, ny)
    private int countOnwardMoves(int nx, int ny) {
        int count = 0;
        for (int k = 0; k < 8; k++) {
            int tx = nx + moveX[k];
            int ty = ny + moveY[k];
            if (isSafe(tx, ty)) count++;
        }
        return count;
    }

    // Nariše (samo) polja šahovnice – brez številk in poti
    private void drawEmptyBoard() {
        tilesPane.getChildren().clear();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Rectangle tile = new Rectangle(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                tile.setFill((i + j) % 2 == 0 ? Color.BEIGE : Color.SADDLEBROWN);
                tilesPane.getChildren().add(tile);
            }
        }
    }

    // Animira pot: na vsako polje doda številko poteze, črto med potezama in premika krog (skakača)
    private void animateTour() {
        // Poišči zaporedje koordinat iz matrike board (1..N)
        int total = SIZE * SIZE;
        int[] xs = new int[total];
        int[] ys = new int[total];
        for (int step = 1; step <= total; step++) {
            outer:
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (board[i][j] == step) {
                        xs[step - 1] = i;
                        ys[step - 1] = j;
                        break outer;
                    }
                }
            }
        }

        overlayPane.getChildren().clear();
        Circle knight = new Circle(TILE_SIZE / 4.0);
        knight.setFill(Color.RED);
        overlayPane.getChildren().add(knight);

        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        for (int idx = 0; idx < total; idx++) {
            final int i = idx; // za lambda
            Duration t = Duration.millis(200 * i);
            timeline.getKeyFrames().add(new KeyFrame(t, ev -> {
                int cx = xs[i];
                int cy = ys[i];

                // premik skakača
                knight.setCenterX(cx * TILE_SIZE + TILE_SIZE / 2.0);
                knight.setCenterY(cy * TILE_SIZE + TILE_SIZE / 2.0);

                // številka poteze na polju
                Label lbl = new Label(String.valueOf(i + 1));
                lbl.setMinSize(TILE_SIZE, TILE_SIZE);
                lbl.setAlignment(Pos.CENTER);
                lbl.setTextFill(((cx + cy) % 2 == 0) ? Color.BLACK : Color.WHITE);
                lbl.setLayoutX(cx * TILE_SIZE);
                lbl.setLayoutY(cy * TILE_SIZE);
                overlayPane.getChildren().add(lbl);

                // črta od prejšnjega polja do trenutnega
                if (i > 0) {
                    Line line = new Line(
                        xs[i - 1] * TILE_SIZE + TILE_SIZE / 2.0,
                        ys[i - 1] * TILE_SIZE + TILE_SIZE / 2.0,
                        cx * TILE_SIZE + TILE_SIZE / 2.0,
                        cy * TILE_SIZE + TILE_SIZE / 2.0
                    );
                    line.setStroke(Color.DARKRED);
                    line.setStrokeWidth(2);
                    overlayPane.getChildren().add(line);
                }
            }));
        }

        // Ustavi animacijo po zadnjem koraku
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(200 * total + 10), ev -> timeline.stop()));
        timeline.playFromStart();
    }

    // (Neobvezno) izpiše zaporedje obiskanih polj v terminal (po korakih 1..N)
    private void printVisited() {
        System.out.println("Zaporedje obiskanih polj (korak: (x, y)):");
        for (int step = 1; step <= SIZE * SIZE; step++) {
            boolean found = false;
            for (int x = 0; x < SIZE && !found; x++) {
                for (int y = 0; y < SIZE && !found; y++) {
                    if (board[x][y] == step) {
                        System.out.printf("%3d: (%d, %d)%n", step, x, y);
                        found = true;
                    }
                }
            }
        }
    }
}
