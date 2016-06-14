package system.graphics.report.pdf;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import system.MainHandler;
import system.data.Word;
import system.graphics.common.AbstractController;
import system.graphics.common.Scenetype;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller extends AbstractController {
    @FXML protected Text title;
    @FXML protected TextField companyName;
    @FXML protected Text companyNameLabel;
    @FXML protected TextField reportNr;
    @FXML protected Text reportNrLabel;
    @FXML protected TextField workerName;
    @FXML protected Text workerNameLabel;
    @FXML protected Button cancel;
    @FXML protected Button createpdf;

    private system.graphics.report.Controller parentController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setLanguage();
    }

    private void init() {
        this.companyName.setText(this.getData().getCompanyName());
    }

    @FXML
    protected void handleCancel() {
        this.parentController.setButtonsDisabled(false);
        this.scene.getStageHandler().getStage().close();
    }

    @FXML
    protected void handlePdf() {
        if (MainHandler.getReportHandler().generatePdf(this.parentController.getEvent())) {
            this.getData().setCompanyName(this.companyName.getText());
            this.parentController.getEvent().sendToArchive();
            this.scene.getStageHandler().getStage().close();
            this.parentController.setButtonsDisabled(false);
            MainHandler.getPrimaryStageHandler().switchSceneTo(Scenetype.EVENTMANAGER);
        }
    }

    @Override
    public <T> void prepareToDisplay(T object) {
        if (object instanceof system.graphics.report.Controller) {
            this.parentController = (system.graphics.report.Controller) object;
            this.parentController.setButtonsDisabled(true);
            this.init();
        }
    }

    @Override
    public void setLanguage() {
        this.title.setText(Word.REPORTPDF.toString());
        this.companyName.setPromptText(Word.COMPANYNAME.toString());
        this.companyNameLabel.setText(Word.COMPANYNAME.toString());
        this.reportNr.setPromptText(Word.REPORTNR.toString());
        this.reportNrLabel.setText(Word.REPORTNR.toString());
        this.workerName.setPromptText(Word.WORKERNAME.toString());
        this.workerNameLabel.setText(Word.WORKERNAME.toString());
        this.cancel.setText(Word.CANCEL.toString());
        this.createpdf.setText(Word.CREATEPDF.toString());
    }
}
