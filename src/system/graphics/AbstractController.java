package system.graphics;

import javafx.scene.Scene;
import system.SceneHandler;
import system.StageHandler;

public abstract class AbstractController {
    protected CustomStage myStage;
    protected Scene myScene;

    public void initData(CustomStage stage, Scene scene) {
        this.myStage = stage;
        this.myScene = scene;
    }

    protected StageHandler getMyStageHandler() {
        return this.myStage.getParentStageHandler();
    }

    protected SceneHandler getMySceneHandler() {
        return this.myStage.getSceneHandler();
    }
}
