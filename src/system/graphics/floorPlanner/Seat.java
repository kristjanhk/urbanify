package system.graphics.floorPlanner;

import javafx.scene.shape.Circle;

/**
 * Iste saaliplaanis (ja müügipunktis)
 * On Circle mähisklass
 * Luuakse programmi töö käigus, seega ei saa FXMLis ette valmistada
 */
public class Seat extends Circle {
    private Seattype seattype;
    private int x;
    private int y;

    public Seat() {
        super(25);
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
        if (this.seattype.equals(Seattype.AVAILABLE)) {
            this.seattype = Seattype.UNAVAILABLE;
            //this.setStyle("-fx-fill: #5b5c5c");
            // TODO: 27.04.2016 set from css?, or use togglebutton 
        } else {
            this.seattype = Seattype.AVAILABLE;
            //this.setStyle("-fx-fill: #7e2b2c");
        }
    }

    @Override
    public String toString() {
        return String.valueOf(this.x);
    }
}
