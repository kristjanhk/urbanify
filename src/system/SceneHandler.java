package system;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class SceneHandler {
    private Scene currentScene; //temp?, mis saab mitme stseeniga, arraylist?

    public SceneHandler() throws IOException {
        currentScene = new Scene(FXMLLoader.load(getClass().getResource("graphics/eventCreator/eventCreator.fxml")));
        //laetakse praegu fxmlis sisse
        //currentScene.getStylesheets().add(getClass().getResource("graphics/common/lightTheme.css").toExternalForm());
        MainHandler.getStageHandler().getPrimaryStage().setScene(currentScene);
    }

    public Scene getCurrentScene() {
        return currentScene;
    }
}
