import javafx.application.Application;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Naloga 3: FX aplikacija s TableView za vizualizacijo tabele poll.
 *
 * Datumski elementi so prikazani v berljivi obliki (format: dd.MM.yyyy
 * HH:mm:ss).
 */
public class naloga3 extends Application {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/glasovanja";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";

    // Formatter za berljiv prikaz datuma
    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    // ===================== Model Poll =====================
    public static class Poll {
        private final IntegerProperty id;
        private final StringProperty vprasanje;
        private final StringProperty moznosti;
        private final ObjectProperty<LocalDateTime> openedAt;
        private final ObjectProperty<LocalDateTime> closedAt;

        public Poll(int id, String vprasanje, String moznosti,
                LocalDateTime openedAt, LocalDateTime closedAt) {
            this.id = new SimpleIntegerProperty(id);
            this.vprasanje = new SimpleStringProperty(vprasanje);
            this.moznosti = new SimpleStringProperty(moznosti);
            this.openedAt = new SimpleObjectProperty<>(openedAt);
            this.closedAt = new SimpleObjectProperty<>(closedAt);
        }

        // Getterji za PropertyValueFactory
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

        public String getMoznosti() {
            return moznosti.get();
        }

        public StringProperty moznostiProperty() {
            return moznosti;
        }

        public LocalDateTime getOpenedAt() {
            return openedAt.get();
        }

        public ObjectProperty<LocalDateTime> openedAtProperty() {
            return openedAt;
        }

        public LocalDateTime getClosedAt() {
            return closedAt.get();
        }

        public ObjectProperty<LocalDateTime> closedAtProperty() {
            return closedAt;
        }
    }

    // ===================== FX Aplikacija =====================
    @Override
    public void start(Stage stage) {

        TableView<Poll> table = new TableView<>();

        // Stolpec ID
        TableColumn<Poll, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(50);

        // Stolpec Vprašanje
        TableColumn<Poll, String> colVprasanje = new TableColumn<>("Vprašanje");
        colVprasanje.setCellValueFactory(new PropertyValueFactory<>("vprasanje"));
        colVprasanje.setPrefWidth(220);

        // Stolpec Možnosti
        TableColumn<Poll, String> colMoznosti = new TableColumn<>("Možnosti");
        colMoznosti.setCellValueFactory(new PropertyValueFactory<>("moznosti"));
        colMoznosti.setPrefWidth(200);

        // Nadstolpec Časi s podstolpci openedAt in closedAt
        TableColumn<Poll, String> colCasi = new TableColumn<>("Časi");

        TableColumn<Poll, LocalDateTime> colOpened = new TableColumn<>("Odprto");
        colOpened.setCellValueFactory(new PropertyValueFactory<>("openedAt"));
        colOpened.setPrefWidth(160);
        // Formatiran prikaz datuma
        colOpened.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.format(DISPLAY_FORMAT));
            }
        });

        TableColumn<Poll, LocalDateTime> colClosed = new TableColumn<>("Zaprto");
        colClosed.setCellValueFactory(new PropertyValueFactory<>("closedAt"));
        colClosed.setPrefWidth(160);
        // Formatiran prikaz datuma
        colClosed.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.format(DISPLAY_FORMAT));
            }
        });

        colCasi.getColumns().addAll(colOpened, colClosed);

        // Sortiranje po ID padajoče
        colId.setSortType(TableColumn.SortType.DESCENDING);

        // Napolnimo tabelo s podatki iz baze
        ObservableList<Poll> data = loadPollsFromDB();
        table.setItems(data);

        table.getColumns().addAll(colId, colVprasanje, colMoznosti, colCasi);

        // Aktiviraj sortiranje
        table.getSortOrder().add(colId);

        StackPane root = new StackPane(table);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 850, 400);
        stage.setTitle("Glasovanja - Tabela Poll (TableView)");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Naloži vse poll zapise iz podatkovne baze.
     */
    private ObservableList<Poll> loadPollsFromDB() {
        ObservableList<Poll> list = FXCollections.observableArrayList();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM poll")) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String vprasanje = rs.getString("vprasanje");
                String moznosti = rs.getString("moznosti");
                LocalDateTime openedAt = rs.getTimestamp("openedAt").toLocalDateTime();
                LocalDateTime closedAt = rs.getTimestamp("closedAt").toLocalDateTime();

                list.add(new Poll(id, vprasanje, moznosti, openedAt, closedAt));
            }

        } catch (SQLException e) {
            System.err.println("Napaka pri branju iz baze: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
