package system.graphics.floorPlanner;


import system.graphics.AbstractController;
import system.graphics.Scenetype;

public class Controller extends AbstractController {


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
