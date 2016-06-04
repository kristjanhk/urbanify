package system.graphics.eventCreator;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    private TextField currencyText;
    private boolean currencyTextValidated = true;
    private boolean priceTextValidated;
    private TextField ticketText;
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

    public Ticket(Controller parentController, VBox parentNode, String name, String price, String currency) {
        this(parentController, parentNode);
        this.priceText.setText(price);
        this.priceTextValidated = true;
        this.currencyText.setText(currency);
        this.ticketText.setText(name);
        this.ticketTextValidated = true;
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
        this.getChildren().add(createVBox(this.priceText));

        this.currencyText = new TextField();
        this.currencyText.setMinSize(60.0, 70.5);
        this.currencyText.setMaxSize(60.0, 70.5);
        this.currencyText.setPrefSize(60.0, 70.5);
        this.currencyText.setStyle("-fx-alignment: center; -fx-padding: 0 0 0 0");
        this.getChildren().add(createVBox(this.currencyText));

        this.ticketText = new TextField();
        this.ticketText.setMinSize(300.0, 70.5);
        this.ticketText.setMaxSize(300.0, 70.5);
        this.ticketText.setPrefSize(300.0, 70.5);
        this.getChildren().add(createVBox(this.ticketText));
    }

    private static VBox createVBox(Node node) {
        VBox vBox = new VBox(node);
        HBox.setMargin(vBox, new Insets(0.0, 0.0, 0.0, 30.0));
        return vBox;
    }

    public void setLanguage() {
        this.priceText.setPromptText(Word.PRICE.toString());
        this.setCurrencyText();
        this.ticketText.setPromptText(Word.TICKETYPE.toString());
    }

    private void addValidation() {
        MainHandler.setValidationFor(this.priceText, "\\d+([\\.\\,]\\d+)?").addListener(
                (observable, oldValue, newValue) -> {
                    this.priceTextValidated = !newValue;
                    this.parentController.checkNextButtonValidation();
                });
        this.priceText.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (this.priceText.getText().contains(",")) {
                    this.priceText.setText(this.priceText.getText().replace(",", "."));
                }
            }
        });
        MainHandler.setValidationFor(this.currencyText, "^(?=\\s*\\S).{1}$").addListener(
                (observable, oldValue, newValue) -> {
                    this.currencyTextValidated = !newValue;
                    this.parentController.checkNextButtonValidation();
                });
        this.currencyText.textProperty().addListener((observable, oldValue, newValue) -> {
            this.parentController.getTickets().stream().filter(node -> node instanceof Ticket).
                    forEach(node -> ((Ticket) node).setCurrencyText(this.currencyText.getText()));
        });
        MainHandler.setValidationFor(this.ticketText, "^(?=\\s*\\S).*$").addListener(
                (observable, oldValue, newValue) -> {
                    this.ticketTextValidated = !newValue;
                    this.parentController.checkNextButtonValidation();
                });
    }

    public String getCurrencyText() {
        return this.currencyText.getText();
    }

    public void setCurrencyText(String text) {
        this.currencyText.setText(text);
    }

    private void setCurrencyText() {
        if (this.parentController.getTickets().size() > 1) {
            this.currencyText.setText(((Ticket) this.parentController.getTickets().get(0)).getCurrencyText());
        } else {
            this.currencyText.setText(Lang.getActiveLang().getCurrency());
        }
    }

    public boolean isValid() {
        return this.priceTextValidated && this.currencyTextValidated && this.ticketTextValidated;
    }

    public String getType() {
        return this.ticketText.getText();
    }

    public double getPrice() {
        return Double.parseDouble(this.priceText.getText());
    }
}
