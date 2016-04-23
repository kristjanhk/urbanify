package system.graphics.eventCreator;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import system.graphics.AbstractController;
import system.graphics.Scenetype;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller extends AbstractController implements Initializable {
    public TextField eventText;
    public DatePicker calendar;
    public TextField timeText; // TODO: 23.04.2016 picker
    public CheckBox openSeating;
    public CheckBox assignedSeating;
    public Button cancel;
    public Button next;
    public Button create;
    public Button addTicketButton;
    public VBox ticketVBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.eventText.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                if (!this.eventText.getText().equals("tere")) { // TODO: 23.04.2016 pattern matching
                    this.eventText.setText("");
                }
            }
        });
        // TODO: 23.04.2016 add pattern listeners
    }

    // TODO: 14.04.2016 to be changed
    public void addTicket() {
        this.ticketVBox.getChildren().add(this.ticketVBox.getChildren().size() - 1, new Ticket(this.ticketVBox));
    }

    public void openFloorPlanner() {

    }

    public void doCancel() {
        this.scene.getStageHandler().switchSceneTo(Scenetype.MAINMENU);
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
}
