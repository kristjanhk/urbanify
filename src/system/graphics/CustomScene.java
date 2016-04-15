package system.graphics;

import javafx.beans.NamedArg;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import system.MainHandler;
import system.StageHandler;
import system.graphics.common.Csstype;

public class CustomScene extends Scene {
    private StageHandler stageHandler;
    private Scenetype scenetype;
    private AbstractController controller;

    // TODO: 9.04.2016 themes, list, settings file
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
