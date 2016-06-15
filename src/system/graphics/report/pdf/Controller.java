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
import java.util.HashMap;
import java.util.Map;
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
    private boolean companyNameValidated = false;
    private boolean reportNrValidated = false;
    private boolean workerNameValidated = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setLanguage();
        this.addValidation();
    }

    private void init() {
        this.companyName.setText(this.getData().getCompanyName());
    }

    private void addValidation() {
        MainHandler.setValidationFor(this.companyName, "^(?=\\s*\\S).*$").addListener(
                (observable, oldValue, newValue) -> {
                    this.companyNameValidated = !newValue;
                    this.checkPdfButtonValidation();
                });
        MainHandler.setValidationFor(this.reportNr, "^(?=\\s*\\S).*$").addListener(
                (observable, oldValue, newValue) -> {
                    this.reportNrValidated = !newValue;
                    this.checkPdfButtonValidation();
                });
        MainHandler.setValidationFor(this.workerName, "^(?=\\s*\\S).*$").addListener(
                (observable, oldValue, newValue) -> {
                    this.workerNameValidated = !newValue;
                    this.checkPdfButtonValidation();
                });
    }

    private void checkPdfButtonValidation() {
        boolean valid = this.companyNameValidated && this.reportNrValidated && this.workerNameValidated;
        this.createpdf.setDisable(!valid);
    }

    @FXML
    protected void handleCancel() {
        this.parentController.setButtonsDisabled(false);
        this.scene.getStageHandler().getStage().close();
    }

    @FXML
    protected void handlePdf() {
        Map<String, Object> variables = new HashMap<>();
        variables.put("logo", "LOGO"); // TODO: 15.06.2016 image or text 
        variables.put("companyname", this.companyName.getText());
        variables.put("reportnr", this.reportNr.getText());
        variables.put("workername", this.workerName.getText());
        if (MainHandler.getReportHandler().generatePdf(this.parentController.getEvent(), variables)) {
            this.getData().setCompanyName(this.companyName.getText());
            //this.parentController.getEvent().sendToArchive();
            this.scene.getStageHandler().getStage().close();
            this.parentController.setButtonsDisabled(false);
            //MainHandler.getPrimaryStageHandler().switchSceneTo(Scenetype.EVENTMANAGER);
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
