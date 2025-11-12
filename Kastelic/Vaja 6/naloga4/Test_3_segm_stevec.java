
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Test_3_segm_stevec extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Segment s = new Segment();
        Segment s1 = new Segment(s);
        s.nasl = s1;
        Segment s2 = new Segment(s1);
        s1.nasl = s2;

        HBox root = new HBox(10);
        root.getChildren().addAll(s2.getView(), s1.getView(), s.getView());

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("3-segment counter demo");
        primaryStage.show();

        // animate counting up
        Timeline t = new Timeline(new KeyFrame(Duration.millis(100), ev -> {
            try {
                s.up();
            } catch (MOverFlowException e) {
                s.overflow();
            }
        }));
        t.setCycleCount(999);
        t.play();
    }
}
