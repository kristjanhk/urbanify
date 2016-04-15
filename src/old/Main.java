package old;

import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Group group = new Group();

        Rectangle rectangle = new Rectangle(100, 100, 100, 100);
        rectangle.setFill(Color.INDIANRED);
        group.getChildren().add(rectangle);

        RotateTransition rotateTransition = new RotateTransition(Duration.millis(150), rectangle);
        rotateTransition.setByAngle(1081);
        rotateTransition.setCycleCount(Timeline.INDEFINITE);

        Scene scene = new Scene(group, 300, 300, Color.SNOW);
        primaryStage.setTitle("Piletisüsteem");
        primaryStage.setScene(scene);
        primaryStage.show();
        rotateTransition.play();
    }
}
