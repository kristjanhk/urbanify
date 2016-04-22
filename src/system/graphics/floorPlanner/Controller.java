package system.graphics.floorPlanner;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
        this.floorPlan.setAlignment(Pos.BOTTOM_CENTER);
        this.floorPlan.setMinSize(100, 100);
        this.floorPlan.getChildren().add(new Group());
        StackPane.setMargin(getFloorGroup(), new Insets(10));
        this.floorPlan.widthProperty().addListener((observable, oldValue, newValue) -> {
            double paneWidth = newValue.doubleValue() - 20;
            double groupWidth = this.getFloorGroup().getBoundsInParent().getWidth();
            double groupOrigWidth = this.getFloorGroup().getBoundsInLocal().getWidth();
            double groupHeight = this.getFloorGroup().getBoundsInParent().getHeight();
            double groupOrigHeight = this.getFloorGroup().getBoundsInLocal().getHeight();
            System.out.println(String.valueOf(paneWidth - groupWidth > 60) + ", " +
                    String.valueOf(groupWidth < groupOrigWidth) + ", " +
                    String.valueOf(groupHeight < groupOrigHeight) + ", " +
                    String.valueOf(groupHeight < this.floorPlan.getHeight()));
            double suhe = paneWidth / groupWidth;
                if (groupWidth > paneWidth) {
                    checkResize(getGroupMaxY(), true, suhe);
                } else if (paneWidth - groupWidth > 120 && //laiuses vähemalt 2x istet ruumi
                        groupWidth < groupOrigWidth && // ollakse laiuselt väiksem kui orig
                        groupHeight < groupOrigHeight && // ollakse pikkuselt väiksem kui orig
                        groupHeight < this.floorPlan.getHeight()) { //ollakse pikkuselt väiksem kui pane
                    checkResize(getGroupMaxY(), true, suhe);
                }
        });
        this.floorPlan.heightProperty().addListener((observable, oldValue, newValue) -> {
            double paneHeight = newValue.doubleValue() - 20;
            double groupHeight = this.getFloorGroup().getBoundsInParent().getHeight();
            double groupOrigHeight = this.getFloorGroup().getBoundsInLocal().getHeight();
            double groupWidth = this.getFloorGroup().getBoundsInParent().getWidth();
            double groupOrigWidth = this.getFloorGroup().getBoundsInLocal().getWidth();
            double suhe = paneHeight / groupHeight;
            if (groupHeight > paneHeight) {
                checkResize(getGroupMaxY(), true, suhe);
            } else if (paneHeight - groupHeight > 60 + 10 && //pikkuses vähemalt 1x iste + padding ruumi
                    groupHeight < groupOrigHeight && //ollakse pikkuselt väiksem kui orig
                    groupWidth < groupOrigWidth && // ollakse laiuselt väiksem kui orig
                    groupWidth < this.floorPlan.getWidth()) { // ollakse laiuselt väiksem kui pane
                checkResize(getGroupMaxY(), true, suhe);
            }
        });
    }

    private void resize(ReadOnlyDoubleProperty propertyType, Number value) {
        double propertyValue = value.doubleValue() - 20;
        double groupWidth = this.getFloorGroup().getBoundsInParent().getWidth();
        double groupOrigWidth = this.getFloorGroup().getBoundsInLocal().getWidth();
        double groupHeight = this.getFloorGroup().getBoundsInParent().getHeight();
        double groupOrigHeight = this.getFloorGroup().getBoundsInLocal().getHeight();
        if (propertyType.equals(this.floorPlan.widthProperty())) {
            double ratio = propertyValue / groupWidth;
            if (groupWidth > propertyValue) {
                checkResize(getGroupMaxY(), true, ratio);
            } else if (propertyValue - groupWidth > 60) {

            }
        } else if (propertyType.equals(this.floorPlan.heightProperty())){
            double ratio = propertyValue / groupHeight;
            if (groupHeight > propertyValue) {
                checkResize(getGroupMaxY(), true, ratio);
            }
        }
    }

    // TODO: 20.04.2016 asukohtade, suuruste muutused teha


    public void addRowAction() {
        double maxy = getGroupMaxY();
        this.addNewRow();
        this.checkResize(maxy, false, 0.95);
    }

    public void removeRowAction() {
        double maxy = getGroupMaxY();
        this.removeFirstRow();
        this.checkResize(maxy, false, 0.95);
    }

    public void addColumnAction() {
        double maxy = getGroupMaxY();
        this.addNewColumn();
        this.checkResize(maxy, false, 0.95);
    }

    public void removeColumnAction() {
        double maxy = getGroupMaxY();
        this.removeLastColumn();
        this.checkResize(maxy, false, 0.95);
    }

    private void addNewRow() {
        Group group = new Group();
        for (int i = 0; i < this.columnCount; i++) {
            group.getChildren().add(new Seat(getFloor().size(), i));
        }
        getFloor().add(group);
        setRowCountText();
        if (this.columnCount < 1) {
            this.addNewColumn();
        }
    }

    private void removeFirstRow() {
        if (getFloor().size() > 0) {
            if (getFloor().size() == 1) {
                this.columnCount = 0;
                setColumnCountText();
            }
            getFloor().remove(getFloor().size() - 1);
            setRowCountText();
        }
    }

    private void addNewColumn() {
        for (int i = getFloor().size() - 1; i >= 0; i--) {
            ((Group) getFloor().get(i)).getChildren().add(new Seat(i, this.columnCount));
        }
        this.columnCount++;
        setColumnCountText();
        if (getFloor().size() < 1) {
            this.addNewRow();
        }
    }

    private void removeLastColumn() {
        if (this.columnCount > 0) {
            this.columnCount--;
            if (this.columnCount == 0) {
                getFloor().clear();
                setRowCountText();
            } else {
                for (Node group : getFloor()) {
                    ((Group) group).getChildren().remove(this.columnCount);
                }
            }
            setColumnCountText();
        }
    }

    private void setColumnCountText() {
        this.columnCountText.setText(String.valueOf(this.columnCount));
    }

    private void setRowCountText() {
        this.rowCountText.setText(String.valueOf(getFloor().size()));
    }

    private ObservableList<Node> getFloor() {
        return getFloorGroup().getChildren();
    }

    private Group getFloorGroup() {
        return (Group) this.floorPlan.getChildren().get(0);
    }


    private void checkResize(double prevY, boolean forced, double scalefactor) {
        if (prevY != -1.0) {
            if (forced) {

                //System.out.println("orig: " + getGroupMaxY());

                getFloorGroup().setScaleX(getFloorGroup().getScaleX() * scalefactor);
                getFloorGroup().setScaleY(getFloorGroup().getScaleY() * scalefactor);

                //System.out.println("scale: " + getGroupMaxY());

                double newY = getGroupMaxY();

                this.maxY += prevY - newY;
                getFloorGroup().setTranslateY(this.maxY);

                //System.out.println("norm: " + getGroupMaxY());
            }
        }
    }

        /*// TODO: 21/04/2016 offsets
        int width = this.columnCount * 60;
        int height = getFloor().size() * 60;
        System.out.println(width);
        System.out.println(height);
        if (width > this.floorPlan.getWidth() - 50 || height > this.floorPlan.getHeight() - 50) {
            double ratio;
            double widthratio = (this.floorPlan.getWidth() - 50) / width;
            double heightratio = (this.floorPlan.getHeight() - 50) / height;
            System.out.println(widthratio);
            System.out.println(heightratio);
            if (widthratio > heightratio) {
                ratio = heightratio;
            } else {
                ratio = widthratio;
            }
            System.out.println(ratio);
            this.floorPlan.getChildren().get(0).setScaleX(ratio);
            this.floorPlan.getChildren().get(0).setScaleY(ratio);
            this.floorPlan.getChildren().get(0).resizeRelocate(10, 10, 0, 0);*/
    //}

    private double getGroupMaxY() {
        return getFloorGroup().getBoundsInParent().getMaxY();
    }

    // TODO: 14.04.2016 to be changed

    public void doCancel() {
        // TODO: 14.04.2016 reset current scene?
        this.scene.getStageHandler().switchSceneTo(Scenetype.EVENTCREATOR);
    }

    public void doSave() {
        // TODO: 21.04.2016 save
        this.checkResize(getGroupMaxY(), true, 0.95);
    }

    public void doCreate() {
        MainHandler.changeGlobalThemeTo(Csstype.toggleTheme());
    }

    public void selectPlan(int id) {

    }

    public void selectType(int id) {

    }

    @Override
    public void prepareToDisplay() {

    }


}
