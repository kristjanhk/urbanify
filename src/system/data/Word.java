package system.data;

import java.util.HashMap;

/**
 * Sõnad
 * Hoiab endas sõnu kui konstante
 * Iga sõna on Word klassi isend
 * Igas isendis on temale vastavad sõnad erinevates keeltes
 * Keele määrab ära Lang klassi activeLanguage
 */
public enum Word {
    NEWEVENT, OPENEVENT, SETTINGS, ARCHIVE, EVENTCREATOR, POINTOFSALE, NAMEYOUREVENT, PRICE, TICKETYPE, OPENSEATING,
    ASSIGNEDSEATING, NEXT, CANCEL, BACK, CREATE, SAVE, NEWFLOORPLAN, FLOORPLANS, FLOORTYPE, ROWS, SEATSINROW, STAGE,
    SCREEN, EVENTS, SETDATE, SETTIME, MAXNROFSEATS, SETMAXNROFSEATS, DATEFORMAT, TIMEFORMAT, APPLY, OK, PATH,
    FULLSCREEN, LANGUAGE, THEME, LIGHT, DARK, WARM, SETSEATINGTYPE, SEATINGTYPE, ENGLISH, ESTONIAN, VÕRO, GERMAN,
    RUSSIAN, CHECKOUT, TOTAL, POS, FINISH, COST, QUANTITY;
    private static HashMap<Word, String[]> words = createLanguages();

