package system.graphics.report;

import javafx.fxml.FXML;
import system.graphics.common.AbstractController;
import system.graphics.common.Scenetype;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Aruande controller
 */
public class Controller extends AbstractController {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setLanguage();
    }

    @FXML
    protected void doBack() {
        this.scene.getStageHandler().switchSceneTo(Scenetype.EVENTMANAGER);
    }

    @Override
    public <T> void prepareToDisplay(T object) {

    }

    @Override
    public void setLanguage() {

    }
}
