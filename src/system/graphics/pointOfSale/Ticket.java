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
import system.data.Lang;

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

    public void disableButtons() {
        this.removeTicket.setDisable(true);
        this.addTicket.setDisable(true);
    }

    private void initChildren() {
        this.ticketName = new Text(this.tickettype);
        this.ticketName.getStyleClass().add("text35");
        this.ticketName.setStrokeType(StrokeType.OUTSIDE);
        this.ticketName.setStrokeWidth(0.0);
        this.ticketName.setWrappingWidth(280);
        GridPane.setMargin(this.ticketName, new Insets(0.0, 0.0, 0.0, 0.0));
        this.add(this.ticketName,0,0);
        this.getColumnConstraints().add(new ColumnConstraints(280,280,280));

        this.removeTicket = new Button("-");
        this.removeTicket.getStyleClass().add("buttonRound");
        this.removeTicket.setMnemonicParsing(false);
        HBox.setMargin(this.removeTicket, new Insets(0.0, 0.0, 0.0, 0.0));
        this.removeTicket.setOnMouseClicked(event -> {
            if (this.ticketAmount.getValue() > 0) {
                this.ticketAmount.set(this.ticketAmount.getValue() - 1);
                this.parentController.freeSeat(this.event.getTicketPrice(this.tickettype));
            }
        });
        this.add(this.removeTicket,1,0);
        this.getColumnConstraints().add(new ColumnConstraints(70,70,70));

        Text ticketAmountText = new Text("0");
        ticketAmountText.getStyleClass().add("text35");
        ticketAmountText.setTextAlignment(TextAlignment.CENTER);
        ticketAmountText.setStrokeType(StrokeType.OUTSIDE);
        ticketAmountText.setStrokeWidth(0.0);
        ticketAmountText.setWrappingWidth(100.0); //57.8671875??
        HBox.setMargin(ticketAmountText, new Insets(0.0, 0, 0.0, 0));
        ticketAmountText.textProperty().bind(this.ticketAmount.asString());
        this.add(ticketAmountText,2,0);
        this.getColumnConstraints().add(new ColumnConstraints(100,100,100));

        this.addTicket = new Button("ò");
        this.addTicket.getStyleClass().add("buttonRound");
        this.addTicket.setMnemonicParsing(false);
        this.addTicket.setOnMouseClicked(event -> {
            if (this.parentController.occupySeat(this.event.getTicketPrice(this.tickettype))) {
                this.ticketAmount.set(this.ticketAmount.getValue() + 1);
            }
        });
        this.add(this.addTicket,3,0);
        this.getColumnConstraints().add(new ColumnConstraints(70,70,70));

        this.ticketCost = new Text();
        this.ticketCost.setText(String.format("%.2f", this.parentController.getEvent().
                getTicketPrice(this.tickettype)) + " " + Lang.getActiveLang().getCurrency());
        this.ticketCost.getStyleClass().add("text35");
        this.ticketCost.setTextAlignment(TextAlignment.CENTER);
        this.ticketCost.setStrokeType(StrokeType.OUTSIDE);
        this.ticketCost.setStrokeWidth(0.0);
        this.ticketCost.setWrappingWidth(180); //96.8671875??
        HBox.setMargin(this.ticketCost, new Insets(0.0, 0.0, 0.0, 0.0));
        this.add(this.ticketCost,4,0);
        this.getColumnConstraints().add(new ColumnConstraints(180,180,180));
    }
}