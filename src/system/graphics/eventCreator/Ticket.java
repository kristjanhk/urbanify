package system.graphics.eventCreator;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Ticket extends HBox {
    private VBox parentNode;
    private Button deleteTicket;
    private TextField priceText;
    private TextField ticketText;

    public Ticket(VBox parentNode) {
        super();
        this.parentNode = parentNode;
        this.setPrefSize(506.0, 12.0);
        VBox.setMargin(this, new Insets(0, 75.0, 20.0, 40.0));
        this.initChildren();
    }

    private void initChildren() {
        this.deleteTicket = new Button("ó");
        this.deleteTicket.setMinSize(70.5, 70.5);
        this.deleteTicket.setMaxSize(70.5, 70.5);
        this.deleteTicket.setPrefSize(70.5, 70.5);
        this.deleteTicket.setTextAlignment(TextAlignment.CENTER);
        this.deleteTicket.setMnemonicParsing(false);
        this.deleteTicket.setOnMouseClicked(event -> this.parentNode.getChildren().remove(this));
        this.getChildren().add(this.deleteTicket);

        // TODO: 23.04.2016 add pattern listeners 
        
        this.priceText = new TextField();
        this.priceText.setMinSize(110.0, 70.5);
        this.priceText.setMaxSize(110.0, 70.5);
        this.priceText.setPrefSize(110.0, 70.5);
        this.priceText.setPromptText("price");
        this.getChildren().add(createVBox(this.priceText, createText("price")));

        this.ticketText = new TextField();
        this.ticketText.setMinSize(300.0, 70.5);
        this.ticketText.setMaxSize(300.0, 70.5);
        this.ticketText.setPrefSize(300.0, 70.5);
        this.ticketText.setPromptText("ticket type");
        this.getChildren().add(createVBox(this.ticketText, createText("ticket type")));
    }

    private static Text createText(String string) {
        Text text = new Text(string);
        text.getStyleClass().add("text19");
        text.setStrokeType(StrokeType.OUTSIDE);
        text.setStrokeWidth(0.0);
        VBox.setMargin(text, new Insets(6.0, 0.0, 0.0, 20.0));
        return text;
    }

    private static VBox createVBox(Node node, Text text) {
        VBox vBox = new VBox(node, text);
        HBox.setMargin(vBox, new Insets(0.0, 0.0, 0.0, 40.0));
        return vBox;
    }

    public TextField getPriceText() {
        return priceText;
    }

    public TextField getTicketText() {
        return ticketText;
    }
}
