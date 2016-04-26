package system.graphics.pointOfSale;


import system.graphics.AbstractController;
import system.graphics.Scenetype;

public class Controller extends AbstractController {

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
