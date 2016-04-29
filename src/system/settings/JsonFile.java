package system.settings;

import system.graphics.common.Csstype;

public class JsonFile {
    private Lang activeLanguage;
    private Csstype activeTheme;

    public JsonFile() {}

    public Lang getActiveLanguage() {
        return this.activeLanguage;
    }

    public Csstype getActiveTheme() {
        return this.activeTheme;
    }

    public void saveCurrentData() {
        this.activeLanguage = Lang.getActiveLang();
        this.activeTheme = Csstype.getActiveTheme();
    }
}
