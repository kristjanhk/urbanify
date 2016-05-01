package system.graphics.archive;


import system.graphics.common.AbstractController;
import system.graphics.common.Scenetype;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller extends AbstractController {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    // TODO: 21.04.2016 to be changed

    public void doNext() {

    }

    public void doBack() {
        this.scene.getStageHandler().switchSceneTo(Scenetype.MAINMENU);
    }

    @Override
    public void setLanguage() {

    }
}
