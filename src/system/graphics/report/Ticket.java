package system.graphics.report;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;

/**
 * Pilet aruande stseenis
 * On GridPane mähisklass
 * Luuakse programmi töö käigus, seega ei saa FXMLis ette valmistada
 */
public class Ticket extends GridPane {
    private enum Texttype {COST, QUANTITY, TOTAL}
    private String currency;
    private Text ticket;
    private SimpleDoubleProperty cost = new SimpleDoubleProperty();
    private Text quantity;
    private Text total;

    public Ticket(String ticketname, ArrayList<Double> ticketdata, String currency) {
        this.currency = currency;
        this.setAlignment(Pos.CENTER_RIGHT);
        this.setMaxSize(750, 50.0);
        this.setMinSize(750, 50.0);
        this.setPrefSize(750, 50.0);
        this.getStyleClass().add("textFlow30");
        VBox.setMargin(this, new Insets(0.0, 0.0, 20.0, 0.0));
        this.initChildren(ticketname);
        this.update(ticketdata);
    }

    private void initChildren(String ticketname) {
        this.ticket = new Text(ticketname);
        this.ticket.getStyleClass().add("text35");
        this.ticket.setStrokeType(StrokeType.OUTSIDE);
        this.ticket.setStrokeWidth(0.0);
        this.ticket.setWrappingWidth(242.0);
        GridPane.setMargin(this.ticket, new Insets(0.0, 0.0, 0.0, 20.0));
        this.add(this.ticket, 0, 0);
        this.getColumnConstraints().add(new ColumnConstraints(242, 242, 242));

        Text costText = new Text();
        costText.textProperty().bind(this.cost.asString("%.2f").concat(" " + this.currency));
        costText.getStyleClass().add("text35");
        costText.setStrokeType(StrokeType.OUTSIDE);
        costText.setStrokeWidth(0.0);
        costText.setWrappingWidth(200);
        costText.setTextAlignment(TextAlignment.CENTER);
        GridPane.setMargin(costText, new Insets(0.0, 0.0, 0.0, 0));
        this.add(costText, 1, 0);
        this.getColumnConstraints().add(new ColumnConstraints(200, 200, 200));

        this.quantity = new Text();
        this.quantity.getStyleClass().add("text35");
        this.quantity.setStrokeType(StrokeType.OUTSIDE);
        this.quantity.setStrokeWidth(0.0);
        this.quantity.setWrappingWidth(125);
        this.quantity.setTextAlignment(TextAlignment.CENTER);
        GridPane.setMargin(this.quantity, new Insets(0.0, 0.0, 0.0, 0));
        this.add(this.quantity, 2, 0);
        this.getColumnConstraints().add(new ColumnConstraints(125, 125, 125));

        this.total = new Text();
        this.total.getStyleClass().add("text35");
        this.total.setStrokeType(StrokeType.OUTSIDE);
        this.total.setStrokeWidth(0.0);
        this.total.setWrappingWidth(175.0);
        this.total.setTextAlignment(TextAlignment.CENTER);
        GridPane.setMargin(this.total, new Insets(0.0, 0.0, 0.0, 0));
        this.add(this.total, 3, 0);
        this.getColumnConstraints().add(new ColumnConstraints(175, 175, 175));
    }

    private void setTextFor(Texttype type, Double data) {
        switch (type) {
            case COST:
                this.cost.set(data);
                break;
            case QUANTITY:
                this.quantity.setText(String.valueOf(data.intValue()));
                break;
            case TOTAL:
                this.total.setText(String.format("%.2f", data) + " " + this.currency);
                break;
        }
    }

    public void update(ArrayList<Double> ticketdata) {
        this.setTextFor(Texttype.COST, ticketdata.get(0));
        this.setTextFor(Texttype.QUANTITY, ticketdata.get(1));
        this.setTextFor(Texttype.TOTAL, ticketdata.get(0) * ticketdata.get(1));
    }

    public String getTicketName() {
        return this.ticket.getText();
    }

    public int getAmount() {
        return Integer.parseInt(this.quantity.getText());
    }

    public double getPrice() {
        return this.getAmount() * this.cost.get();
    }
}
