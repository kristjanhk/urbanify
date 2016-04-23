package system;

import javafx.application.Application;
import javafx.stage.Stage;
import system.graphics.CustomScene;
import system.graphics.common.Csstype;

import java.util.ArrayList;

public class MainHandler extends Application{
    private static ArrayList<StageHandler> stageHandlers = new ArrayList<>();
    private static EventHandler eventHandler;
    private static FileHandler fileHandler;
    private static ReportHandler reportHandler;

    public static void main(String[] args) {
        fileHandler = new FileHandler();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        stageHandlers.add(new StageHandler(primaryStage));
        //todo init all handlers
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

    public static ArrayList<StageHandler> getStageHandlers() {
        return stageHandlers;
    }

    public static StageHandler getStageHandler(int id) {
        return stageHandlers.get(id);
    }

    public static void createNewStage() {
        stageHandlers.add(new StageHandler());
    }

    public static void changeGlobalThemeTo(Csstype csstype) {
        for (StageHandler stageHandler : stageHandlers) {
            stageHandler.getScenes().forEach((type, scene) -> changeSceneThemeTo(scene, csstype));
        }
    }

    public static void changeSceneThemeTo(CustomScene scene, Csstype csstype) {
        //http://stackoverflow.com/questions/17769388/javafx-change-css-at-runtime
        //https://community.oracle.com/thread/2566519?start=0&tstart=0
        // TODO: 21.04.2016 hoida alles mingit põhi css faili, vahetada aind theme 
        scene.getStylesheets().clear();
        scene.getStylesheets().add("system/graphics/common/" + csstype.toString(true));
    }
}

//cssi hierarhia
//default caspian.css < API settings < user's Scene css < user's Parent css < setStyle()

/*
mainhandler - hoiab handlereid
    |
Arraylist<StageHandler>
   /|\
StageHandler - hoiab ühte lava ja selle jaoks scenesid, nende loogika
    |
Hashmap<Scenetype, CustomScene>
   /|\
CustomScene
    |
fxml + controller - controller saab läbi scenei eelmistele asjadele ligi
    |
css
*/