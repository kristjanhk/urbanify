package system;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.stage.Stage;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.controlsfx.validation.decoration.StyleClassValidationDecoration;
import system.graphics.common.CustomScene;
import system.graphics.common.Csstype;

import java.util.ArrayList;

public class MainHandler extends Application{
    private static ArrayList<StageHandler> stageHandlers = new ArrayList<>();
    private static EventHandler eventHandler;
    private static FileHandler fileHandler;
    private static ReportHandler reportHandler;

    public static void main(String[] args) {
        fileHandler = new FileHandler();
        eventHandler = new EventHandler();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        stageHandlers.add(new StageHandler(primaryStage));
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

    public static void createNewStage() {
        stageHandlers.add(new StageHandler());
    }

    public static void changeGlobalThemeTo(Csstype csstype) {
        for (StageHandler stageHandler : stageHandlers) {
            stageHandler.getScenes().forEach((type, scene) -> changeSceneThemeTo(scene, csstype));
        }
    }

    public static void changeSceneThemeTo(CustomScene scene, Csstype csstype) {
        scene.getStylesheets().clear();
        scene.getStylesheets().add("system/graphics/common/" + csstype.toString(true));
    }

    //http://stackoverflow.com/questions/29607080/textfield-component-validation-with-controls-fx
    public static ReadOnlyBooleanProperty setValidationFor(Node node, String validation) {
        ValidationSupport validationSupport = new ValidationSupport();
        validationSupport.setValidationDecorator(
                new StyleClassValidationDecoration("validationError", "validationWarning"));
        validationSupport.registerValidator((Control) node, true,
                Validator.createRegexValidator("error", validation, Severity.ERROR));
        return validationSupport.invalidProperty();
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