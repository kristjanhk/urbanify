package system.graphics.archive;


import system.graphics.AbstractController;
import system.graphics.Scenetype;

public class Controller extends AbstractController {


    // TODO: 21.04.2016 to be changed

    public void doNext() {

    }

    public void doBack() {
        this.scene.getStageHandler().switchSceneTo(Scenetype.MAINMENU);
    }


    @Override
    public void prepareToDisplay() {

    }
}
