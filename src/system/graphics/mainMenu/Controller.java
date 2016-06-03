package system.graphics.mainMenu;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import system.data.Lang;
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
    @FXML protected ColumnConstraints constraint;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setLanguage();
    }

    @FXML protected void openNewEvent() {
        this.scene.getStageHandler().switchSceneTo(Scenetype.EVENTCREATOR);
    }

    @FXML protected void openEvents() {
        this.scene.getStageHandler().switchSceneTo(Scenetype.EVENTMANAGER);
    }

    @FXML protected void openSettings() {
        this.scene.getStageHandler().switchSceneTo(Scenetype.SETTINGS);
    }

    @FXML protected void openArchive() {
        this.scene.getStageHandler().switchSceneTo(Scenetype.ARCHIVE);
    }

    @Override
    public void setLanguage() {
        if (Lang.getActiveLang() == Lang.RUSSIAN) {
            this.constraint.setMaxWidth(200.0);
            this.constraint.setMinWidth(200.0);
            this.constraint.setPrefWidth(200.0);
        } else {
            this.constraint.setMaxWidth(90.0);
            this.constraint.setMinWidth(90.0);
            this.constraint.setPrefWidth(90.0);
        }
        this.newEventText.setText(Word.NEWEVENT.toString());
        this.eventsText.setText(Word.EVENTS.toString());
        this.settingsText.setText(Word.SETTINGS.toString());
        this.archiveText.setText(Word.ARCHIVE.toString());
    }
}
