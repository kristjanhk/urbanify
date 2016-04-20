package system.graphics.eventManager;


import system.graphics.AbstractController;
import system.graphics.Scenetype;

public class Controller extends AbstractController {


    // TODO: 14.04.2016 to be changed

    public void openEvent(int id) {

    }

    public void removeEvent(int id) {

    }

    public void doBack() {
        this.scene.getStageHandler().switchSceneTo(Scenetype.MAINMENU);
    }

    public void doNext() {

    }


    @Override
    public void prepareToDisplay() {

    }
}
