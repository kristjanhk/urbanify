package system.graphics;

public enum Scenetype {
    MAINMENU("mainMenu", "test.fxml"),
    EVENTCREATOR("eventCreator", "eventCreator.fxml");

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
