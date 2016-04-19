package system.graphics.floorPlanner;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import system.graphics.AbstractController;
import system.graphics.Scenetype;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller extends AbstractController implements Initializable{
    public Button removeRow;
    public Button addRow;
    public Button removeSeat;
    public Button addSeat;
    public Button cancel;
    public Button save;
    public Button create;
    public Pane floorPlanPane;

    private int columnCount = 0;
    //private ArrayList<ArrayList<Seat>> floor = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> floor = new ArrayList<>();
    // rows<columns<seat>>
    // TODO: 20.04.2016 hashmap?
    // TODO: 20.04.2016 custom classes?

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }





    public void addNewRow() {
        this.floor.add(new ArrayList<>());
        for (int i = 0; i < this.columnCount; i++) {
            this.floor.get(this.floor.size() - 1).add(8);
        }
        printout();
    }

    public void removeLastRow() {
        if (this.floor.size() > 0) {
            this.floor.remove(this.floor.size() - 1);
        }
        printout();
    }

    public void addNewColumn() {
        for (ArrayList<Integer> row : this.floor) {
            row.add(8);
        }
        this.columnCount++;
        printout();
    }

    public void removeLastColumn() {
        if (this.columnCount > 0) {
            for (ArrayList<Integer> row : this.floor) {
                row.remove(this.columnCount - 1);
            }
            this.columnCount--;
        }
        printout();
    }


    private void printout() {
        System.out.println(this.floor);
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
