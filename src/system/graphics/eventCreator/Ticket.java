package system.graphics.eventCreator;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class Ticket extends HBox {
    private VBox parentNode;
    private Button deleteTicket;
    private TextField priceText;
    private TextField ticketText;

    public Ticket(VBox parentNode) {
        super();
        this.parentNode = parentNode;
        this.setPrefSize(664.0, 136.0);
        this.initChildren();
    }

    private void initChildren() {
        this.deleteTicket = new Button("ó");
        this.deleteTicket.setMinWidth(70.5);
        this.deleteTicket.setMaxWidth(70.5);
        this.deleteTicket.setPrefWidth(70.5);
        this.deleteTicket.setTextAlignment(TextAlignment.CENTER);
        this.deleteTicket.setMnemonicParsing(false);
        this.deleteTicket.setOnMouseClicked(event -> this.parentNode.getChildren().remove(this));
        HBox.setMargin(this.deleteTicket, new Insets(40.0, 0.0, 0.0, 40.0));

        // TODO: 23.04.2016 add pattern listeners 
        
        this.priceText = new TextField();
        this.priceText.setMinWidth(110.0);
        this.priceText.setMaxWidth(110.0);
        this.priceText.setPrefWidth(110.0);
        this.priceText.setPromptText("price");
        HBox.setMargin(this.priceText, new Insets(40.0, 0.0, 0.0, 40.0));

        this.ticketText = new TextField();
        this.ticketText.setMinWidth(292.5);
        this.ticketText.setMaxWidth(292.5);
        this.ticketText.setPrefWidth(174.0);
        this.ticketText.setPromptText("ticket type");
        HBox.setMargin(this.ticketText, new Insets(40.0, 0.0, 0.0, 40.0));

        this.getChildren().addAll(this.deleteTicket, this.priceText, this.ticketText);
    }

    public TextField getPriceText() {
        return priceText;
    }

    public TextField getTicketText() {
        return ticketText;
    }
}
