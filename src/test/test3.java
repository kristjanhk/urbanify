/*
package test;
        import javafx.application.Application;
        import javafx.application.Platform;
        import javafx.beans.value.ChangeListener;
        import javafx.beans.value.ObservableValue;
        import javafx.scene.Group;
        import javafx.scene.Node;
        import javafx.scene.Scene;
        import javafx.scene.layout.Pane;
        import javafx.scene.layout.StackPane;
        import javafx.scene.shape.SVGPath;
        import javafx.scene.shape.SVGPathBuilder;
        import javafx.scene.transform.Scale;
        import javafx.scene.transform.Translate;
        import javafx.stage.Stage;

public class test3 extends Application {

    private final static String svg = "M100 50 L150 0 L200 50 L150 100 Z";
    private Pane editPane;
    private Group graphics;
    private Node content;

    @Override
    public void start(final Stage stage) throws Exception {
        stage.setTitle(getClass().getSimpleName());


        //scene - stackpane(root) - pane(editpane) - group(graphics) - group(content) - children(items)
        //content group checks its bounds in graphics group
        //editpane wh
        //scale = smaller of (editpane w / content w, editpane h / content h)
        //scalex = editpane w - content w * scale
        //scaley = editpane h - content h * scale
        //add transform translate scalex, scaley
        //

        editPane = new Pane();
        graphics = new Group();
        editPane.getChildren().add(graphics);

        StackPane root = new StackPane();
        root.getChildren().add(editPane);

        Scene scene = new Scene(root, 800, 800);
        stage.setScene(scene);
        stage.show();

        content = loadContent();
        graphics.getChildren().add(content);

        editPane.widthProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                adjustTransform();
            }});

        editPane.heightProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                adjustTransform();
            }});

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                adjustTransform();
            }
        });
    }

    private Node loadContent() {
        SVGPath p = SVGPathBuilder.create().content(svg).style("-fx-stroke-width:5;-fx-fill:null;-fx-stroke:black").build();
        Group content = new Group();
        content.getChildren().add(p);
        return content;
    }

    private void adjustTransform() {
        graphics.getTransforms().clear();

        double cx = content.getBoundsInParent().getMinX();
        double cy = content.getBoundsInParent().getMinY();
        double cw = content.getBoundsInParent().getWidth();
        double ch = content.getBoundsInParent().getHeight();

        double ew = editPane.getWidth();
        double eh = editPane.getHeight();

        if (ew > 0.0 && eh > 0.0) {
            double scale = Math.min(ew/cw, eh/ch);

            // Offset to center content
            double sx = 0.5 * (ew - cw*scale);
            double sy = 0.5 * (eh - ch*scale);

            graphics.getTransforms().add(new Translate(sx, sy));
            graphics.getTransforms().add(new Translate(-cx, -cy));
            graphics.getTransforms().add(new Scale(scale, scale, cx, cy));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}*/
