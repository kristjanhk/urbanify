package system.graphics.mainMenu;

import javafx.scene.control.Button;
import system.graphics.AbstractController;
import system.graphics.Scenetype;

public class Controller extends AbstractController{
    public Button next;
    public Button quit;

    public void doNext() {
        this.stageHandler.switchSceneTo(Scenetype.EVENTCREATOR);
    }

    public void doQuit() {
        System.out.println("quit pressed");
    }
}
