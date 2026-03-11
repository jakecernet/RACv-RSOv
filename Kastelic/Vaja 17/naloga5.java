/* Vizualizirajte izvajanje ugibanja besede. Predlagam, da nad TableView in naloge 4 dodate GridView v katerega horizontalno dodate toliko gumbov, 
kot je dolgo ugibano geslo. Vsak izmed gumbov naj predstavlja eno izmed črk gesla. Uporabo dogodkov s tipkovnice demonstrira primer iz vira: 
https://docs.oracle.com/javafx/2/events/KeyboardExample.java.htm (mar. 2026). Najlaže vam bo, če po vsakem dogodku tipkovnice gumbe na novo prepišete
z morebitnimi spremembami vrednosti */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.event.KeyEvent;

import java.sql.*;

public class naloga5 extends Application {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/vislice";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    
    private GridPane letterGrid;
    private String currentPassword;
    private StringBuilder guessedLetters;
    private int wrongGuesses;
    private int passwordId;
    private Label gameStatusLabel;
    private Label wrongGuessesLabel;
    
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
        Label title = new Label("VISLICE");
        title.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
        
        // Game section
        VBox gameSection = createGameSection();
        
        // Set focus to root for keyboard events
        root.setFocusTraversable(true);
        root.setOnKeyPressed(this::handleKeyPress);
        
        // Add sections to root
        root.getChildren().addAll(title, gameSection);
        
        // Create scene
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
        Scene scene = new Scene(scrollPane, 1000, 800);
        
        // Set stage
        stage.setTitle("Vislice - Vizualizacija igre");
        stage.setScene(scene);
        stage.show();
        
