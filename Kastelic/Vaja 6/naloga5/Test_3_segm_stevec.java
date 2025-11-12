
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

        HBox digits = new HBox(10);
        digits.getChildren().addAll(s2.getView(), s1.getView(), s.getView());

        // control buttons
        javafx.scene.control.Button startUp = new javafx.scene.control.Button("StartUP");
        javafx.scene.control.Button startDown = new javafx.scene.control.Button("StartDown");
        javafx.scene.control.Button stop = new javafx.scene.control.Button("Stop");
        javafx.scene.control.Button pulse = new javafx.scene.control.Button("Pulse");

        HBox controls = new HBox(8, startUp, startDown, stop, pulse);
        controls.setAlignment(javafx.geometry.Pos.CENTER);

        javafx.scene.layout.VBox root = new javafx.scene.layout.VBox(12, digits, controls);
        root.setAlignment(javafx.geometry.Pos.CENTER);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("3-segment counter demo");
        primaryStage.show();

        // ticker emits a tick every 1 second; initially stopped
        final Timeline[] ticker = new Timeline[1];
        final boolean[] modeUp = { false };
        final boolean[] modeDown = { false };

        Runnable tickAction = () -> {
            if (modeUp[0]) {
                try {
                    s.up();
                } catch (MOverFlowException e) {
                    s.overflow();
                }
            } else if (modeDown[0]) {
                s.down();
            }
        };

        ticker[0] = new Timeline(new KeyFrame(Duration.seconds(1), ev -> tickAction.run()));
        ticker[0].setCycleCount(Timeline.INDEFINITE);

        startUp.setOnAction(ev -> {
            s.setValue(0);
            s1.setValue(0);
            s2.setValue(0);
            modeUp[0] = true;
            modeDown[0] = false;
            if (ticker[0].getStatus() != Timeline.Status.RUNNING)
                ticker[0].play();
        });

        startDown.setOnAction(ev -> {
            s.setValue(9);
            s1.setValue(9);
            s2.setValue(9);
            modeUp[0] = false;
            modeDown[0] = true;
            if (ticker[0].getStatus() != Timeline.Status.RUNNING)
                ticker[0].play();
        });

        stop.setOnAction(ev -> {
            modeUp[0] = false;
            modeDown[0] = false;
            ticker[0].stop();
        });

        pulse.setOnAction(ev -> {
            if (!modeUp[0] && !modeDown[0]) {
                try {
                    s.up();
                } catch (MOverFlowException e) {
                    s.overflow();
                }
            } else {
                tickAction.run();
            }
        });
    }
}
