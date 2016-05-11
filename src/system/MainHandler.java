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

/**
 * Peaklass
 * Hoiab ning võimaldab ligipääsu kõikidele handleritele
 * Võimaldab stseeni stiilide vahetuse
 * Võimaldab lisada tippudele valideerimist
 */
public class MainHandler extends Application {
    private static StageHandler stageHandler;
    private static FileHandler fileHandler;
    private static ReportHandler reportHandler;

    public static void main(String[] args) throws Exception {
        fileHandler = new FileHandler();
        reportHandler = new ReportHandler();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        stageHandler = new StageHandler(primaryStage);
    }

    public static StageHandler getStageHandler() {
        return stageHandler;
    }

    public static FileHandler getFileHandler() {
        return fileHandler;
    }

    public static ReportHandler getReportHandler() {
        return reportHandler;
    }

    public static void changeGlobalThemeTo(Csstype csstype) {
        stageHandler.getScenes().forEach((type, scene) -> changeSceneThemeTo(scene, csstype));
    }

    public static void changeSceneThemeTo(CustomScene scene, Csstype csstype) {
        scene.getStylesheets().clear();
        scene.getStylesheets().add("system/graphics/common/" + csstype.toString(true));
    }

    /**
     * Lisab antud tipule antud valideerimise
     * Kasutab controlsfx teeki http://fxexperience.com/controlsfx/
     * (abimaterjal http://stackoverflow.com/questions/29607080/textfield-component-validation-with-controls-fx)
     *
     * @param node       tipp, millele valideerimist vaja
     * @param validation regulaaravaldis
     * @return ReadOnlyBooleanProperty isInvalid
     */
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
mainhandler - staatiline main meetod
    |
StageHandler - haldab lava
    |
Hashmap<Scenetype, CustomScene>
   /|\
CustomScene - mähisklass, haldab stseeni
    |
fxml + controller - fxml määrab üldjoontes stseeni sisu, controller loogikat
*/