package system.graphics.common;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import system.FileHandler;
import system.data.JsonFile;
import system.MainHandler;

public abstract class AbstractController implements Initializable {
    protected CustomScene scene;

    public void initData(CustomScene scene) {
        this.scene = scene;
        this.scene.getStageHandler().getResizeHandler().addResizeListener(this.scene);
    }

    protected FileHandler getFileHandler() {
        return MainHandler.getFileHandler();
    }

    protected JsonFile getData() {
        return this.getFileHandler().getData();
    }

    @FXML
    protected void handleStageLocationOffset(MouseEvent event) {
        if (Cursor.DEFAULT.equals(this.scene.getStageHandler().getResizeHandler().getCursor()) &&
                !this.scene.getStageHandler().getStage().isMaximized()) {
            this.scene.getStageHandler().setStageOffsets(event.getScreenX(), event.getScreenY());
        }
    }

    @FXML
    protected void handleStageLocationDrag(MouseEvent event) {
        if (Cursor.DEFAULT.equals(this.scene.getStageHandler().getResizeHandler().getCursor()) &&
                !this.scene.getStageHandler().getStage().isMaximized()) {
            this.scene.getStageHandler().setStageXY(event.getScreenX(), event.getScreenY());
        }
    }

    @FXML
    protected void handleStageMinimize() {
        this.scene.getStageHandler().getStage().setIconified(true);
    }

    @FXML
    protected void handleStageMaximize() {
        this.scene.getStageHandler().getStage().setMaximized(
                !this.scene.getStageHandler().getStage().isMaximized());

    }

    @FXML
    protected void handleStageClose() {
        Platform.exit();
    }

    public <T> void prepareToDisplay(T object) {}

    public abstract void setLanguage();
}
