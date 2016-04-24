package system.graphics.mainMenu;

import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import system.JsonFile;
import system.graphics.AbstractController;
import system.graphics.Scenetype;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller extends AbstractController implements Initializable {
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
        // TODO: 23.04.2016 init tekstid
    }

    public void openNewEvent() {
        this.scene.getStageHandler().switchSceneTo(Scenetype.EVENTCREATOR);
    }

    public void openEvents() {
        this.scene.getStageHandler().switchSceneTo(Scenetype.EVENTMANAGER);
    }

    public void openSettings() {
        this.scene.getStageHandler().switchSceneTo(Scenetype.SETTINGS);
        //MainHandler.getFileHandler().loadData();
    }

    public void openArchive() {
        this.scene.getStageHandler().switchSceneTo(Scenetype.ARCHIVE);
        //MainHandler.getFileHandler().saveData();
        System.out.println(this.getData().getWord(JsonFile.Word.CANCEL, JsonFile.Lang.ESTONIAN));
    }

    @Override
    public void prepareToDisplay(Scenetype prevSceneType) {}
}