    private static HashMap<Word, String[]> createLanguages() {
        HashMap<Word, String[]> wordsMap = new HashMap<>();
        wordsMap.put(Word.NEWEVENT, new String[]{"new event", "uus üritus", "vahtsõnõ ettevõtminõ?",
                "neue Ereignis", "TODO"});
        wordsMap.put(Word.OPENEVENT, new String[]{"open event", "ava üritused", "valla ettevõtminõsõ?",
                "offene Ereignis", "TODO"});
        wordsMap.put(Word.SETTINGS, new String[]{"settings", "seaded", "TODO",
                "Einstellungen", "TODO"});
        wordsMap.put(Word.ARCHIVE, new String[]{"archive", "arhiiv", "kiräkogo?",
                "Archiv", "TODO"});
        wordsMap.put(Word.EVENTCREATOR, new String[]{"event creator", "ürituse looja", "ettevõtminõsõ luuja?",
                "Ereigniserzeuger??", "TODO"});
        wordsMap.put(Word.POINTOFSALE, new String[]{"point of sale", "müügipunkt", "TODO",
                "TODO", "TODO"});
        wordsMap.put(Word.NAMEYOUREVENT, new String[]{"name your event", "nimeta üritus", "TODO",
                "nennen Sie Ihre Ereignis", "TODO"});
        wordsMap.put(Word.PRICE, new String[]{"price", "hind", "hind",
                "Preis", "TODO"});
        wordsMap.put(Word.TICKETYPE, new String[]{"ticket type", "pileti tüüp", "paprõ sort",
                "Ticket-typ", "TODO"});
        wordsMap.put(Word.OPENSEATING, new String[]{"open seating", "vabad kohad", "vaba kotosõ",
                "freie Sitzplätze", "TODO"});
        wordsMap.put(Word.ASSIGNEDSEATING, new String[]{"assigned seating", "määratud kohad", "määrät kotosõ",
                "zuweisen Sitzplätze?", "TODO"});
        wordsMap.put(Word.SETDATE, new String[]{"set date", "määra kuupäev", "määrä kuupäiv",
                "Datum einstellen", "TODO"});
        wordsMap.put(Word.SETTIME, new String[]{"set time", "määra aeg", "määrä aig",
                "Zeit einstellen", "TODO"});
        wordsMap.put(Word.MAXNROFSEATS, new String[]{"max nr of seats", "ülim arv kohti",
                "kõgõ suurõmb nummõr kotosõ", "maximale Anzahl der Sitze", "TODO"});
        wordsMap.put(Word.SETMAXNROFSEATS, new String[]{"set max nr of seats", "määra ülim arv kohti",
                "TODO kõgõ suurõmb nummõr kotosõ", "TODO maximale Anzahl der Sitze", "TODO"});
        wordsMap.put(Word.NEXT, new String[]{"next", "edasi", "edesi",
                "weiter", "TODO"});
        wordsMap.put(Word.CANCEL, new String[]{"cancel", "katkesta", "TODO",
                "abbrechen", "TODO"});
        wordsMap.put(Word.BACK, new String[]{"back", "tagasi", "tagasi",
                "zurück", "TODO"});
        wordsMap.put(Word.CREATE, new String[]{"create", "loo", "luu",
                "erstellen", "TODO"});
        wordsMap.put(Word.SAVE, new String[]{"save", "salvesta", "TODO",
                "speichern", "TODO"});
        wordsMap.put(Word.NEWFLOORPLAN, new String[]{"new floorplan", "uus saaliplaan", "luu",
                "neue Gebäudeplan?", "TODO"});
        wordsMap.put(Word.FLOORPLANS, new String[]{"plan", "plaan", "plaan/kava",
                "Plan", "TODO"});
        wordsMap.put(Word.FLOORTYPE, new String[]{"type", "tüüp", "sort",
                "Typ", "TODO"});
        wordsMap.put(Word.ROWS, new String[]{"rows", "read", "rinnan",
                "Reihen", "TODO"});
        wordsMap.put(Word.SEATSINROW, new String[]{"seats in row", "kohad reas", "kotosõ rian",
                "Sitze in Reihe", "TODO"});
        wordsMap.put(Word.STAGE, new String[]{"stage", "lava", "laba",
                "Bühne", "TODO"});
        wordsMap.put(Word.SCREEN, new String[]{"screen", "ekraan", "pildikruut",
                "Bildschrim", "TODO"});
        wordsMap.put(Word.EVENTS, new String[]{"events", "üritused", "ettevõtmõsõ",
                "Ereignisse", "TODO"});
        wordsMap.put(Word.DATEFORMAT, new String[]{"dd/mm/yyyy", "pp.kk.aaaa", "pp.kk.aaaa",
                "tt.mm.jj??", "TODO"});
        wordsMap.put(Word.TIMEFORMAT, new String[]{"hh:mm", "hh:mm", "hh:mm",
                "ss:mm??", "TODO"});
        wordsMap.put(Word.APPLY, new String[]{"apply", "rakenda", "TODO",
                "TODO", "TODO"});
        wordsMap.put(Word.OK, new String[]{"ok", "ok", "TODO",
                "TODO", "TODO"});
        wordsMap.put(Word.PATH, new String[]{"location path", "kausta asukoht", "TODO",
                "TODO", "TODO"});
        wordsMap.put(Word.FULLSCREEN, new String[]{"fullscreen", "täisekraan", "TODO",
                "TODO", "TODO"});
        wordsMap.put(Word.LANGUAGE, new String[]{"language", "keel", "TODO",
                "TODO", "TODO"});
        wordsMap.put(Word.THEME, new String[]{"theme", "kujundus", "TODO",
                "TODO", "TODO"});
        wordsMap.put(Word.LIGHT, new String[]{"light", "hele", "TODO",
                "TODO", "TODO"});
        wordsMap.put(Word.DARK, new String[]{"dark", "tume", "TODO",
                "TODO", "TODO"});
        wordsMap.put(Word.WARM, new String[]{"warm", "soe", "TODO",
                "TODO", "TODO"});
        wordsMap.put(Word.SETSEATINGTYPE, new String[]{"set seating type", "määra kohtade tüüp", "TODO",
                "TODO", "TODO"});
        wordsMap.put(Word.SEATINGTYPE, new String[]{"seating type", "kohtade tüüp", "TODO",
                "TODO", "TODO"});
        wordsMap.put(Word.ENGLISH, new String[]{"english", "inglise keel", "TODO",
                "TODO", "TODO"});
        wordsMap.put(Word.ESTONIAN, new String[]{"estonian", "eesti keel", "TODO",
                "TODO", "TODO"});
        wordsMap.put(Word.VÕRO, new String[]{"võro", "võro kiil", "TODO",
                "TODO", "TODO"});
        wordsMap.put(Word.GERMAN, new String[]{"german", "saksa keel", "TODO",
                "deutsch", "TODO"});
        wordsMap.put(Word.RUSSIAN, new String[]{"russian", "vene keel", "TODO",
                "TODO", "pусский язык"});
        wordsMap.put(Word.CHECKOUT, new String[]{"checkout", "lõpeta?", "TODO",
                "TODO", "TODO"});
        wordsMap.put(Word.TOTAL, new String[]{"total", "kokku", "TODO",
                "TODO", "TODO"});
        wordsMap.put(Word.POS, new String[]{"pos", "müük", "TODO",
                "TODO", "TODO"});
        wordsMap.put(Word.FINISH, new String[]{"finish", "lõpeta", "TODO",
                "TODO", "TODO"});
        wordsMap.put(Word.COST, new String[]{"cost", "hind", "TODO",
                "TODO", "TODO"});
        wordsMap.put(Word.QUANTITY, new String[]{"quantity", "kogus", "TODO",
                "TODO", "TODO"});
        return wordsMap;
    }

    public String inLang(Lang lang) {
        return words.get(this)[lang.getIndex()];
    }

    @Override
    public String toString() {
        return words.get(this)[Lang.getActiveLang().getIndex()];
    }
}
