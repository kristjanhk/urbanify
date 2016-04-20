package system.graphics.floorPlanner;

import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
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
    public Text rowCountText;
    public Text columnCountText;

    private int columnCount = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.addNewRow();
        this.addNewColumn();
    }

    // TODO: 20.04.2016 istmete asukohad vaja paika panna
    // TODO: 20.04.2016 asukohtade, suuruste muutused teha

    public void addNewRow() {
        Group group = new Group();
        for (int i = 0; i < this.columnCount; i++) {
            group.getChildren().add(new Seat(getFloor().size(), i));
        }
        getFloor().add(group);
        this.rowCountText.setText(String.valueOf(getFloor().size()));
        printout();
    }

    public void removeLastRow() {
        if (getFloor().size() > 0) {
            getFloor().remove(getFloor().size() - 1);
        }
        this.rowCountText.setText(String.valueOf(getFloor().size()));
        printout();
    }

    public void addNewColumn() {
        for (int i = 0; i < getFloor().size(); i++) {
            ((Group) getFloor().get(i)).getChildren().add(new Seat(i, this.columnCount));
        }
        this.columnCount++;
        this.columnCountText.setText(String.valueOf(this.columnCount));
        printout();
    }

    public void removeLastColumn() {
        if (this.columnCount > 0) {
            for (Node group : getFloor()) {
                ((Group) group).getChildren().remove(this.columnCount - 1);
            }
            this.columnCount--;
        }
        this.columnCountText.setText(String.valueOf(this.columnCount));
        printout();
    }

    private ObservableList<Node> getFloor() {
        return this.floorPlanPane.getChildren();
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
