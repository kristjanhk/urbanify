package system.graphics.mainMenu;

import javafx.scene.control.Button;
import system.graphics.AbstractController;
import system.graphics.Scenetype;

public class Controller extends AbstractController {
    public Button next;
    public Button quit;

    public void doNext() {
        this.scene.getStageHandler().switchSceneTo(Scenetype.EVENTCREATOR);
    }

    public void doQuit() {
        System.exit(0);
    }

    public void openNewEvent() {
        this.scene.getStageHandler().switchSceneTo(Scenetype.EVENTCREATOR);
    }

    public void openEvents() {
        this.scene.getStageHandler().switchSceneTo(Scenetype.EVENTMANAGER);
    }

    public void openArchive() {
        this.scene.getStageHandler().switchSceneTo(Scenetype.ARCHIVE);
    }

    @Override
    public void prepareToDisplay() {

    }
}
