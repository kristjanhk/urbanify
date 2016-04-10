package system.graphics.eventCreator;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import system.MainHandler;
import system.graphics.AbstractController;
import system.graphics.Scenetype;
import system.graphics.common.Csstype;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller extends AbstractController implements Initializable{
    public TextField nameYourEvent;
    public TextField setDate;
    public TextField setTime;
    public Button cancel;
    public Button next;
    public Button deleteTicketField;
    public TextField priceField;
    public TextField ticketField;
    public Button addTicketField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //siia saab controlleri käivitumisel asjadega tegeleda
        //ja ka failidega tegeleda?
    }

    public void vaheta() {
        MainHandler.changeSceneThemeTo(this.scene, Csstype.toggleTheme());
    }

    public void doCancel() {
        this.stageHandler.switchSceneTo(Scenetype.MAINMENU);
    }
}
