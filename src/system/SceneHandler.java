package system;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import system.graphics.AbstractController;
import system.graphics.CustomPrimaryStage;
import system.graphics.CustomStage;
import system.graphics.Scenetype;
import system.graphics.common.Csstype;

import java.io.IOException;
import java.util.ArrayList;

public class SceneHandler {
    private CustomStage parentStage;
    private ArrayList<Scene> scenes = new ArrayList<>();

    public SceneHandler(CustomStage stage) {
        this.parentStage = stage;
        Scene scene = createScene(Scenetype.MAINMENU);
        this.scenes.add(scene);
        if (this.parentStage instanceof CustomPrimaryStage) {
            ((CustomPrimaryStage) this.parentStage).getPrimaryStage().setScene(scene);
        } else {
            this.parentStage.setScene(scene);
        }
    }

    public ArrayList<Scene> getScenes() {
        return scenes;
    }

    // TODO: 10.04.2016 current/primaryscene vaja kätte saada v ei?
    
    private Scene createScene(Scenetype scenetype) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(getResourceFile(scenetype)));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            AbstractController controller = fxmlLoader.getController();
            controller.initData(this.parentStage, scene);
            // TODO: 9.04.2016 themes, list, settings file
            MainHandler.changeSceneThemeTo(scene, Csstype.lightTheme);
            //scene.getStylesheets().add(getClass().getResource("graphics/common/lightTheme.css").toExternalForm());
            return scene;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getResourceFile(Scenetype type) {
        return "graphics/" + type.toPackageString() + "/" + type.toSceneString();
    }

    public void switchSceneTo(Scenetype scenetype) {
        // TODO: 9.04.2016 stseenid ette luua?, mingi delay värk laadimisel 
        Scene scene = createScene(scenetype);
        this.scenes.add(scene);
        if (this.parentStage instanceof CustomPrimaryStage) {
            ((CustomPrimaryStage) this.parentStage).getPrimaryStage().setScene(scene);
        } else {
            this.parentStage.setScene(scene);
        }
        // TODO: 9.04.2016 add scene to list 
        // TODO: 9.04.2016 switch to existing scene if exists 
    }
}
