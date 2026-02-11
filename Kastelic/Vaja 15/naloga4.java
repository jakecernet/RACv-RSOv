import javafx.application.Application;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Naloga 4: FX aplikacija za aktiviranje/deaktiviranje polov (vprašanj).
 *
 * Pravila:
 * - Sočasno je aktiven zgolj en pol/vprašanje.
 * - Ob aktivaciji enega se vsi ostali deaktivirajo.
 * - Pol je aktiven, če je openedAt v preteklosti in closedAt v prihodnosti
 * (closedAt = 10 dni naprej od zdaj).
 * - Aktivacija: openedAt = NOW(), closedAt = NOW() + 10 dni.
 * - Deaktivacija: closedAt = NOW() (čas zapiranja nastavimo na trenutek).
 */
public class naloga4 extends Application {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/glasovanja";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";

    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    private TableView<Poll> table;
    private Label statusLabel;

    // ===================== Model Poll =====================
    public static class Poll {
        private final IntegerProperty id;
        private final StringProperty vprasanje;
        private final ObjectProperty<LocalDateTime> openedAt;
        private final ObjectProperty<LocalDateTime> closedAt;
        private final BooleanProperty aktiven;

        public Poll(int id, String vprasanje, LocalDateTime openedAt, LocalDateTime closedAt) {
            this.id = new SimpleIntegerProperty(id);
            this.vprasanje = new SimpleStringProperty(vprasanje);
            this.openedAt = new SimpleObjectProperty<>(openedAt);
            this.closedAt = new SimpleObjectProperty<>(closedAt);
            // Aktiven = openedAt v preteklosti IN closedAt v prihodnosti
            LocalDateTime now = LocalDateTime.now();
            this.aktiven = new SimpleBooleanProperty(
                    openedAt.isBefore(now) && closedAt.isAfter(now));
        }

        public int getId() {
            return id.get();
        }

        public IntegerProperty idProperty() {
            return id;
        }

        public String getVprasanje() {
            return vprasanje.get();
        }

        public StringProperty vprasanjeProperty() {
            return vprasanje;
        }

        public LocalDateTime getOpenedAt() {
            return openedAt.get();
        }

        public ObjectProperty<LocalDateTime> openedAtProperty() {
            return openedAt;
        }

        public void setOpenedAt(LocalDateTime v) {
            openedAt.set(v);
        }

        public LocalDateTime getClosedAt() {
            return closedAt.get();
        }

        public ObjectProperty<LocalDateTime> closedAtProperty() {
            return closedAt;
        }

        public void setClosedAt(LocalDateTime v) {
            closedAt.set(v);
        }

        public boolean isAktiven() {
            return aktiven.get();
        }

        public BooleanProperty aktivenProperty() {
            return aktiven;
        }

        public void setAktiven(boolean v) {
            aktiven.set(v);
        }

        @Override
        public String toString() {
            return "[" + id.get() + "] " + vprasanje.get();
        }
    }

