package system.data;

import system.MainHandler;
import system.StageHandler;

import java.util.Currency;
import java.util.Locale;

/**
 * Keeled
 * Hoiab endas keeli kui konstante
 * Iga keel on Lang klassi isend
 * Iga isend määrab ära Word klassis isendite sõna, samuti määra ära lokaali (kalendri jaoks)
 */
public enum Lang {
    ENGLISH(0, Locale.UK, "english"),
    ESTONIAN(1, new Locale.Builder().setLanguage("et").setScript("Latn").setRegion("EE").build(), "eesti keel"),
    VÕRO(2, new Locale.Builder().setLanguage("et").setScript("Latn").setRegion("EE").build(), "võro kiil"),
    GERMAN(3, new Locale.Builder().setLanguage("de").setScript("Latn").setRegion("GR").build(), "deutsch"),
    RUSSIAN(4, new Locale.Builder().setLanguage("ru").setScript("Cyrl").setRegion("RU").build(), "русский язык");

    private int index;
    private Locale locale;
    private String name;
    private static Lang activeLang;

    Lang(int i, Locale locale, String name) {
        this.index = i;
        this.locale = locale;
        this.name = name;
    }

    public static Lang getActiveLang() {
        return activeLang;
    }

    public static void setActiveLang(Lang lang) {
        activeLang = lang;
        Locale.setDefault(lang.locale);
        for (StageHandler stageHandler : MainHandler.getStageHandlers()) {
            if (stageHandler != null) {
                stageHandler.getScenes().forEach(
                        (scenetype, customScene) -> customScene.getController().setLanguage());
            }
        }
    }

    public String getName() {
        return this.name;
    }

    public Locale getLocale() {
        return this.locale;
    }

    public String getCurrency() {
        return this.getCurrencyIn(this);
    }

    public String getCurrencyIn(Lang language) {
        if (language == RUSSIAN) {
            return "\u20BD";
        }
        return Currency.getInstance(language.locale).getSymbol();
    }

    public int getIndex() {
        return this.index;
    }
}
