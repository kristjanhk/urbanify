package system.graphics.common;

/**
 * Stseenitüübid
 * Hoiab endas stseenitüüpe kui konstante
 * Iga stseenitüüp on Scenetype klassi isend
 * Stseenitüüp määrab ära antud stseeni kausta asukoha ja FXML faili nime
 */
public enum Scenetype {
    //foldername, fxml name
    MAINMENU("mainMenu", "mainMenu.fxml"),
    EVENTCREATOR("eventCreator", "eventCreator.fxml"),
    EVENTMANAGER("eventManager", "eventManager.fxml"),
    FLOORPLANNER("floorPlanner", "floorPlanner.fxml"),
    POINTOFSALE("pointOfSale", "pointOfSale.fxml"),
    ARCHIVE("eventManager", "archive.fxml"),
    SETTINGS("settings", "settings.fxml"),
    REPORT("report", "report.fxml"),
    TICKETINFO("ticketInfo", "ticketInfo.fxml"),
    CLIENTLOGO("ticketInfo/clientLogo", "clientLogo.fxml");

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
