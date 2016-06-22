package system;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
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

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Base64;

/**
 * Peaklass
 * Hoiab ning võimaldab ligipääsu kõikidele handleritele
 * Võimaldab stseeni stiilide vahetuse
 * Võimaldab lisada tippudele valideerimist
 */
public class MainHandler extends Application {
    private static StageHandler[] stageHandlers = new StageHandler[3];
    private static FileHandler fileHandler;
    private static ReportHandler reportHandler;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        fileHandler = new FileHandler();
        reportHandler = new ReportHandler();
        stageHandlers[0] = new StageHandler(primaryStage, "Urbanify");
        stageHandlers[1] = new StageHandler("Urbanify 2");
        stageHandlers[2] = new StageHandler("Popup");
    }

    public static StageHandler[] getStageHandlers() {
        return stageHandlers;
    }

    public static StageHandler getPrimaryStageHandler() {
        return stageHandlers[0];
    }

    public static StageHandler getSecondaryStageHandler() {
        return stageHandlers[1];
    }

    public static StageHandler getTertiaryStageHandler() {
        return stageHandlers[2];
    }

    public static FileHandler getFileHandler() {
        return fileHandler;
    }

    public static ReportHandler getReportHandler() {
        return reportHandler;
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

    /**
     * Lisab antud tipule antud valideerimise
     * Kasutab controlsfx teeki http://fxexperience.com/controlsfx/
     * (http://stackoverflow.com/questions/29607080/textfield-component-validation-with-controls-fx)
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

    public static ImageView createQrCode(String text, double size, int cropsize) {
        ImageView qrcode = new ImageView();
        qrcode.setFitWidth(size);
        qrcode.setFitHeight(size);
        qrcode.setPickOnBounds(true);
        qrcode.setPreserveRatio(true);
        try {
            BitMatrix bytematrix = new QRCodeWriter().encode(text, BarcodeFormat.QR_CODE,
                    (int) qrcode.getFitWidth(), (int) qrcode.getFitHeight());
            Canvas canvas = new Canvas((int) qrcode.getFitWidth() - cropsize, (int) qrcode.getFitHeight() - cropsize);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            if (Csstype.getActiveTheme().equals(Csstype.DARK)) {
                gc.setFill(Color.BLACK);
            } else {
                gc.setFill(Color.valueOf("5b5c5c"));
            }
            for (int i = 0; i < canvas.getHeight(); i++) {
                for (int j = 0; j < canvas.getWidth(); j++) {
                    if (bytematrix.get(i, j)) {
                        gc.fillRect(i, j, 1, 1);
                    }
                }
            }
            SnapshotParameters params = new SnapshotParameters();
            params.setFill(Color.TRANSPARENT);
            qrcode.setImage(new WritableImage(canvas.snapshot(params, null).getPixelReader(), cropsize, cropsize,
                    (int) canvas.getWidth() - cropsize, (int) canvas.getHeight() - cropsize));
            return qrcode;
        } catch (WriterException ignored) {
            return null;
        }
    }

    public static String encrypt(String plaintext) {
        try {
            SecretKeySpec sks = new SecretKeySpec(getFileHandler().getData().getKeyBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, sks);
            AlgorithmParameters params = cipher.getParameters();
            byte[] ivbytes = params.getParameterSpec(IvParameterSpec.class).getIV();
            byte[] encrypted = cipher.doFinal(plaintext.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(ivbytes) + ";" +
                    Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}