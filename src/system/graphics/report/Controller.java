package system.graphics.report;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
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
    public Text title;
    public Text datetime;
    public Text tickettype;
    public Text cost;
    public Text quantity;
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
        PieChart.Data data1 = new PieChart.Data("tavapilet", 5);
        PieChart.Data data2 = new PieChart.Data("lapsepilet", 10);
        PieChart.Data data3 = new PieChart.Data("pensionär", 12);
        pieChart.setLegendVisible(false);
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        data.addAll(data1,data2,data3);
        pieChart.setData(data);
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
        this.endEvent.setText(Word.FINISH.toString());
        this.pos.setText(Word.POS.toString());
    }
}
