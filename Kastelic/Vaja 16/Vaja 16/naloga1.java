/* Spišite javansko aplikacijo, ki bo omogočala vizualizacijo poljubne spletne strani. Uporabite komponento/class WebView. */
import javafx.scene.Scene;
import javafx.application.Application;
import javafx.scene.web.WebView;

public class naloga1 extends Application {
    @Override
    public void start(javafx.stage.Stage stage) {
        WebView webView = new WebView();
        webView.getEngine().load("https://cernetic.cc/");

        Scene scene = new Scene(webView, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(naloga1.class, args);
    }
}