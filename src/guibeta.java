/**
 * Created by Kristen on 02.04.2016.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;

public class guibeta extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            BorderPane page = FXMLLoader.load(Main.class.getResource("newProov.fxml"));

            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Superpilet 3000");
            primaryStage.show();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
/*
    @Override
    public void start(Stage primaryStage) {
        BorderPane border = new BorderPane();
        Text eventCreator = new Text("event creator");
        Font defFont50 = new Font("GeosansLight", 50);
        Font defFont35 = new Font("GeosansLight", 35);
        eventCreator.setFont(defFont50);
        eventCreator.setFill(Color.valueOf("B0AFB0"));

        TextField nameEvent = new TextField();
        nameEvent.setPromptText("name your event");
        nameEvent.setFont(defFont35);

        VBox vertbox1 = new VBox();
        Insets insets = new Insets(40,40,40,40);
        vertbox1.setPadding(insets);
        vertbox1.getChildren().addAll(eventCreator, nameEvent);
        border.setTop(vertbox1);

        Scene scene = new Scene(border, 1600, 900, Color.valueOf("E6E6E5"));
        primaryStage.setTitle("Piletisüsteem");
        primaryStage.setScene(scene);
        primaryStage.show();
    }*/
}
