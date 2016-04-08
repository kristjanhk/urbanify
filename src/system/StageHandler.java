package system;

import javafx.stage.Stage;

public class StageHandler {
    private Stage primaryStage;

    public StageHandler(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Superpilet 3000");
        this.primaryStage.setMinHeight(670);
        this.primaryStage.setMinWidth(1000);
        //this.primaryStage.setFullScreen(true);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
