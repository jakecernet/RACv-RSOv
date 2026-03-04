/* JavaFX aplikacija je namenjena nadzornemu sistemu, pri čemer v aplikaciji spremljamo 'sliko' z večih spletnih podatkovnih tokov 
(npr. s šestih spletnih/IP kamer). Vsak od teh tokov ima svoj lasten spletni naslov. Vizualizacija področje okna aplikacije 
horizontalno razdeli na dva dela: levega v širini ¾ okna in desnega v velikosti ¼ širine okna. Desni je nadalje vertikalno razdeljen 
na dimenzijsko enake pravokotnike razsežnosti v razmerju 16:9 (recimo, da jih je 4 ali 5). Vsak od definiranih naj prikazuje 
poljubno izbrano (in različno) 'sliko'. */
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebView;

public class naloga2 extends Application {
    @Override
    public void start(javafx.stage.Stage stage) {
        javafx.scene.layout.HBox root = new javafx.scene.layout.HBox();
        root.setPrefSize(1200, 675);

        // Main camera view (left side - 3/4 width)
        WebView mainView = new WebView();
        mainView.getEngine().load("https://www.hribi.net/kamere/kamnisko_sedlo_2326.jpg");
        mainView.setPrefSize(900, 675);
        javafx.scene.layout.HBox.setHgrow(mainView, javafx.scene.layout.Priority.NEVER);

        // Right side panel (1/4 width) with 5 cameras stacked vertically
        javafx.scene.layout.VBox rightPanel = new javafx.scene.layout.VBox();
        rightPanel.setPrefSize(300, 675);
        javafx.scene.layout.HBox.setHgrow(rightPanel, javafx.scene.layout.Priority.NEVER);

        String[] urls = {
            "https://kamere.dars.si/kamere/Vrhnika/CP_Vrhnika_Panorama_izhod.JPG",
            "https://kamere.dars.si/kamere/drsi_vgrc/VrhnikaMan2_Vrm2_0001.jpg",
            "https://kamere.dars.si/kamere/drsi_vgrc/VrhnikaMan1_Vrm1_0001.jpg",
            "https://meteo.arso.gov.si/uploads/probase/www/observ/webcam/KREDA-ICA_dir/siwc_KREDA-ICA_e.jpg",
            "https://www.drsc.si/kamere/Bohinjska/Boh1_0001.jpg"
        };

        for (String url : urls) {
            WebView wv = new WebView();
            wv.getEngine().load(url);
            // 16:9 ratio: width=300, height=300*(9/16)=~168, 5 views fit in 675 (5*135=675)
            wv.setPrefSize(300, 135);
            wv.setMaxSize(300, 135);
            javafx.scene.layout.VBox.setVgrow(wv, javafx.scene.layout.Priority.NEVER);
            rightPanel.getChildren().add(wv);
        }

        root.getChildren().addAll(mainView, rightPanel);

        Scene scene = new Scene(root, 1200, 675);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(naloga2.class, args);
    }
}