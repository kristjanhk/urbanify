package system.graphics.floorPlanner;

import javafx.scene.shape.Rectangle;

public class Seat extends Rectangle {
    private Seattype seattype;

    public Seat(Seattype seattype) {
        this.seattype = seattype;
    }

    public Seat() {
        this.seattype = Seattype.AVAILABLE;
    }

    public void setSeattype(Seattype seattype) {
        this.seattype = seattype;
    }

    public Seattype getSeattype() {
        return this.seattype;
    }
}
