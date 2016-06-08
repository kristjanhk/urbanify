package system.graphics.ticketInfo;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import system.data.Word;
import system.graphics.common.AbstractController;
import system.graphics.common.ClientScreen;
import system.graphics.report.Ticket;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller extends AbstractController {
    @FXML protected Text title;
    @FXML protected Text datetime;
    @FXML protected Text tickettype;
    @FXML protected Text cost;
    @FXML protected Text quantity;
    @FXML protected Text total;
    @FXML protected VBox ticketVBox;
    @FXML protected Text total2;
    @FXML protected Text totalQuantity;
    @FXML protected Text totalPrice;
    @FXML protected Button back;


    private system.graphics.pointOfSale.Controller parentController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setLanguage();
    }

    public void init() {
        this.title.setText(this.parentController.getEvent().getName());
        this.setDatetime();
        if (ClientScreen.getActiveScreenType().equals(ClientScreen.SECONDARY)) {
            this.scene.getStageHandler().showStage();
        }
    }

    public void updateTicket(String ticketname, ArrayList<Double> ticketdata, String currency) {
        for (Node node : this.ticketVBox.getChildren()) {
            if (((Ticket) node).getTicketName().equals(ticketname)) {
                ((Ticket) node).update(ticketdata);
                return;
            }
        }
        this.ticketVBox.getChildren().add(new Ticket(ticketname, ticketdata, currency));
    }

    public void removeTicket(String ticketname) {
        for (Node node : this.ticketVBox.getChildren()) {
            if (((Ticket) node).getTicketName().equals(ticketname)) {
                this.ticketVBox.getChildren().remove(node);
                return;
            }
        }
    }

    private void setDatetime() {
        this.datetime.setText(this.parentController.getEvent().getFormattedDate() + " " +
                this.parentController.getEvent().getTime());
    }

    public void updateTotals() {
        int quantity = 0;
        double total = 0.0;
        for (Node node : this.ticketVBox.getChildren()) {
            quantity += ((Ticket) node).getAmount();
            total += ((Ticket) node).getPrice();
        }
        this.totalQuantity.setText(String.valueOf(quantity));
        this.totalPrice.setText(String.format("%.2f", total) + " " +
                this.parentController.getEvent().getCurrency());
    }

    @FXML
    protected void handleBack() {
        this.scene.getStageHandler().getStage().close();
        this.parentController.cancel();
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
        this.tickettype.setText(Word.TICKETYPE.toString());
        this.cost.setText(Word.COST.toString());
        this.quantity.setText(Word.QUANTITY.toString());
        this.total.setText(Word.TOTAL.toString());
        this.total2.setText(Word.TOTAL.toString());
        this.back.setText(Word.BACK.toString());
    }
}
