package system.graphics.common;

import javafx.fxml.Initializable;
import system.FileHandler;
import system.settings.JsonFile;
import system.MainHandler;

public abstract class AbstractController implements Initializable {
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

    public abstract void setLanguage();
}