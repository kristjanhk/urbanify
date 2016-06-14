package system.graphics.settings;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import system.MainHandler;
import system.graphics.common.AbstractController;
import system.graphics.common.Csstype;
import system.graphics.common.Scenetype;
import system.data.Lang;
import system.data.Word;
import system.graphics.common.ClientScreen;

import java.io.File;
import java.net.URL;
import java.util.Base64;
import java.util.ResourceBundle;

/**
 * Seadete controller
 */
public class Controller extends AbstractController {
    @FXML protected Text settingsText;
    @FXML protected Button back;
    @FXML protected Text pathText;
    @FXML protected Text filepath;
    @FXML protected MenuButton language;
    @FXML protected MenuButton theme;
    @FXML protected Text langLabel;
    @FXML protected Text themeLabel;
    @FXML protected Pane qrContent;
    @FXML protected Button qrButton;
    @FXML protected Text qrLabel;
    @FXML protected MenuButton clientScreen;
    @FXML protected Text clientScreenLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setLanguage();
        this.filepath.setText(this.getFileHandler().getPath());
        this.language.setText(Word.valueOf(Lang.getActiveLang().toString()).inLang(Lang.getActiveLang()));
    }

    @Override
    public <T> void prepareToDisplay(T object) {
        this.qrContent.getChildren().clear();
    }

    @FXML
    protected void handleDirectoryChange() {
        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle(Word.FOLDER.toString());
        dc.setInitialDirectory(new File(System.getProperty("user.dir")));
        File sd = dc.showDialog(this.getStage());
        if (sd != null) {
            this.getFileHandler().setPath(sd.getAbsolutePath());
            this.filepath.setText(sd.getAbsolutePath());
        }
    }

    @FXML
    protected void handleThemeSwitch(ActionEvent event) {
        MainHandler.changeGlobalThemeTo(Csstype.valueOf(((MenuItem) event.getSource()).getId()));
        this.theme.setText(Word.valueOf(Csstype.getActiveTheme().toString()).toString());
        if (this.qrContent.getChildren().size() > 0) {
            this.handleQrCodeGenerate();
        }
    }

    @FXML
    protected void handleLangSwitch(ActionEvent event) {
        Lang newLang = null;
        for (Lang currentLang : Lang.values()) {
            if (((MenuItem) event.getSource()).getText().equals(currentLang.getName())) {
                newLang = currentLang;
                break;
            }
        }
        if (newLang != null) {
            Lang.setActiveLang(newLang);
            this.language.setText(Word.valueOf(newLang.toString()).inLang(newLang));
        }
    }

    @FXML
    protected void handleScreenTypeChange(ActionEvent event) {
        ClientScreen.setActiveScreenType(ClientScreen.valueOf(((MenuItem) event.getSource()).getId()));
        MainHandler.getSecondaryStageHandler().changeScreen();
        this.clientScreen.setText(Word.valueOf(ClientScreen.getActiveScreenType().name()).toString());
    }

    @FXML
    protected void handleQrCodeGenerate() {
        this.qrContent.getChildren().clear();
        this.qrContent.getChildren().add(
                MainHandler.createQrCode("Auth: " +
                        Base64.getEncoder().encodeToString(this.getData().getKeyBytes()), 320.0, 37));
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
        this.langLabel.setText(Word.LANGUAGE.toString());
        this.themeLabel.setText(Word.THEME.toString());
        this.qrButton.setText(Word.SHOWQR.toString());
        this.qrLabel.setText(Word.AUTHKEY.toString());
        this.clientScreen.setText(Word.valueOf(ClientScreen.getActiveScreenType().name()).toString());
        this.clientScreen.getItems().get(0).setText(Word.PRIMARY.toString());
        this.clientScreen.getItems().get(1).setText(Word.SECONDARY.toString());
        this.clientScreenLabel.setText(Word.SCREENTYPE.toString());
    }
}
