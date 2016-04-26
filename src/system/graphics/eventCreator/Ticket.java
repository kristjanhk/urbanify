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
import system.settings.Word;

public class Ticket extends HBox {
    private VBox parentNode;
    private TextField priceText;
    private Text priceLabel;
    private TextField ticketText;
    private Text ticketLabel;

    public Ticket(VBox parentNode) {
        super();
        this.parentNode = parentNode;
        this.setPrefSize(506.0, 12.0);
        VBox.setMargin(this, new Insets(0, 75.0, 20.0, 40.0));
        this.initChildren();
        this.setLanguage();
    }

    private void initChildren() {
        Button deleteTicket = new Button("ó");
        deleteTicket.setMinSize(70.5, 70.5);
        deleteTicket.setMaxSize(70.5, 70.5);
        deleteTicket.setPrefSize(70.5, 70.5);
        deleteTicket.setTextAlignment(TextAlignment.CENTER);
        deleteTicket.setMnemonicParsing(false);
        deleteTicket.getStyleClass().add("buttonSmall");
        deleteTicket.setOnMouseClicked(event -> this.parentNode.getChildren().remove(this));
        this.getChildren().add(deleteTicket);

        // TODO: 23.04.2016 add pattern listeners 
        
        this.priceText = new TextField();
        this.priceText.setMinSize(110.0, 70.5);
        this.priceText.setMaxSize(110.0, 70.5);
        this.priceText.setPrefSize(110.0, 70.5);
        this.priceLabel = createText();
        this.getChildren().add(createVBox(this.priceText, this.priceLabel));

        this.ticketText = new TextField();
        this.ticketText.setMinSize(300.0, 70.5);
        this.ticketText.setMaxSize(300.0, 70.5);
        this.ticketText.setPrefSize(300.0, 70.5);
        this.ticketLabel = createText();
        this.getChildren().add(createVBox(this.ticketText, this.ticketLabel));
    }

    private static Text createText() {
        Text text = new Text();
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

    public void setLanguage() {
        this.priceText.setPromptText(Word.PRICE.toString());
        this.priceLabel.setText(Word.PRICE.toString());
        this.ticketText.setPromptText(Word.TICKETYPE.toString());
        this.ticketLabel.setText(Word.TICKETYPE.toString());
    }

    public TextField getPriceText() {
        return priceText;
    }

    public TextField getTicketText() {
        return ticketText;
    }
}
