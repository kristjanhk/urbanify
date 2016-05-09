package system.data;

import system.MainHandler;

import java.util.Currency;
import java.util.Locale;

/**
 * Keeled
 * Hoiab endas keeli kui konstante
 * Iga keel on Lang klassi isend
 * Iga isend määrab ära Word klassis isendite sõna, samuti määra ära lokaali (kalendri jaoks)
 */
public enum Lang {
    ENGLISH(0, Locale.UK),
    ESTONIAN(1, new Locale.Builder().setLanguage("et").setScript("Latn").setRegion("EE").build()),
    VÕRO(2, new Locale.Builder().setLanguage("et").setScript("Latn").setRegion("EE").build()),
    GERMAN(3, new Locale.Builder().setLanguage("de").setScript("Latn").setRegion("GR").build()),
    RUSSIAN(4, new Locale.Builder().setLanguage("ru").setScript("Cyrl").setRegion("RU").build());

    private int index;
    private Locale locale;
    private static Lang activeLang;

    Lang(int i, Locale locale) {
        this.index = i;
        this.locale = locale;
    }

    public static Lang getActiveLang() {
        return activeLang;
    }

    public static void setActiveLang(Lang lang) {
        activeLang = lang;
        Locale.setDefault(lang.locale);
        if (MainHandler.getStageHandler() != null) {
            MainHandler.getStageHandler().getScenes().forEach(
                    (scenetype, customScene) -> customScene.getController().setLanguage());
        }
    }

    public String getCurrency() {
        return this.getCurrencyIn(this);
    }

    public String getCurrencyIn(Lang language) {
        return Currency.getInstance(language.locale).getSymbol();
    }

    public int getIndex() {
        return this.index;
    }
}
