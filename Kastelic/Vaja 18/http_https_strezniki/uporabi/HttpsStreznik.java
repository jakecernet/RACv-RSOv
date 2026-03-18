package uporabi;

import com.sun.net.httpserver.*;
import javax.net.ssl.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.security.*;
import java.util.concurrent.Executors;

public class HttpsStreznik {
    public static void main(String[] args) {
        try {
            // 1. Nastavi vrata (uporabljam tvojih 6443)
            HttpsServer server = HttpsServer.create(new InetSocketAddress(5443), 0);
            
            // 2. SSL Konfiguracija
            SSLContext sslContext = SSLContext.getInstance("TLS");
            char[] password = "mojeGeslo".toCharArray();
            KeyStore ks = KeyStore.getInstance("JKS");
            
            try (FileInputStream fis = new FileInputStream("keystore/mojKeystore1.jks")) {
                ks.load(fis, password);
            }

            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(ks, password);
            sslContext.init(kmf.getKeyManagers(), null, null);

            // 3. Poveži HTTPS konfiguracijo s strežnikom
            server.setHttpsConfigurator(new HttpsConfigurator(sslContext));

            // 4. DODAJ: Upravljalnik niti (Executor), da strežnik dejansko "živi"
            server.setExecutor(Executors.newCachedThreadPool());

            server.createContext("/", exchange -> {
                String resp = "Varna povezava iz Jave!";
                byte[] bytes = resp.getBytes();
                exchange.sendResponseHeaders(200, bytes.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(bytes);
                }
            });

            server.start();
            System.out.println("Varen strežnik teče na: https://localhost:6443");
            System.out.println("Za zaustavitev pritisni CTRL+C.");

            // 5. DODAJ: Sidro, ki drži glavno nit budno
            Thread.currentThread().join();

        } catch (Exception e) {
            System.err.println("Napaka pri zagonu: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
