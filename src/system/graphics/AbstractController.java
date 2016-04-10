package system.graphics;

import javafx.scene.Scene;
import javafx.stage.Stage;
import system.StageHandler;

public abstract class AbstractController {
    protected StageHandler stageHandler;
    protected Scene scene;

    public void initData(StageHandler stageHandler, Scene scene) {
        this.stageHandler = stageHandler;
        this.scene = scene;
    }

    protected Stage getMyStage() {
        return this.stageHandler.getStage();
    }

    protected Scene getPreviousScene() {
        int myId = this.stageHandler.getSceneId(this.scene);
        if (myId > 0) {
            return this.stageHandler.getScene(myId - 1);
        }
        return null;
    }

    protected Scene getNextScene() {
        int myId = this.stageHandler.getSceneId(this.scene);
        if (this.stageHandler.getScenes().size() > myId + 1) {
            return this.stageHandler.getScene(myId + 1);
        }
        return null;
    }
}
