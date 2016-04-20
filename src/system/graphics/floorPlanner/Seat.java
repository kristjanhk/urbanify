package system.graphics.floorPlanner;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Seat extends Circle {
    private Seattype seattype;
    private int x;
    private int y;

    public Seat() {
        super(25, Color.GRAY);
        this.seattype = Seattype.AVAILABLE;
        //this.setMouseHover();
        this.getStyleClass().setAll("seatButton");
        this.setOnMouseClicked(event -> this.handleClick());
    }

    // TODO: 20.04.2016 asukohad määrata pane ja group abiga vms
    public Seat(int row, int column) {
        this();
        this.x = row;
        this.y = column;
        this.setCenterX(30 + this.x * 60);
        this.setCenterY(100 + this.y * 60);
    }

    public void setSeattype(Seattype seattype) {
        this.seattype = seattype;
    }

    public Seattype getSeattype() {
        return this.seattype;
    }
    
    private void handleClick() {
        System.out.println("Clicked: (" + this.x + "," + this.y + ")");
        // TODO: 20.04.2016 toggle colors / seattypes 
    }

    // TODO: 20.04.2016 tegelda hoveriga v cssi kohe?
    //see siin ei tööta muidugi

    //http://stackoverflow.com/questions/13074459/javafx-2-and-css-pseudo-classes-setting-hover-attributes-in-setstyle-method
    /*private void setMouseHover() {
        this.styleProperty().bind(Bindings
                        .when(this.hoverProperty())
                        .then(new SimpleStringProperty("-fx-background-color: blue;"))
                        .otherwise(new SimpleStringProperty("-fx-background-color: #5b5c5c;")));
    }*/


    @Override
    public String toString() {
        return String.valueOf(this.y);
    }
}
