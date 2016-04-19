package system;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import system.graphics.CustomScene;
import system.graphics.Scenetype;

import java.io.IOException;
import java.util.HashMap;

public class StageHandler {
    private Stage stage;
    private HashMap<Scenetype, CustomScene> scenes = new HashMap<>();

    public StageHandler() {
        this(new Stage());
    }

    public StageHandler(Stage primaryStage) {
        this.stage = primaryStage;
        this.initScenes();
        this.switchSceneTo(Scenetype.MAINMENU);
        // TODO: 11.04.2016 moditavad argumendid
        primaryStage.setTitle("Superpilet 3000");
        primaryStage.setMinHeight(670);
        primaryStage.setMinWidth(1000);
        primaryStage.show();
        //primaryStage.setFullScreen(true);
    }

    public Stage getStage() {
        return stage;
    }

    public HashMap<Scenetype, CustomScene> getScenes() {
        return scenes;
    }

    private void initScenes() {
        for (Scenetype scenetype : Scenetype.values()) {
            this.scenes.put(scenetype, createScene(scenetype));
        }
    }
    
    private CustomScene createScene(Scenetype scenetype) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(getResourceFile(scenetype)));
        try {
            return new CustomScene(fxmlLoader.load(), fxmlLoader, this, scenetype);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void replaceScene(Scenetype scenetype) {
        this.scenes.replace(scenetype, createScene(scenetype));
    }

    private String getResourceFile(Scenetype type) {
        return "graphics/" + type.toPackageString() + "/" + type.toSceneString();
    }

    public void switchSceneTo(Scenetype scenetype) {
        this.scenes.get(scenetype).getController().prepareToDisplay();
        this.stage.setScene(this.scenes.get(scenetype));
    }
}
