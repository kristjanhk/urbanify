package system.graphics.eventCreator;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.controlsfx.validation.ValidationSupport;
import system.MainHandler;
import system.graphics.common.AbstractController;
import system.graphics.common.Scenetype;
import system.data.Event;
import system.data.Word;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Ürituse looja controller
 */
public class Controller extends AbstractController {
    @FXML protected Text eventCreator;
    @FXML protected TextField eventText;
    @FXML protected DatePicker calendar;
    @FXML protected Text calendarLabel;
    @FXML protected TextField timeText;
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
    @FXML protected Text ticketLabel;

    private String seatingType = "";
    private Event event;
    private boolean fullyUpdateable = true;
    private boolean eventTextValidated = false;
    private boolean calendarValidated = false;
    private boolean timeTextValidated = false;
    private boolean seatingValidated = false;
    private boolean maxSeatsValidated = true;
    private ValidationSupport maxSeatsValidation;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.event = new Event();
        this.setLanguage();
        this.addValidation();
        this.addTicket();
    }

    public boolean isFullyUpdateable() {
        return this.fullyUpdateable;
    }

    @FXML
    protected void addTicket() {
        this.ticketVBox.getChildren().add(this.ticketVBox.getChildren().size() - 1, new Ticket(this, this.ticketVBox));
    }

    private void addTicket(String name, String price, String currency) {
        this.ticketVBox.getChildren().add(this.ticketVBox.getChildren().size() - 1,
                new Ticket(this, this.ticketVBox, name, price, currency));
    }


    public ObservableList<Node> getTickets() {
        return this.ticketVBox.getChildren();
    }

    @FXML
    protected void handleSeatingSwitch(ActionEvent event) {
        this.seatingType = ((MenuItem) event.getSource()).getId();
        this.seating.setText(Word.valueOf(this.seatingType).toString());
        this.setNextText();
    }

    @FXML
    protected void doCancel() {
        if (this.event.isActive()) {
            this.scene.getStageHandler().switchSceneTo(Scenetype.REPORT);
        } else {
            this.scene.getStageHandler().switchSceneTo(Scenetype.MAINMENU);
        }
    }

    @FXML
    protected void doNext() {
        this.event.readyCreator(this.eventText.getText(), this.ticketVBox.getChildren(),
                ((Ticket) this.ticketVBox.getChildren().get(0)).getCurrencyText(), this.calendar.getValue(),
                this.timeText.getText(), this.maxSeats.getText());
        if (seatingType.equals("OPENSEATING")) {
            if (this.event.isActive()) {
                this.scene.getStageHandler().switchSceneTo(Scenetype.REPORT, this.event);
            } else {
                this.event.setActive();
                this.scene.getStageHandler().switchSceneTo(Scenetype.EVENTMANAGER, this.event);
            }
        } else {
            if (this.event.isActive()) {
                this.scene.getStageHandler().switchSceneTo(Scenetype.REPORT, this.event);
            } else {
                this.scene.getStageHandler().switchSceneTo(Scenetype.FLOORPLANNER, this.event);
            }

        }
    }

    @Override
    public <T> void prepareToDisplay(T object) {
        if (object instanceof Event) {
            this.loadFromEvent((Event) object);
        } else if (!(object instanceof Boolean)) {
            this.scene.getStageHandler().replaceScene(Scenetype.EVENTCREATOR);
            this.scene.getStageHandler().replaceScene(Scenetype.FLOORPLANNER);
        }
    }

    private void loadFromEvent(Event event) {
        this.event = event;
        this.ticketVBox.getChildren().remove(0);
        for (String ticket : event.getTickets().keySet()) {
            if (event.getTicketAmount(ticket) != 0) {
                this.fullyUpdateable = false;
            }
            this.addTicket(ticket, String.valueOf(event.getTicketPrice(ticket)), event.getCurrency());
        }
        if (!this.fullyUpdateable) {
            this.ticketVBox.getChildren().stream().filter(node -> node instanceof Ticket).forEach(
                    node -> ((Ticket) node).disable(event.getTicketAmount(((Ticket) node).getType()) > 0));
        }
        this.eventCreator.setText(Word.EVENTUPDATER.toString());
        this.eventText.setText(event.getName());
        this.eventTextValidated = true;
        this.eventText.setDisable(!this.fullyUpdateable);
        this.calendar.setValue(event.getDate());
        this.calendarValidated = true;
        this.calendar.setDisable(!this.fullyUpdateable);
        this.timeText.setText(event.getTime());
        this.timeTextValidated = true;
        this.timeText.setDisable(!this.fullyUpdateable);
        this.seating.setDisable(true);
        if (event.getFloorPlan() != null) {
            this.seating.setText(Word.ASSIGNEDSEATING.toString());
            this.seatingType = "ASSIGNEDSEATING";
            this.setNextText();
        } else {
            this.seating.setText(Word.OPENSEATING.toString());
            this.seatingType = "OPENSEATING";
            this.setNextText();
            this.maxSeats.setText(String.valueOf(
                    event.getMaxSeats() == -1 ? "" : event.getMaxSeats() + event.getTotalTicketAmount()));
            this.next.setText(Word.UPDATE.toString());
            this.loadValidation(event);
        }
    }

    //726 72[6-9] (726-729) | 7[3-9]\\d    (730-799) | [8-9]\\d\\d  (800-999) | [1-9]\\d{3,} (1000-)
    //472 47[2-9] (472-479) | 4[8-9]\\d    (480-499) | [5-9]\\d\\d  (500-999) | [1-9]\\d{3,} (1000-)
    //38  3[8-9]  (38-39)   | [4-9]\\d     (40-99)   | [1-9]\\d{2,} (100-)
    //27  2[7-9]  (27-29)   | [3-9]\\d     (30-99)   | [1-9]\\d{2,} (100-)
    //4   [4-9]   (4-9)     | [1-9]\\d{1,} (10-)
    private void loadValidation(Event event) {
        String ustring = String.valueOf(event.getTotalTicketAmount());
        StringBuilder validation = new StringBuilder("^$|^");
        for (int i = 1; i < ustring.length() + 1; i++) {
            validation.append(ustring.substring(0, ustring.length() - i));
            validation.append("[");
            if (i == 1) {
                validation.append(ustring.charAt(ustring.length() - i));
            } else {
                validation.append(String.valueOf(Character.getNumericValue(ustring.charAt(ustring.length() - i)) + 1));
            }
            validation.append("-9]");
            for (int j = 0; j < i - 1; j++) {
                validation.append("\\d");
            }
            validation.append("|");
        }
        validation.append("[1-9]\\d{");
        validation.append(ustring.length());
        validation.append(",}$");
        MainHandler.registerValidator(this.maxSeatsValidation, this.maxSeats, validation.toString());
    }

    @Override
    public void setLanguage() {
        this.eventCreator.setText(Word.EVENTCREATOR.toString());
        this.eventText.setPromptText(Word.NAMEYOUREVENT.toString());
        this.priceLabel.setText(Word.PRICE.toString());
        this.ticketLabel.setText(Word.TICKETYPE.toString());
        this.calendar.setPromptText(Word.DATEFORMAT.toString());
        this.calendarLabel.setText(Word.SETDATE.toString());
        this.timeText.setPromptText(Word.TIMEFORMAT.toString());
        this.timeLabel.setText(Word.SETTIME.toString());
        this.seating.setText(Word.SEATINGTYPE.toString());
        this.seating.getItems().get(0).setText(Word.ASSIGNEDSEATING.toString());
        this.seating.getItems().get(1).setText(Word.OPENSEATING.toString());
        this.seatingLabel.setText(Word.SETSEATINGTYPE.toString());
        this.maxSeats.setPromptText(Word.UNLIMITED.toString());
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
        this.maxSeatsValidation = MainHandler.validate(this.maxSeats, "^$|^[1-9]\\d*$");
        this.maxSeatsValidation.invalidProperty().addListener((observable, oldValue, newValue) -> {
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
        //this.next.setDisable(false);
    }
}