        // Request focus for keyboard input
        root.requestFocus();
    }
    
    /**
     * Create game section with game board and controls
     */
    private VBox createGameSection() {
        VBox gameBox = new VBox();
        gameBox.setStyle("-fx-border-color: #cccccc; -fx-padding: 15; -fx-border-radius: 5;");
        gameBox.setSpacing(15);
        
        // Game info
        HBox infoBox = new HBox();
        infoBox.setSpacing(30);
        
        gameStatusLabel = new Label("Izberi geslo...");
        gameStatusLabel.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");
        
        wrongGuessesLabel = new Label("Napake: 0/6");
        wrongGuessesLabel.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");
        
        infoBox.getChildren().addAll(gameStatusLabel, wrongGuessesLabel);
        
        // Letter grid for displaying the password
        letterGrid = new GridPane();
        letterGrid.setHgap(8);
        letterGrid.setAlignment(Pos.CENTER);
        
        // Start new game button
        Button newGameButton = new Button("Nova igra");
        newGameButton.setStyle("-fx-padding: 8; -fx-font-size: 12;");
        newGameButton.setOnAction(event -> startNewGame());
        
        // Guessed letters display
        Label guessedLabel = new Label("Ugibane črke:");
        guessedLabel.setStyle("-fx-font-size: 12; -fx-font-weight: bold;");
        
        Label guessedLettersDisplay = new Label("");
        guessedLettersDisplay.setStyle("-fx-font-size: 12; -fx-font-weight: normal; -fx-text-fill: #0066cc;");
        guessedLettersDisplay.setId("guessedDisplay");
        
        HBox guessedBox = new HBox();
        guessedBox.setSpacing(10);
        guessedBox.getChildren().addAll(guessedLabel, guessedLettersDisplay);
        
        gameBox.getChildren().addAll(infoBox, letterGrid, guessedBox, newGameButton);
        return gameBox;
    }
    
    /**
     * Start a new game
     */
    private void startNewGame() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // For simplicity, select a random password between 10-20 characters
            String sql = "SELECT id, geslo FROM besede WHERE LENGTH(geslo) >= 10 AND LENGTH(geslo) <= 20 ORDER BY RAND() LIMIT 1";
            
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(sql);
                
                if (rs.next()) {
                    passwordId = rs.getInt("id");
                    currentPassword = rs.getString("geslo");
                    guessedLetters = new StringBuilder();
                    wrongGuesses = 0;
                    
                    displayGame();
                    gameStatusLabel.setText("Igra se je začela! Dolžina gesla: " + currentPassword.length());
                    wrongGuessesLabel.setText("Napake: 0/6");
                    
                    // Request focus for keyboard input
                    letterGrid.getParent().requestFocus();
                } else {
                    gameStatusLabel.setText("Ni dovolj gesel v bazi!");
                }
            }
            
        } catch (SQLException e) {
            gameStatusLabel.setText("Napaka: " + e.getMessage());
        }
    }
    
    /**
     * Display the current game state
     */
    private void displayGame() {
        if (currentPassword == null) {
            return;
        }
        
        letterGrid.getChildren().clear();
        
        int col = 0;
        for (char c : currentPassword.toCharArray()) {
            if (c == ' ') {
                // Space - show empty button
                Button spaceButton = new Button(" ");
                spaceButton.setStyle("-fx-padding: 10; -fx-font-size: 14; -fx-min-width: 40; -fx-min-height: 40;");
                spaceButton.setDisable(true);
                letterGrid.add(spaceButton, col, 0);
            } else {
                // Letter - show either guessed or underscore
                String display;
                if (guessedLetters.toString().contains(String.valueOf(c).toLowerCase())) {
                    display = String.valueOf(c).toUpperCase();
                } else {
                    display = "_";
                }
                
                Button letterButton = new Button(display);
                letterButton.setStyle("-fx-padding: 10; -fx-font-size: 14; -fx-min-width: 40; -fx-min-height: 40; " +
                    (display.equals("_") ? "-fx-background-color: #ffcccc;" : "-fx-background-color: #ccffcc;"));
                letterButton.setDisable(true);
                letterGrid.add(letterButton, col, 0);
            }
            col++;
        }
        
        // Update guessed letters display
        Label guessedDisplay = (Label) letterGrid.getParent().lookup("#guessedDisplay");
        if (guessedDisplay != null) {
            guessedDisplay.setText(guessedLetters.toString().toUpperCase());
        }
    }
    
    /**
     * Handle keyboard input
     */
    private void handleKeyPress(KeyEvent event) {
        if (currentPassword == null) {
            event.consume();
            return;
        }
        
        String character = event.getText();
        
        // Check if it's a letter
        if (character.length() == 1 && Character.isLetter(character.charAt(0))) {
            char letter = Character.toLowerCase(character.charAt(0));
            String letterStr = String.valueOf(letter);
            
            // Check if already guessed
            if (guessedLetters.toString().contains(letterStr)) {
                gameStatusLabel.setText("Črka '" + letter + "' je že bila ugibana!");
                event.consume();
                return;
            }
            
            // Add to guessed letters
            guessedLetters.append(letterStr);
            
            // Check if correct
            if (!currentPassword.toLowerCase().contains(letterStr)) {
                wrongGuesses++;
                gameStatusLabel.setText("Narobe! Črka '" + letter + "' ni v besedi.");
            } else {
                gameStatusLabel.setText("Pravilno! Črka '" + letter + "' je v besedi.");
            }
            
            // Update display
            wrongGuessesLabel.setText("Napake: " + wrongGuesses + "/6");
            displayGame();
            
            // Check win/loss
            if (isWordGuessed()) {
                gameStatusLabel.setText("✓ ZMAGAL SI! Geslo je: " + currentPassword);
                updateDatabaseAfterGame(true);
            } else if (wrongGuesses >= 6) {
                gameStatusLabel.setText("✗ IZGUBIL SI! Geslo je bilo: " + currentPassword);
                updateDatabaseAfterGame(false);
            }
            
            event.consume();
        }
    }
    
    /**
     * Check if word is guessed
     */
    private boolean isWordGuessed() {
        for (char c : currentPassword.toCharArray()) {
            if (c != ' ' && !guessedLetters.toString().contains(String.valueOf(c).toLowerCase())) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Update database after game
     */
    private void updateDatabaseAfterGame(boolean success) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = """
                UPDATE besede 
                SET zadnja_uporaba = NOW(), 
                    st_uporab = st_uporab + 1, 
                    uspesni_poskusi = uspesni_poskusi + ?
                WHERE id = ?
                """;
            
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, success ? 1 : 0);
                ps.setInt(2, passwordId);
                ps.executeUpdate();
            }
            
        } catch (SQLException e) {
            gameStatusLabel.setText("Napaka pri posodabljanju baze: " + e.getMessage());
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
            // Silently handle error
        }
    }
}
