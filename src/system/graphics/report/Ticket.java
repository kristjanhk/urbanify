package system.graphics.report;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import system.data.Lang;

import java.util.ArrayList;

/**
 * Pilet aruande stseenis
 * On HBoxi mähisklass
 * Luuakse programmi töö käigus, seega ei saa FXMLis ette valmistada
 */
public class Ticket extends HBox{

    public Ticket(String ticketname, ArrayList<Double> ticketdata) {
        this.setAlignment(Pos.CENTER_RIGHT);
        this.setMaxSize(715.0, 50.0);
        this.setMinSize(715.0, 50.0);
        this.setPrefSize(715.0, 50.0);
        this.getStyleClass().add("textFlow30");
        VBox.setMargin(this, new Insets(0.0, 0.0, 20.0, 0.0));
        this.initChildren(ticketname, ticketdata);
    }

    private void initChildren(String ticketname, ArrayList<Double> ticketdata) {
        Text ticket = new Text(ticketname);
        ticket.getStyleClass().add("text35");
        ticket.setStrokeType(StrokeType.OUTSIDE);
        ticket.setStrokeWidth(0.0);
        ticket.setWrappingWidth(180.0);
        HBox.setMargin(ticket, new Insets(0.0, 0.0, 0.0, 20.0));
        this.getChildren().add(ticket);

        Text cost = new Text(String.format("%.2f", ticketdata.get(0)) + " " + Lang.getActiveLang().getCurrency());
        cost.getStyleClass().add("text35");
        cost.setStrokeType(StrokeType.OUTSIDE);
        cost.setStrokeWidth(0.0);
        cost.setWrappingWidth(134.0);
        cost.setTextAlignment(TextAlignment.CENTER);
        HBox.setMargin(cost, new Insets(0.0, 0.0, 0.0, 3.0));
        this.getChildren().add(cost);

        Text quantity = new Text(String.valueOf(ticketdata.get(1).intValue()));
        quantity.getStyleClass().add("text35");
        quantity.setStrokeType(StrokeType.OUTSIDE);
        quantity.setStrokeWidth(0.0);
        quantity.setWrappingWidth(188.0);
        quantity.setTextAlignment(TextAlignment.CENTER);
        HBox.setMargin(quantity, new Insets(0.0, 0.0, 0.0, 44.0));
        this.getChildren().add(quantity);

        Text total = new Text(String.format("%.2f", ticketdata.get(0) * ticketdata.get(1)) + " " +
                Lang.getActiveLang().getCurrency());
        total.getStyleClass().add("text35");
        total.setStrokeType(StrokeType.OUTSIDE);
        total.setStrokeWidth(0.0);
        total.setWrappingWidth(136.0);
        total.setTextAlignment(TextAlignment.CENTER);
        HBox.setMargin(total, new Insets(0.0, 0.0, 0.0, 12.0));
        this.getChildren().add(total);
    }
}
