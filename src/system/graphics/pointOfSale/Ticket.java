package system.graphics.pointOfSale;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import system.data.Event;
import system.data.Lang;

public class Ticket extends HBox {
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
        VBox.setMargin(this, new Insets(0, 40.0, 25.0, 0));
        this.initChildren();
    }

    public void save() {
        this.event.addTicketAmount(this.tickettype, this.ticketAmount.doubleValue());
    }

    public void disableButtons() {
        this.removeTicket.setDisable(true);
        this.addTicket.setDisable(true);
    }

    private void initChildren() {
        this.ticketName = new Text(this.tickettype);
        this.ticketName.getStyleClass().add("text35");
        this.ticketName.setStrokeType(StrokeType.OUTSIDE);
        this.ticketName.setStrokeWidth(0.0);
        this.ticketName.setWrappingWidth(150.0);
        this.getChildren().add(this.ticketName);

        this.removeTicket = new Button("-");
        this.removeTicket.getStyleClass().add("buttonRound");
        this.removeTicket.setMnemonicParsing(false);
        HBox.setMargin(this.removeTicket, new Insets(0.0, 0.0, 0.0, 20.0));
        this.removeTicket.setOnMouseClicked(event -> {
            if (this.ticketAmount.getValue() > 0) {
                this.ticketAmount.set(this.ticketAmount.getValue() - 1);
                this.parentController.freeSeat(this.event.getTicketPrice(this.tickettype));
            }
        });
        this.getChildren().add(this.removeTicket);

        Text ticketAmountText = new Text("0");
        ticketAmountText.getStyleClass().add("text35");
        ticketAmountText.setTextAlignment(TextAlignment.CENTER);
        ticketAmountText.setStrokeType(StrokeType.OUTSIDE);
        ticketAmountText.setStrokeWidth(0.0);
        ticketAmountText.setWrappingWidth(58.0); //57.8671875??
        HBox.setMargin(ticketAmountText, new Insets(0.0, 20.0, 0.0, 20.0));
        ticketAmountText.textProperty().bind(this.ticketAmount.asString());
        this.getChildren().add(ticketAmountText);

        this.addTicket = new Button("ò");
        this.addTicket.getStyleClass().add("buttonRound");
        this.addTicket.setMnemonicParsing(false);
        this.addTicket.setOnMouseClicked(event -> {
            if (this.parentController.occupySeat(this.event.getTicketPrice(this.tickettype))) {
                this.ticketAmount.set(this.ticketAmount.getValue() + 1);
            }
        });
        this.getChildren().add(this.addTicket);

        this.ticketCost = new Text();
        this.ticketCost.setText(String.format("%.2f", this.parentController.getEvent().
                getTicketPrice(this.tickettype)) + " " + Lang.getActiveLang().getCurrency());
        this.ticketCost.getStyleClass().add("text35");
        this.ticketCost.setTextAlignment(TextAlignment.CENTER);
        this.ticketCost.setStrokeType(StrokeType.OUTSIDE);
        this.ticketCost.setStrokeWidth(0.0);
        this.ticketCost.setWrappingWidth(97.0); //96.8671875??
        HBox.setMargin(this.ticketCost, new Insets(0.0, 0.0, 0.0, 40.0));
        this.getChildren().add(this.ticketCost);
    }
}