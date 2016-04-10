package system;

import javafx.stage.Stage;
import system.graphics.CustomPrimaryStage;
import system.graphics.CustomStage;
import java.util.ArrayList;

public class StageHandler {
    private ArrayList<CustomStage> stages = new ArrayList<>();

    public StageHandler(Stage primaryStage) {
        CustomPrimaryStage customPrimaryStage = new CustomPrimaryStage(primaryStage, this);
        this.stages.add(customPrimaryStage);
        /*// TODO: 10.04.2016 test
        CustomStage customPrimaryStage = (CustomStage) Proxy.newProxyInstance(
                CustomStage.class.getClassLoader(),
                new Class[] {CustomStage.class},
                new Invocationtest());
        customPrimaryStage.manualInit(this);*/
        // TODO: 10.04.2016 load from settings or smth 
        customPrimaryStage.getPrimaryStage().setTitle("Superpilet 3000");
        customPrimaryStage.getPrimaryStage().setMinHeight(670);
        customPrimaryStage.getPrimaryStage().setMinWidth(1000);
        //this.primaryStage.setFullScreen(true);
        customPrimaryStage.getPrimaryStage().show();
    }

    public ArrayList<CustomStage> getStages() {
        return stages;
    }

    // TODO: 10.04.2016 primaryscene vaja eraldi kätte saada v ei? 

    public void createNewStage() {
        createNewStage(new CustomStage(this));
    }

    public void createNewStage(CustomStage stage) {
        this.stages.add(stage);
    }
}
