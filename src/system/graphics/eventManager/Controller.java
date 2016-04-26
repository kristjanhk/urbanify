package system.graphics.eventManager;


import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import system.graphics.common.AbstractController;
import system.graphics.common.Scenetype;
import system.settings.Word;

public class Controller extends AbstractController {
    public Text eventsText;
    public VBox eventsVBox;
    public Button backButton;
    private int testint = 0;


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

    public void doTest() {
        this.testint += 1;
        this.eventsVBox.getChildren().add(new Event(this.eventsVBox,
                "Kogu küla lõõtsafest " + testint, "1/4/201" + testint, "13:0" + testint));
    }


    @Override
    public void prepareToDisplay(Scenetype prevSceneType) {

    }

    @Override
    public void setLanguage() {
        this.eventsText.setText(Word.EVENTS.toString());
        this.backButton.setText(Word.BACK.toString());
    }
}
