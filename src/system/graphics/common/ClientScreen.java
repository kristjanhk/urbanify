package system.graphics.common;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

public enum ClientScreen {
    PRIMARY(0),
    SECONDARY(1);

    private static ClientScreen activeScreenType;
    private int index;

    ClientScreen(int index) {
        this.index = index;
    }

    public static ClientScreen getActiveScreenType() {
        return activeScreenType;
    }

    public static void setActiveScreenType(ClientScreen screenType) {
        activeScreenType = screenType;
    }

    public static Rectangle2D getActiveVisualBounds() {
        return Screen.getScreens().get(getActiveScreenType().index).getVisualBounds();
    }
}
