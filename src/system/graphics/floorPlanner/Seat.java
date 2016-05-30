package system.graphics.floorPlanner;

import javafx.fxml.FXML;
import javafx.scene.shape.Circle;

/**
 * Iste saaliplaanis (ja müügipunktis)
 * On Circle mähisklass
 * Luuakse programmi töö käigus, seega ei saa FXMLis ette valmistada
 */
public class Seat extends Circle {
    public enum Seattype {AVAILABLE, UNAVAILABLE}
    private Seattype seattype;
    private int x;
    private int y;

    public Seat() {
        super(25);
        this.toggleStyle();
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

    @FXML
    protected void handleClick() {
        System.out.println("Clicked: (" + this.x + "," + this.y + ")");
        this.toggleStyle();
    }

    private void toggleStyle() {
        String styleclass = "";
        if (this.getStyleClass().size() > 0) {
            styleclass = this.getStyleClass().get(0);
            this.getStyleClass().clear();
        }
        if (styleclass.equals("") || this.seattype == Seattype.UNAVAILABLE) {
            this.seattype = Seattype.AVAILABLE;
            this.getStyleClass().add("seat_available");
        } else {
            this.seattype = Seattype.UNAVAILABLE;
            this.getStyleClass().add("seat_unavailable");
        }
    }

    @Override
    public String toString() {
        return String.valueOf(this.x);
    }
}
