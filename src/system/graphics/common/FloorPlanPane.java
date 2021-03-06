package system.graphics.common;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import system.data.Event;
import system.data.Lang;
import system.data.Word;
import system.graphics.floorPlanner.Controller;
import system.graphics.floorPlanner.Imagetype;
import system.graphics.floorPlanner.Seat;

import java.util.ArrayList;
import java.util.HashMap;

public class FloorPlanPane extends VBox {
    public enum Property {DIMENSIONS, UNAVAILABLE, IMAGETYPE, DISABLED}

    private AbstractController parentController;
    private StackPane floorPlan;
    private StackPane imagePane;
    private Text imageText;
    private ImageView imageView;
    private int columnCount;
    private double maxY = 0.0;
    private boolean fixpos = false;

    public FloorPlanPane() {
        this.floorPlan = new StackPane();
        this.floorPlan.setAlignment(Pos.BOTTOM_CENTER);
        this.floorPlan.setMinSize(0.0, 0.0);
        this.floorPlan.setPrefSize(9999.0, 9999.0);
        this.floorPlan.getStyleClass().add("background");
        this.createImagePane();
        this.getChildren().addAll(this.floorPlan, this.imagePane);
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
        this.setAlignment(Pos.CENTER);
        this.setPrefSize(9999.0, 9999.0);
        HBox.setMargin(this, new Insets(0.0, 20.0, 0.0, 0.0));
    }

    public void init(AbstractController parentController, ArrayList<Integer> defaultSize) {
        this.parentController = parentController;
        this.createNewFloorPlan(defaultSize);
    }

    private void createImagePane() {
        this.imagePane = new StackPane();
        VBox.setMargin(this.imagePane, new Insets(0.0, 0.0, 20.0, 0.0));
        this.imageView = new ImageView();
        this.imageView.setFitWidth(445.0);
        this.imageView.setFitHeight(47.0);
        this.imageText = new Text();
        this.imageText.getStyleClass().add("text35");
        this.replaceImageText(Word.STAGE);
        this.imagePane.getChildren().addAll(this.imageView, this.imageText);
        this.setFloorPlanImage(Imagetype.STAGE);
    }

    private void replaceImageText(Word type) {
        this.imageText.setText(type.toString());
        this.imageText.setTranslateY(0.0);
        if (type == Word.STAGE) {
            this.imageText.setTranslateY(10.0);
        } else {
            this.imageText.setTranslateY(40.0);
        }
    }

