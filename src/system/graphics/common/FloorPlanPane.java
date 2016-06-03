package system.graphics.common;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import system.data.Event;
import system.data.Lang;
import system.data.Word;
import system.graphics.floorPlanner.Imagetype;
import system.graphics.floorPlanner.Seat;

import java.util.ArrayList;
import java.util.HashMap;

public class FloorPlanPane extends VBox {
    public enum Property {DIMENSIONS, UNAVAILABLE, IMAGETYPE}
    private AbstractController parentController;
    private StackPane floorPlan;
    private ImageView floorPlanImage;
    private int columnCount;
    private double maxY = 0.0;

    public FloorPlanPane() {
        this.floorPlan = new StackPane();
        this.floorPlan.setAlignment(Pos.BOTTOM_CENTER);
        this.floorPlan.setMinSize(0.0, 0.0);
        this.floorPlan.setPrefSize(9999.0, 9999.0);
        this.floorPlan.getStyleClass().add("background");
        this.floorPlanImage = new ImageView();
        this.floorPlanImage.setFitWidth(443.0);
        this.floorPlanImage.setFitHeight(82.0);
        VBox.setMargin(this.floorPlanImage, new Insets(0.0, 0.0, 20.0, 0.0));
        this.getChildren().addAll(this.floorPlan, this.floorPlanImage);
        this.setFloorPlanImage(Imagetype.STAGE);
        this.floorPlan.widthProperty().addListener((observable, oldValue, newValue) -> {
            this.checkPaneWidthResize(newValue);
        });
        this.floorPlan.heightProperty().addListener((observable, oldValue, newValue) -> {
            this.checkPaneHeightResize(newValue);
        });
    }

    public FloorPlanPane(AbstractController parentController) {
        this();
        this.parentController = parentController;
    }

    public void init(AbstractController parentController, ArrayList<Integer> defaultSize) {
        this.parentController = parentController;
        this.createNewFloorPlan(defaultSize, true);
    }

    public int getSize() {
        return this.getFloor().size() * this.columnCount;
    }

    private void setColumnCountText() {
        if (parentController instanceof system.graphics.floorPlanner.Controller) {
            ((system.graphics.floorPlanner.Controller) this.parentController).setColumnCountText(this.columnCount);
        }
    }

    private void setRowCountText() {
        if (parentController instanceof system.graphics.floorPlanner.Controller) {
            ((system.graphics.floorPlanner.Controller) this.parentController).setRowCountText(this.getFloor().size());
        }
    }

    private void validateButtons() {
        if (parentController instanceof system.graphics.floorPlanner.Controller) {
            ((system.graphics.floorPlanner.Controller) this.parentController).validateButtons();
        }
    }

    public void setSeatsHovering(Seat triggerer, boolean trigger) {
        for (Node group : this.getFloor()) {
            for (Node seat : ((Group) group).getChildren()) {
                Seat target = (Seat) seat;
                if (!triggerer.equals(target)) {
                    if (triggerer.getX() == target.getX() || triggerer.getY() == target.getY()) {
                        target.triggerHover(trigger);
                    }
                }
            }
        }
    }

    public void save(String name, String imageName, Event event) {
        Word imageConstant = Word.toEnum(imageName.toLowerCase());
        if (imageConstant != null) {
            HashMap<Property, Object> floorPlan = new HashMap<>(3);
            ArrayList<Integer> dimensions = new ArrayList<>(2);
            dimensions.add(this.getFloor().size());
            dimensions.add(this.columnCount);
            ArrayList<ArrayList<Integer>> unavailables = new ArrayList<>();
            for (Node group : this.getFloor()) {
                for (Node seat : ((Group) group).getChildren()) {
                    ArrayList<Integer> seatCoordinates = ((Seat) seat).isUnavailable();
                    if (seatCoordinates != null) {
                        unavailables.add(seatCoordinates);
                    }
                }
            }
            floorPlan.put(Property.DIMENSIONS, dimensions);
            floorPlan.put(Property.UNAVAILABLE, unavailables);
            floorPlan.put(Property.IMAGETYPE, imageConstant.inLang(Lang.ENGLISH).toUpperCase());
            if (event != null) {
                event.setFloorPlan(floorPlan);
                event.setMaxSeats(this.getSize() - unavailables.size());
            } else {
                this.parentController.getData().saveFloorPlan(name, floorPlan);
            }

        }
    }

