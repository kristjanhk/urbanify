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

public class Ticket extends HBox {
    private Controller parentController;
    private Text ticketName;
    private SimpleIntegerProperty ticketAmount = new SimpleIntegerProperty(0);
    private Text ticketCost;


    public Ticket(Controller parentController, String ticketName) {
        super();
        this.parentController = parentController;
        this.setAlignment(Pos.CENTER_LEFT);
        VBox.setMargin(this, new Insets(0, 40.0, 25.0, 0));
        this.initChildren(ticketName);
    }

    private void initChildren(String ticketName) {
        this.ticketName = new Text(ticketName);
        this.ticketName.getStyleClass().add("text35");
        this.ticketName.setStrokeType(StrokeType.OUTSIDE);
        this.ticketName.setStrokeWidth(0.0);
        this.ticketName.setWrappingWidth(150.0);
        this.getChildren().add(this.ticketName);

        Button removeTicket = new Button("-");
        removeTicket.getStyleClass().add("buttonRound");
        removeTicket.setMnemonicParsing(false);
        HBox.setMargin(removeTicket, new Insets(0.0, 0.0, 0.0, 20.0));
        removeTicket.setOnMouseClicked(event -> {
            if (this.ticketAmount.getValue() > 0) {
                this.ticketAmount.set(this.ticketAmount.getValue() - 1);
            }
            System.out.println(this.ticketAmount.getValue());
        });
        this.getChildren().add(removeTicket);

        Text ticketAmountText = new Text("0");
        ticketAmountText.getStyleClass().add("text35");
        ticketAmountText.setTextAlignment(TextAlignment.CENTER);
        ticketAmountText.setStrokeType(StrokeType.OUTSIDE);
        ticketAmountText.setStrokeWidth(0.0);
        ticketAmountText.setWrappingWidth(58.0); //57.8671875??
        HBox.setMargin(ticketAmountText, new Insets(0.0, 20.0, 0.0, 20.0));
        ticketAmountText.textProperty().bind(this.ticketAmount.asString());
        this.getChildren().add(ticketAmountText);

        Button addTicket = new Button("ò");
        addTicket.getStyleClass().add("buttonRound");
        addTicket.setMnemonicParsing(false);
        addTicket.setOnMouseClicked(event -> {
            this.ticketAmount.set(this.ticketAmount.getValue() + 1);
            System.out.println(this.ticketAmount.getValue());
        });
        // TODO: 4.05.2016 dont sell more tickets than seats
        this.getChildren().add(addTicket);

        this.ticketCost = new Text();
        this.ticketCost.setText(String.valueOf(this.parentController.getEvent().getTicketPrice(ticketName)) + " €");
        // TODO: 4.05.2016 store currency in eventobject 
        this.ticketCost.getStyleClass().add("text35");
        this.ticketCost.setTextAlignment(TextAlignment.CENTER);
        this.ticketCost.setStrokeType(StrokeType.OUTSIDE);
        this.ticketCost.setStrokeWidth(0.0);
        this.ticketCost.setWrappingWidth(97.0); //96.8671875??
        HBox.setMargin(this.ticketCost, new Insets(0.0, 0.0, 0.0, 40.0));
        this.getChildren().add(this.ticketCost);
    }
}