/*
package system.graphics.floorPlanner;

import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import system.MainHandler;
import system.graphics.AbstractController;
import system.graphics.Scenetype;
import system.graphics.common.Csstype;

import java.net.URL;
import java.util.ResourceBundle;

public class backup extends AbstractController implements Initializable {
    public Button removeRow;
    public Button addRow;
    public Button removeSeat;
    public Button addSeat;
    public Button cancel;
    public Button save;
    public Button create;
    public BorderPane borderPane;
    public AnchorPane floorPlanPane;
    public Text rowCountText;
    public Text columnCountText;

    private int columnCount = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.floorPlanPane.getChildren().add(new Group());
        AnchorPane.setBottomAnchor(this.floorPlanPane.getChildren().get(0), 10.0);
        AnchorPane.setLeftAnchor(this.floorPlanPane.getChildren().get(0), 10.0);
    }

    // TODO: 20.04.2016 asukohtade, suuruste muutused teha

    public void addNewRow() {
        Group group = new Group();
        for (int i = 0; i < this.columnCount; i++) {
            group.getChildren().add(new Seat(getFloor().size(), i));
        }
        getFloor().add(group);
        setRowCountText();
        if (this.columnCount < 1) {
            this.addNewColumn();
        }
        resize();
        printout();
    }

    public void removeFirstRow() {
        if (getFloor().size() > 0) {
            if (getFloor().size() == 1) {
                this.columnCount = 0;
                setColumnCountText();
            }
            getFloor().remove(getFloor().size() - 1);
            setRowCountText();
        }
        resize();
        printout();
    }

    public void addNewColumn() {
        for (int i = getFloor().size() - 1; i >= 0; i--) {
            ((Group) getFloor().get(i)).getChildren().add(new Seat(i, this.columnCount));
        }
        this.columnCount++;
        setColumnCountText();
        if (getFloor().size() < 1) {
            this.addNewRow();
        }
        resize();
        printout();
    }

    public void removeLastColumn() {
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
        resize();
        printout();
    }

    private void setColumnCountText() {
        this.columnCountText.setText(String.valueOf(this.columnCount));
    }

    private void setRowCountText() {
        this.rowCountText.setText(String.valueOf(getFloor().size()));
    }

    private ObservableList<Node> getFloor() {
        return ((Group) this.floorPlanPane.getChildren().get(0)).getChildren();
    }

    private void resize() {
        // TODO: 21/04/2016 offsets
        int width = this.columnCount * 60;
        int height = getFloor().size() * 60;
        System.out.println(width);
        System.out.println(height);
        if (width > this.floorPlanPane.getWidth() - 50 || height > this.floorPlanPane.getHeight() - 50) {
            double ratio;
            double widthratio = (this.floorPlanPane.getWidth() - 50) / width;
            double heightratio = (this.floorPlanPane.getHeight() - 50) / height;
            System.out.println(widthratio);
            System.out.println(heightratio);
            if (widthratio > heightratio) {
                ratio = heightratio;
            } else {
                ratio = widthratio;
            }
            System.out.println(ratio);
            this.floorPlanPane.getChildren().get(0).setScaleX(ratio);
            this.floorPlanPane.getChildren().get(0).setScaleY(ratio);
            this.floorPlanPane.getChildren().get(0).resizeRelocate(10, 10, 0, 0);
        }
    }

    private void printout() {
        StringBuilder out = new StringBuilder();
        for (Node group : getFloor()) {
            out.append("[");
            ((Group) group).getChildren().forEach(out::append);
            out.append("], ");
        }
        System.out.println(out);
    }


    // TODO: 14.04.2016 to be changed

    public void doCancel() {
        // TODO: 14.04.2016 reset current scene?
        this.scene.getStageHandler().switchSceneTo(Scenetype.EVENTMANAGER);
    }

    public void doSave() {
        // TODO: 21.04.2016 save
        System.out.println(this.scene.getStylesheets());
    }

    public void doCreate() {
        System.out.println(this.scene.getScenetype());
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
*/