    public void setFloorPlanImage(String imageName) {
        Word imageConstant = Word.toEnum(imageName);
        if (imageConstant != null) {
            this.setFloorPlanImage(Imagetype.valueOf(imageConstant.inLang(Lang.ENGLISH).toUpperCase()));
        }
    }

    private void setFloorPlanImage(Imagetype imagetype) {
        this.floorPlanImage.setImage(imagetype.toImage());
    }

    private ArrayList<Integer> getSavedFloorPlanDimensions(String name, Event event) {
        Object dimensions;
        if (event != null) {
            dimensions = event.getFloorPlan().get(Property.DIMENSIONS);
        } else {
            dimensions = this.parentController.getData().getFloorPlan(name).get(FloorPlanPane.Property.DIMENSIONS);
        }

        ArrayList<Integer> floorPlanSize = new ArrayList<>(2);
        for (int i = 0; i < ((ArrayList) dimensions).size(); i++) {
            Object number = ((ArrayList) dimensions).get(i);
            if (number instanceof Number) {
                floorPlanSize.add(((Number) number).intValue());
            }
        }
        return floorPlanSize;
    }

    private ArrayList<ArrayList<Integer>> getSavedFloorPlanUnavailables(String name, Event event) {
        Object seats;
        if (event != null) {
            seats = event.getFloorPlan().get(Property.UNAVAILABLE);
        } else {
            seats = this.parentController.getData().getFloorPlan(name).get(FloorPlanPane.Property.UNAVAILABLE);
        }

        ArrayList<ArrayList<Integer>> unavailables = new ArrayList<>();
        for (Object seatData : (ArrayList) seats) {
            ArrayList<Integer> data = new ArrayList<>(2);
            for (Object coordinate : (ArrayList) seatData) {
                if (coordinate instanceof Number) {
                    data.add(((Number) coordinate).intValue());
                }
            }
            unavailables.add(data);
        }
        return unavailables;
    }

    private Imagetype getSavedFloorPlanImageType(String name, Event event) {
        Object imagetype;
        if (event != null) {
            imagetype = event.getFloorPlan().get(Property.IMAGETYPE);
        } else {
            imagetype = this.parentController.getData().getFloorPlan(name).get(FloorPlanPane.Property.IMAGETYPE);
        }
        return Imagetype.valueOf((String) imagetype);
    }

    public String getSavedFloorPlanImageTypeString(Event event) {
        return (String) event.getFloorPlan().get(Property.IMAGETYPE);
    }

    public void loadFloorPlan(String name, Event event) {
        this.createNewFloorPlan(this.getSavedFloorPlanDimensions(name, event), false);
        this.setFloorPlanImage(this.getSavedFloorPlanImageType(name, event));
        ArrayList<ArrayList<Integer>>  unavailables = this.getSavedFloorPlanUnavailables(name, event);
        for (Node group : this.getFloor()) {
            ((Group) group).getChildren().stream().filter(
                    seat -> unavailables.contains(((Seat) seat).getCoordinates())).forEach(
                    unavailableSeat -> {
                        ((Seat) unavailableSeat).setSeattype(Seat.Seattype.UNAVAILABLE);
                        if (this.parentController instanceof system.graphics.report.Controller) {
                            ((Seat) unavailableSeat).lock();
                        }
                    });
        }
    }

    public void loadFloorPlan(String name) {
        this.loadFloorPlan(name, null);
    }

    public void loadFloorPlan(Event event) {
        this.loadFloorPlan(null, event);
    }

