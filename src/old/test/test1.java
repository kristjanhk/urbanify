package old.test;
import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javafx.stage.Stage;

public class test1 extends Application {

    private static double difference = 0.0;

    @Override
    public void start(final Stage stage) throws Exception {
        stage.setTitle(getClass().getSimpleName());
        //scene - stackpane(root) - group(group) - group1(row) - children(items)
        Group group = new Group();
        StackPane root = new StackPane(group);
        root.setAlignment(Pos.BOTTOM_CENTER);
        StackPane.setMargin(group, new Insets(10));
        Scene scene = new Scene(root, 1000, 1000);
        stage.setScene(scene);
        stage.show();

        scene.setOnScroll(event1 -> {
            event1.consume();
            if (event1.getDeltaY() == 0) {
                return;
            }
            double scaleFactor = event1.getDeltaY() > 0 ? 1.1 : 1/1.1;
            double maxy = group.getBoundsInParent().getMaxY();
            System.out.println(group.getBoundsInLocal());
            System.out.println(group.getBoundsInParent());
            System.out.println(group.getScaleX() + ", " + scaleFactor);
            group.setScaleX(group.getScaleX() * scaleFactor);
            group.setScaleY(group.getScaleY() * scaleFactor);
            System.out.println(group.getBoundsInLocal());
            System.out.println(group.getBoundsInParent());
            double maxy2 = group.getBoundsInParent().getMaxY();
            difference += maxy - maxy2;
            group.setTranslateY(difference);
        });

       /* scene.setOnScroll(event1 -> zoom(group, event1.getDeltaY() > 0 ? 1.1 : 1/1.1,
                event1.getSceneX(), event1.getSceneY()));*/

        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.C)) {
                Group group1 = new Group();
                Rectangle rectangle = new Rectangle(group.getChildren().size() * 60, 0, 50, 50);
                rectangle.setFill(Color.INDIANRED);
                Rectangle rectangle2 = new Rectangle(group.getChildren().size() * 60, -60, 50, 50);
                rectangle2.setFill(Color.BLUEVIOLET);
                group1.getChildren().addAll(rectangle, rectangle2);
                group.getChildren().add(group1);
            }
        });



    }

    public static void zoom(Node node, double factor, double x, double y) {
        double oldScale = node.getScaleX();
        double scale = oldScale * factor;
        if (scale < 0.05) scale = 0.05;
        if (scale > 50)  scale = 50;
        node.setScaleX(scale);
        node.setScaleY(scale);

        double  f = (scale / oldScale)-1;
        Bounds bounds = node.localToScene(node.getBoundsInLocal());
        double dx = (x - (bounds.getWidth()/2 + bounds.getMinX()));
        double dy = (y - (bounds.getHeight()/2 + bounds.getMinY()));

        node.setTranslateX(node.getTranslateX()-f*dx);
        node.setTranslateY(node.getTranslateY()-f*dy);
    }

    public static void zoom(Node node, ScrollEvent event) {
        zoom(node, Math.pow(1.01, event.getDeltaY()), event.getSceneX(), event.getSceneY());
    }
    public static void zoom(Node node, ZoomEvent event) {
        zoom(node, event.getZoomFactor(), event.getSceneX(), event.getSceneY());
    }

    private void adjustTransform() {
        /*graphics.getTransforms().clear();

        double cx = content.getBoundsInParent().getMinX();
        double cy = content.getBoundsInParent().getMinY();
        double cw = content.getBoundsInParent().getWidth();
        double ch = content.getBoundsInParent().getHeight();

        double ew = editPane.getWidth();
        double eh = editPane.getHeight();

        if (ew > 0.0 && eh > 0.0) {
            double scale = Math.min(ew / cw, eh / ch);

            // Offset to center content
            double sx = 0.5 * (ew - cw * scale);
            double sy = 0.5 * (eh - ch * scale);

            graphics.getTransforms().add(new Translate(sx, sy));
            graphics.getTransforms().add(new Translate(-cx, -cy));
            graphics.getTransforms().add(new Scale(scale, scale, cx, cy));*/
        //}
    }

    public static void main(String[] args) {
        launch(args);
    }
}
