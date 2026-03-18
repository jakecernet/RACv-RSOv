import com.sun.net.httpserver.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class HttpStreznik_popravljena {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        // DODAJ TOLE: Ustvari bazen niti, da strežnik ne bo odvisen od main niti
        server.setExecutor(Executors.newCachedThreadPool());

        // Kontekst 1: /
        server.createContext("/", exchange -> {
            String resp = "Pozdravček! Nam je fajn";
            byte[] bytes = resp.getBytes("UTF-8");
            exchange.sendResponseHeaders(200, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        });

        // Kontekst 2: /pozdravi
        server.createContext("/pozdravi", exchange -> {
            String resp = "ojlala, pozdravček";
            byte[] bytes = resp.getBytes("UTF-8");
            exchange.sendResponseHeaders(200, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        });

        // Kontekst 3: /odjavi
        server.createContext("/odjavi", exchange -> {
            String resp = "{\"datum\":\"danes\",\"odjava\":\"sedajle\",\"stevec\":122}";
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            byte[] bytes = resp.getBytes("UTF-8");
            exchange.sendResponseHeaders(200, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        });

        server.start();
        System.out.println("Strežnik teče na: http://localhost:8000");
        System.out.println("Dostopni endpointi:");
        System.out.println("  http://localhost:8000/");
        System.out.println("  http://localhost:8000/pozdravi");
        System.out.println("  http://localhost:8000/odjavi");
        System.out.println("Pritisni CTRL+C za izklop.");

        // DODAJ TOLE: Prepreči takojšen izhod iz programa
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            System.out.println("Strežnik ustavljen.");
        }
    }
}
