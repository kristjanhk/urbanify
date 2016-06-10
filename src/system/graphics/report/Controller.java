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
import system.graphics.common.FloorPlanPane;
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
    @FXML protected Text seatsLeft;
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
        this.setSeatsLeft();
        this.createTickets();
        this.createTotal();
        this.createPieChart();
        if (!this.event.isActive()) {
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
        if (MainHandler.getReportHandler().generatePdf(this.event)) {
            this.event.sendToArchive();
            this.scene.getStageHandler().switchSceneTo(Scenetype.EVENTMANAGER);
        }
    }

    @FXML
    protected void doPos() {
        this.scene.getStageHandler().switchSceneTo(Scenetype.POINTOFSALE, this.event);
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

    private void setSeatsLeft() {
        if (this.event.getFloorPlan() != null) {
            int seatsleft = FloorPlanPane.getSeatsLeft(this.event);
            this.seatsLeft.setText(Word.SEATSLEFT.toString() + ":" + seatsleft);
            this.pos.setDisable(seatsleft == 0);
        } else {
            this.seatsLeft.setText(Word.SEATSLEFT.toString() + ": " +
                    (this.event.getMaxSeats() == -1 ? Word.UNLIMITED.toString() : this.event.getMaxSeats()));
            this.pos.setDisable(this.event.getMaxSeats() < 1 && this.event.getMaxSeats() != -1);
        }
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
