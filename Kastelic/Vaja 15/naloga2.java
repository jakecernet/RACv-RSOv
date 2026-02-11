import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.*;

/**
 * Naloga 2: FX aplikacija za izvajanje SQL povpraševanj nad bazo glasovanja.
 *
 * Okno je vertikalno razdeljeno na dva dela:
 * - Zgornji del: vnos SQL povpraševanja + gumb za izvedbo
 * - Spodnji del: pasiven prikaz rezultata povpraševanja
 */
public class naloga2 extends Application {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/glasovanja";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";

    private TextArea queryInput;
    private TextArea resultOutput;

    @Override
    public void start(Stage stage) {

        // --- Zgornji del: vnos povpraševanja ---
        Label lblQuery = new Label("SQL povpraševanje:");
        queryInput = new TextArea("SELECT * FROM poll;");
        queryInput.setPrefRowCount(5);
        queryInput.setPromptText("Vnesite SQL povpraševanje...");

        Button btnExecute = new Button("Izvedi povpraševanje");
        btnExecute.setOnAction(e -> executeQuery());
        btnExecute.setMaxWidth(Double.MAX_VALUE);

        VBox topPane = new VBox(8, lblQuery, queryInput, btnExecute);
        topPane.setPadding(new Insets(10));

        // --- Spodnji del: prikaz rezultata ---
        Label lblResult = new Label("Rezultat:");
        resultOutput = new TextArea();
        resultOutput.setEditable(false); // pasiven prikaz
        resultOutput.setPrefRowCount(15);
        resultOutput.setStyle("-fx-font-family: 'Consolas', monospace;");

        VBox bottomPane = new VBox(8, lblResult, resultOutput);
        bottomPane.setPadding(new Insets(10));
        VBox.setVgrow(resultOutput, Priority.ALWAYS);

        // --- SplitPane: vertikalna razdelitev ---
        SplitPane splitPane = new SplitPane();
        splitPane.setOrientation(Orientation.VERTICAL);
        splitPane.getItems().addAll(topPane, bottomPane);
        splitPane.setDividerPositions(0.35);

        Scene scene = new Scene(splitPane, 700, 550);
        stage.setTitle("Glasovanja - SQL poizvedbe");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Izvede vneseno SQL povpraševanje in prikaže rezultat.
     */
    private void executeQuery() {
        String sql = queryInput.getText().trim();
        if (sql.isEmpty()) {
            resultOutput.setText("Vnesite SQL povpraševanje.");
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                Statement stmt = conn.createStatement()) {

            boolean hasResultSet = stmt.execute(sql);

            if (hasResultSet) {
                // SELECT - prikažemo rezultat kot tabelo
                ResultSet rs = stmt.getResultSet();
                ResultSetMetaData meta = rs.getMetaData();
                int colCount = meta.getColumnCount();

                StringBuilder sb = new StringBuilder();

                // Glava tabele
                for (int i = 1; i <= colCount; i++) {
                    sb.append(String.format("%-25s", meta.getColumnLabel(i)));
                }
                sb.append("\n");
                sb.append("-".repeat(25 * colCount)).append("\n");

                // Vrstice
                int rowCount = 0;
                while (rs.next()) {
                    for (int i = 1; i <= colCount; i++) {
                        String val = rs.getString(i);
                        sb.append(String.format("%-25s", val == null ? "NULL" : val));
                    }
                    sb.append("\n");
                    rowCount++;
                }

                sb.append("\n").append(rowCount).append(" vrstic.");
                resultOutput.setText(sb.toString());

            } else {
                // INSERT, UPDATE, DELETE ...
                int affected = stmt.getUpdateCount();
                resultOutput.setText("Ukaz uspešno izveden.\nŠtevilo spremenjenih vrstic: " + affected);
            }

        } catch (SQLException e) {
            resultOutput.setText("NAPAKA: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
