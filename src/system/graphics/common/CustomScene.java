package system.graphics.common;

import javafx.beans.NamedArg;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import system.MainHandler;
import system.StageHandler;

/**
 * Kohandatud stseen
 * Töö lihtsustamiseks loodud mähisklass
 */
public class CustomScene extends Scene {
    private StageHandler stageHandler;
    private Scenetype scenetype;
    private AbstractController controller;

    public CustomScene(@NamedArg("root") Parent root, FXMLLoader fxmlLoader,
                       StageHandler stageHandler, Scenetype scenetype) {
        super(root);
        this.stageHandler = stageHandler;
        this.scenetype = scenetype;
        this.controller = fxmlLoader.getController();
        this.controller.initData(this);
        MainHandler.changeSceneThemeTo(this, Csstype.getActiveTheme());
    }

    public AbstractController getController() {
        return controller;
    }

    public StageHandler getStageHandler() {
        return stageHandler;
    }

    public Scenetype getScenetype() {
        return scenetype;
    }
}
