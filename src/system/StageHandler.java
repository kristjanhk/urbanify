package system;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import system.graphics.AbstractController;
import system.graphics.Scenetype;
import system.graphics.common.Csstype;

import java.io.IOException;
import java.util.ArrayList;

public class StageHandler {
    private Stage stage;
    private ArrayList<Scene> scenes = new ArrayList<>();

    public StageHandler() {
        this(new Stage());
    }

    public StageHandler(Stage primaryStage) {
        this.stage = primaryStage;
        Scene scene = createScene(Scenetype.MAINMENU);
        this.scenes.add(scene);
        this.stage.setScene(scene);
        // TODO: 11.04.2016 moditavad argumendid
        primaryStage.setTitle("Superpilet 3000");
        primaryStage.setMinHeight(670);
        primaryStage.setMinWidth(1000);
        primaryStage.show();
        //primaryStage.setFullScreen(true);
    }

    public Stage getStage() {
        return stage;
    }

    public ArrayList<Scene> getScenes() {
        return scenes;
    }

    public Scene getScene(int id) {
        return this.scenes.get(id);
    }

    public int getSceneId(Scene scene) {
        return this.scenes.indexOf(scene);
    }
    
    private Scene createScene(Scenetype scenetype) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(getResourceFile(scenetype)));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            AbstractController controller = fxmlLoader.getController();
            controller.initData(this, scene);
            // TODO: 9.04.2016 themes, list, settings file
            MainHandler.changeSceneThemeTo(scene, Csstype.getActiveTheme());
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
        this.stage.setScene(scene);
        // TODO: 9.04.2016 add scene to list 
        // TODO: 9.04.2016 switch to existing scene if exists 
    }
}
