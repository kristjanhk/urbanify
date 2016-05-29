package system;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import system.graphics.common.CustomScene;
import system.graphics.common.Scenetype;

import java.io.IOException;
import java.util.HashMap;

/**
 * Lavahaldaja
 * Hoiab endas lava, stseene
 * Tegeleb stseenide loomisega, nende vahetamisega, lava asukoha muutmisega
 */
public class StageHandler {
    private Stage stage;
    private ResizeHandler resizeHandler;
    private HashMap<Scenetype, CustomScene> scenes = new HashMap<>();
    private double stageXOffset = 0.0;
    private double stageYOffset = 0.0;

    public StageHandler(Stage primaryStage) {
        this.stage = primaryStage;
        primaryStage.initStyle(StageStyle.UNDECORATED);
        this.resizeHandler = new ResizeHandler(primaryStage);
        primaryStage.setTitle("Superpilet 3000");
        primaryStage.setHeight(880);
        primaryStage.setWidth(1220);
        primaryStage.setMinHeight(720);
        primaryStage.setMinWidth(1100);
        this.initScenes();
        this.switchSceneTo(Scenetype.MAINMENU);
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> MainHandler.getFileHandler().saveData());
    }

    public Stage getStage() {
        return this.stage;
    }

    public HashMap<Scenetype, CustomScene> getScenes() {
        return this.scenes;
    }

    public CustomScene getScene(Scenetype scenetype) {
        return this.scenes.get(scenetype);
    }

    public ResizeHandler getResizeHandler() {
        return this.resizeHandler;
    }

    public void setStageOffsets(double eventX, double eventY) {
        this.stageXOffset = this.stage.getX() - eventX;
        this.stageYOffset = this.stage.getY() - eventY;
    }

    public void setStageXY(double eventX, double eventY) {
        this.stage.setX(eventX + this.stageXOffset);
        this.stage.setY(eventY + this.stageYOffset);
    }

    private void initScenes() {
        for (Scenetype scenetype : Scenetype.values()) {
            this.scenes.put(scenetype, createScene(scenetype));
        }
    }

    /**
     * Loob uue stseeni antud stseenitüübi kohale
     * (igat stseenitüüi saab korraga olla vaid üks)
     *
     * @param scenetype stseenitüüp, mida vaja luua
     * @return new Customscene
     */
    private CustomScene createScene(Scenetype scenetype) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(getResourceFile(scenetype)));
        try {
            return new CustomScene(fxmlLoader.load(), fxmlLoader, this, scenetype);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void replaceScene(Scenetype scenetype) {
        this.scenes.replace(scenetype, createScene(scenetype));
    }

    private String getResourceFile(Scenetype type) {
        return "graphics/" + type.toPackageString() + "/" + type.toSceneString();
    }

    /**
     * Vahetab laval oleva stseeni antud stseenitüübi vastu
     * Võimaldab anda kaasa suvalist objekti, mille järgi stseeni controller saab stseeni ette valmistada
     *
     * @param scenetype stseenitüüp, millele vaja vahetada
     * @param object    suvaline objekt, selle määratlemisega tegeleb stseeni controller ise
     */
    public <T> void switchSceneTo(Scenetype scenetype, T object) {
        this.scenes.get(scenetype).getController().prepareToDisplay(object);
        this.scenes.get(scenetype).getController().setMaximizeButton();
        this.stage.setScene(this.scenes.get(scenetype));
    }

    public void switchSceneTo(Scenetype scenetype) {
        this.switchSceneTo(scenetype, null);
    }
}
