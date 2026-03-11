/* Ob vsakem zaključku igranja naj se uporabniku na zaslon izpiše seznam podatkov v obliki

id, geslo, st_uporab, uspesni_poskusi, procent_uspešnosti

za vsa gesla v padajočem redu procenta uspešnosti. */

import java.sql.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class naloga3 {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/vislice";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    
    private String currentPassword;
    private StringBuilder guessedLetters;
    private int wrongGuesses;
    private int passwordId;
    private boolean gameOver;
    private int totalGuesses;
    
    public static void main(String[] args) {
        naloga3 game = new naloga3();
        game.initializeDatabase();
        game.playGame();
    }
    
    /**
     * Initializes the database and tables if they don't exist.
     */
    private void initializeDatabase() {
        try {
            // First, create database if it doesn't exist
            String createDbUrl = "jdbc:mysql://localhost:3306";
            try (Connection connTemp = DriverManager.getConnection(createDbUrl, DB_USER, DB_PASSWORD)) {
                String createDbSql = "CREATE DATABASE IF NOT EXISTS vislice";
                try (Statement stmt = connTemp.createStatement()) {
                    stmt.execute(createDbSql);
                    System.out.println("✓ Baza podatkov 'vislice' je bila preverjena/ustvarjena.");
                }
            }
            
            // Now connect to the specific database and create table
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                // Create table if it doesn't exist
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
                    System.out.println("✓ Tabela 'besede' je bila preverjena/ustvarjena.");
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
                        System.out.println("✓ Testni podatki so bili vstavljeni v bazo.");
                    }
                } else {
                    System.out.println("✓ Baza že vsebuje " + rowCount + " gesel.");
                }
            }
            
        } catch (SQLException e) {
            System.out.println("Napaka pri inicijalizaciji baze: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void playGame() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            System.out.println("\n=== VISLICE ===\n");
            
            // Get password length from user (with retry on invalid length)
            Scanner scanner = new Scanner(System.in);
            boolean passwordSelected = false;
            
            while (!passwordSelected) {
                System.out.print("Vnesi dolžino gesla: ");
                int passwordLength = scanner.nextInt();
                
                // Select password that hasn't been used for the longest time
                selectPasswordByLongestUnused(conn, passwordLength);
                
                if (currentPassword == null) {
                    System.out.println("Nobeno geslo te dolžine ni na voljo! Poskusi drugo dolžino.\n");
                    continue;
                }
                
                passwordSelected = true;
            }
            
            System.out.println("\nIgra se je začela! Geslo ima " + currentPassword.length() + " črk.");
            System.out.println("Prebodi se napakami. Ima te 6 poskusov.\n");
            
            // Initialize game state
            guessedLetters = new StringBuilder();
            wrongGuesses = 0;
            gameOver = false;
            totalGuesses = 0;
            
            // Game loop
            while (!gameOver && wrongGuesses < 6) {
                displayGameState();
                
                System.out.print("\nUgibaj črko: ");
                String input = scanner.next().toLowerCase();
                
                if (input.length() != 1) {
                    System.out.println("Vnesi samo eno črko!");
                    continue;
                }
                
                char letter = input.charAt(0);
                
                if (guessedLetters.toString().contains(String.valueOf(letter))) {
                    System.out.println("To črko si že ugibala!");
                    continue;
                }
                
                guessedLetters.append(letter);
                totalGuesses++;
                
                if (!currentPassword.contains(String.valueOf(letter))) {
                    wrongGuesses++;
                    System.out.println("Napačno! Izgubil si 1 poskus. Ostani ti " + (6 - wrongGuesses) + " poskusov.");
                } else {
                    System.out.println("Pravilno!");
                }
                
                // Check if word is complete
                if (isWordGuessed()) {
                    gameOver = true;
                    displayGameState();
                    System.out.println("\n✓ ZMAGAL SI! Geslo je bilo: " + currentPassword);
                    System.out.println("Poskusov: " + totalGuesses + " | Napake: " + wrongGuesses);
                    updateDatabaseAfterGame(conn, passwordId, true);
                } else if (wrongGuesses >= 6) {
                    gameOver = true;
                    System.out.println("\n✗ IZGUBIL SI! Geslo je bilo: " + currentPassword);
                    System.out.println("Poskusov: " + totalGuesses + " | Napake: " + wrongGuesses);
                    updateDatabaseAfterGame(conn, passwordId, false);
                }
            }
            
            // Display all passwords statistics sorted by success percentage
            displayAllPasswordsStatistics(conn);
            
            scanner.close();
            
        } catch (SQLException e) {
            System.out.println("Napaka pri povezavi na bazo: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Selects a password of the specified length that hasn't been used for the longest time.
     */
    private void selectPasswordByLongestUnused(Connection conn, int length) throws SQLException {
        String sql = "SELECT id, geslo FROM besede WHERE LENGTH(geslo) = ? ORDER BY zadnja_uporaba ASC LIMIT 1";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, length);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                passwordId = rs.getInt("id");
                currentPassword = rs.getString("geslo");
            }
        }
    }
    
    private void displayGameState() {
        StringBuilder display = new StringBuilder();
        
        for (char c : currentPassword.toCharArray()) {
            if (guessedLetters.toString().contains(String.valueOf(c))) {
                display.append(c);
            } else if (c == ' ') {
                display.append(' ');
            } else {
                display.append('_');
            }
            display.append(' ');
        }
        
        System.out.println("\nGeslo: " + display.toString());
        System.out.println("Ugibane črke: " + guessedLetters.toString());
        System.out.println("Napake: " + wrongGuesses + "/6");
    }
    
    private boolean isWordGuessed() {
        for (char c : currentPassword.toCharArray()) {
            if (c != ' ' && !guessedLetters.toString().contains(String.valueOf(c))) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Updates database after each game with comprehensive statistics.
     * Updates: zadnja_uporaba (last used), st_uporab (total uses), uspesni_poskusi (successful attempts)
     */
    private void updateDatabaseAfterGame(Connection conn, int id, boolean success) throws SQLException {
        String sql = """
            UPDATE besede 
            SET zadnja_uporaba = NOW(), 
                st_uporab = st_uporab + 1, 
                uspesni_poskusi = uspesni_poskusi + ?
            WHERE id = ?
            """;
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, success ? 1 : 0);
            ps.setInt(2, id);
            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("\n✓ Baza podatkov je bila posodobljena.");
            }
        }
    }
    
    /**
     * Displays all passwords with their statistics sorted by success percentage (descending).
     * Format: id, geslo, st_uporab, uspesni_poskusi, procent_uspešnosti
     */
    private void displayAllPasswordsStatistics(Connection conn) throws SQLException {
        String sql = """
            SELECT id, geslo, st_uporab, uspesni_poskusi,
                   CASE WHEN st_uporab > 0 THEN (uspesni_poskusi * 100.0 / st_uporab) ELSE 0 END as procent_uspesnosti
            FROM besede
            ORDER BY procent_uspesnosti DESC
            """;
        
        System.out.println("\n" + "=".repeat(90));
        System.out.println("STATISTIKA VSEH GESEL");
        System.out.println("=".repeat(90));
        System.out.printf("%-3s | %-25s | %-10s | %-10s | %-15s%n", "ID", "GESLO", "UPORAB", "USPEŠNI", "% USPEŠNOSTI");
        System.out.println("-".repeat(90));
        
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            
            int rowCount = 0;
            while (rs.next()) {
                int id = rs.getInt("id");
                String geslo = rs.getString("geslo");
                int stUporab = rs.getInt("st_uporab");
                int uspesniPoskusi = rs.getInt("uspesni_poskusi");
                double procent = rs.getDouble("procent_uspesnosti");
                
                System.out.printf("%-3d | %-25s | %-10d | %-10d | %14.2f%%%n", 
                    id, geslo, stUporab, uspesniPoskusi, procent);
                rowCount++;
            }
            
            System.out.println("=".repeat(90));
            System.out.println("Skupno gesel: " + rowCount);
            System.out.println("");
        }
    }
}
