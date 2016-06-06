package system.graphics.common;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

public enum ClientScreentype {
    PRIMARY(0),
    SECONDARY(1);

    private static ClientScreentype activeScreenType;
    private int index;

    ClientScreentype(int index) {
        this.index = index;
    }

    public static ClientScreentype getActiveScreenType() {
        return activeScreenType;
    }

    public static void setActiveScreenType(ClientScreentype screenType) {
        activeScreenType = screenType;
    }

    public static Rectangle2D getActiveVisualBounds() {
        return Screen.getScreens().get(getActiveScreenType().index).getVisualBounds();
    }
}
