package system.graphics.eventCreator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.controlsfx.control.PopOver;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.controlsfx.validation.decoration.StyleClassValidationDecoration;
import system.graphics.common.AbstractController;
import system.graphics.common.Scenetype;
import system.settings.Event;
import system.settings.Word;

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
    private PopOver maxSeatsPopOver;
    private Event event = new Event();
    private ValidationSupport validationSupport = new ValidationSupport();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setLanguage();
        this.createMaxSeatsHelper();
        validationSupport.setValidationDecorator(
                new StyleClassValidationDecoration("validationError", "validationWarning"));
        this.addValidationTo(this.eventText, "\\d+([\\.\\,]\\d+)?"); //validate integer and double
        /*this.eventText.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                if (!this.eventText.getText().equals("tere")) { // TODO: 23.04.2016 pattern matching
                    this.eventText.setText("");
                }
            }
        });*/
        // TODO: 23.04.2016 add pattern listeners
    }

    //http://stackoverflow.com/questions/29607080/textfield-component-validation-with-controls-fx
    private void addValidationTo(Node node, String validation) {
        Validator<String> validator = (control, value) -> ValidationResult.fromErrorIf(control, "error msg",
                (value == null || !value.matches(validation)));
        this.validationSupport.registerValidator((Control) node, true, validator);
    }

    @FXML
    protected void addTicket() {
        this.ticketVBox.getChildren().add(this.ticketVBox.getChildren().size() - 1, new Ticket(this.ticketVBox));
    }

    @FXML
    protected void handleSeatingSwitch(ActionEvent event) {
        String seatingType = ((MenuItem) event.getSource()).getId();
        this.seating.setText(Word.valueOf(seatingType).toString());
        this.event.setSeatingType(seatingType);
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
        String seatingType = this.event.getSeatingType();
        if (seatingType == null || seatingType.equals("OPENSEATING")) {
            this.next.setText("next");
            if (seatingType == null) {
                this.maxSeats.setVisible(false);
                this.maxSeatsLabel.setVisible(false);
            } else {
                this.maxSeats.setVisible(true);
                this.maxSeatsLabel.setVisible(true);
            }
        } else if (seatingType.equals("ASSIGNEDSEATING")) {
            this.next.setText("create");
            this.maxSeats.setVisible(false);
            this.maxSeatsLabel.setVisible(false);
        }
    }

    private void createMaxSeatsHelper() {
        this.maxSeatsPopOver = new PopOver(new Text("enter 0 for no limit"));
        this.maxSeatsPopOver.setArrowLocation(PopOver.ArrowLocation.RIGHT_CENTER);
        this.maxSeats.setOnMouseEntered(event -> this.maxSeatsPopOver.show(this.maxSeats));
        this.maxSeats.setOnMouseExited(event -> this.maxSeatsPopOver.hide());
    }
}
