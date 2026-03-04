/* Avtomatizirajte proces iz naloge tri: recimo, da vsakih 30sekund izvedete preklop med vsebino centralnega dela in zadnjim elementov s spiska z desne strani. 
Pri tem naj se spisek na desni strani obnaša kot vrsta; za prikaz v centralnem delu vedno vzamete spodnjega, iz centralnega dela vedno umikate vsebino na
mesto zgornjega elementa. Ohranite interaktivnost iz naloge 3. */
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.web.WebView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class naloga4 extends Application {
    private WebView mainView;
    private javafx.scene.layout.VBox rightPanel;
    private java.util.List<String> urlList;
    private String currentMainUrl;
    private Timeline autoSwitchTimeline;

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

        setupAutoSwitch();
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
            javafx.scene.layout.VBox.setVgrow(wv, javafx.scene.layout.Priority.NEVER);
            rightPanel.getChildren().add(wv);
        }
    }

    private void setupAutoSwitch() {
        autoSwitchTimeline = new Timeline(new KeyFrame(Duration.seconds(30), e -> switchMainView()));
        autoSwitchTimeline.setCycleCount(Timeline.INDEFINITE);
        autoSwitchTimeline.play();
    }

    private void switchMainView() {
        if (urlList.isEmpty()) return;

        // Take the last URL from the list to display in the main view
        String nextUrl = urlList.remove(urlList.size() - 1);
        String previousMainUrl = currentMainUrl;

        currentMainUrl = nextUrl;
        mainView.getEngine().load(currentMainUrl);

        // Add the previous main URL back to the front of the list
        urlList.add(0, previousMainUrl);

        // Rebuild the right panel to reflect the new order
        buildRightPanel();
    }

    public static void main(String[] args) {
        Application.launch(naloga4.class, args);
    }
}