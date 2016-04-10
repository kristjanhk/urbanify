package system;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import system.graphics.CustomStage;
import system.graphics.common.Csstype;

public class MainHandler extends Application{
    private static StageHandler stageHandler;
    private static EventHandler eventHandler;
    private static FileHandler fileHandler;
    private static ReportHandler reportHandler;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        stageHandler = new StageHandler(primaryStage);
        //todo init all handlers
        Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);
    }

    public static StageHandler getStageHandler() {
        return stageHandler;
    }

    public static FileHandler getFileHandler() {
        return fileHandler;
    }

    public static EventHandler getEventHandler() {
        return eventHandler;
    }

    public static ReportHandler getReportHandler() {
        return reportHandler;
    }

    public static void changeGlobalThemeTo(Csstype csstype) {
        for (CustomStage stage : stageHandler.getStages()) {
            for (Scene scene : stage.getSceneHandler().getScenes()) {
                changeSceneThemeTo(scene, csstype);
            }
        }
    }

    public static void changeSceneThemeTo(Scene scene, Csstype csstype) {
        scene.getStylesheets().add("system/graphics/common/" + csstype.toString());
    }
}



























/*
mainhandler - hoiab handlereid
    |
stagehandler - haldab kõiki lavasid, lavade loogika
    |
Arraylist<CustomStage>
   /|\
CustomStage - lava, hoiab endas scenehandlerit
    |
Scenehandler - hoiab endas scenesid lava jaoks, sceneide loogika
    |
Arraylist<Scene>
   /|\
Scene
    |
fxml + controller - controlleris on oma lava ja scenei kohta viide olemas
    |
css
*/
