package system.graphics.eventCreator;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Ticket extends GridPane {
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
        //HBox.setMargin(this.deleteTicket, new Insets(40.0, 0.0, 0.0, 40.0));
        //this.getChildren().add(createVBox(this.deleteTicket, createText("delete ticket")));
        //this.getChildren().add(new VBox(this.deleteTicket, createText("delete ticket")));
        //this.add(this.deleteTicket, 0, 0);
        this.addColumn(0, this.deleteTicket, createText("delete ticket"));

        // TODO: 23.04.2016 add pattern listeners 
        
        this.priceText = new TextField();
        this.priceText.setMinWidth(110.0);
        this.priceText.setMaxWidth(110.0);
        this.priceText.setPrefWidth(110.0);
        this.priceText.setPromptText("price");
        //HBox.setMargin(this.priceText, new Insets(40.0, 0.0, 0.0, 40.0));
        //this.getChildren().add(createVBox(this.priceText, createText("set price")));
        //this.getChildren().add(new VBox(this.priceText, createText("set price")));
        this.addColumn(1, this.priceText, createText("set price"));

        this.ticketText = new TextField();
        this.ticketText.setMinWidth(292.5);
        this.ticketText.setMaxWidth(292.5);
        this.ticketText.setPrefWidth(174.0);
        this.ticketText.setPromptText("ticket type");
        //HBox.setMargin(this.ticketText, new Insets(40.0, 0.0, 0.0, 40.0));
        //this.getChildren().add(createVBox(this.ticketText, createText("set ticket type")));
        //this.getChildren().add(new VBox(this.ticketText, createText("set ticket type")));
        this.addColumn(2, this.ticketText, createText("set ticket type"));
    }

    private static Text createText(String string) {
        Text text = new Text(string);
        text.setStrokeType(StrokeType.OUTSIDE);
        text.setStrokeWidth(0.0);
        text.setWrappingWidth(284.0);
        //VBox.setMargin(text, new Insets(6.0, 0.0, 0.0, 20.0));
        return text;
    }

    private static VBox createVBox(Node node, Text text) {
        VBox vBox = new VBox(node, text);
        //HBox.setMargin(vBox, new Insets(40.0, 0.0, 0.0, 40.0));
        return vBox;
    }

    public TextField getPriceText() {
        return priceText;
    }

    public TextField getTicketText() {
        return ticketText;
    }
}
