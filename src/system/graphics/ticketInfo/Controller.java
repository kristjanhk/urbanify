package system.graphics.ticketInfo;

import system.graphics.common.AbstractController;
import system.graphics.common.ClientScreen;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller extends AbstractController {

    private system.graphics.pointOfSale.Controller parentController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setLanguage();
    }

    public void init() {
        if (ClientScreen.getActiveScreenType().equals(ClientScreen.SECONDARY)) {
            this.scene.getStageHandler().showStage();
        }
    }

    @Override
    public <T> void prepareToDisplay(T object) {
        if (object instanceof system.graphics.pointOfSale.Controller) {
            this.parentController = (system.graphics.pointOfSale.Controller) object;
            this.parentController.setClientScreenController(this);
        }
    }

    @Override
    public void setLanguage() {

    }
}
