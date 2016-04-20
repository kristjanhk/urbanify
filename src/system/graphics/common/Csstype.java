package system.graphics.common;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public enum Csstype {
    LIGHT("lightTheme.css"),
    DARK("darkTheme.css");

    private static Csstype activeTheme = LIGHT;
    private static final List<Csstype> csstypes = Arrays.asList(values());
    private static Random random = new Random();
    private String themeName;


    Csstype(String themeName) {
        this.themeName = themeName;
    }

    private static void setActiveTheme(Csstype activeTheme) {
        Csstype.activeTheme = activeTheme;
    }

    public static Csstype getActiveTheme() {
        return activeTheme;
    }

    public static Csstype randomTheme() {
        return csstypes.get(random.nextInt(csstypes.size()));
    }

    public static Csstype toggleTheme() {
        switch (activeTheme) {
            case LIGHT:
                return DARK;
            case DARK:
                return LIGHT;
            default:
                return LIGHT;
        }
    }

    public String toString(boolean setActive) {
        if (setActive) {
            setActiveTheme(this);
        }
        return this.themeName;
    }
}
