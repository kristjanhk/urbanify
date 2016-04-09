package system;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import system.graphics.AbstractController;
import system.graphics.Scenetype;

import java.io.IOException;
import java.util.ArrayList;

public class SceneHandler {
    private StageHandler stageHandler;
    private ArrayList<Scene> scenes = new ArrayList<>();

    public SceneHandler(StageHandler stageHandler) {
        this.stageHandler = stageHandler;
        Scene firstScene = createScene(Scenetype.MAINMENU);
        this.stageHandler.getPrimaryStage().setScene(firstScene);
        this.scenes.add(firstScene);
    }

    public ArrayList<Scene> getScenes() {
        return scenes;
    }

    public Scene getCurrentScene() {
        return scenes.get(0);
    }
    
    private Scene createScene(Scenetype scenetype) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(getResourceFile(scenetype)));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            AbstractController controller = fxmlLoader.getController();
            controller.initData(this.stageHandler.getPrimaryStage(), scene);
            // TODO: 9.04.2016 themes, list, settings file 
            // FIXME: 9.04.2016 mitme stseeni korral?
            scene.getStylesheets().add(getClass().getResource("graphics/common/lightTheme.css").toExternalForm());
            return scene;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getResourceFile(Scenetype type) {
        return "graphics/" + type.toPackageString() + "/" + type.toSceneString() + ".fxml";
    }

    public void switchSceneTo(Scenetype scenetype) {
        // TODO: 9.04.2016 stseenid ette luua?, mingi delay värk laadimisel 
        Scene scene = createScene(scenetype);
        this.stageHandler.getPrimaryStage().setScene(scene);
        // TODO: 9.04.2016 add scene to list 
        // TODO: 9.04.2016 switch to existing scene if exists 
    }
}
