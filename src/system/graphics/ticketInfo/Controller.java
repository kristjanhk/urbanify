package system.graphics.ticketInfo;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import system.data.Word;
import system.graphics.common.AbstractController;
import system.graphics.common.ClientScreen;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller extends AbstractController {
    @FXML protected Text title;
    @FXML protected Text datetime;

    private system.graphics.pointOfSale.Controller parentController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setLanguage();
    }

    public void init() {
        this.setDatetime();
        if (ClientScreen.getActiveScreenType().equals(ClientScreen.SECONDARY)) {
            this.scene.getStageHandler().showStage();
        }
    }

    private void setDatetime() {
        this.datetime.setText(this.parentController.getEvent().getFormattedDate() + " " +
                this.parentController.getEvent().getTime());
    }

    @Override
    public <T> void prepareToDisplay(T object) {
        if (object instanceof system.graphics.pointOfSale.Controller) {
            this.parentController = (system.graphics.pointOfSale.Controller) object;
            this.parentController.setClientScreenController(this);
        }
    }

    @Override
    public void setLanguage() {
        this.title.setText(Word.TICKETINFO.toString());
    }
}
