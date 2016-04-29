package system.graphics.pointOfSale;


import system.graphics.common.AbstractController;
import system.graphics.common.Scenetype;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller extends AbstractController {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    // TODO: 14.04.2016 to be changed

    public void doNext() {

    }

    public void doBack() {
        this.scene.getStageHandler().switchSceneTo(Scenetype.MAINMENU);
    }

    public void doCheckout() {

    }

    public void addTicketCount(int id) {

    }

    public void removeTicketCount(int id) {

    }

    public void selectSeat(int id) {

    }

    @Override
    public void prepareToDisplay(Scenetype prevSceneType) {

    }

    @Override
    public void setLanguage() {

    }
}
