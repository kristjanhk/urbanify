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

    //primary stagehandler
    public StageHandler(Stage primaryStage, String title) {
        this.stage = primaryStage;
        this.init(title);
        this.resizeHandler = new ResizeHandler(this.stage);
        this.stage.setOnCloseRequest(event -> {
            MainHandler.getFileHandler().saveData();
            for (StageHandler stageHandler : MainHandler.getStageHandlers()) {
                if (!stageHandler.equals(this)) {
                    stageHandler.getStage().close();
                }
            }
        });
        this.initSaveableScenes();
        this.switchSceneTo(Scenetype.MAINMENU);
        this.showStage(true);
    }

    //other stagehandlers
    public StageHandler(String title) {
        this.stage = new Stage();
        this.init(title);
        if (title.equals("Popup")) {
            this.scenes.put(Scenetype.REPORTPDF, createScene(Scenetype.REPORTPDF));
            this.switchSceneTo(Scenetype.REPORTPDF);
        } else {
            this.stage.setOnCloseRequest(event -> {
                CustomScene scene = MainHandler.getPrimaryStageHandler().getScene(Scenetype.POINTOFSALE);
                if (scene != null) {
                    ((Controller) scene.getController()).cancel();
                }
            });
            this.scenes.put(Scenetype.TICKETINFO, createScene(Scenetype.TICKETINFO));
            this.switchSceneTo(Scenetype.TICKETINFO);
            this.changeScreen();
        }
    }

    private void init(String title) {
        this.stage.initStyle(StageStyle.UNDECORATED);
        this.stage.setTitle(title);
        if (title.equals("Popup")) {
            this.stage.setAlwaysOnTop(true);
            this.stage.setHeight(550);
            this.stage.setWidth(600);
            this.stage.setMinHeight(550);
            this.stage.setMinWidth(600);
        } else {
            this.stage.setHeight(880);
            this.stage.setWidth(1220);
            this.stage.setMinHeight(720);
            this.stage.setMinWidth(1180);
        }
    }

    public void changeScreen() {
        this.stage.setX(ClientScreen.getActiveVisualBounds().getMinX());
        this.stage.setY(ClientScreen.getActiveVisualBounds().getMinY());
        this.stage.centerOnScreen();
    }

    public void showStage(boolean maximized) {
        this.stage.setMaximized(maximized);
        if (ClientScreen.isSecondScreenEnabled() &&
                MainHandler.getSecondaryStageHandler() != null &&
                MainHandler.getSecondaryStageHandler().equals(this)) {
            this.stage.toFront();
        }
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
