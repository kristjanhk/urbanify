package system.graphics.floorPlanner;

import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import system.graphics.AbstractController;
import system.graphics.Scenetype;

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
    public Pane floorPlanPane;

    private int columnCount = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    // TODO: 20.04.2016 istmete asukohad vaja paika panna
    // TODO: 20.04.2016 asukohtade, suuruste muutused teha

    public void addNewRow() {
        Group group = new Group();
        for (int i = 0; i < this.columnCount; i++) {
            group.getChildren().add(new Seat(this.floorPlanPane, group));
        }
        getFloor().add(group);
        printout();
    }

    public void removeLastRow() {
        if (getFloor().size() > 0) {
            getFloor().remove(getFloor().size() - 1);
        }
        printout();
    }

    public void addNewColumn() {
        for (Node group : getFloor()) {
            ((Group) group).getChildren().add(new Seat(this.floorPlanPane)); //crashib???
        }
        this.columnCount++;
        printout();
    }

    public void removeLastColumn() {
        if (this.columnCount > 0) {
            for (Node group : getFloor()) {
                ((Group) group).getChildren().remove(this.columnCount - 1);
            }
            this.columnCount--;
        }
        printout();
    }

    private ObservableList<Node> getFloor() {
        return this.floorPlanPane.getChildren();
    }

    private void printout() {
        System.out.println(getFloor());
    }


    // TODO: 14.04.2016 to be changed

    public void doCancel() {
        // TODO: 14.04.2016 reset current scene?
        this.scene.getStageHandler().switchSceneTo(Scenetype.EVENTCREATOR);
    }

    public void doSave() {

    }

    public void doCreate() {

    }

    public void selectPlan(int id) {

    }

    public void selectType(int id) {

    }

    @Override
    public void prepareToDisplay() {

    }


}
