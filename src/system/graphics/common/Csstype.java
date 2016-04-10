package system.graphics.common;

public enum Csstype {
    lightTheme("lightTheme.css"),
    darkTheme("darkTheme.css");

    private static Csstype activeTheme = lightTheme;
    private String themeName;


    Csstype(String themeName) {
        this.themeName = themeName;
    }

    public String toString() {
        setActiveTheme(this);
        return this.themeName;
    }

    public Csstype getActiveTheme() {
        return activeTheme;
    }

    public static void setActiveTheme(Csstype activeTheme) {
        Csstype.activeTheme = activeTheme;
    }
}
