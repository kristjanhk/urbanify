package system;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import system.graphics.common.Csstype;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class MainHandler extends Application{
    private static ArrayList<StageHandler> stageHandlers = new ArrayList<>();
    private static EventHandler eventHandler;
    private static FileHandler fileHandler;
    private static ReportHandler reportHandler;

    public static void main(String[] args) {
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

    
    // TODO: 11.04.2016 2 args, remove old one and add new one
    //http://stackoverflow.com/questions/10887525/javafx-style-class-wont-refresh
    public static void changeGlobalThemeTo(Csstype csstype) {
        for (StageHandler stageHandler : stageHandlers) {
            /*for (Scene scene : stageHandler.getScenes()) {
                changeSceneThemeTo(scene, csstype);
            }*/
            //http://stackoverflow.com/questions/1066589/iterate-through-a-hashmap
            Iterator it = stageHandler.getScenes().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                changeSceneThemeTo((Scene) pair.getKey(), csstype);
                it.remove();
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
Arraylist<StageHandler>
   /|\
StageHandler - hoiab ühte lava ja selle jaoks scenesid, nende loogika
    |
Hashmap<Scene>
   /|\
Scene
    |
fxml + controller - controlleris on oma lava ja scenei kohta viide olemas
    |
css
*/