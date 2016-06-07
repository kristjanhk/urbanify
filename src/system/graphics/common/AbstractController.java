package system.graphics.common;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import system.FileHandler;
import system.data.JsonFile;
import system.MainHandler;

/**
 * Controllerite abstrakte ülemklass
 * Iga controller saab ligi oma stseenile
 * Iga controller tegeleb minimize, maximize, close nuppudega ja lava asukoha liigutamisega
 */
public abstract class AbstractController implements Initializable {
    protected CustomScene scene;

    @FXML
    protected Button maximizeButton;

    public void initData(CustomScene scene) {
        this.scene = scene;
        this.scene.getStageHandler().getResizeHandler().addResizeListener(this.scene);
    }

    public CustomScene getScene() {
        return this.scene;
    }

    public void setMaximizeButton() {
        this.maximizeButton.setText(this.getStage().isMaximized() ? "↓" : "↑");
    }

    protected FileHandler getFileHandler() {
        return MainHandler.getFileHandler();
    }

    protected JsonFile getData() {
        return this.getFileHandler().getData();
    }

    protected Stage getStage() {
        return this.scene.getStageHandler().getStage();
    }

    @FXML
    protected void handleStageLocationOffset(MouseEvent event) {
        if (Cursor.DEFAULT.equals(this.scene.getStageHandler().getResizeHandler().getCursor()) &&
                !this.getStage().isMaximized()) {
            this.scene.getStageHandler().setStageOffsets(event.getScreenX(), event.getScreenY());
        }
    }

    @FXML
    protected void handleStageLocationDrag(MouseEvent event) {
        if (Cursor.DEFAULT.equals(this.scene.getStageHandler().getResizeHandler().getCursor()) &&
                !this.getStage().isMaximized()) {
            this.scene.getStageHandler().setStageXY(event.getScreenX(), event.getScreenY());
        }
    }

    @FXML
    protected void handleStageMinimize() {
        this.getStage().setIconified(true);
    }

    @FXML
    protected void handleStageMaximize() {
        this.getStage().setMaximized(!this.getStage().isMaximized());
        this.setMaximizeButton();
    }

    @FXML
    protected void handleStageClose() {
        this.getStage().fireEvent(new WindowEvent(this.getStage(), WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    /**
     * Meetod, mida kutsutakse enne controlleri stseenile vahetamist
     * Võimaldab stseeni ette valmistada (nt uue üritusega müügipunkti stseen (seljuhul on objektiks Event))
     *
     * @param object suvaline objekt, selle määratlemisega tegeleb alamklassi controller ise
     *               NB: objekt peab olemas olema, võib olla null
     */
    public <T> void prepareToDisplay(T object) {}

    public abstract void setLanguage();
}
