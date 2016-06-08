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
        // TODO: 09/06/2016 läpakale testimiseks aind, REMOVEME
        if (Screen.getScreens().size() == 1) {
            return Screen.getScreens().get(0).getVisualBounds();
        }
        return Screen.getScreens().get(getActiveScreenType().index).getVisualBounds();
    }
}
