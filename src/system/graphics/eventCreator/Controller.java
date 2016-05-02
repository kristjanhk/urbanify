package system.graphics.eventCreator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.controlsfx.control.PopOver;
import system.MainHandler;
import system.graphics.common.AbstractController;
import system.graphics.common.Scenetype;
import system.data.Event;
import system.data.Word;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller extends AbstractController {
    public Text eventCreator;
    public TextField eventText;
    public DatePicker calendar;
    public Text calendarLabel;
    public TextField timeText; // TODO: 23.04.2016 picker
    public Text timeLabel;
    public MenuButton seating;
    public Text seatingLabel;
    public TextField maxSeats;
    public Text maxSeatsLabel;
    public Button cancel;
    public Button next;
    public Button addTicketButton;
    public VBox ticketVBox;
    private String seatingType = "";
    private PopOver maxSeatsPopOver;
    private Event event = new Event();
    private boolean eventTextValidated = false;
    private boolean calendarValidated = false;
    private boolean timeTextValidated = false;
    private boolean seatingValidated = false;
    private boolean maxSeatsValidated = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setLanguage();
        this.createMaxSeatsHelper();
        this.addValidation();
    }

    @FXML
    protected void addTicket() {
        this.ticketVBox.getChildren().add(this.ticketVBox.getChildren().size() - 1,
                new Ticket(this, this.ticketVBox));
    }

    @FXML
    protected void handleSeatingSwitch(ActionEvent event) {
        this.seatingType = ((MenuItem) event.getSource()).getId();
        this.seating.setText(Word.valueOf(this.seatingType).toString());
        this.setNextText();
    }

    @FXML
    protected void doCancel() {
        this.scene.getStageHandler().switchSceneTo(Scenetype.MAINMENU);
        // TODO: 26.04.2016 remove procedure? // TODO: 1.05.2016 for floorplan too 
        this.scene.getStageHandler().replaceScene(this.scene.getScenetype());
    }

    @FXML
    protected void doNext() {
        // FIXME: 21.04.2016
        this.scene.getStageHandler().switchSceneTo(Scenetype.FLOORPLANNER);
        this.event.readyCreator(this.eventText.getText(), this.ticketVBox.getChildren(), this.calendar.getValue(),
                this.timeText.getText(), this.seating.getText(), this.maxSeats.getText());
    }

    @Override
    public void setLanguage() {
        this.eventCreator.setText(Word.EVENTCREATOR.toString());
        this.eventText.setPromptText(Word.NAMEYOUREVENT.toString());
        this.calendar.setPromptText(Word.DATEFORMAT.toString());
        this.calendarLabel.setText(Word.SETDATE.toString());
        this.timeText.setPromptText(Word.TIMEFORMAT.toString());
        this.timeLabel.setText(Word.SETTIME.toString());
        this.seating.setText(Word.SEATINGTYPE.toString());
        this.seating.getItems().get(0).setText(Word.ASSIGNEDSEATING.toString());
        this.seating.getItems().get(1).setText(Word.OPENSEATING.toString());
        this.seatingLabel.setText(Word.SETSEATINGTYPE.toString());
        this.maxSeats.setPromptText(Word.MAXNROFSEATS.toString()); // TODO: 1.05.2016 font size 
        this.maxSeatsLabel.setText(Word.SETMAXNROFSEATS.toString());
        this.cancel.setText(Word.CANCEL.toString());
        this.ticketVBox.getChildren().stream()
                .filter(node -> node instanceof Ticket)
                .forEach(node -> ((Ticket) node).setLanguage());
        this.setNextText();
    }

    private void setNextText() {
        if (this.seatingType.equals("")) {
            this.next.setText("next");
            this.maxSeats.setVisible(false);
            this.maxSeatsLabel.setVisible(false);
        } else {
            if (this.seatingType.equals("OPENSEATING")) {
                this.next.setText("next");
                this.maxSeats.setVisible(true);
                this.maxSeatsLabel.setVisible(true);
            } else if (this.seatingType.equals("ASSIGNEDSEATING")) {
                this.next.setText("create");
                this.maxSeats.setVisible(false);
                this.maxSeatsLabel.setVisible(false);
            }
            this.seatingValidated = true;
            this.checkNextButtonValidation();
        }
    }

    private void createMaxSeatsHelper() {
        this.maxSeatsPopOver = new PopOver(new Text("enter 0 for no limit"));
        this.maxSeatsPopOver.setArrowLocation(PopOver.ArrowLocation.RIGHT_CENTER);
        this.maxSeats.setOnMouseEntered(event -> this.maxSeatsPopOver.show(this.maxSeats));
        this.maxSeats.setOnMouseExited(event -> this.maxSeatsPopOver.hide());
    }

    private void addValidation() {
        MainHandler.setValidationFor(this.eventText, "^(?=\\s*\\S).*$").addListener(
                (observable, oldValue, newValue) -> {
                    this.eventTextValidated = !newValue;
                    this.checkNextButtonValidation();
                });
        MainHandler.setValidationFor(this.calendar.getEditor(), "^(?=\\s*\\S).*$").addListener(
                (observable, oldValue, newValue) -> {
                    this.calendarValidated = !newValue;
                    this.checkNextButtonValidation();
                });
        MainHandler.setValidationFor(this.timeText, "^[0-2][0-9]:[0-5][0-9]$").addListener(
                (observable, oldValue, newValue) -> {
                    this.timeTextValidated = !newValue;
                    this.checkNextButtonValidation();
                });
        MainHandler.setValidationFor(this.maxSeats, "^\\d+$").addListener(
                (observable, oldValue, newValue) -> {
                    this.maxSeatsValidated = !newValue;
                    this.checkNextButtonValidation();
                });
    }

    public void checkNextButtonValidation() {
        boolean valid = this.eventTextValidated && this.calendarValidated && this.timeTextValidated &&
                this.seatingValidated;
        if (!this.seatingType.equals("ASSIGNEDSEATING") && !this.maxSeatsValidated) {
            valid = false;
        } else if (this.ticketVBox.getChildren().size() < 2) {
            valid = false;
        }
        for (Node node : this.ticketVBox.getChildren()) {
            if (node instanceof Ticket && !((Ticket) node).isValid()) {
                valid = false;
                break;
            }
        }
        this.next.setDisable(!valid);
    }
}
