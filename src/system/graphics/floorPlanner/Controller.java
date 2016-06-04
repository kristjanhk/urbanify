package system.graphics.floorPlanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import system.MainHandler;
import system.data.Event;
import system.graphics.common.AbstractController;
import system.graphics.common.FloorPlanPane;
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
    @FXML protected FloorPlanPane floorPlan;
    @FXML protected Text rowCountText;
    @FXML protected Text rowText;
    @FXML protected Text columnCountText;
    @FXML protected Text columnText;

    private Event event;
    private boolean titleValidated;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setLanguage();
        ArrayList<Integer> defaultSize = new ArrayList<>(2);
        defaultSize.add(5);
        defaultSize.add(5);
        this.floorPlan.init(this, defaultSize);
        this.loadFloorPlans();
        this.initSaveButton();
        MainHandler.setValidationFor(this.floorPlanTextField, "^(?=\\s*\\S).*$").addListener(
                (observable, oldValue, newValue) -> {
                    this.titleValidated = !newValue;
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

    public void setColumnCountText(int count) {
        this.columnCountText.setText(String.valueOf(count));
        this.validateButtons();
    }

    public void setRowCountText(int count) {
        this.rowCountText.setText(String.valueOf(count));
        this.validateButtons();
    }

    private void setFloorPlanTextFieldHighlight(boolean enable) {
        if (enable && !this.titleValidated) {
            this.floorPlanTextField.getStyleClass().add("floorPlanTextField_highlighted");
        } else {
            this.floorPlanTextField.getStyleClass().remove("floorPlanTextField_highlighted");
        }
    }

    private void loadFloorPlans() {
        this.getData().getFloorPlans().forEach(
                (name, floorplan) -> this.createFloorPlanItem(name));
    }

    private void createFloorPlanItem(String name) {
        MenuItem floorPlanItem = new MenuItem(name);
        floorPlanItem.setMnemonicParsing(false);
        floorPlanItem.setOnAction(event -> this.floorPlan.loadFloorPlan(name));
        this.floorPlans.getItems().add(floorPlanItem);
    }

    @FXML
    protected void addRowAction() {
        this.floorPlan.addRowAction();
    }

    @FXML
    protected void removeRowAction() {
        this.floorPlan.removeRowAction();
    }

    @FXML
    protected void addColumnAction() {
        this.floorPlan.addColumnAction();
    }

    @FXML
    protected void removeColumnAction() {
        this.floorPlan.removeColumnAction();
    }

    @FXML
    protected void handleFloorTypeSwitch(ActionEvent event) {
        this.floorTypes.setText(Word.valueOf(((MenuItem) event.getSource()).getId()).toString());
        this.floorPlan.setFloorPlanImage(this.floorTypes.getText());
    }

    @FXML
    protected void doCancel() {
        this.scene.getStageHandler().switchSceneTo(Scenetype.EVENTCREATOR, false);
    }

    @FXML
    protected void doSave() {
        if (this.floorPlan.getSize() > 0 && this.titleValidated) {
            this.floorPlan.save(this.floorPlanTextField.getText(), this.floorTypes.getText(), null);
            this.createFloorPlanItem(this.floorPlanTextField.getText());
        }
    }

    @FXML
    protected void doCreate() {
        this.floorPlan.save(null, this.floorTypes.getText(), this.event);
        this.event.setActive();
        this.scene.getStageHandler().switchSceneTo(Scenetype.EVENTMANAGER, this.event);
    }

    public void validateButtons() {
        if (this.floorPlan.getSize() > 0) {
            this.save.setDisable(false);
            this.create.setDisable(false);
        } else {
            this.save.setDisable(true);
            this.create.setDisable(true);
        }
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