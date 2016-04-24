package system.graphics;

import system.FileHandler;
import system.JsonFile;
import system.MainHandler;

public abstract class AbstractController {
    protected CustomScene scene;

    public void initData(CustomScene scene) {
        this.scene = scene;
    }

    protected FileHandler getFileHandler() {
        return MainHandler.getFileHandler();
    }

    protected JsonFile getData() {
        return this.getFileHandler().getData();
    }

    public abstract void prepareToDisplay(Scenetype prevSceneType);
}
