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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import system.MainHandler;
import system.data.Event;
import system.graphics.common.AbstractController;
import system.graphics.common.Scenetype;
import system.data.Word;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Saaliplaani looja controller
 */
public class Controller extends AbstractController {
    @FXML protected TextField floorPlanTextField;
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
    private boolean titleValidated;
    private int columnCount;
    private double maxY = 0.0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<Integer> defaultSize = new ArrayList<>(2);
        defaultSize.add(5);
        defaultSize.add(5);
        this.createNewFloorPlan(defaultSize, true);
        this.setLanguage();
        this.setFloorPlanImage();
        this.loadFloorPlans();
        this.initSaveButton();
        MainHandler.setValidationFor(this.floorPlanTextField, "^(?=\\s*\\S).*$").addListener(
                (observable, oldValue, newValue) -> {
                    this.titleValidated = !newValue;
                });
        this.floorPlan.widthProperty().addListener((observable, oldValue, newValue) -> {
            this.checkPaneWidthResize(newValue);
        });
        this.floorPlan.heightProperty().addListener((observable, oldValue, newValue) -> {
            this.checkPaneHeightResize(newValue);
        });
    }

    private void initSaveButton() {
        this.save.setOnMouseEntered(event -> this.setFloorPlanTextFieldHighlight(true));
        this.save.setOnMouseExited(event -> this.setFloorPlanTextFieldHighlight(false));
        // FIXME: 3.06.2016 
        this.save.setOnMouseReleased(event -> this.save.getStyleClass().remove("save_button"));
        this.save.setOnMousePressed(event -> {
            if (!this.titleValidated) {
                this.save.getStyleClass().add("save_button");
            }
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
        if (this.getFloor().size() != 0) {
            double prevY = getGroupMaxY();
            this.addNewRow();
            this.checkPaneHeightResize(this.floorPlan.getHeight());
            this.correctY(prevY);
        } else {
            this.addNewRow();
        }
    }

    @FXML
    protected void removeRowAction() {
        if (this.getFloor().size() > 1) {
            double prevY = getGroupMaxY();
            this.removeFirstRow();
            this.checkPaneHeightResize(this.floorPlan.getHeight());
            this.correctY(prevY);
        } else {
            this.resetFloor();
        }
    }

    @FXML
    protected void addColumnAction() {
        if (this.columnCount != 0) {
            this.addNewColumn();
            this.checkPaneWidthResize(this.floorPlan.getWidth());
        } else {
            this.addNewColumn();
        }
    }

    @FXML
    protected void removeColumnAction() {
        this.removeLastColumn();
        this.checkPaneWidthResize(this.floorPlan.getWidth());
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
        this.validateButtons();
    }

    private void setRowCountText() {
        this.rowCountText.setText(String.valueOf(getFloor().size()));
        this.validateButtons();
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
        this.maxY = 0.0;
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

    private void setFloorPlanTextFieldHighlight(boolean enable) {
        if (enable && !this.titleValidated) {
            this.floorPlanTextField.getStyleClass().add("floorPlanTextField_highlighted");
        } else {
            this.floorPlanTextField.getStyleClass().remove("floorPlanTextField_highlighted");
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
        this.getData().getFloorPlans().forEach((name, floorplan) -> this.createFloorPlanItem(name));
    }

    private void createFloorPlanItem(String name) {
        MenuItem floorPlanItem = new MenuItem(name);
        floorPlanItem.setMnemonicParsing(false);
        floorPlanItem.setOnAction(event -> this.loadFloorPlan(name));
        this.floorPlans.getItems().add(floorPlanItem);
    }

    private void loadFloorPlan(String name) {
        this.createNewFloorPlan(this.getData().getFloorPlanDimensions(name), false);
        // TODO: 1.06.2016  set seat types
        ArrayList<ArrayList<Integer>>  unavailables = this.getData().getFloorPlanUnavailables(name);
        for (Node group : this.getFloor()) {
            ((Group) group).getChildren().stream().filter(
                    seat -> unavailables.contains(((Seat) seat).getCoordinates())).forEach(
                    seat -> ((Seat) seat).setSeattype(Seat.Seattype.UNAVAILABLE));
        }
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
        if (this.getFloor().size() * this.columnCount > 0 && this.titleValidated) {
            ArrayList<ArrayList<Integer>> unavailables = new ArrayList<>();
            for (Node group : this.getFloor()) {
                for (Node seat : ((Group) group).getChildren()) {
                    ArrayList<Integer> seatCoordinates = ((Seat) seat).isUnavailable();
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
        // TODO: 3.06.2016 pass floorplan to event
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
        this.floorPlanTextField.setPromptText(Word.NEWFLOORPLAN.toString());
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