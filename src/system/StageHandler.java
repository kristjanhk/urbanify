package system;

import javafx.stage.Stage;

import java.util.ArrayList;

public class StageHandler {
    private SceneHandler sceneHandler;
    private ArrayList<Stage> stages = new ArrayList<>();

    public StageHandler(Stage primaryStage) {
        this.stages.add(primaryStage);
        this.sceneHandler = new SceneHandler(this);
        primaryStage.setTitle("Superpilet 3000");
        primaryStage.setMinHeight(670);
        primaryStage.setMinWidth(1000);
        //this.primaryStage.setFullScreen(true);
    }

    public ArrayList<Stage> getStages() {
        return stages;
    }

    public Stage getPrimaryStage() {
        return stages.get(0);
    }

    public SceneHandler getSceneHandler() {
        return sceneHandler;
    }
}
