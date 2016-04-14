package system;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import system.graphics.AbstractController;
import system.graphics.Scenetype;
import system.graphics.common.Csstype;

import java.io.IOException;
import java.util.HashMap;

public class StageHandler {
    private Stage stage;
    private HashMap<Scenetype, Scene> scenes = new HashMap<>();

    public StageHandler() {
        this(new Stage());
    }

    public StageHandler(Stage primaryStage) {
        this.stage = primaryStage;
        this.initScenes();
        this.switchSceneTo(Scenetype.MAINMENU);
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

    public HashMap<Scenetype, Scene> getScenes() {
        return scenes;
    }

    private void initScenes() {
        Scenetype.getScenetypes().forEach(this::createScene);
    }
    
    private Scene createScene(Scenetype scenetype) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(getResourceFile(scenetype)));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            AbstractController controller = fxmlLoader.getController();
            controller.initData(this, scene, scenetype);
            // TODO: 9.04.2016 themes, list, settings file
            MainHandler.changeSceneThemeTo(scene, Csstype.getActiveTheme());
            this.scenes.put(scenetype, scene);
            return scene;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void replaceScene() {

    }

    private String getResourceFile(Scenetype type) {
        return "graphics/" + type.toPackageString() + "/" + type.toSceneString();
    }

    public void switchSceneTo(Scenetype scenetype) {
        this.stage.setScene(this.scenes.get(scenetype));
    }
}
