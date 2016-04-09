package system;

import javafx.application.Application;
import javafx.stage.Stage;

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
        stageHandler.getPrimaryStage().show();
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
}
