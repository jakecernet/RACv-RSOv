/* Spišite FX aplikacijo z uporabniškim vmesnikom, ki bo na zaslonu vizualizirala vsa gesla iz podatkovne zbirke s 
pomočjo komponente/razreda TableView. V pomoč naj vam bo vir z https://docs.oracle.com/javafx/2/ui_controls/table-view.htm */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;

import java.sql.*;

public class naloga4 extends Application {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/vislice";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    
    private TableView<GesloData> tableView;
    
    public static void main(String[] args) {
        Application.launch(args);
    }
    
    @Override
    public void start(Stage stage) {
        // Initialize database
        initializeDatabase();
        
        // Create main layout
        VBox root = new VBox();
        root.setPadding(new Insets(15));
        root.setSpacing(10);
        
        // Title
        Label title = new Label("STATISTIKA GESEL");
        title.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
        
        // Create TableView
        tableView = new TableView<>();
        setupTableColumns();
        
        // Load data from database
        loadDataFromDatabase();
        
        // Refresh button
        Button refreshButton = new Button("Osveži podatke");
        refreshButton.setStyle("-fx-padding: 8; -fx-font-size: 12;");
        refreshButton.setOnAction(event -> loadDataFromDatabase());
        
        // Add components to root
        root.getChildren().addAll(title, tableView, refreshButton);
        
        // Create scene
        Scene scene = new Scene(root, 900, 600);
        
        // Set stage
        stage.setTitle("Vislice - Statistika gesel");
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * Set up table columns
     */
    private void setupTableColumns() {
        // ID Column
        TableColumn<GesloData, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> 
            javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getId())
        );
        idColumn.setPrefWidth(50);
        
        // Geslo Column
        TableColumn<GesloData, String> gesloColumn = new TableColumn<>("Geslo");
        gesloColumn.setCellValueFactory(cellData -> 
            javafx.beans.binding.Bindings.createStringBinding(() -> cellData.getValue().getGeslo())
        );
        gesloColumn.setPrefWidth(200);
        