    private void createNewFloorPlan(ArrayList<Integer> dimensions, boolean init) {
        this.resetFloor();
        for (int row = 0; row < dimensions.get(0); row++) {
            if (init) {
                this.addNewRow();
            } else {
                this.addRowAction();
            }
        }
        for (int column = 0; column < dimensions.get(1) - 1; column++) {
            if (init) {
                this.addNewColumn();
            } else {
                this.addColumnAction();
            }
        }
    }

    private ObservableList<Node> getFloor() {
        return this.getFloorGroup().getChildren();
    }

    private Group getFloorGroup() {
        return (Group) this.floorPlan.getChildren().get(0);
    }

    private double getGroupMaxY() {
        return this.getFloorGroup().getBoundsInParent().getMaxY();
    }

    private void resizeFloorPlan(double prevY, double scalefactor) {
        this.getFloorGroup().setScaleX(getFloorGroup().getScaleX() * scalefactor);
        this.getFloorGroup().setScaleY(getFloorGroup().getScaleY() * scalefactor);
        this.correctY(prevY);
    }

    private void correctY(double prevY) {
        if (prevY != -1.0) {
            double newY = this.getGroupMaxY();
            this.maxY += prevY - newY;
            this.getFloorGroup().setTranslateY(this.maxY);
        }
    }

    private void resetY() { // FIXME: 24.04.2016 ei toimi
        this.maxY = 0.0;
        this.getFloorGroup().setTranslateY(this.maxY);
    }

    private void resetFloor() {
        this.floorPlan.getChildren().clear();
        this.floorPlan.getChildren().add(new Group());
        StackPane.setMargin(this.getFloorGroup(), new Insets(10));
        this.columnCount = 0;
        this.maxY = 0.0;
        this.setRowCountText();
        this.setColumnCountText();
        this.validateButtons();
    }


    public void addRowAction() {
        if (this.getFloor().size() != 0) {
            double prevY = getGroupMaxY();
            this.addNewRow();
            this.checkPaneHeightResize(this.getHeight());
            this.correctY(prevY);
        } else {
            this.addNewRow();
        }
    }

    public void removeRowAction() {
        if (this.getFloor().size() > 1) {
            double prevY = getGroupMaxY();
            this.removeFirstRow();
            this.checkPaneHeightResize(this.getHeight());
            this.correctY(prevY);
        } else {
            this.resetFloor();
        }
    }

    public void addColumnAction() {
        if (this.columnCount != 0) {
            this.addNewColumn();
            this.checkPaneWidthResize(this.getWidth());
        } else {
            this.addNewColumn();
        }
    }

    public void removeColumnAction() {
        this.removeLastColumn();
        this.checkPaneWidthResize(this.getWidth());
    }

    private void addNewRow() {
        Group group = new Group();
        for (int i = 0; i < this.columnCount; i++) {
            group.getChildren().add(new Seat(this, this.getFloor().size(), i));
        }
        this.getFloor().add(group);
        this.setRowCountText();
        if (this.columnCount < 1) {
            this.addNewColumn();
        }
    }

    private void removeFirstRow() {
        if (this.getFloor().size() > 0) {
            if (this.getFloor().size() == 1) {
                this.columnCount = 0;
                this.setColumnCountText();
                this.resetY();
            }
            this.getFloor().remove(this.getFloor().size() - 1);
            this.setRowCountText();
        }
    }

    private void addNewColumn() {
        for (int i = this.getFloor().size() - 1; i >= 0; i--) {
            ((Group) this.getFloor().get(i)).getChildren().add(new Seat(this, i, this.columnCount));
        }
        this.columnCount++;
        this.setColumnCountText();
        if (getFloor().size() < 1) {
            this.addNewRow();
        }
    }

    private void removeLastColumn() {
        if (this.columnCount > 0) {
            this.columnCount--;
            if (this.columnCount == 0) {
                this.getFloor().clear();
                this.setRowCountText();
                this.resetY();
            } else {
                for (Node group : this.getFloor()) {
                    ((Group) group).getChildren().remove(this.columnCount);
                }
            }
            this.setColumnCountText();
        }
    }

