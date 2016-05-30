package system.graphics.floorPlanner;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;

/**
 * Iste saaliplaanis (ja müügipunktis)
 * On Circle mähisklass
 * Luuakse programmi töö käigus, seega ei saa FXMLis ette valmistada
 */
public class Seat extends Button {
    public enum Seattype {AVAILABLE, UNAVAILABLE}
    private Seattype seattype;
    private Controller parentController;
    private int x;
    private int y;

    public Seat(Controller parentController) {
        super();
        this.parentController = parentController;
        double radius = 25.0;
        this.setShape(new Circle(radius));
        this.setMinSize(radius * 2, radius * 2);
        this.setMaxSize(radius * 2, radius * 2);
        this.toggleStyle(true);
        this.setOnMouseClicked(event -> this.handleClick());
        this.setOnMouseEntered(event -> this.parentController.setSeatsHovering(this, true));
        this.setOnMouseExited(event -> this.parentController.setSeatsHovering(this, false));
    }

    // TODO: 20.04.2016 asukohad määrata pane ja group abiga vms
    public Seat(Controller parentController, int row, int column) {
        this(parentController);
        this.x = column;
        this.y = row;
        if (column == 0 || row == 0) {
            this.setText();
        }
        this.setCenterX(30 + column * 60);
        this.setCenterY(500 - row * 60);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    private void setText() {
        super.setText(String.valueOf(this.x == 0 ? this.y + 1 : this.x + 1));
        this.setStyle("-fx-font-size: 20");
        this.setAlignment(Pos.BASELINE_CENTER);
    }

    private void setCenterX(double location) {
        this.setLayoutX(location - this.getWidth() / 2);
    }

    private void setCenterY(double location) {
        this.setLayoutY(location - this.getHeight() / 2);
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
        this.toggleStyle(false);
    }

    private void toggleStyle(boolean init) {
        String styleclass = "";
        if (init) {
            this.getStyleClass().clear();
        }
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

    public void triggerHover(boolean enable) {
        this.setHover(enable);
    }

    @Override
    public String toString() {
        return String.valueOf(this.x);
    }
}
