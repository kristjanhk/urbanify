package system.graphics.settings;


import system.graphics.common.AbstractController;
import system.graphics.common.Scenetype;

public class Controller extends AbstractController {


    // TODO: 21.04.2016 to be changed

    public void doNext() {

    }

    public void doBack() {
        this.scene.getStageHandler().switchSceneTo(Scenetype.MAINMENU);
    }


    @Override
    public void prepareToDisplay(Scenetype prevSceneType) {

    }

    @Override
    public void setLanguage() {

    }
}
