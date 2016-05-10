package system.graphics.mainMenu;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import system.graphics.common.AbstractController;
import system.graphics.common.Scenetype;
import system.data.Word;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Peamenüü controller
 */
public class Controller extends AbstractController {
    @FXML protected ImageView newEventImage;
    @FXML protected ImageView eventsImage;
    @FXML protected ImageView settingsImage;
    @FXML protected ImageView archiveImage;
    @FXML protected Text newEventText;
    @FXML protected Text eventsText;
    @FXML protected Text settingsText;
    @FXML protected Text archiveText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setLanguage();
    }

    public void openNewEvent() {
        this.scene.getStageHandler().switchSceneTo(Scenetype.EVENTCREATOR, null);
    }

    public void openEvents() {
        this.scene.getStageHandler().switchSceneTo(Scenetype.EVENTMANAGER, null);
    }

    public void openSettings() {
        this.scene.getStageHandler().switchSceneTo(Scenetype.SETTINGS);
    }

    public void openArchive() {
        this.scene.getStageHandler().switchSceneTo(Scenetype.ARCHIVE, null);
    }

    @Override
    public void setLanguage() {
        this.newEventText.setText(Word.NEWEVENT.toString());
        this.eventsText.setText(Word.EVENTS.toString());
        this.settingsText.setText(Word.SETTINGS.toString());
        this.archiveText.setText(Word.ARCHIVE.toString());
    }
}
