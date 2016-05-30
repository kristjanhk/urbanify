package system.graphics.floorPlanner;

import javafx.scene.image.Image;
import system.graphics.common.Csstype;

import java.util.HashMap;

public enum Imagetype {
    SCREEN(new HashMap<Csstype, String>() {{
        put(Csstype.LIGHT, "screen.png");
        put(Csstype.DARK, "screenDark.png");
    }}),
    STAGE(new HashMap<Csstype, String>() {{
        put(Csstype.LIGHT, "stage.png");
        put(Csstype.DARK, "stageDark.png");
    }});

    private HashMap<Csstype, String> themedImages;

    Imagetype(HashMap<Csstype, String> themedImages) {
        this.themedImages = themedImages;
    }

    public String toString() {
        return "system/graphics/floorPlanner/images/" + this.themedImages.get(Csstype.getActiveTheme());
    }

    public Image toImage() {
        return new Image(this.toString());
    }
}