        // St_uporab Column
        TableColumn<GesloData, Integer> stUporabColumn = new TableColumn<>("Uporab");
        stUporabColumn.setCellValueFactory(cellData -> 
            javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getStUporab())
        );
        stUporabColumn.setPrefWidth(80);
        
        // Uspesni_poskusi Column
        TableColumn<GesloData, Integer> uspesniColumn = new TableColumn<>("Uspešni");
        uspesniColumn.setCellValueFactory(cellData -> 
            javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getUspesniPoskusi())
        );
        uspesniColumn.setPrefWidth(80);
        
        // Procent_uspešnosti Column
        TableColumn<GesloData, String> procentColumn = new TableColumn<>("% Uspešnosti");
        procentColumn.setCellValueFactory(cellData -> 
            javafx.beans.binding.Bindings.createStringBinding(() -> 
                String.format("%.2f%%", cellData.getValue().getProcentUspesnosti())
            )
        );
        procentColumn.setPrefWidth(120);
        
        // Zahtevnost Column
        TableColumn<GesloData, Integer> zahtevnostColumn = new TableColumn<>("Zahtevnost");
        zahtevnostColumn.setCellValueFactory(cellData -> 
            javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getZahtevnost())
        );
        zahtevnostColumn.setPrefWidth(90);
        
        // Zadnja uporaba Column
        TableColumn<GesloData, String> zadnjaColumn = new TableColumn<>("Zadnja uporaba");
        zadnjaColumn.setCellValueFactory(cellData -> 
            javafx.beans.binding.Bindings.createStringBinding(() -> cellData.getValue().getZadnjaUporaba())
        );
        zadnjaColumn.setPrefWidth(150);
        
        // Add columns to table
        tableView.getColumns().addAll(idColumn, gesloColumn, stUporabColumn, uspesniColumn, 
                                       procentColumn, zahtevnostColumn, zadnjaColumn);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
    
    /**
     * Load data from database and populate table
     */
    private void loadDataFromDatabase() {
        ObservableList<GesloData> data = FXCollections.observableArrayList();
        
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = """
                SELECT id, geslo, st_uporab, uspesni_poskusi, zadnja_uporaba, ocenjena_zahtevnost,
                       CASE WHEN st_uporab > 0 THEN (uspesni_poskusi * 100.0 / st_uporab) ELSE 0 END as procent_uspesnosti
                FROM besede
                ORDER BY procent_uspesnosti DESC
                """;
            
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(sql);
                
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String geslo = rs.getString("geslo");
                    int stUporab = rs.getInt("st_uporab");
                    int uspesniPoskusi = rs.getInt("uspesni_poskusi");
                    double procentUspesnosti = rs.getDouble("procent_uspesnosti");
                    int zahtevnost = rs.getInt("ocenjena_zahtevnost");
                    
                    String zadnjaUporaba = "Nikoli";
                    Timestamp ts = rs.getTimestamp("zadnja_uporaba");
                    if (ts != null) {
                        zadnjaUporaba = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(ts);
                    }
                    
                    data.add(new GesloData(id, geslo, stUporab, uspesniPoskusi, 
                                          procentUspesnosti, zahtevnost, zadnjaUporaba));
                }
            }
            
            tableView.setItems(data);
            
        } catch (SQLException e) {
            showError("Napaka pri branju podatkov: " + e.getMessage());
        }
    }
    
    /**
     * Initialize database if it doesn't exist
     */
    private void initializeDatabase() {
        try {
            // Create database if it doesn't exist
            String createDbUrl = "jdbc:mysql://localhost:3306";
            try (Connection connTemp = DriverManager.getConnection(createDbUrl, DB_USER, DB_PASSWORD)) {
                String createDbSql = "CREATE DATABASE IF NOT EXISTS vislice";
                try (Statement stmt = connTemp.createStatement()) {
                    stmt.execute(createDbSql);
                }
            }
            
            // Create table if it doesn't exist
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String createTableSql = """
                    CREATE TABLE IF NOT EXISTS besede (
                        id INT PRIMARY KEY,
                        geslo VARCHAR(30) NOT NULL,
                        st_uporab INT NOT NULL DEFAULT 0,
                        zadnja_uporaba TIMESTAMP NULL,
                        uspesni_poskusi INT NOT NULL DEFAULT 0,
                        ocenjena_zahtevnost INT NOT NULL
                    )
                    """;
                
                try (Statement stmt = conn.createStatement()) {
                    stmt.execute(createTableSql);
                }
                
                // Check if table is empty and insert sample data if needed
                String checkSql = "SELECT COUNT(*) as cnt FROM besede";
                int rowCount = 0;
                try (Statement stmt = conn.createStatement()) {
                    ResultSet rs = stmt.executeQuery(checkSql);
                    if (rs.next()) {
                        rowCount = rs.getInt("cnt");
                    }
                }
                
                if (rowCount == 0) {
                    String insertSql = """
                        INSERT INTO besede (id, geslo, st_uporab, zadnja_uporaba, uspesni_poskusi, ocenjena_zahtevnost) 
                        VALUES 
                            (1, 'modro jutranje nebo', 5, FROM_UNIXTIME(1736630400), 3, 2),
                            (2, 'tiha zelena pot', 12, FROM_UNIXTIME(1738531200), 9, 3),
                            (3, 'stara lesena hiša', 0, NULL, 0, 1),
                            (4, 'miren gozdni oddih', 20, FROM_UNIXTIME(1734825600), 14, 4),
                            (5, 'sveža gorska voda', 3, FROM_UNIXTIME(1739145600), 1, 2),
                            (6, 'sončni travniški dan', 18, FROM_UNIXTIME(1738022400), 12, 5),
                            (7, 'tiho šumeče listje', 7, FROM_UNIXTIME(1732924800), 4, 1),
                            (8, 'mehka zimska odeja', 9, FROM_UNIXTIME(1736035200), 6, 3),
                            (9, 'topel domači kruh', 14, FROM_UNIXTIME(1729200000), 9, 4),
                            (10, 'mirno večerno jezero', 2, FROM_UNIXTIME(1735084800), 1, 1)
                        """;
                    
                    try (Statement stmt = conn.createStatement()) {
                        stmt.execute(insertSql);
                    }
                }
            }
            
        } catch (SQLException e) {
            showError("Napaka pri inicijalizaciji baze: " + e.getMessage());
        }
    }
    
    /**
     * Show error dialog
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Napaka");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * Inner class to represent password data
     */
    public static class GesloData {
        private final int id;
        private final String geslo;
        private final int stUporab;
        private final int uspesniPoskusi;
        private final double procentUspesnosti;
        private final int zahtevnost;
        private final String zadnjaUporaba;
        
        public GesloData(int id, String geslo, int stUporab, int uspesniPoskusi, 
                        double procentUspesnosti, int zahtevnost, String zadnjaUporaba) {
            this.id = id;
            this.geslo = geslo;
            this.stUporab = stUporab;
            this.uspesniPoskusi = uspesniPoskusi;
            this.procentUspesnosti = procentUspesnosti;
            this.zahtevnost = zahtevnost;
            this.zadnjaUporaba = zadnjaUporaba;
        }
        
        public int getId() { return id; }
        public String getGeslo() { return geslo; }
        public int getStUporab() { return stUporab; }
        public int getUspesniPoskusi() { return uspesniPoskusi; }
        public double getProcentUspesnosti() { return procentUspesnosti; }
        public int getZahtevnost() { return zahtevnost; }
        public String getZadnjaUporaba() { return zadnjaUporaba; }
    }
}
