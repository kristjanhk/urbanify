package system.graphics;

import javafx.stage.Stage;
import system.SceneHandler;
import system.StageHandler;

public class CustomStage extends Stage {
    private StageHandler parentStageHandler;
    private SceneHandler sceneHandler;

    public CustomStage(StageHandler stageHandler) {
        super();
        init(stageHandler);
    }

    protected CustomStage() {
        super();
    }

    public StageHandler getParentStageHandler() {
        return this.parentStageHandler;
    }
    
    public SceneHandler getSceneHandler() {
        return this.sceneHandler;
    }
    
    protected void init(StageHandler stageHandler) {
        this.parentStageHandler = stageHandler;
        this.sceneHandler = new SceneHandler(this);
    }
}
