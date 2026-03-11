/* Avtomatizirajte proces iz naloge tri: recimo, da vsakih 30sekund izvedete preklop med vsebino centralnega dela in zadnjim elementov s spiska z desne strani. 
Pri tem naj se spisek na desni strani obnaša kot vrsta; za prikaz v centralnem delu vedno vzamete spodnjega, iz centralnega dela vedno umikate vsebino na
mesto zgornjega elementa. Ohranite interaktivnost iz naloge 3. */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.web.WebView;
import javafx.application.Platform;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class naloga4 extends Application {
    private WebView mainView;
    private javafx.scene.layout.VBox rightPanel;
    private java.util.List<String> urlList;
    private String currentMainUrl;
    private ScheduledExecutorService scheduler;

    @Override
    public void start(Stage stage) {
        javafx.scene.layout.HBox root = new javafx.scene.layout.HBox();
        root.setPrefSize(1200, 675);

        // Main camera view (left side - 3/4 width)
        mainView = new WebView();
        currentMainUrl = "https://kamere.dars.si/kamere/Vrhnika/CP_Vrhnika_Panorama_izhod.JPG";
        mainView.getEngine().load(currentMainUrl);
        mainView.setPrefSize(900, 675);
        javafx.scene.layout.HBox.setHgrow(mainView, javafx.scene.layout.Priority.NEVER);

        // Right side panel (1/4 width) with 5 cameras stacked vertically
        rightPanel = new javafx.scene.layout.VBox();
        rightPanel.setStyle("-fx-border-color: #333333; -fx-padding: 5;");
        rightPanel.setPrefSize(300, 675);
        rightPanel.setSpacing(0);
        javafx.scene.layout.HBox.setHgrow(rightPanel, javafx.scene.layout.Priority.NEVER);

        String[] urls = {
            "https://www.hribi.net/kamere/kamnisko_sedlo_2326.jpg",
            "https://kamere.dars.si/kamere/drsi_vgrc/VrhnikaMan2_Vrm2_0001.jpg",
            "https://kamere.dars.si/kamere/drsi_vgrc/VrhnikaMan1_Vrm1_0001.jpg",
            "https://meteo.arso.gov.si/uploads/probase/www/observ/webcam/KREDA-ICA_dir/siwc_KREDA-ICA_e.jpg",
            "https://www.drsc.si/kamere/Bohinjska/Boh1_0001.jpg"
        };

        urlList = new java.util.ArrayList<>(java.util.Arrays.asList(urls));

        buildRightPanel();

        root.getChildren().addAll(mainView, rightPanel);

        Scene scene = new Scene(root, 1200, 675);
        stage.setScene(scene);
        stage.show();

        // Start automatic swapping every 30 seconds
        startAutomaticSwap();

        // Stop scheduler on window close
        stage.setOnCloseRequest(event -> {
            scheduler.shutdown();
        });
    }

    private void buildRightPanel() {
        rightPanel.getChildren().clear();
        double cellHeight = 675.0 / urlList.size();
        
        for (int i = 0; i < urlList.size(); i++) {
            String url = urlList.get(i);
            WebView wv = new WebView();
            wv.getEngine().load(url);
            wv.setPrefSize(300, cellHeight);
            wv.setMinSize(300, cellHeight);
            wv.setMaxSize(300, cellHeight);
            wv.setStyle("-fx-border-color: #cccccc; -fx-cursor: hand;");
            javafx.scene.layout.VBox.setVgrow(wv, javafx.scene.layout.Priority.NEVER);

            final int index = i;
            wv.setOnMouseClicked(event -> {
                // Manual click interaction - swap with clicked element
                String clickedUrl = urlList.get(index);
                String previousMainUrl = currentMainUrl;

                currentMainUrl = clickedUrl;
                mainView.getEngine().load(currentMainUrl);

                urlList.remove(index);
                urlList.add(0, previousMainUrl);

                buildRightPanel();
            });

            rightPanel.getChildren().add(wv);
        }
    }

    private void startAutomaticSwap() {
        scheduler = Executors.newScheduledThreadPool(1);
        
        scheduler.scheduleAtFixedRate(() -> {
            // Run on JavaFX thread
            Platform.runLater(() -> {
                if (urlList.size() > 0) {
                    // Take the last element (bottom of queue)
                    int lastIndex = urlList.size() - 1;
                    String lastUrl = urlList.get(lastIndex);
                    String previousMainUrl = currentMainUrl;

                    // Move last element to main view
                    currentMainUrl = lastUrl;
                    mainView.getEngine().load(currentMainUrl);

                    // Move previous main to top (front of queue)
                    urlList.remove(lastIndex);
                    urlList.add(0, previousMainUrl);

                    // Rebuild the right panel
                    buildRightPanel();
                }
            });
        }, 5, 5, TimeUnit.SECONDS);
    }

    public static void main(String[] args) {
        Application.launch(naloga4.class, args);
    }
}

