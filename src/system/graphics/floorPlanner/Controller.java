package system.graphics.floorPlanner;

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
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
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
    private double dx = 0.0;
    private double dy = 0.0;
    private double scalefactor = 0.95;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.floorPlan.setAlignment(Pos.BOTTOM_CENTER);
        this.floorPlan.setMinSize(100, 100);
        this.floorPlan.getChildren().add(new Group());
        StackPane.setMargin(getFloorGroup(), new Insets(10));
    }

    // TODO: 20.04.2016 asukohtade, suuruste muutused teha

    public void addNewRow() {
        double maxy = getFloorGroup().getBoundsInParent().getMaxY();
        Group group = new Group();
        for (int i = 0; i < this.columnCount; i++) {
            group.getChildren().add(new Seat(getFloor().size(), i));
        }
        getFloor().add(group);
        setRowCountText();
        if (this.columnCount < 1) {
            this.addNewColumn();
        }
        if (maxy != -1) {
            double newmaxy = getFloorGroup().getBoundsInParent().getMaxY();
            this.dy += maxy - newmaxy;
            getFloorGroup().setTranslateY(dy);
        }
        //resize();
        printout();
    }

    public void removeFirstRow() {
        double maxy = getFloorGroup().getBoundsInParent().getMaxY();
        if (getFloor().size() > 0) {
            if (getFloor().size() == 1) {
                this.columnCount = 0;
                setColumnCountText();
            }
            getFloor().remove(getFloor().size() - 1);
            setRowCountText();
        }
        if (maxy != -1) {
            double newmaxy = getFloorGroup().getBoundsInParent().getMaxY();
            this.dy += maxy - newmaxy;
            getFloorGroup().setTranslateY(dy);
        }
        //resize();
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
        //resize();
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
        //resize();
        printout();
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

    private void resize() {
        System.out.println(getFloorGroup().getBoundsInLocal());
        System.out.println(getFloorGroup().getBoundsInParent());

        //double maxx = getFloorGroup().getBoundsInParent().getMaxX();
        double maxy = getFloorGroup().getBoundsInParent().getMaxY();

        getFloorGroup().setScaleX(getFloorGroup().getScaleX() * this.scalefactor);
        getFloorGroup().setScaleY(getFloorGroup().getScaleY() * this.scalefactor);

        System.out.println(getFloorGroup().getBoundsInParent());

        //double newmaxx = getFloorGroup().getBoundsInParent().getMaxX();
        double newmaxy = getFloorGroup().getBoundsInParent().getMaxY();

        //this.dx += maxx - newmaxx;
        this.dy += maxy - newmaxy;

        //getFloorGroup().setTranslateX(dx); // FIXME: 22.04.2016 x pidi pole vaja liikuda??
        getFloorGroup().setTranslateY(dy);

        System.out.println(getFloorGroup().getBoundsInParent());
        System.out.println();

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
    }

    private void printout() {
        /*StringBuilder out = new StringBuilder();
        for (Node group : getFloor()) {
            out.append("[");
            ((Group) group).getChildren().forEach(out::append);
            out.append("], ");
        }
        System.out.println(out);*/
       // System.out.println(getFloorGroup().getBoundsInLocal());
    }


    // TODO: 14.04.2016 to be changed

    public void doCancel() {
        // TODO: 14.04.2016 reset current scene?
        this.scene.getStageHandler().switchSceneTo(Scenetype.EVENTCREATOR);
    }

    public void doSave() {
        // TODO: 21.04.2016 save
        resize();
        /*Group seats = (Group) this.floorPlan.getChildren().get(0);
        System.out.println(seats.getBoundsInParent());

        //seats.getTransforms().clear();
        System.out.println(seats.getTranslateX());
        System.out.println(seats.getTranslateY());

        double seatsx = seats.getBoundsInParent().getMinX();
        double seatsy = seats.getBoundsInParent().getMinY();
        double seatsw = seats.getBoundsInParent().getWidth();
        double seatsh = seats.getBoundsInParent().getHeight();

        double middlex = (seatsx + seatsw) / 2;
        double middley = (seatsy + seatsh) / 2;

        seats.getTransforms().add(new Scale(0.95, 0.95, middlex, middley));

        double seatnewx = seats.getBoundsInParent().getMinX();
        double seatnewy = seats.getBoundsInParent().getMinY();

        seats.getTransforms().add(new Translate(seatsx - seatnewx, seatsy - seatnewy));*/
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
