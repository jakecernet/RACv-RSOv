import java.sql.*;
import java.io.*;
import java.nio.file.*;

/**
 * Naloga 1: Ponastavitev podatkovne zbirke glasovanja
 * 
 * Program prebere SQL datoteko glasovanja.sql in izvede ukaze za
 * ustvarjanje/ponastavitev podatkovne zbirke.
 * 
 * Tehnologija: MySQL (MariaDB) z JDBC gonilnikom.
 * Razlog izbire: MySQL/MariaDB je široko podprt, odprtokoden RDBMS,
 * primeren za učne namene. JDBC gonilnik omogoča enostavno povezavo iz Jave.
 */
public class naloga1 {

    // Nastavitve povezave - prilagodi glede na svojo konfiguracijo
    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";

    public static void main(String[] args) {
        System.out.println("=== Ponastavitev podatkovne zbirke 'glasovanja' ===\n");

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                Statement stmt = conn.createStatement()) {

            // 1. Brisanje obstoječe baze (če obstaja) in ustvarjanje nove
            System.out.println("1. Brisanje obstoječe baze (če obstaja)...");
            stmt.executeUpdate("DROP DATABASE IF EXISTS glasovanja");

            System.out.println("2. Ustvarjanje nove baze 'glasovanja'...");
            stmt.executeUpdate("CREATE DATABASE glasovanja");
            stmt.executeUpdate("USE glasovanja");

            // 2. Ustvarjanje tabele poll
            System.out.println("3. Ustvarjanje tabele 'poll'...");
            stmt.executeUpdate("""
                        CREATE TABLE `poll` (
                            `id` tinyint DEFAULT NULL,
                            `vprasanje` varchar(28) DEFAULT NULL,
                            `moznosti` varchar(100) DEFAULT NULL,
                            `openedAt` datetime NOT NULL DEFAULT current_timestamp,
                            `closedAt` datetime NOT NULL DEFAULT current_timestamp
                        ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_slovenian_ci ROW_FORMAT = DYNAMIC
                    """);

            // 3. Vnos podatkov v tabelo poll
            System.out.println("4. Vnos podatkov v tabelo 'poll'...");
            stmt.executeUpdate(
                    """
                                INSERT INTO `poll` VALUES
                                (1, 'Poznaš mehotovega sestriča?', '["da","ne","kaj te briga"]', '1970-01-21', '1970-01-21'),
                                (2, 'Pada zunaj dež?', '["da","ne"]', '1970-01-20', '1970-01-20'),
                                (3, 'Prestavimo današnji test?', '["da","niti pod razno"]', '1970-01-21', '1970-01-21'),
                                (4, 'Kdo je ubil Janeza?', '["cia","nsa","meho","gavrilo"]', '1970-01-21', '1970-01-21'),
                                (5, 'Najvišja gora na Balkanu je:', '["everest","k2","mckinley/denali","Alahova gora"]', '1970-01-21', '1970-01-21'),
                                (6, 'Kateri dan bi pisali test?', '["pon","tor","čet","sob"]', '1970-01-20', '1970-01-21')
                            """);

            // 4. Ustvarjanje tabele pollanswer
            System.out.println("5. Ustvarjanje tabele 'pollanswer'...");
            stmt.executeUpdate("""
                        CREATE TABLE `pollanswer` (
                            `id` tinyint DEFAULT NULL,
                            `poll_id` tinyint DEFAULT NULL,
                            `user` varchar(6) DEFAULT NULL,
                            `answer` varchar(30) DEFAULT NULL,
                            `answeredAt` datetime NOT NULL DEFAULT current_timestamp
                        ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_slovenian_ci ROW_FORMAT = DYNAMIC
                    """);

            // 5. Vnos podatkov v tabelo pollanswer
            System.out.println("6. Vnos podatkov v tabelo 'pollanswer'...");
            stmt.executeUpdate("""
                        INSERT INTO `pollanswer` VALUES
                        (1, 1, 'jovo', 'da', '1970-01-21'),
                        (2, 1, 'meho', 'da', '1970-01-21'),
                        (3, 1, 'bob', 'da', '1970-01-21'),
                        (4, 1, 'jagoda', 'da', '1970-01-21'),
                        (5, 1, 'matic', 'da', '1970-01-21'),
                        (6, 2, 'meho', 'da', '1970-01-20'),
                        (7, 2, 'matic', 'da', '1970-01-20'),
                        (8, 2, 'bili', 'ne', '1970-01-20'),
                        (9, 2, 'jan', 'da', '1970-01-20'),
                        (10, 3, 'tim', 'niti pod razno', '1970-01-21'),
                        (11, 3, 'jovo', 'da', '1970-01-21'),
                        (12, 3, 'jan', 'da', '1970-01-21'),
                        (13, 3, 'roža', 'niti pod razno', '1970-01-21'),
                        (14, 3, 'zana', 'da', '1970-01-21'),
                        (15, 3, 'matic', 'niti pod razno', '1970-01-21'),
                        (16, 4, 'stane', 'gavrilo', '1970-01-21'),
                        (17, 4, 'roža', 'gavrilo', '1970-01-21'),
                        (18, 4, 'bob', 'cia', '1970-01-21'),
                        (19, 4, 'tim', 'meho', '1970-01-21'),
                        (20, 4, 'zana', 'cia', '1970-01-21'),
                        (21, 5, 'bob', 'k2', '1970-01-21'),
                        (22, 5, 'zana', 'k2', '1970-01-21'),
                        (23, 5, 'meho', 'everest', '1970-01-21'),
                        (24, 5, 'tim', 'Alahova gora', '1970-01-21'),
                        (25, 5, 'jagoda', 'mckinley/denali', '1970-01-21'),
                        (26, 5, 'cilka', 'mckinley/denali', '1970-01-21'),
                        (27, 6, 'tim', 'pon', '1970-01-20'),
                        (28, 6, 'jovo', 'čet', '1970-01-20'),
                        (29, 6, 'jagoda', 'čet', '1970-01-20'),
                        (30, 6, 'bob', 'pon', '1970-01-20')
                    """);

            // 6. Preverimo vsebino
            System.out.println("\n=== Vsebina tabele 'poll' ===");
            ResultSet rs = stmt.executeQuery("SELECT * FROM glasovanja.poll");
            while (rs.next()) {
                System.out.printf("ID: %d | Vprašanje: %-30s | Odprto: %s | Zaprto: %s%n",
                        rs.getInt("id"),
                        rs.getString("vprasanje"),
                        rs.getString("openedAt"),
                        rs.getString("closedAt"));
            }

            System.out.println("\n=== Vsebina tabele 'pollanswer' ===");
            rs = stmt.executeQuery("SELECT * FROM glasovanja.pollanswer");
            while (rs.next()) {
                System.out.printf("ID: %2d | Poll: %d | Uporabnik: %-6s | Odgovor: %-15s | Čas: %s%n",
                        rs.getInt("id"),
                        rs.getInt("poll_id"),
                        rs.getString("user"),
                        rs.getString("answer"),
                        rs.getString("answeredAt"));
            }

            System.out.println("\n✓ Podatkovna zbirka 'glasovanja' uspešno ponastavljena!");

        } catch (SQLException e) {
            System.err.println("Napaka pri delu z bazo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
