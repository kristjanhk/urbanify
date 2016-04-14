package system.graphics;

import javafx.scene.Scene;
import javafx.stage.Stage;
import system.StageHandler;

public abstract class AbstractController {
    protected StageHandler stageHandler;
    protected Scene scene;
    protected Scenetype scenetype;

    public void initData(StageHandler stageHandler, Scene scene, Scenetype scenetype) {
        this.stageHandler = stageHandler;
        this.scene = scene;
        this.scenetype = scenetype;
    }

    protected Stage getMyStage() {
        return this.stageHandler.getStage();
    }
}
