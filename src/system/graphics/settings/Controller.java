package system.graphics.settings;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import system.MainHandler;
import system.graphics.common.AbstractController;
import system.graphics.common.Csstype;
import system.graphics.common.Scenetype;
import system.settings.Lang;
import system.settings.Word;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller extends AbstractController {
    public Text settingsText;
    public Button back;
    public Text pathText;
    public Text filepath;
    public MenuButton language;
    public MenuButton theme;
    public CheckBox fullscreen;
    // TODO: 29.04.2016 location path  textflow peale või labeliks


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setLanguage();
        this.filepath.setText(this.getFileHandler().getPath());
    }

    @FXML
    protected void handleDirectoryChange() {
        DirectoryChooser dc = new DirectoryChooser();
        File sd = dc.showDialog(this.scene.getStageHandler().getStage());
        if (sd != null) {
            this.getFileHandler().setPath(sd.getAbsolutePath());
            this.filepath.setText(sd.getAbsolutePath());
        }
    }

    @FXML
    protected void handleThemeSwitch(ActionEvent event) {
        switch (((MenuItem) event.getSource()).getId()) {
            case "light":
                MainHandler.changeGlobalThemeTo(Csstype.LIGHT);
                break;
            case "dark":
                MainHandler.changeGlobalThemeTo(Csstype.DARK);
                break;
            case "warm":
                //MainHandler.changeGlobalThemeTo(Csstype.WARM);
                // TODO: 29.04.2016 warm theme
                break;
        }

    }

    @FXML
    protected void handleLangSwitch(ActionEvent event) {
        switch (((MenuItem) event.getTarget()).getText()) {
            case "english":
                Lang.setActiveLang(Lang.ENGLISH);
                break;
            case "eesti keel":
                Lang.setActiveLang(Lang.ESTONIAN);
                break;
            case "deutsch":
                Lang.setActiveLang(Lang.GERMAN);
                break;
            case "pусский язык":
                Lang.setActiveLang(Lang.RUSSIAN);
                break;
            case "võro kiil":
                Lang.setActiveLang(Lang.VÕRO);
                break;
        }
    }

    @FXML
    protected void doBack() {
        this.scene.getStageHandler().switchSceneTo(Scenetype.MAINMENU);
    }

    @Override
    public void prepareToDisplay(Scenetype prevSceneType) {

    }

    @Override
    public void setLanguage() {
        this.settingsText.setText(Word.SETTINGS.toString());
        this.back.setText(Word.BACK.toString());
        this.pathText.setText(Word.PATH.toString());
        this.language.setText(Word.LANGUAGE.toString());
        this.theme.setText(Word.THEME.toString());
        this.theme.getItems().get(0).setText(Word.LIGHT.toString());
        this.theme.getItems().get(1).setText(Word.DARK.toString());
        this.theme.getItems().get(2).setText(Word.WARM.toString());
        this.fullscreen.setText(Word.FULLSCREEN.toString());
    }
}
