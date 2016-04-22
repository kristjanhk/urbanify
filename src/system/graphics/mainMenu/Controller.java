package system.graphics.mainMenu;

import javafx.scene.image.ImageView;
import system.graphics.AbstractController;
import system.graphics.Scenetype;

public class Controller extends AbstractController {
    public ImageView newEvent;
    public ImageView events;
    public ImageView archive;

    public void openNewEvent() {
        this.scene.getStageHandler().switchSceneTo(Scenetype.EVENTCREATOR);
    }

    public void openEvents() {
        // FIXME: 21.04.2016
        this.scene.getStageHandler().switchSceneTo(Scenetype.EVENTMANAGER);
    }

    public void openSettings() {
        this.scene.getStageHandler().switchSceneTo(Scenetype.SETTINGS);
    }

    public void openArchive() {
        // FIXME: 21.04.2016
        this.scene.getStageHandler().switchSceneTo(Scenetype.EVENTCREATOR);
    }

    @Override
    public void prepareToDisplay() {

    }
}
