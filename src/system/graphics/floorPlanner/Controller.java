package system.graphics.floorPlanner;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import system.data.Event;
import system.graphics.common.AbstractController;
import system.graphics.common.Scenetype;
import system.data.Word;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;

/**
 * Saaliplaani looja controller
 */
public class Controller extends AbstractController {
    @FXML protected Text floorPlanText;
    @FXML protected ImageView floorPlanImage;
    @FXML protected MenuButton floorPlans;
    @FXML protected MenuButton floorTypes;
    @FXML protected Button removeRow;
    @FXML protected Button addRow;
    @FXML protected Button removeSeat;
    @FXML protected Button addSeat;
    @FXML protected Button cancel;
    @FXML protected Button save;
    @FXML protected Button create;
    @FXML protected BorderPane borderPane;
    @FXML protected StackPane floorPlan;
    @FXML protected Text rowCountText;
    @FXML protected Text rowText;
    @FXML protected Text columnCountText;
    @FXML protected Text columnText;

    private Event event;

    private int columnCount;
    private double maxY = 0.0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setLanguage();
        this.resetFloor();
        this.setFloorPlanImage();
        this.loadFloorPlans();
        this.floorPlan.widthProperty().addListener((observable, oldValue, newValue) -> {
            this.checkPaneWidthResize(newValue);
        });
        this.floorPlan.heightProperty().addListener((observable, oldValue, newValue) -> {
            this.checkPaneHeightResize(newValue);
        });
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
            this.resize(this.getGroupMaxY(), ratio);
        } else if (groupWidth < groupOrigWidth &&
                groupHeight < groupOrigHeight &&
                groupHeight < this.floorPlan.getHeight() - 30) {
            this.resize(this.getGroupMaxY(), ratio);
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
            this.resize(this.getGroupMaxY(), ratio);
        } else if (groupHeight < groupOrigHeight &&
                groupWidth < groupOrigWidth &&
                groupWidth < this.floorPlan.getWidth() - 30) {
            this.resize(this.getGroupMaxY(), ratio);
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

    @FXML
    protected void addRowAction() {
        double prevY = getGroupMaxY();
        this.addNewRow();
        this.checkPaneHeightResize(this.floorPlan.getHeight());
        this.correctY(prevY);
        this.validateButtons();
    }

    @FXML
    protected void removeRowAction() {
        double prevY = getGroupMaxY();
        this.removeFirstRow();
        this.checkPaneHeightResize(this.floorPlan.getHeight());
        this.correctY(prevY);
        this.validateButtons();
    }

    @FXML
    protected void addColumnAction() {
        this.addNewColumn();
        this.checkPaneWidthResize(this.floorPlan.getWidth());
        this.validateButtons();
    }

    @FXML
    protected void removeColumnAction() {
        this.removeLastColumn();
        this.checkPaneWidthResize(this.floorPlan.getWidth());
        this.validateButtons();
    }

    private void addNewRow() {
        Group group = new Group();
        for (int i = 0; i < this.columnCount; i++) {
            group.getChildren().add(new Seat(this, getFloor().size(), i));
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

    private void setColumnCountText() {
        this.columnCountText.setText(String.valueOf(this.columnCount));
    }

    private void setRowCountText() {
        this.rowCountText.setText(String.valueOf(getFloor().size()));
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

    private void resize(double prevY, double scalefactor) {
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
        this.setRowCountText();
        this.setColumnCountText();
        this.validateButtons();
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

    @FXML
    protected void handleFloorTypeSwitch(ActionEvent event) {
        this.floorTypes.setText(Word.valueOf(((MenuItem) event.getSource()).getId()).toString());
        this.setFloorPlanImage();
    }

    private void setFloorPlanImage() {
        String floortype = this.floorTypes.getText();
        if (floortype.equals(this.floorTypes.getItems().get(0).getText())) {
            this.floorPlanImage.setImage(Imagetype.STAGE.toImage());
        } else {
            this.floorPlanImage.setImage(Imagetype.SCREEN.toImage());
        }
    }

    private void loadFloorPlans() {
        this.getData().getFloorPlans().forEach((name, floorplan) -> {
            this.createFloorPlanItem(name);
        });
    }

    private void createFloorPlanItem(String name) {
        MenuItem floorPlanItem = new MenuItem(name);
        floorPlanItem.setMnemonicParsing(false);
        floorPlanItem.setOnAction(event -> this.loadFloorPlan(name));
        this.floorPlans.getItems().add(floorPlanItem);
    }

    private void loadFloorPlan(String name) {
        this.resetFloor();
        Integer[] dimensions = this.getData().getFloorPlanDimensions(name);

        /*for (int i = 0; i < floorPlan.size(); i++) {
            this.addRowAction();
        }
        for (int i = 0; i < floorPlan.get(0).size() - 1; i++) {
            this.addColumnAction();
        }*/

        // TODO: 1.06.2016  set seat types
    }

    private void validateButtons() {
        if (this.getFloor().size() * this.columnCount > 0) {
            this.save.setDisable(false);
            this.create.setDisable(false);
        } else {
            this.save.setDisable(true);
            this.create.setDisable(true);
        }
    }

    public void doCancel() {
        this.scene.getStageHandler().switchSceneTo(Scenetype.EVENTCREATOR, false);
    }

    public void doSave() {
        if (this.getFloor().size() * this.columnCount > 0) {
            HashSet<Integer[]> unavailables = new HashSet<>();
            for (Node group : this.getFloor()) {
                for (Node seat : ((Group) group).getChildren()) {
                    Integer[] seatCoordinates = ((Seat) seat).isUnavailable();
                    if (seatCoordinates != null) {
                        unavailables.add(seatCoordinates);
                    }
                }
            }
            this.getData().saveFloorPlan("floorplan", this.getFloor().size(), this.columnCount, unavailables);
            this.createFloorPlanItem("floorplan");
        }
    }

    public void doCreate() {
        this.scene.getStageHandler().switchSceneTo(Scenetype.EVENTMANAGER, this.event);
    }

    @Override
    public <T> void prepareToDisplay(T object) {
        if (object instanceof Event) {
            this.event = ((Event) object);
        }
    }

    @Override
    public void setLanguage() {
        this.floorPlanText.setText(Word.NEWFLOORPLAN.toString());
        this.floorPlans.setText(Word.FLOORPLAN.toString());
        this.floorTypes.setText(Word.STAGE.toString());
        this.floorTypes.getItems().get(0).setText(Word.STAGE.toString());
        this.floorTypes.getItems().get(1).setText(Word.SCREEN.toString());
        this.rowText.setText(Word.ROWS.toString());
        this.columnText.setText(Word.SEATSINROW.toString());
        this.cancel.setText(Word.CANCEL.toString());
        this.save.setText(Word.SAVE.toString());
        this.create.setText(Word.CREATE.toString());
    }
}
