package system;

import javafx.stage.Stage;

public class StageHandler {
    private Stage primaryStage;

    public StageHandler(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Superpilet 3000");
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}