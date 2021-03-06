package system.graphics.pointOfSale;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import system.data.Event;

import java.util.ArrayList;

public class Ticket extends GridPane {
    private Controller parentController;
    private Event event;
    private Text ticketName;
    private Button removeTicket;
    private SimpleIntegerProperty ticketAmount = new SimpleIntegerProperty(0);
    private Button addTicket;
    private Text ticketCost;

    private String tickettype;

    public Ticket(Controller parentController, String ticketName, Event event) {
        super();
        this.parentController = parentController;
        this.tickettype = ticketName;
        this.event = event;
        this.setAlignment(Pos.CENTER_LEFT);
        VBox.setMargin(this, new Insets(0, 0, 20, 0));
        this.initChildren();
        this.disableButtons(true);
    }

    public String getCurrentData() {
        if (this.ticketAmount.intValue() > 0) {
            return this.tickettype + ": " + this.ticketAmount.intValue() + "\n";
        }
        return "";
    }

    public void save() {
        this.event.addTicketAmount(this.tickettype, this.ticketAmount.doubleValue());

    }

    public void disableButtons(boolean init) {
        this.removeTicket.setDisable(true);
        if (this.event.getFloorPlan() != null || !init) {
            this.addTicket.setDisable(true);
        }
    }

    public void enableAddTicketButton() {
        this.addTicket.setDisable(false);
    }

    private ArrayList<Double> getTicketData() {
        ArrayList<Double> ticketdata = new ArrayList<>(2);
        ticketdata.add(this.event.getTicketPrice(this.tickettype));
        ticketdata.add(this.ticketAmount.doubleValue());
        return ticketdata;
    }

    private void initChildren() {
        this.ticketName = new Text(this.tickettype);
        this.ticketName.getStyleClass().add("text35");
        this.ticketName.setStrokeType(StrokeType.OUTSIDE);
        this.ticketName.setStrokeWidth(0.0);
        this.ticketName.setWrappingWidth(280);
        GridPane.setMargin(this.ticketName, new Insets(0.0, 0.0, 0.0, 0.0));
        this.add(this.ticketName, 0, 0);
        this.getColumnConstraints().add(new ColumnConstraints(280, 280, 280));

        this.removeTicket = new Button("ô");
        this.removeTicket.getStyleClass().add("buttonRound");
        this.removeTicket.setMnemonicParsing(false);
        HBox.setMargin(this.removeTicket, new Insets(0.0, 0.0, 0.0, 0.0));
        this.removeTicket.setOnMouseClicked(event -> {
            if (this.ticketAmount.getValue() > 0) {
                this.ticketAmount.set(this.ticketAmount.getValue() - 1);
                this.parentController.freeSeat(this.event.getTicketPrice(this.tickettype));
                if (this.ticketAmount.getValue() != 0) {
                    this.parentController.getClientController().updateTicket(
                            this.tickettype, this.getTicketData(), this.event.getCurrency());
                } else {
                    this.parentController.getClientController().removeTicket(this.tickettype);
                }
                this.parentController.getClientController().updateTotals();
            }
        });
        this.ticketAmount.addListener((observable, oldValue, newValue) -> {
            this.removeTicket.setDisable(newValue.intValue() == 0);
        });
        this.add(this.removeTicket, 1, 0);
        this.getColumnConstraints().add(new ColumnConstraints(70, 70, 70));

        Text ticketAmountText = new Text("0");
        ticketAmountText.getStyleClass().add("text35");
        ticketAmountText.setTextAlignment(TextAlignment.CENTER);
        ticketAmountText.setStrokeType(StrokeType.OUTSIDE);
        ticketAmountText.setStrokeWidth(0.0);
        ticketAmountText.setWrappingWidth(100.0);
        HBox.setMargin(ticketAmountText, new Insets(0.0, 0, 0.0, 0));
        ticketAmountText.textProperty().bind(this.ticketAmount.asString());
        this.add(ticketAmountText, 2, 0);
        this.getColumnConstraints().add(new ColumnConstraints(100, 100, 100));

        this.addTicket = new Button("ò");
        this.addTicket.getStyleClass().add("buttonRound");
        this.addTicket.setMnemonicParsing(false);
        this.addTicket.setOnMouseClicked(event -> {
            if (this.parentController.occupySeat(this.event.getTicketPrice(this.tickettype))) {
                this.ticketAmount.set(this.ticketAmount.getValue() + 1);
                this.parentController.validateCheckoutButton();
                this.parentController.getClientController().updateTicket(
                        this.tickettype, this.getTicketData(), this.event.getCurrency());
                this.parentController.getClientController().updateTotals();
            }
        });
        this.parentController.getSeatsLeftProperty().addListener((observable, oldValue, newValue) -> {
            this.addTicket.setDisable(newValue.intValue() == 0);
        });
        this.add(this.addTicket, 3, 0);
        this.getColumnConstraints().add(new ColumnConstraints(70, 70, 70));

        this.ticketCost = new Text();
        this.ticketCost.setText(String.format("%.2f", this.parentController.getEvent().
                getTicketPrice(this.tickettype)) + " " + event.getCurrency());
        this.ticketCost.getStyleClass().add("text35");
        this.ticketCost.setTextAlignment(TextAlignment.CENTER);
        this.ticketCost.setStrokeType(StrokeType.OUTSIDE);
        this.ticketCost.setStrokeWidth(0.0);
        this.ticketCost.setWrappingWidth(180);
        HBox.setMargin(this.ticketCost, new Insets(0.0, 0.0, 0.0, 0.0));
        this.add(this.ticketCost, 4, 0);
        this.getColumnConstraints().add(new ColumnConstraints(180, 180, 180));
    }
}