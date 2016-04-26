package system.settings;

import system.MainHandler;
import system.StageHandler;

public enum Lang {
    ENGLISH(0),
    ESTONIAN(1),
    VÕRO(2),
    GERMAN(3),
    RUSSIAN(4);
    private int index;
    private static Lang activeLang;

    // TODO: 25.04.2016 set active language

    Lang(int i) {
        this.index = i;
    }

    public static Lang getActiveLang() {
        return activeLang;
    }

    public static void setActiveLang(Lang lang) {
        activeLang = lang;
        for (StageHandler stageHandler : MainHandler.getStageHandlers()) {
            stageHandler.getScenes().forEach((scenetype, customScene) -> customScene.getController().setLanguage());
        }
    }

    public int getIndex() {
        return this.index;
    }
}
