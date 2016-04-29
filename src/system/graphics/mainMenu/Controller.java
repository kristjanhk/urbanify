package system.graphics.mainMenu;

import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import system.graphics.common.AbstractController;
import system.graphics.common.Scenetype;
import system.settings.Word;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller extends AbstractController {
    public ImageView newEventImage;
    public ImageView eventsImage;
    public ImageView settingsImage;
    public ImageView archiveImage;
    public Text newEventText;
    public Text eventsText;
    public Text settingsText;
    public Text archiveText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setLanguage();
    }

    public void openNewEvent() {
        this.scene.getStageHandler().switchSceneTo(Scenetype.EVENTCREATOR);
    }

    public void openEvents() {
        this.scene.getStageHandler().switchSceneTo(Scenetype.EVENTMANAGER);
    }

    public void openSettings() {
        this.scene.getStageHandler().switchSceneTo(Scenetype.SETTINGS);
    }

    public void openArchive() {
        //this.scene.getStageHandler().switchSceneTo(Scenetype.ARCHIVE);
        //Lang.setActiveLang(Lang.values()[new Random().nextInt(Lang.values().length)]);
        DirectoryChooser dc = new DirectoryChooser();
        File selecteddirectory = dc.showDialog(this.scene.getStageHandler().getStage());

        if (selecteddirectory == null) {
            System.out.println("Selected dir is null!");
        } else {
            System.out.println(selecteddirectory.getAbsolutePath());
        }
    }

    @Override
    public void prepareToDisplay(Scenetype prevSceneType) {}

    @Override
    public void setLanguage() {
        this.newEventText.setText(Word.NEWEVENT.toString());
        this.eventsText.setText(Word.EVENTS.toString());
        this.settingsText.setText(Word.SETTINGS.toString());
        this.archiveText.setText(Word.ARCHIVE.toString());
    }
}
