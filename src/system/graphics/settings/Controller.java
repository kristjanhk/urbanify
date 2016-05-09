package system.graphics.settings;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import system.MainHandler;
import system.graphics.common.AbstractController;
import system.graphics.common.Csstype;
import system.graphics.common.Scenetype;
import system.data.Lang;
import system.data.Word;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Seadete controller
 */
public class Controller extends AbstractController {
    public Text settingsText;
    public Button back;
    public Text pathText;
    public Text filepath;
    public MenuButton language;
    public MenuButton theme;
    // TODO: 29.04.2016 location path textflow peale või labeliks


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setLanguage();
        this.filepath.setText(this.getFileHandler().getPath());
        this.language.setText(Word.valueOf(Lang.getActiveLang().toString()).inLang(Lang.getActiveLang()));
    }

    @FXML
    protected void handleDirectoryChange() {
        DirectoryChooser dc = new DirectoryChooser();
        File sd = dc.showDialog(this.getStage());
        if (sd != null) {
            this.getFileHandler().setPath(sd.getAbsolutePath());
            this.filepath.setText(sd.getAbsolutePath());
        }
    }

    @FXML
    protected void handleThemeSwitch(ActionEvent event) {
        MainHandler.changeGlobalThemeTo(Csstype.valueOf(((MenuItem) event.getSource()).getId()));
        // TODO: 29.04.2016 warm theme
        this.theme.setText(Word.valueOf(Csstype.getActiveTheme().toString()).toString());
    }

    @FXML
    protected void handleLangSwitch(ActionEvent event) {
        Lang lang = null;
        switch (((MenuItem) event.getSource()).getText()) {
            case "english":
                lang = Lang.ENGLISH;
                break;
            case "eesti keel":
                lang = Lang.ESTONIAN;
                break;
            case "võro kiil":
                lang = Lang.VÕRO;
                break;
            case "deutsch":
                lang = Lang.GERMAN;
                break;
            case "pусский язык":
                lang = Lang.RUSSIAN;
                break;
        }
        if (lang != null) {
            Lang.setActiveLang(lang);
            this.language.setText(Word.valueOf(lang.toString()).inLang(lang));
        }
    }

    @FXML
    protected void doBack() {
        this.scene.getStageHandler().switchSceneTo(Scenetype.MAINMENU);
    }

    @Override
    public void setLanguage() {
        this.settingsText.setText(Word.SETTINGS.toString());
        this.back.setText(Word.BACK.toString());
        this.pathText.setText(Word.PATH.toString());
        this.theme.setText(Word.valueOf(Csstype.getActiveTheme().toString()).toString());
        this.theme.getItems().get(0).setText(Word.LIGHT.toString());
        this.theme.getItems().get(1).setText(Word.DARK.toString());
        this.theme.getItems().get(2).setText(Word.WARM.toString());
    }
}