    public AbstractController getParentController() {
        return this.parentController;
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

    public void setSeatStyle(Seat.Seattype seattype, ArrayList<Integer> coordinates) {
        for (Node group : this.getFloor()) {
            for (Node seat : ((Group) group).getChildren()) {
                Seat target = (Seat) seat;
                if (target.getCoordinates().equals(coordinates)) {
                    target.setSeattype(seattype);
                }
            }
        }
    }

    public boolean hasSeatsSelected() {
        for (Node group : this.getFloor()) {
            for (Node seat : ((Group) group).getChildren()) {
                Seat target = (Seat) seat;
                if (target.isOccupied()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void save(String name, String imageName, Event event) {
        Word imageConstant = Word.toEnum(imageName.toLowerCase(), Lang.getActiveLang());
        if (imageConstant != null) {
            HashMap<Property, Object> floorPlan = new HashMap<>(3);
            ArrayList<Integer> dimensions = new ArrayList<>(2);
            dimensions.add(this.getFloor().size());
            dimensions.add(this.columnCount);
            ArrayList<ArrayList<Integer>> unavailables = new ArrayList<>();
            ArrayList<ArrayList<Integer>> disabled = new ArrayList<>();
            for (Node group : this.getFloor()) {
                for (Node seat : ((Group) group).getChildren()) {
                    ArrayList<Integer> unavailableCoordinates = ((Seat) seat).isUnavailable();
                    ArrayList<Integer> disabledCoordinates = ((Seat) seat).hasBeenDisabled();
                    if (unavailableCoordinates != null) {
                        unavailables.add(unavailableCoordinates);
                    }
                    if (disabledCoordinates != null) {
                        disabled.add(disabledCoordinates);
                    }
                }
            }
            floorPlan.put(Property.DIMENSIONS, dimensions);
            floorPlan.put(Property.UNAVAILABLE, unavailables);
            floorPlan.put(Property.DISABLED, disabled);
            floorPlan.put(Property.IMAGETYPE, imageConstant.inLang(Lang.ENGLISH).toUpperCase());
            if (event != null) {
                event.setFloorPlan(floorPlan);
                event.setMaxSeats(0);
            } else {
                this.parentController.getData().saveFloorPlan(name, floorPlan);
            }
        }
    }

    public void setFloorPlanImage(String imageName) {
        Word imageConstant = Word.toEnum(imageName);
        this.replaceImageText(imageConstant);
        if (imageConstant != null) {
            this.setFloorPlanImage(Imagetype.valueOf(imageConstant.inLang(Lang.ENGLISH).toUpperCase()));
        }
    }

    private void setFloorPlanImage(Imagetype imagetype) {
        this.replaceImageText(Word.valueOf(imagetype.name()));
        this.imageView.setImage(imagetype.toImage());
    }

    public static int getSeatsLeft(Event event) {
        ArrayList<Integer> size = getSavedFloorPlanDimensions(event);
        ArrayList<ArrayList<Integer>> unavailables = getSavedFloorPlanUnavailables(event);
        ArrayList<ArrayList<Integer>> disabled = getSavedFloorPlanDisabled(event);
        return size.get(0) * size.get(1) - unavailables.size() - disabled.size();
    }

    public static ArrayList<Integer> getSavedFloorPlanDimensions(Object from) {
        return getSavedFloorPlanDimensions(null, from);
    }

    public static ArrayList<Integer> getSavedFloorPlanDimensions(String name, Object from) {
        Object dimensions;
        if (from instanceof Event) {
            dimensions = ((Event) from).getFloorPlan().get(Property.DIMENSIONS);
        } else {
            dimensions = ((AbstractController) from).getData().
                    getFloorPlan(name).get(FloorPlanPane.Property.DIMENSIONS);
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

    public static ArrayList<ArrayList<Integer>> getSavedFloorPlanUnavailables(Object from) {
        return getSavedFloorPlanCoordinates(null, from, Property.UNAVAILABLE);
    }

    public static ArrayList<ArrayList<Integer>> getSavedFloorPlanDisabled(Object from) {
        return getSavedFloorPlanCoordinates(null, from, Property.DISABLED);
    }

    private static ArrayList<ArrayList<Integer>> getSavedFloorPlanCoordinates(String name, Object from, Property type) {
        Object seats;
        if (from instanceof Event) {
            seats = ((Event) from).getFloorPlan().get(type);
        } else {
            seats = ((AbstractController) from).getData().getFloorPlan(name).get(type);
        }
        ArrayList<ArrayList<Integer>> selected = new ArrayList<>();
        for (Object seatData : (ArrayList) seats) {
            ArrayList<Integer> data = new ArrayList<>(2);
            for (Object coordinate : (ArrayList) seatData) {
                if (coordinate instanceof Number) {
                    data.add(((Number) coordinate).intValue());
                }
            }
            selected.add(data);
        }
        return selected;
    }

    public static Imagetype getSavedFloorPlanImageType(Object from) {
        return getSavedFloorPlanImageType(null, from);
    }

    public static Imagetype getSavedFloorPlanImageType(String name, Object from) {
        Object imagetype;
        if (from instanceof Event) {
            imagetype = ((Event) from).getFloorPlan().get(Property.IMAGETYPE);
        } else {
            imagetype = ((AbstractController) from).getData().getFloorPlan(name).get(FloorPlanPane.Property.IMAGETYPE);
        }
        return Imagetype.valueOf((String) imagetype);
    }

    public String getSavedFloorPlanImageTypeString(Event event) {
        Word imageType = Word.toEnum(
                ((String) event.getFloorPlan().get(Property.IMAGETYPE)).toLowerCase(), Lang.ENGLISH);
        if (imageType != null) {
            return imageType.toString();
        }
        return null;
    }

    public void loadFloorPlan(String name, Object from) {
        this.createNewFloorPlan(getSavedFloorPlanDimensions(name, from));
        this.setFloorPlanImage(getSavedFloorPlanImageType(name, from));
        ArrayList<ArrayList<Integer>> unavailables = getSavedFloorPlanCoordinates(name, from, Property.UNAVAILABLE);
        ArrayList<ArrayList<Integer>> disabled = getSavedFloorPlanCoordinates(name, from, Property.DISABLED);
        for (Node group : this.getFloor()) {
            ((Group) group).getChildren().stream().filter(
                    seat -> unavailables.contains(((Seat) seat).getCoordinates())).forEach(
                    unavailableSeat -> {
                        ((Seat) unavailableSeat).setSeattype(Seat.Seattype.UNAVAILABLE);
                        if (this.parentController instanceof system.graphics.pointOfSale.Controller) {
                            ((Seat) unavailableSeat).lock();
                        }
                    });
            ((Group) group).getChildren().stream().filter(
                    seat -> disabled.contains(((Seat) seat).getCoordinates())).forEach(
                    disabledSeat -> {
                        ((Seat) disabledSeat).setSeattype(Seat.Seattype.DISABLED);
                        ((Seat) disabledSeat).disable();
                        ((Seat) disabledSeat).lock();
                    });
        }
        if (from instanceof Controller) {
            this.fixpos = true;
            this.floorPlan.setVisible(false);
        }
    }

    public void loadFloorPlan(Event event) {
        this.loadFloorPlan(null, event);
    }

    private void createNewFloorPlan(ArrayList<Integer> dimensions) {
        this.resetFloor();
        for (int row = 0; row < dimensions.get(0); row++) {
            this.addNewRow();
        }
        for (int column = 0; column < dimensions.get(1) - 1; column++) {
            this.addNewColumn();
        }
    }

    private ObservableList<Node> getFloor() {
        return this.getFloorGroup().getChildren();
    }

    private Group getFloorGroup() {
        return (Group) this.floorPlan.getChildren().get(0);
    }

    private void resetFloor() {
        this.floorPlan.getChildren().clear();
        this.floorPlan.getChildren().add(new Group());
        StackPane.setMargin(this.getFloorGroup(), new Insets(10));
        this.columnCount = 0;
        this.resetY();
        this.setRowCountText();
        this.setColumnCountText();
        this.validateButtons();
    }


    public void addRowAction() {
        if (this.getFloor().size() != 0) {
            double prevY = getGroupMaxY();
            this.addNewRow();
            this.checkPaneHeightResize(this.floorPlan.getHeight());
            this.correctY(prevY);
        } else {
            this.addNewRow();
        }
    }

    public void removeRowAction() {
        if (this.getFloor().size() > 1) {
            double prevY = this.getGroupMaxY();
            this.removeFirstRow();
            this.checkPaneHeightResize(this.floorPlan.getHeight());
            this.correctY(prevY);
        } else {
            this.resetFloor();
        }
    }

    public void addColumnAction() {
        if (this.columnCount != 0) {
            this.addNewColumn();
            this.checkPaneWidthResize(this.floorPlan.getWidth());
        } else {
            this.addNewColumn();
        }
    }

    public void removeColumnAction() {
        this.removeLastColumn();
        this.checkPaneWidthResize(this.floorPlan.getWidth());
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
            this.getFloor().remove(this.getFloor().size() - 1);
            this.setRowCountText();
            if (this.getFloor().size() == 0) {
                this.columnCount = 0;
                this.setColumnCountText();
                this.resetY();
            }
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

    /**
     * Saaliplaani laiuse kontroll
     * <p>
     * Vähendame suurust kui istmetegrupi laius on suurem kui saaliplaani laius
     * Suurendame suurust kui istmetegrupi laius on väiksem kui istmegrupi scalemata laius,
     * istmetegrupi kõrgus on väiksem kui istmegrupi scalemata kõrgus,
     * istmetegrupi kõrgus on väiksem kui saaliplaani kõrgus - 30
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
                groupHeight < this.floorPlan.getHeight() - 30) {
            this.resizeFloorPlan(this.getGroupMaxY(), ratio);
            this.checkPaneHeightResize(this.floorPlan.getHeight());
        }
    }

    /**
     * Saaliplaani kõrguse kontroll
     * <p>
     * Vähendame suurust kui istmetegrupi kõrgus on suurem kui saaliplaani kõrgus
     * Suurendame suurust kui istmetegrupi kõrgus on väiksem kui istmegrupi scalemata kõrgus,
     * istmetegrupi laius on väiksem kui istmegrupi scalemata laius,
     * istmetegrupi laius on väiksem kui saaliplaani laius - 30
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
                groupWidth < this.floorPlan.getWidth() - 30) {
            this.resizeFloorPlan(this.getGroupMaxY(), ratio);
            this.checkPaneWidthResize(this.floorPlan.getWidth());
        }
    }

    private double getGroupMaxY() {
        return this.getFloorGroup().getBoundsInParent().getMaxY();
    }

    private void resizeFloorPlan(double prevY, double scalefactor) {
        this.getFloorGroup().setScaleX(this.getFloorGroup().getScaleX() * scalefactor);
        this.getFloorGroup().setScaleY(this.getFloorGroup().getScaleY() * scalefactor);
        this.correctY(prevY);
    }

    private void correctY(double prevY) {
        if (prevY != -1.0) {
            double newY = this.getGroupMaxY();
            this.maxY += prevY - newY;
            this.getFloorGroup().setTranslateY(this.maxY);
        }
    }

    private void resetY() {
        this.maxY = 0.0;
        this.getFloorGroup().setTranslateY(this.maxY);
    }

    public void fixPosition() {
        if (this.fixpos) {
            this.checkPaneHeightResize(this.floorPlan.getHeight());
            this.floorPlan.setVisible(true);
            this.fixpos = false;
        }
    }
}