    // ===================== FX Aplikacija =====================
    @Override
    public void start(Stage stage) {

        // --- Tabela polov ---
        table = new TableView<>();

        TableColumn<Poll, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(40);

        TableColumn<Poll, String> colVprasanje = new TableColumn<>("Vprašanje");
        colVprasanje.setCellValueFactory(new PropertyValueFactory<>("vprasanje"));
        colVprasanje.setPrefWidth(220);

        TableColumn<Poll, LocalDateTime> colOpened = new TableColumn<>("Odprto (openedAt)");
        colOpened.setCellValueFactory(new PropertyValueFactory<>("openedAt"));
        colOpened.setPrefWidth(155);
        colOpened.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.format(DISPLAY_FORMAT));
            }
        });

        TableColumn<Poll, LocalDateTime> colClosed = new TableColumn<>("Zaprto (closedAt)");
        colClosed.setCellValueFactory(new PropertyValueFactory<>("closedAt"));
        colClosed.setPrefWidth(155);
        colClosed.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.format(DISPLAY_FORMAT));
            }
        });

        // Stolpec za prikaz statusa (aktiven/neaktiven)
        TableColumn<Poll, Boolean> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(new PropertyValueFactory<>("aktiven"));
        colStatus.setPrefWidth(90);
        colStatus.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("");
                    setStyle("");
                } else if (item) {
                    setText("AKTIVEN");
                    setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                } else {
                    setText("neaktiven");
                    setStyle("-fx-text-fill: gray;");
                }
            }
        });

        table.getColumns().addAll(colId, colVprasanje, colOpened, colClosed, colStatus);

        // --- Gumbi ---
        Button btnActivate = new Button("Aktiviraj izbrani pol");
        btnActivate.setOnAction(e -> activatePoll());
        btnActivate.setPrefWidth(200);

        Button btnDeactivateAll = new Button("Deaktiviraj vse");
        btnDeactivateAll.setOnAction(e -> deactivateAllPolls());
        btnDeactivateAll.setPrefWidth(200);

        Button btnRefresh = new Button("Osveži");
        btnRefresh.setOnAction(e -> refreshTable());
        btnRefresh.setPrefWidth(200);

        statusLabel = new Label("Izberite pol in kliknite 'Aktiviraj'.");

        HBox buttonBar = new HBox(10, btnActivate, btnDeactivateAll, btnRefresh);
        buttonBar.setAlignment(Pos.CENTER);
        buttonBar.setPadding(new Insets(10));

        VBox root = new VBox(10, table, buttonBar, statusLabel);
        root.setPadding(new Insets(10));
        VBox.setVgrow(table, Priority.ALWAYS);

        // Napolnimo tabelo
        refreshTable();

        Scene scene = new Scene(root, 750, 450);
        stage.setTitle("Glasovanja - Aktivacija polov");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Aktivira izbrani pol:
     * 1. Deaktivira vse aktivne pole (closedAt = NOW).
     * 2. Aktivira izbranega (openedAt = NOW, closedAt = NOW + 10 dni).
     */
    private void activatePoll() {
        Poll selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            statusLabel.setText("Najprej izberite pol iz tabele!");
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            conn.setAutoCommit(false);

            // 1. Deaktiviraj vse (closedAt = NOW)
            try (PreparedStatement psDeactivate = conn.prepareStatement(
                    "UPDATE poll SET closedAt = NOW() WHERE closedAt > NOW()")) {
                psDeactivate.executeUpdate();
            }

            // 2. Aktiviraj izbranega (openedAt = NOW, closedAt = NOW + 10 dni)
            try (PreparedStatement psActivate = conn.prepareStatement(
                    "UPDATE poll SET openedAt = NOW(), closedAt = DATE_ADD(NOW(), INTERVAL 10 DAY) WHERE id = ?")) {
                psActivate.setInt(1, selected.getId());
                psActivate.executeUpdate();
            }

            conn.commit();
            statusLabel.setText("Pol [" + selected.getId() + "] '" + selected.getVprasanje() + "' je zdaj AKTIVEN.");
            refreshTable();

        } catch (SQLException e) {
            statusLabel.setText("Napaka: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Deaktivira vse pole (closedAt = NOW).
     */
    private void deactivateAllPolls() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("UPDATE poll SET closedAt = NOW() WHERE closedAt > NOW()");
            statusLabel.setText("Vsi poli so bili deaktivirani.");
            refreshTable();

        } catch (SQLException e) {
            statusLabel.setText("Napaka: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Osveži podatke v tabeli iz baze.
     */
    private void refreshTable() {
        ObservableList<Poll> list = FXCollections.observableArrayList();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT id, vprasanje, openedAt, closedAt FROM poll")) {

            while (rs.next()) {
                list.add(new Poll(
                        rs.getInt("id"),
                        rs.getString("vprasanje"),
                        rs.getTimestamp("openedAt").toLocalDateTime(),
                        rs.getTimestamp("closedAt").toLocalDateTime()));
            }

        } catch (SQLException e) {
            statusLabel.setText("Napaka pri nalaganju: " + e.getMessage());
            e.printStackTrace();
        }

        table.setItems(list);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
