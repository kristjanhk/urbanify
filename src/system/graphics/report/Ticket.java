package system.graphics.report;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import system.data.Lang;

import java.util.ArrayList;

/**
 * Pilet aruande stseenis
 * On GridPane mähisklass
 * Luuakse programmi töö käigus, seega ei saa FXMLis ette valmistada
 */
public class Ticket extends GridPane {

    public Ticket(String ticketname, ArrayList<Double> ticketdata) {
        this.setAlignment(Pos.CENTER_RIGHT);
        this.setMaxSize(750, 50.0);
        this.setMinSize(750, 50.0);
        this.setPrefSize(750, 50.0);
        this.getStyleClass().add("textFlow30");
        VBox.setMargin(this, new Insets(0.0, 0.0, 20.0, 0.0));
        this.initChildren(ticketname, ticketdata);
    }

    private void initChildren(String ticketname, ArrayList<Double> ticketdata) {
        Text ticket = new Text(ticketname);
        ticket.getStyleClass().add("text35");
        ticket.setStrokeType(StrokeType.OUTSIDE);
        ticket.setStrokeWidth(0.0);
        ticket.setWrappingWidth(242.0);
        GridPane.setMargin(ticket, new Insets(0.0, 0.0, 0.0, 20.0));
        this.add(ticket,0,0);
        this.getColumnConstraints().add(new ColumnConstraints(242,242,242));

        Text cost = new Text(String.format("%.2f", ticketdata.get(0)) + " " + Lang.getActiveLang().getCurrency());
        cost.getStyleClass().add("text35");
        cost.setStrokeType(StrokeType.OUTSIDE);
        cost.setStrokeWidth(0.0);
        cost.setWrappingWidth(125.0);
        cost.setTextAlignment(TextAlignment.CENTER);
        GridPane.setMargin(cost, new Insets(0.0, 0.0, 0.0, 0));
        this.add(cost,1,0);
        this.getColumnConstraints().add(new ColumnConstraints(125,125,125));

        Text quantity = new Text(String.valueOf(ticketdata.get(1).intValue()));
        quantity.getStyleClass().add("text35");
        quantity.setStrokeType(StrokeType.OUTSIDE);
        quantity.setStrokeWidth(0.0);
        quantity.setWrappingWidth(200.0);
        quantity.setTextAlignment(TextAlignment.CENTER);
        GridPane.setMargin(quantity, new Insets(0.0, 0.0, 0.0, 0));
        this.add(quantity,2,0);
        this.getColumnConstraints().add(new ColumnConstraints(200,200,200));

        Text total = new Text(String.format("%.2f", ticketdata.get(0) * ticketdata.get(1)) + " " +
                Lang.getActiveLang().getCurrency());
        total.getStyleClass().add("text35");
        total.setStrokeType(StrokeType.OUTSIDE);
        total.setStrokeWidth(0.0);
        total.setWrappingWidth(175.0);
        total.setTextAlignment(TextAlignment.CENTER);
        GridPane.setMargin(total, new Insets(0.0, 0.0, 0.0, 0));
        this.add(total,3,0);
        this.getColumnConstraints().add(new ColumnConstraints(175,175,175));
    }
}
