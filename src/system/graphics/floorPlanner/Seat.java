package system.graphics.floorPlanner;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Seat extends Circle {
    private Seattype seattype;
    private int x;
    private int y;

    public Seat() {
        super(25, Color.GRAY);
        this.seattype = Seattype.AVAILABLE;
        this.setOnMouseClicked(event -> this.handleClick());
    }

    // TODO: 20.04.2016 asukohad määrata pane ja group abiga vms
    public Seat(int row, int column) {
        this();
        this.x = column;
        this.y = row;
        this.setCenterX(30 + column * 60);
        this.setCenterY(500 - row * 60);
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

    @Override
    public String toString() {
        return String.valueOf(this.x);
    }
}


/*


|
|
|
|
|





 */
