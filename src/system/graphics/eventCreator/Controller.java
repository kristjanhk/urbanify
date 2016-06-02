package system.graphics.eventCreator;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
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

import static javafx.geometry.Pos.CENTER;

/**
 * Ürituse looja controller
 */
public class Controller extends AbstractController {
    @FXML protected Text eventCreator;
    @FXML protected TextField eventText;
    @FXML protected DatePicker calendar;
    @FXML protected Text calendarLabel;
    @FXML protected TextField timeText; //TODO: 23.04.2016 picker
    @FXML protected Text timeLabel;
    @FXML protected MenuButton seating;
    @FXML protected Text seatingLabel;
    @FXML protected TextField maxSeats;
    @FXML protected Text maxSeatsLabel;
    @FXML protected Button cancel;
    @FXML protected Button next;
    @FXML protected Button addTicketButton;
    @FXML protected VBox ticketVBox;
    @FXML protected Text priceLabel;
    @FXML protected Text currencyLabel;
    @FXML protected Text ticketLabel;

    private Text popoverText;
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
        this.createMaxSeatsHelper();
        this.setLanguage();
        this.addValidation();
        this.addTicket();
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
    }

    @FXML
    protected void doNext() {
        /*this.event.readyCreator(this.eventText.getText(), this.ticketVBox.getChildren(), this.calendar.getValue(),
                this.timeText.getText(), this.seating.getText(), this.maxSeats.getText());*/
        if (seatingType.equals("OPENSEATING")) {
            this.scene.getStageHandler().switchSceneTo(Scenetype.EVENTMANAGER, this.event);
        } else {
            this.scene.getStageHandler().switchSceneTo(Scenetype.FLOORPLANNER, this.event);
        }
    }

    @Override
    public <T> void prepareToDisplay(T object) {
        if (!(object instanceof Boolean)) {
            this.scene.getStageHandler().replaceScene(Scenetype.EVENTCREATOR);
            this.scene.getStageHandler().replaceScene(Scenetype.FLOORPLANNER);
        }
    }

    @Override
    public void setLanguage() {
        this.eventCreator.setText(Word.EVENTCREATOR.toString());
        this.eventText.setPromptText(Word.NAMEYOUREVENT.toString());
        this.priceLabel.setText(Word.PRICE.toString());
        this.currencyLabel.setText(Word.CURRENCY.toString());
        this.ticketLabel.setText(Word.TICKETYPE.toString());
        this.calendar.setPromptText(Word.DATEFORMAT.toString());
        this.calendarLabel.setText(Word.SETDATE.toString());
        this.timeText.setPromptText(Word.TIMEFORMAT.toString());
        this.timeLabel.setText(Word.SETTIME.toString());
        this.seating.setText(Word.SEATINGTYPE.toString());
        this.seating.getItems().get(0).setText(Word.ASSIGNEDSEATING.toString());
        this.seating.getItems().get(1).setText(Word.OPENSEATING.toString());
        this.seatingLabel.setText(Word.SETSEATINGTYPE.toString());
        this.popoverText.setText(Word.POPOVERTEXT.toString());
        this.maxSeats.setPromptText(Word.MAXNROFSEATS.toString());
        this.maxSeatsLabel.setText(Word.SETMAXNROFSEATS.toString());
        this.cancel.setText(Word.CANCEL.toString());
        this.ticketVBox.getChildren().stream()
                .filter(node -> node instanceof Ticket)
                .forEach(node -> ((Ticket) node).setLanguage());
        this.setNextText();
    }

    private void setNextText() {
        if (this.seatingType.equals("")) {
            this.next.setText(Word.NEXT.toString());
            this.maxSeats.setVisible(false);
            this.maxSeatsLabel.setVisible(false);
        } else {
            if (this.seatingType.equals("OPENSEATING")) {
                this.next.setText(Word.CREATE.toString());
                this.maxSeats.setVisible(true);
                this.maxSeatsLabel.setVisible(true);
            } else if (this.seatingType.equals("ASSIGNEDSEATING")) {
                this.next.setText(Word.NEXT.toString());
                this.maxSeats.setVisible(false);
                this.maxSeatsLabel.setVisible(false);
            }
            this.seatingValidated = true;
            this.checkNextButtonValidation();
        }
    }

    private void createMaxSeatsHelper() {
        this.popoverText = new Text();
        this.popoverText.getStyleClass().add("popOver");
        HBox box = new HBox(this.popoverText);
        box.setPrefHeight(60);
        box.setMinHeight(60);
        box.setAlignment(CENTER);
        box.getStyleClass().add("popOver");
        this.maxSeatsPopOver = new PopOver(box);
        maxSeatsPopOver.setCornerRadius(0);
        this.maxSeatsPopOver.setArrowLocation(PopOver.ArrowLocation.RIGHT_CENTER);
        this.maxSeatsPopOver.setArrowSize(0);
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
        MainHandler.setValidationFor(this.timeText, "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$").addListener(
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
        //this.next.setDisable(!valid);
        this.next.setDisable(false);
    }

    public ObservableList<Node> getTickets() {
        return this.ticketVBox.getChildren();
    }
}
