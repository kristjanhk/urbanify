package system.graphics;

import javafx.stage.Stage;
import system.StageHandler;

public class CustomPrimaryStage extends CustomStage {
    private Stage primaryStage;

    // TODO: 10.04.2016 dynamic variables or smth
    //http://stackoverflow.com/questions/11984165/java-architecture-issue-for-extending-instances-need-for-a-pattern-recommendati

    public CustomPrimaryStage(Stage primaryStage, StageHandler stageHandler) {
        super();
        this.primaryStage = primaryStage;
        init(stageHandler);
    }

    // TODO: 10.04.2016 pass on all methods

    public Stage getPrimaryStage() {
        return this.primaryStage;
    }
}
