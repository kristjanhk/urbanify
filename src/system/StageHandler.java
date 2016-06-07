package system;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import system.graphics.common.ClientScreen;
import system.graphics.common.CustomScene;
import system.graphics.common.Scenetype;
import system.graphics.pointOfSale.Controller;

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

    public StageHandler(Stage primaryStage, String title) {
        this.stage = primaryStage;
        this.init(title);
        this.stage.setOnCloseRequest(event -> {
            MainHandler.getFileHandler().saveData();
            MainHandler.getSecondaryStageHandler().getStage().close();
        });
        this.initSaveableScenes();
        this.switchSceneTo(Scenetype.MAINMENU);
        this.showStage();
    }

    public StageHandler(String title) {
        this.stage = new Stage();
        this.init(title);
        this.stage.setOnCloseRequest(event -> ((Controller) MainHandler.getPrimaryStageHandler().
                getScene(Scenetype.POINTOFSALE).getController()).cancel());
        this.scenes.put(Scenetype.TICKETINFO, createScene(Scenetype.TICKETINFO));
        this.switchSceneTo(Scenetype.TICKETINFO);
        this.stage.setX(ClientScreen.getActiveVisualBounds().getMinX());
        this.stage.setY(ClientScreen.getActiveVisualBounds().getMinY());
        this.stage.centerOnScreen();
    }

    private void init(String title) {
        this.stage.initStyle(StageStyle.UNDECORATED);
        this.resizeHandler = new ResizeHandler(this.stage);
        this.stage.setTitle(title);
        this.stage.setHeight(880);
        this.stage.setWidth(1220);
        this.stage.setMinHeight(720);
        this.stage.setMinWidth(1180);
    }

    public void showStage() {
        //this.stage.setMaximized(true);
        this.stage.toFront();
        this.stage.show();
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

    private void initSaveableScenes() {
        this.scenes.put(Scenetype.EVENTMANAGER, createScene(Scenetype.EVENTMANAGER));
        this.scenes.put(Scenetype.ARCHIVE, createScene(Scenetype.ARCHIVE));
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
        if (this.scenes.get(scenetype) == null) {
            this.scenes.put(scenetype, createScene(scenetype));
        }
        this.scenes.get(scenetype).getController().prepareToDisplay(object);
        this.scenes.get(scenetype).getController().setMaximizeButton();
        this.stage.setScene(this.scenes.get(scenetype));
    }

    public void switchSceneTo(Scenetype scenetype) {
        this.switchSceneTo(scenetype, null);
    }
}
