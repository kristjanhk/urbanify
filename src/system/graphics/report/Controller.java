package system.graphics.report;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import system.MainHandler;
import system.data.Event;
import system.data.Lang;
import system.data.Word;
import system.graphics.common.AbstractController;
import system.graphics.common.Scenetype;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
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
    public Text total;
    public VBox ticketVBox;
    public Text total2;
    public Text quantityTotal;
    public Text soldTotal;
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
        this.setTitle();
        this.setDatetime();
        this.createTickets();
        this.createTotal();
        this.createPieChart();
    }

    @FXML
    protected void doBack() {
        this.scene.getStageHandler().switchSceneTo(this.event.isActive() ? Scenetype.EVENTMANAGER : Scenetype.ARCHIVE);
    }

    @FXML
    protected void doEndEvent() throws Exception {
        // TODO: 10.05.2016 archive/deactivate event
        MainHandler.getReportHandler().generatePdf(this.event);
    }

    @FXML
    protected void doPos() {
        this.scene.getStageHandler().switchSceneTo(Scenetype.POINTOFSALE, this.event);
    }

    private void setTitle() {
        this.title.setText(this.event.getName());
    }

    private void setDatetime() {
        this.datetime.setText(this.event.getDate().format(
                DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(Lang.getActiveLang().getLocale())) +
                " " + this.event.getTime());
    }

    private void createTickets() {
        if (this.ticketVBox.getChildren().size() > 0) {
            this.ticketVBox.getChildren().clear();
        }
        this.event.getTickets().forEach(
                (tickettype, ticketdata) -> this.ticketVBox.getChildren().add(new Ticket(tickettype, ticketdata)));
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
                " " + Lang.getActiveLang().getCurrency());
    }

    private void createPieChart() {
        ArrayList<PieChart.Data> data = new ArrayList<>();
        this.event.getTickets().forEach((tickettype, ticketdata) -> {
            if (ticketdata.get(1) > 0.0) {
                data.add(new PieChart.Data(tickettype, ticketdata.get(1)));
            }
        });
        pieChart.setLegendVisible(false);
        pieChart.setData(FXCollections.observableArrayList(data));
    }

    @Override
    public <T> void prepareToDisplay(T object) {
        if (object instanceof Event) {
            if (this.event != null) {
                if (!this.event.equals(object)) {
                    this.scene.getStageHandler().replaceScene(Scenetype.REPORT);
                    this.scene.getStageHandler().getScene(Scenetype.REPORT).
                            getController().prepareToDisplay(object);
                } else {
                    this.setDatetime();
                }
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
        this.endEvent.setText(Word.FINISH.toString());
        this.pos.setText(Word.POS.toString());
    }
}
