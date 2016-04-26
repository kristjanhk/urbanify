package test;

import javafx.application.Application;
import javafx.beans.value.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.*;
import javafx.stage.Stage;

// demonstrates scaling a test pane with content in it.
// slide the slider at the bottom of the scene around to shrink and grow the content.
public class test4 extends Application {
    public static void main(String[] args) { launch(); }
    @Override public void start(Stage stage) throws Exception {
        // create a test pane for scaling.
        Pane testPane = new Pane();
        // make the test pane background a different color if you want to see the extent of the pane.
        testPane.setStyle("-fx-background-color: blue;");

        // create some text content to place in the test pane.
        Text text = new Text("Upper left corner");
        text.setStyle("-fx-font-size: 30px;");
        text.setFill(Color.WHITE);
        text.setTextOrigin(VPos.TOP);
        testPane.getChildren().add(text);
        Scale scaleTransform = new Scale();
        testPane.getTransforms().addAll(scaleTransform, new Translate(0, 0));

        // slider to scale the test pane.
        final Slider scaler = new Slider(.25, 3, 1);
        scaleTransform.xProperty().bind(scaler.valueProperty());
        scaleTransform.yProperty().bind(scaler.valueProperty());

        // stackpane added to pad out empty space when testPane is scaled small.
        // stackpane also clips the zoomed content when it gets larger than it's standard layout.
        final StackPane stack = new StackPane();
        stack.getChildren().addAll(testPane);
        StackPane.setAlignment(testPane, Pos.TOP_LEFT);
        stack.setStyle("-fx-background-color: blue;");

        final Rectangle clip = new Rectangle();
        testPane.layoutBoundsProperty().addListener(new ChangeListener<Bounds>() {
            @Override public void changed(ObservableValue<? extends Bounds> observable, Bounds oldBounds, Bounds bounds) {
                clip.setWidth(bounds.getWidth());
                clip.setHeight(bounds.getHeight());
            }
        });
        stack.setClip(clip);

        // layout everything.
        VBox layout = new VBox();
        layout.setPrefSize(250, 250);
        layout.getChildren().setAll(stack, scaler);
        VBox.setVgrow(stack, Priority.ALWAYS);

        // show the stage.
        stage.setScene(new Scene(layout));
        stage.show();
    }
}