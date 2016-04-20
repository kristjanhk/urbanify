package test;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBoxBuilder;
import javafx.stage.Stage;

public class Demo extends Application {

    private String theme1Url = getClass().getResource("theme1.css").toExternalForm();
    private String theme2Url = getClass().getResource("theme2.css").toExternalForm();

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        final Scene scene = new Scene(root, 300, 250);
        System.out.println("scene stylesheets: " + scene.getStylesheets());
        scene.getStylesheets().add(theme1Url);
        System.out.println("scene stylesheets: " + scene.getStylesheets());

        final Button btn = new Button("Load Theme 1");
        btn.getStyleClass().add("buttonStyle");
        btn.setOnAction(event -> commonAction(scene, theme2Url, theme1Url));

        final Button btn2 = new Button("Load Theme 2");
        btn2.getStyleClass().add("buttonStyle");
        btn2.setOnAction(event -> commonAction(scene, theme1Url, theme2Url));

        ComboBox<String> comboBox = new ComboBox<>(FXCollections.observableArrayList("Just", "another", "control"));
        root.getChildren().add(VBoxBuilder.create().spacing(10).children(btn, btn2, comboBox).build());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void commonAction(Scene scene, String themeFirst, String themeSecond) {
        scene.getStylesheets().remove(themeFirst);
        System.out.println("scene stylesheets on button click: " + scene.getStylesheets());
        if(!scene.getStylesheets().contains(themeSecond)) {
            scene.getStylesheets().add(themeSecond);
        }
        System.out.println("scene stylesheets on button click: " + scene.getStylesheets());
    }
}