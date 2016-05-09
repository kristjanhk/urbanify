package system.graphics.report;

import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import system.MainHandler;
import system.data.Event;
import system.data.Word;
import system.graphics.common.AbstractController;
import system.graphics.common.Scenetype;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Aruande controller
 */
public class Controller extends AbstractController {
    public PieChart pieChart;
    public Button back;
    public Button endEvent;
    public Button pos;

    private Event event;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setLanguage();
    }

    private void init() {

    }

    @FXML
    protected void doBack() {
        this.scene.getStageHandler().switchSceneTo(this.event.isActive() ? Scenetype.EVENTMANAGER : Scenetype.ARCHIVE);
    }

    @FXML
    protected void doEndEvent() throws Exception {
        MainHandler.getReportHandler().generatePdf(this.event);
    }

    @FXML
    protected void doPos() {
        this.scene.getStageHandler().switchSceneTo(Scenetype.POINTOFSALE, this.event);
    }

    @Override
    public <T> void prepareToDisplay(T object) {
        if (object instanceof Event) {
            if (this.event != null) {
                if (!this.event.equals(object)) {
                    this.scene.getStageHandler().replaceScene(Scenetype.REPORT);
                    this.scene.getStageHandler().getScene(Scenetype.REPORT).
                            getController().prepareToDisplay(object);
                }
            } else {
                this.event = ((Event) object);
                this.init();
            }
        }
    }

    @Override
    public void setLanguage() {
        this.back.setText(Word.BACK.toString());
        // FIXME: 07/05/2016
        this.endEvent.setText("end event");
        this.pos.setText("pos");
    }
}
