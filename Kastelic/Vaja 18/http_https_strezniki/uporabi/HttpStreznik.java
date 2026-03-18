package uporabi;

import com.sun.net.httpserver.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class HttpStreznik {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/", exchange -> {
            String resp = "Pozdravček! Nam je fajn";
            exchange.sendResponseHeaders(200, resp.length());    //200 .. OK
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(resp.getBytes());                        // pošlje odgovor
            }
        });
        server.start();
        System.out.println("Strežnik: http://localhost:8000");
    }
}