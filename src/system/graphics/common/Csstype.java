package system.graphics.common;

/**
 * CSS tüüp
 * Hoiab endas CSSi tüüpe kui konstante
 * Iga tüüp on Csstype isend
 * Igale tüübile vastab Cssi failinimi
 */
public enum Csstype {
    LIGHT("lightTheme.css"),
    DARK("darkTheme.css");

    private static Csstype activeTheme;
    private String themeName;


    Csstype(String themeName) {
        this.themeName = themeName;
    }

    public static void setActiveTheme(Csstype activeTheme) {
        Csstype.activeTheme = activeTheme;
    }

    public static Csstype getActiveTheme() {
        return activeTheme;
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
