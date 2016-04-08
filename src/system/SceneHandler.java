package system;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class SceneHandler {
    private Scene currentScene; //temp?, mis saab mitme stseeniga, arraylist?

    public SceneHandler() throws IOException {
        currentScene = new Scene(FXMLLoader.load(getClass().getResource("system/graphics/eventCreator/eventCreator.fxml")));
        currentScene.getStylesheets().add("system/graphics/common/lightTheme.css");
        Main.getStageHandler().getPrimaryStage().setScene(currentScene);
    }

    public Scene getCurrentScene() {
        return currentScene;
    }
}
