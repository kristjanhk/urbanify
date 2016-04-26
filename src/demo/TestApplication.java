package demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class TestApplication extends Application {

    /**
     @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(final Stage primaryStage) {
        primaryStage.setTitle("Hello World!");
        StackPane root = new StackPane();
        final DateChooser dateChooser = new DateChooser();
        root.getChildren().add(dateChooser);
        Scene scene = new Scene(root, 300, 250);  
        scene.getStylesheets().add(TestApplication.class.getResource("lcd.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setOnHiding(event -> System.out.println("date " + dateChooser.getDate()));
        primaryStage.show();
    }
}
