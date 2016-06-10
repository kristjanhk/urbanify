package system.graphics.floorPlanner;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import system.graphics.common.FloorPlanPane;
import system.graphics.pointOfSale.Controller;

import java.util.ArrayList;

/**
 * Iste saaliplaanis (ja müügipunktis)
 * On Circle mähisklass
 * Luuakse programmi töö käigus, seega ei saa FXMLis ette valmistada
 */
public class Seat extends Button {
    public enum Seattype {AVAILABLE, UNAVAILABLE, OCCUPIED, DISABLED}
    private Seattype seattype;
    private FloorPlanPane floorPlan;
    private int x;
    private int y;
    private boolean locked = false;

    private Seat(FloorPlanPane floorPlan, Seattype seattype) {
        super();
        this.floorPlan = floorPlan;
        double radius = 25.0;
        this.setShape(new Circle(radius));
        this.setMinSize(radius * 2, radius * 2);
        this.setMaxSize(radius * 2, radius * 2);
        this.setGivenStyle(seattype);
        this.setOnMouseClicked(event -> this.handleClick());
        this.setOnMouseEntered(event -> this.floorPlan.setSeatsHovering(this, true));
        this.setOnMouseExited(event -> this.floorPlan.setSeatsHovering(this, false));
    }

    public Seat(FloorPlanPane floorPlan, int row, int column) {
        this(floorPlan, Seattype.AVAILABLE,  row, column);
    }

    public Seat(FloorPlanPane floorPlan, Seattype seattype, int row, int column) {
        this(floorPlan, seattype);
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

    public void lock() {
        this.locked = true;
    }

    public void disable() {
        this.setSeattype(Seattype.DISABLED);
    }

    public void setSeattype(Seattype seattype) {
        this.seattype = seattype;
        this.setGivenStyle(seattype);
    }

    public ArrayList<Integer> isUnavailable() {
        if (this.seattype == Seattype.UNAVAILABLE || this.seattype == Seattype.OCCUPIED) {
            return this.getCoordinates();
        }
        return null;
    }

    public ArrayList<Integer> hasBeenDisabled() {
        if (this.seattype == Seattype.DISABLED) {
            return this.getCoordinates();
        }
        return null;
    }

    public ArrayList<Integer> getCoordinates() {
        ArrayList<Integer> coordinates = new ArrayList<>();
        coordinates.add(this.y);
        coordinates.add(this.x);
        return coordinates;
    }

    @FXML
    protected void handleClick() {
        // TODO: 10.06.2016 remove 
        System.out.println("Clicked: (" + this.y + "," + this.x + ")");
        if (!this.locked) {
            if (this.floorPlan.getParentController() instanceof system.graphics.pointOfSale.Controller) {
                if (this.seattype == Seattype.AVAILABLE) {
                    if (((Controller) this.floorPlan.getParentController()).addSeat(this.getCoordinates())) {
                        this.setGivenStyle(Seattype.OCCUPIED);
                    }
                } else {
                    if (((Controller) this.floorPlan.getParentController()).removeSeat(this.getCoordinates())) {
                        this.setGivenStyle(Seattype.AVAILABLE);
                    }
                }
            } else {
                this.toggleStyle(this.seattype);
            }
        }
    }

    private void toggleStyle(Seattype seattype) {
        if (seattype == Seattype.AVAILABLE) {
            if (this.floorPlan.getParentController() instanceof system.graphics.floorPlanner.Controller) {
                this.setGivenStyle(Seattype.DISABLED);
            } else {
                this.setGivenStyle(Seattype.UNAVAILABLE);
            }
        } else {
            this.setGivenStyle(Seattype.AVAILABLE);
        }
    }

    private void setGivenStyle(Seattype seattype) {
        this.getStyleClass().clear();
        this.seattype = seattype;
        if (seattype == Seattype.AVAILABLE) {
            this.getStyleClass().add("seat_available");
        } else if (seattype == Seattype.OCCUPIED) {
            this.getStyleClass().add("seat_occupied");
        } else if (seattype == Seattype.DISABLED) {
            if (this.floorPlan.getParentController() instanceof system.graphics.floorPlanner.Controller) {
                this.getStyleClass().add("seat_disabled");
            } else {
                this.getStyleClass().add("seat_invisible");
            }
        } else {
            this.getStyleClass().add("seat_unavailable");
        }
    }

    public void triggerHover(boolean enable) {
        if (!(this.floorPlan.getParentController() instanceof system.graphics.pointOfSale.Controller) &&
                this.seattype != Seattype.DISABLED) {
            this.setHover(enable);
        }
    }

    @Override
    public String toString() {
        return String.valueOf(this.x);
    }
}
