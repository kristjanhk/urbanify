package system.graphics.report;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import system.MainHandler;
import system.data.Event;
import system.data.Word;
import system.graphics.common.AbstractController;
import system.graphics.common.Scenetype;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Aruande controller
 */
public class Controller extends AbstractController {
    @FXML protected Text title;
    @FXML protected Text datetime;
    @FXML protected Text tickettype;
    @FXML protected Text cost;
    @FXML protected Text quantity;
    @FXML protected Text total;
    @FXML protected VBox ticketVBox;
    @FXML protected Text total2;
    @FXML protected Text quantityTotal;
    @FXML protected Text soldTotal;
    @FXML protected PieChart pieChart;
    @FXML protected Button back;
    @FXML protected Button edit;
    @FXML protected Button endEvent;
    @FXML protected Button pos;

    private Event event;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setLanguage();
    }

    private void init() {
        this.setTitle();
        this.setDatetime();
        this.createTickets();
        this.createTotal();
        this.createPieChart();
        if (!this.event.isActive()) {
            this.edit.setDisable(true);
            this.endEvent.setDisable(true);
            this.pos.setDisable(true);
        }
    }

    @FXML
    protected void doBack() {
        this.scene.getStageHandler().switchSceneTo(this.event.isActive() ? Scenetype.EVENTMANAGER : Scenetype.ARCHIVE);
    }

    @FXML
    protected void doEdit() {
        this.scene.getStageHandler().replaceScene(Scenetype.EVENTCREATOR);
        this.scene.getStageHandler().switchSceneTo(Scenetype.EVENTCREATOR, this.event);
    }

    @FXML
    protected void doEndEvent() throws Exception {
        MainHandler.getTertiaryStageHandler().replaceScene(Scenetype.REPORTPDF);
        MainHandler.getTertiaryStageHandler().switchSceneTo(Scenetype.REPORTPDF, this);
        MainHandler.getTertiaryStageHandler().showStage(false);
    }

    @FXML
    protected void doPos() {
        this.scene.getStageHandler().switchSceneTo(Scenetype.POINTOFSALE, this.event);
    }

    public Event getEvent() {
        return this.event;
    }

    public void setButtonsDisabled(boolean state) {
        this.back.setDisable(state);
        this.edit.setDisable(state);
        this.endEvent.setDisable(state);
        this.pos.setDisable(state);
    }

    private void setTitle() {
        this.title.setText(this.event.getName());
    }

    private void setDatetime() {
        this.datetime.setText(this.event.getFormattedDate() + " " + this.event.getTime());
    }

    private void createTickets() {
        if (this.ticketVBox.getChildren().size() > 0) {
            this.ticketVBox.getChildren().clear();
        }
        this.event.getTickets().forEach((tickettype, ticketdata) -> this.ticketVBox.getChildren().add(
                new Ticket(tickettype, ticketdata, this.event.getCurrency())));
    }

    private void createTotal() {
        int totalQuantity = 0;
        double totalSold = 0.0;
        for (String tickettype : this.event.getTickets().keySet()) {
            totalQuantity += this.event.getTicketAmount(tickettype);
            totalSold += this.event.getTicketAmount(tickettype) * this.event.getTicketPrice(tickettype);
        }
        this.quantityTotal.setText(String.valueOf(totalQuantity));
        this.soldTotal.setText(String.format("%.2f", totalSold) +
                " " + this.event.getCurrency());
    }

    private void createPieChart() {
        ArrayList<PieChart.Data> data = new ArrayList<>();
        this.event.getTickets().forEach((tickettype, ticketdata) -> {
            if (ticketdata.get(1) > 0.0) {
                data.add(new PieChart.Data(tickettype, ticketdata.get(1)));
            }
        });
        this.pieChart.setLegendVisible(false);
        this.pieChart.setData(FXCollections.observableArrayList(data));
        // TODO: 30.05.2016 bind text size to stage width
    }

    @Override
    public <T> void prepareToDisplay(T object) {
        if (object instanceof Event) {
            if (this.event != null) {
                this.scene.getStageHandler().replaceScene(Scenetype.REPORT);
                this.scene.getStageHandler().getScene(Scenetype.REPORT).
                        getController().prepareToDisplay(object);
            } else {
                this.event = ((Event) object);
                this.init();
            }
        }
    }

    @Override
    public void setLanguage() {
        this.tickettype.setText(Word.TICKETYPE.toString());
        this.cost.setText(Word.COST.toString());
        this.quantity.setText(Word.QUANTITY.toString());
        this.total.setText(Word.TOTAL.toString());
        this.total2.setText(Word.TOTAL.toString());
        this.back.setText(Word.BACK.toString());
        this.edit.setText(Word.EDIT.toString());
        this.endEvent.setText(Word.FINISH.toString());
        this.pos.setText(Word.POS.toString());
    }
}
