package system;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Control;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
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
        return validate(node, validation).invalidProperty();
    }

    public static ValidationSupport validate(Node node, String validation) {
        ValidationSupport validationSupport = new ValidationSupport();
        validationSupport.setValidationDecorator(
                new StyleClassValidationDecoration("validationError", "validationWarning"));
        registerValidator(validationSupport, node, validation);
        return validationSupport;
    }

    public static void registerValidator(ValidationSupport vs, Node node, String validation) {
        vs.registerValidator((Control) node, true, Validator.createRegexValidator("error", validation, Severity.ERROR));
    }

    public static ImageView createQrCode(String text) {
        ImageView qrcode = new ImageView();
        qrcode.setFitWidth(320.0);
        qrcode.setFitHeight(320.0);
        qrcode.setPickOnBounds(true);
        qrcode.setPreserveRatio(true);
        int cropsize = 44;
        try {
            BitMatrix bytematrix = new QRCodeWriter().encode(text, BarcodeFormat.QR_CODE,
                    (int) qrcode.getFitWidth(), (int) qrcode.getFitHeight());
            Canvas canvas = new Canvas((int) qrcode.getFitWidth() - cropsize, (int) qrcode.getFitHeight() - cropsize);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            if (Csstype.getActiveTheme().equals(Csstype.DARK)) {
                gc.setFill(Color.valueOf("262626"));
                gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                gc.setFill(Color.BLACK);
            } else {
                gc.setFill(Color.valueOf("e6e6e5"));
                gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                gc.setFill(Color.valueOf("5b5c5c"));
            }
            for (int i = 0; i < canvas.getHeight(); i++) {
                for (int j = 0; j < canvas.getWidth(); j++) {
                    if (bytematrix.get(i, j)) {
                        gc.fillRect(i, j, 1, 1);
                    }
                }
            }
            qrcode.setImage(new WritableImage(canvas.snapshot(null, null).getPixelReader(), cropsize, cropsize,
                    (int) canvas.getWidth() - cropsize, (int) canvas.getHeight() - cropsize));
            return qrcode;
        } catch (WriterException ignored) {
            return null;
        }
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