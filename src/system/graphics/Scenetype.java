package system.graphics;

public enum Scenetype {
    //foldername, fxml name
    MAINMENU("floorPlanner", "floorPlanner.fxml"),
    EVENTCREATOR("eventCreator", "eventCreator.fxml"),
    EVENTMANAGER("eventManager", "eventManager.fxml"),
    FLOORPLANNER("floorPlanner", "floorPlanner.fxml"),
    POINTOFSALE("pointOfSale", "pointOfSale.fxml"),
    ARCHIVE("archive", "archive.fxml");

    private String packageString;
    private String sceneString;

    Scenetype(String packageString, String sceneString) {
        this.packageString = packageString;
        this.sceneString = sceneString;
    }

    public String toSceneString() {
        return this.sceneString;
    }

    public String toPackageString() {
        return this.packageString;
    }
}
