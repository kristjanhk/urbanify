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
import system.MainHandler;
import system.data.Lang;
import system.data.Word;

/**
 * Pilet ürituse looja stseenis
 * On HBoxi mähisklass
 * Luuakse programmi töö käigus, seega ei saa FXMLis ette valmistada
 */
public class Ticket extends HBox {
    private Controller parentController;
    private VBox parentNode;
    private TextField priceText;
    private Text priceLabel;
    private boolean priceTextValidated;
    private TextField ticketText;
    private Text ticketLabel;
    private TextField currencyText;
    private Text currencyLabel;
    private boolean ticketTextValidated;

    public Ticket(Controller parentController, VBox parentNode) {
        super();
        this.parentController = parentController;
        this.parentNode = parentNode;
        this.setPrefSize(506.0, 12.0);
        VBox.setMargin(this, new Insets(0, 75.0, 20.0, 0));
        this.initChildren();
        this.setLanguage();
        this.addValidation();
    }

    private void initChildren() {
        Button deleteTicket = new Button("ó");
        deleteTicket.setMinSize(70.5, 70.5);
        deleteTicket.setMaxSize(70.5, 70.5);
        deleteTicket.setPrefSize(70.5, 70.5);
        deleteTicket.setTextAlignment(TextAlignment.CENTER);
        deleteTicket.setMnemonicParsing(false);
        deleteTicket.getStyleClass().add("buttonSmall");
        deleteTicket.setOnMouseClicked(event -> {
            this.parentNode.getChildren().remove(this);
            this.parentController.checkNextButtonValidation();
        });
        this.getChildren().add(deleteTicket);

        this.priceText = new TextField();
        this.priceText.setMinSize(140, 70.5);
        this.priceText.setMaxSize(140, 70.5);
        this.priceText.setPrefSize(140, 70.5);
        this.priceLabel = createText();
        this.getChildren().add(createVBox(this.priceText, this.priceLabel));

        this.currencyText = new TextField();
        this.currencyText.setMinSize(60.0, 70.5);
        this.currencyText.setMaxSize(60.0, 70.5);
        this.currencyText.setPrefSize(60.0, 70.5);
        this.currencyLabel = createText();
        this.getChildren().add(createVBox(this.currencyText, this.currencyLabel));
        this.currencyText.setStyle("-fx-alignment: center; -fx-padding: 0 0 0 0");

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
        VBox vBox = new VBox(node);
        HBox.setMargin(vBox, new Insets(0.0, 0.0, 0.0, 30.0));
        return vBox;
    }

    public void setLanguage() {
        this.priceText.setPromptText(Lang.getActiveLang().getCurrency());
        this.priceLabel.setText(Word.PRICE.toString());
        this.ticketText.setPromptText(Word.TICKETYPE.toString());
        this.ticketLabel.setText(Word.TICKETYPE.toString());
    }

    private void addValidation() {
        MainHandler.setValidationFor(this.priceText, "\\d+([\\.\\,]\\d+)?").addListener(
                (observable, oldValue, newValue) -> {
                    this.priceTextValidated = !newValue;
                    this.parentController.checkNextButtonValidation();
                });
        MainHandler.setValidationFor(this.ticketText, "^(?=\\s*\\S).*$").addListener(
                (observable, oldValue, newValue) -> {
                    this.ticketTextValidated = !newValue;
                    this.parentController.checkNextButtonValidation();
                });
    }

    public boolean isValid() {
        return this.priceTextValidated && this.ticketTextValidated;
    }

    public String getType() {
        return this.ticketText.getText();
    }

    public double getPrice() {
        return Double.parseDouble(this.priceText.getText());
    }
}