    // TODO: 23.04.2016 viimane if condition on bugine mõlemal
    // selle asemel get ratio, korrutada oma kõrguse laiusega läbi ja saada teada uus scaletud suurus
    // kontrollida seda kõrguse/laiusega või ymaxga??

    /**
     * Saaliplaani laiuse kontroll
     * <p>
     * Vähendame suurust kui istmetegrupi laius on suurem kui saaliplaani laius
     * Suurendame suurust kui istmetegrupi laius on väiksem kui istmegrupi scalemata laius,
     * istmetegrupi kõrgus on väiksem kui istmegrupi scalemata kõrgus,
     * istmetegrupi kõrgus on väiksem kui saaliplaani kõrgus - 30 fixme
     */
    private void checkPaneWidthResize(Number width) {
        double paneWidth = width.doubleValue() - 20;
        double groupWidth = this.getFloorGroup().getBoundsInParent().getWidth();
        double groupOrigWidth = this.getFloorGroup().getBoundsInLocal().getWidth();
        double groupHeight = this.getFloorGroup().getBoundsInParent().getHeight();
        double groupOrigHeight = this.getFloorGroup().getBoundsInLocal().getHeight();
        double ratio = paneWidth / groupWidth;
        if (groupWidth > paneWidth) {
            this.resizeFloorPlan(this.getGroupMaxY(), ratio);
        } else if (groupWidth < groupOrigWidth &&
                groupHeight < groupOrigHeight &&
                groupHeight < this.getHeight() - 30) {
            this.resizeFloorPlan(this.getGroupMaxY(), ratio);
        }
    }

    /**
     * Saaliplaani kõrguse kontroll
     * <p>
     * Vähendame suurust kui istmetegrupi kõrgus on suurem kui saaliplaani kõrgus
     * Suurendame suurust kui istmetegrupi kõrgus on väiksem kui istmegrupi scalemata kõrgus,
     * istmetegrupi laius on väiksem kui istmegrupi scalemata laius,
     * istmetegrupi laius on väiksem kui saaliplaani laius - 30 fixme
     */
    private void checkPaneHeightResize(Number height) {
        double paneHeight = height.doubleValue() - 20;
        double groupHeight = this.getFloorGroup().getBoundsInParent().getHeight();
        double groupOrigHeight = this.getFloorGroup().getBoundsInLocal().getHeight();
        double groupWidth = this.getFloorGroup().getBoundsInParent().getWidth();
        double groupOrigWidth = this.getFloorGroup().getBoundsInLocal().getWidth();
        double ratio = paneHeight / groupHeight;
        if (groupHeight > paneHeight) {
            this.resizeFloorPlan(this.getGroupMaxY(), ratio);
        } else if (groupHeight < groupOrigHeight &&
                groupWidth < groupOrigWidth &&
                groupWidth < this.getWidth() - 30) {
            this.resizeFloorPlan(this.getGroupMaxY(), ratio);
        }
    }

    private void resize(ReadOnlyDoubleProperty propertyType, Number value) {
        /*double propertyValue = value.doubleValue() - 20;
        double groupWidth = this.getFloorGroup().getBoundsInParent().getWidth();
        double groupOrigWidth = this.getFloorGroup().getBoundsInLocal().getWidth();
        double groupHeight = this.getFloorGroup().getBoundsInParent().getHeight();
        double groupOrigHeight = this.getFloorGroup().getBoundsInLocal().getHeight();
        if (propertyType.equals(this.floorPlan.widthProperty())) {
            double ratio = propertyValue / groupWidth;
            if (groupWidth > propertyValue) {
                resize(getGroupMaxY(), ratio);
            } else if (propertyValue - groupWidth > 60) {

            }
        } else if (propertyType.equals(this.floorPlan.heightProperty())) {
            double ratio = propertyValue / groupHeight;
            if (groupHeight > propertyValue) {
                resize(getGroupMaxY(), ratio);
            }
        }*/
    }
}