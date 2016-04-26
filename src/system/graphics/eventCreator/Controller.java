package system.graphics.eventCreator;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import system.graphics.common.AbstractController;
import system.graphics.common.Scenetype;
import system.settings.Word;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller extends AbstractController implements Initializable {
    public Text eventCreator;
    public TextField eventText;
    public DatePicker calendar;
    public Text calendarLabel;
    public TextField timeText; // TODO: 23.04.2016 picker
    public Text timeLabel;
    public CheckBox openSeating;
    public CheckBox assignedSeating;
    public Button cancel;
    public Button next;
    public Button create;
    public Button addTicketButton;
    public VBox ticketVBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setLanguage();
        this.eventText.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                if (!this.eventText.getText().equals("tere")) { // TODO: 23.04.2016 pattern matching
                    this.eventText.setText("");
                }
            }
        });
        // TODO: 23.04.2016 add pattern listeners
    }

    public void addTicket() {
        this.ticketVBox.getChildren().add(this.ticketVBox.getChildren().size() - 1, new Ticket(this.ticketVBox));
    }

    public void doCancel() {
        this.scene.getStageHandler().switchSceneTo(Scenetype.MAINMENU);
        // TODO: 26.04.2016 remove procedure?
        this.scene.getStageHandler().replaceScene(this.scene.getScenetype());
    }

    public void doNext() {
        // FIXME: 21.04.2016
        this.scene.getStageHandler().switchSceneTo(Scenetype.FLOORPLANNER);
    }

    public void doCreate() {

    }

    @Override
    public void prepareToDisplay(Scenetype prevSceneType) {}

    @Override
    public void setLanguage() {
        this.eventCreator.setText(Word.EVENTCREATOR.toString());
        this.eventText.setPromptText(Word.NAMEYOUREVENT.toString());
        this.calendar.setPromptText(Word.DATEFORMAT.toString());
        this.calendarLabel.setText(Word.SETDATE.toString());
        this.timeText.setPromptText(Word.TIMEFORMAT.toString());
        this.timeLabel.setText(Word.SETTIME.toString());
        this.cancel.setText(Word.CANCEL.toString());
        this.next.setText(Word.NEXT.toString());
        this.create.setText(Word.CREATE.toString());
        this.ticketVBox.getChildren().stream()
                .filter(node -> node instanceof Ticket)
                .forEach(node -> ((Ticket) node).setLanguage());
    }
}
