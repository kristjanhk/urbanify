package system.graphics;

import javafx.scene.Scene;
import javafx.stage.Stage;
import system.MainHandler;
import system.SceneHandler;
import system.StageHandler;

public abstract class AbstractController {
    protected Stage myStage;
    protected Scene myScene;

    public void initData(Stage stage, Scene scene) {
        this.myStage = stage;
        this.myScene = scene;
    }

    protected StageHandler getStageHandler() {
        return MainHandler.getStageHandler();
    }

    protected SceneHandler getSceneHandler() {
        return getStageHandler().getSceneHandler();
    }
}
