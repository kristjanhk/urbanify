package system.graphics;

import system.SceneHandler;
import system.StageHandler;

public interface StageMethod {
    StageHandler getParentStageHandler();
    SceneHandler getSceneHandler();
    void manualInit(StageHandler stageHandler);
}
