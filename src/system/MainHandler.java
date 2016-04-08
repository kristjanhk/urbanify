package system;

import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainHandler extends Application{
    private static StageHandler stageHandler;
    private static SceneHandler sceneHandler;
    private static EventHandler eventHandler;
    private static FileHandler fileHandler;
    private static ReportHandler reportHandler;

    public static void main(String[] args) {
        //??
        Font font = Font.loadFont(
                MainHandler.class.getResource("graphics/common/Lato-Hairline.ttf").toExternalForm(), 10);
        System.out.println(font);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stageHandler = new StageHandler(primaryStage);
        sceneHandler = new SceneHandler();
        //todo init all handlers
        stageHandler.getPrimaryStage().show();
    }

    //maini kaudu saab siis handleritele ligi ja nende asjadele

    public static StageHandler getStageHandler() {
        return stageHandler;
    }

    public static SceneHandler getSceneHandler() {
        return sceneHandler;
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
}
