package system.graphics.floorPlanner;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import system.MainHandler;
import system.graphics.AbstractController;
import system.graphics.Scenetype;
import system.graphics.common.Csstype;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller extends AbstractController implements Initializable {
    public Button removeRow;
    public Button addRow;
    public Button removeSeat;
    public Button addSeat;
    public Button cancel;
    public Button save;
    public Button create;
    public BorderPane borderPane;
    public StackPane floorPlan;
    public Text rowCountText;
    public Text columnCountText;

    private int columnCount = 0;
    private double maxY = 0.0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.floorPlan.getChildren().add(new Group());
        StackPane.setMargin(this.getFloorGroup(), new Insets(10));
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

    public void addRowAction() {
        double prevY = getGroupMaxY();
        this.addNewRow();
        this.checkPaneHeightResize(this.floorPlan.getHeight());
        this.correctY(prevY);
    }

    public void removeRowAction() {
        double prevY = getGroupMaxY();
        this.removeFirstRow();
        this.checkPaneHeightResize(this.floorPlan.getHeight());
        this.correctY(prevY);
    }

    public void addColumnAction() {
        this.addNewColumn();
        this.checkPaneWidthResize(this.floorPlan.getWidth());
    }

    public void removeColumnAction() {
        this.removeLastColumn();
        this.checkPaneWidthResize(this.floorPlan.getWidth());
    }

    private void addNewRow() {
        Group group = new Group();
        for (int i = 0; i < this.columnCount; i++) {
            group.getChildren().add(new Seat(getFloor().size(), i));
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
            ((Group) this.getFloor().get(i)).getChildren().add(new Seat(i, this.columnCount));
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

    private void resetY() {
        this.maxY = 0.0;
        this.getFloorGroup().setTranslateY(this.maxY);
    }

    // TODO: 14.04.2016 to be changed

    public void doCancel() {
        // TODO: 14.04.2016 reset current scene?
        this.scene.getStageHandler().switchSceneTo(Scenetype.EVENTCREATOR);
    }

    public void doSave() {
        // TODO: 21.04.2016 save
        this.resize(getGroupMaxY(), 0.95);
    }

    public void doCreate() {
        MainHandler.changeGlobalThemeTo(Csstype.toggleTheme());
    }

    public void selectPlan(int id) {

    }

    public void selectType(int id) {

    }

    @Override
    public void prepareToDisplay(Scenetype prevSceneType) {

    }


}
