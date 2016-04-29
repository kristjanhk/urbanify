package system.graphics.settings;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.text.Text;
import system.MainHandler;
import system.graphics.common.AbstractController;
import system.graphics.common.Csstype;
import system.graphics.common.Scenetype;
import system.settings.Word;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller extends AbstractController {
    public Text settingsText;
    public Button back;
    public Button apply;
    public Button ok;
    public Text pathText;
    public Text filepath;
    public MenuButton language;
    public MenuButton theme;
    public CheckBox fullscreen;
    // TODO: 29.04.2016 remove ok button??
    // TODO: 29.04.2016 keep only back button??
    // TODO: 29.04.2016 location path  textflow peale või labeliks


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setLanguage();
    }

    @FXML
    public void handleThemeSwitch(ActionEvent event) {
        switch (((MenuItem) event.getSource()).getId()) {
            case "light":
                // FIXME: 29.04.2016 global change
                MainHandler.changeSceneThemeTo(this.scene, Csstype.LIGHT);
                break;
            case "dark":
                // FIXME: 29.04.2016 global change
                MainHandler.changeSceneThemeTo(this.scene, Csstype.DARK);
                break;
            case "warm":
                //MainHandler.changeGlobalThemeTo(Csstype.WARM);
                // TODO: 29.04.2016 warm theme
                break;
        }

    }

    public void doBack() {
        // TODO: 29.04.2016 reset scene??
        this.scene.getStageHandler().switchSceneTo(Scenetype.MAINMENU);
    }

    public void doApply() {
        // TODO: 29.04.2016 save scene??
        this.scene.getStageHandler().switchSceneTo(Scenetype.MAINMENU);
    }

    public void doOk() {
        this.scene.getStageHandler().switchSceneTo(Scenetype.MAINMENU);
    }

    @Override
    public void prepareToDisplay(Scenetype prevSceneType) {
        // TODO: 29.04.2016 save current scene state???
    }

    @Override
    public void setLanguage() {
        this.settingsText.setText(Word.SETTINGS.toString());
        this.back.setText(Word.BACK.toString());
        this.apply.setText(Word.APPLY.toString());
        this.ok.setText(Word.OK.toString());
        this.pathText.setText(Word.PATH.toString());
        // FIXME: 29.04.2016 insert default path if not in jsonfile
        this.filepath.setText("C:\\Users\\Kristen\\Google Drive\\Projects\\kassa3000\\graafika\\icons");
        this.language.setText(Word.LANGUAGE.toString());
        this.theme.setText(Word.THEME.toString());
        this.theme.getItems().get(0).setText(Word.LIGHT.toString());
        this.theme.getItems().get(1).setText(Word.DARK.toString());
        this.theme.getItems().get(2).setText(Word.WARM.toString());
        this.fullscreen.setText(Word.FULLSCREEN.toString());
    }
}
