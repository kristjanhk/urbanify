package system.graphics.eventManager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import system.data.Event;
import system.graphics.common.AbstractController;
import system.graphics.common.CustomScene;
import system.graphics.common.Scenetype;
import system.data.Word;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Ürituste haldaja controller
 */
public class Controller extends AbstractController {
    @FXML protected Text eventsText;
    @FXML protected VBox eventsVBox;
    @FXML protected Button backButton;

    private boolean initialized = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    @FXML
    protected void doBack() {
        this.scene.getStageHandler().switchSceneTo(Scenetype.MAINMENU);
    }

    public CustomScene getScene() {
        return this.scene;
    }

    public VBox getEventsVBox() {
        return this.eventsVBox;
    }

    public HashSet<Event> getEvents() {
        this.checkInitialization();
        // TODO: 5.05.2016 eventide järjekord
        return this.eventsVBox.getChildren().stream().filter(node -> node instanceof EventLine).
                map(node -> ((EventLine) node).getEvent()).collect(Collectors.toCollection(HashSet::new));
    }

    public void addEventLine(Event event) {
        this.eventsVBox.getChildren().add(new EventLine(this, event));
    }

    private void checkInitialization() {
        if (!this.initialized) {
            this.setLanguage();
            for (Event event : this.getData().getEvents(this.getScene().getScenetype())) {
                this.eventsVBox.getChildren().add(new EventLine(this, event));
            }
            this.initialized = true;
        }
    }

    @Override
    public void setLanguage() {
        if (this.scene.getScenetype() == Scenetype.EVENTMANAGER) {
            this.eventsText.setText(Word.EVENTS.toString());
        } else if (this.scene.getScenetype() == Scenetype.ARCHIVE){
            this.eventsText.setText(Word.ARCHIVE.toString());
        }
        this.backButton.setText(Word.BACK.toString());
    }

    @Override
    public <T> void prepareToDisplay(T object) {
        this.checkInitialization();
        if (object instanceof Event) {
            this.eventsVBox.getChildren().add(new EventLine(this, ((Event) object)));
        }
    }
}
