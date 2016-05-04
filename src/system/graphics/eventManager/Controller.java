package system.graphics.eventManager;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import system.data.Event;
import system.graphics.common.AbstractController;
import system.graphics.common.CustomScene;
import system.graphics.common.Scenetype;
import system.data.Word;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller extends AbstractController {
    public Text eventsText;
    public VBox eventsVBox;
    public Button backButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setLanguage();
    }

    public void doBack() {
        this.scene.getStageHandler().switchSceneTo(Scenetype.MAINMENU);
    }

    public CustomScene getScene() {
        return this.scene;
    }

    public VBox getEventsVBox() {
        return this.eventsVBox;
    }

    @Override
    public void setLanguage() {
        this.eventsText.setText(Word.EVENTS.toString());
        this.backButton.setText(Word.BACK.toString());
    }

    @Override
    public <T> void prepareToDisplay(T object) {
        if (object instanceof Event) {
            this.eventsVBox.getChildren().add(new EventLine(this, ((Event) object)));
        }
    }
}
