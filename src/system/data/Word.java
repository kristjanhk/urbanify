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
    LANGUAGE, THEME, LIGHT, DARK, WARM, SETSEATINGTYPE, SEATINGTYPE, ENGLISH, ESTONIAN, VÕRO, GERMAN,
    RUSSIAN, CHECKOUT, TOTAL, POS, FINISH, COST, QUANTITY, NEW, POPOVERTEXT, SEATSLEFT, UNLIMITED, CURRENCY;
    private static HashMap<Word, String[]> words = createLanguages();

    private static HashMap<Word, String[]> createLanguages() {
        HashMap<Word, String[]> wordsMap = new HashMap<>();
        wordsMap.put(Word.NEWEVENT, new String[]{"new event", "uus üritus", "vahtsõnõ üritus",
                "neue Ereignis", "TODO"});
        wordsMap.put(Word.OPENEVENT, new String[]{"open event", "ava üritused", "valla ettevõtminõsõ?",
                "offene Ereignis", "TODO"});
        wordsMap.put(Word.SETTINGS, new String[]{"settings", "seaded", "säädistüs",
                "Einstellungen", "TODO"});
        wordsMap.put(Word.ARCHIVE, new String[]{"archive", "arhiiv", "kiräkogo",
                "Archiv", "TODO"});
        wordsMap.put(Word.EVENTCREATOR, new String[]{"event creator", "ürituse looja", "ürituse luuja",
                "Ereigniserzeuger??", "TODO"});
        wordsMap.put(Word.POINTOFSALE, new String[]{"point of sale", "müügipunkt", "müügikotus",
                "TODO", "TODO"});
        wordsMap.put(Word.NAMEYOUREVENT, new String[]{"name your event", "nimeta üritus", "märgota üritus",
                "nennen Sie Ihre Ereignis", "TODO"});
        wordsMap.put(Word.PRICE, new String[]{"price", "hind", "hind",
                "Preis", "TODO"});
        wordsMap.put(Word.TICKETYPE, new String[]{"ticket type", "pileti tüüp", "lubatähe muud",
                "Ticket-typ", "TODO"});
        wordsMap.put(Word.OPENSEATING, new String[]{"open seating", "vabad kohad", "vaba kotosõ",
                "freie Sitzplätze", "TODO"});
        wordsMap.put(Word.ASSIGNEDSEATING, new String[]{"assigned seating", "määratud kohad", "määrätü kotosõ",
                "zuweisen Sitzplätze?", "TODO"});
        wordsMap.put(Word.SETDATE, new String[]{"set date", "määra kuupäev", "määrä kuupäiv",
                "Datum einstellen", "TODO"});
        wordsMap.put(Word.SETTIME, new String[]{"set time", "määra aeg", "määrä aig",
                "Zeit einstellen", "TODO"});
        wordsMap.put(Word.MAXNROFSEATS, new String[]{"max nr of seats", "suurim arv kohti",
                "kõgõ suurõmb nummõr kotosõid", "maximale Anzahl der Sitze", "TODO"});
        wordsMap.put(Word.SETMAXNROFSEATS, new String[]{"set max nr of seats", "määra suurim arv kohti",
                "määrä kõgõ suurõmb nummõr kotosõid", "TODO maximale Anzahl der Sitze", "TODO"});
        wordsMap.put(Word.NEXT, new String[]{"next", "edasi", "edesi",
                "weiter", "TODO"});
        wordsMap.put(Word.CANCEL, new String[]{"cancel", "katkesta", "jätä katski",
                "abbrechen", "TODO"});
        wordsMap.put(Word.BACK, new String[]{"back", "tagasi", "tagasi",
                "zurück", "TODO"});
        wordsMap.put(Word.CREATE, new String[]{"create", "loo", "luu",
                "erstellen", "TODO"});
        wordsMap.put(Word.SAVE, new String[]{"save", "salvesta", "panõ tallõlõ",
                "speichern", "TODO"});
        wordsMap.put(Word.NEWFLOORPLAN, new String[]{"new floorplan", "uus saaliplaan", "vahtsõnõ saaliplaan",
                "neue Gebäudeplan?", "TODO"});
        wordsMap.put(Word.FLOORPLANS, new String[]{"plan", "plaan", "plaan",
                "Plan", "TODO"});
        wordsMap.put(Word.FLOORTYPE, new String[]{"type", "tüüp", "sort",
                "Typ", "TODO"});
        wordsMap.put(Word.ROWS, new String[]{"rows", "read", "riaq",
                "Reihen", "TODO"});
        wordsMap.put(Word.SEATSINROW, new String[]{"seats in row", "kohad reas", "kotosõ rian",
                "Sitze in Reihe", "TODO"});
        wordsMap.put(Word.STAGE, new String[]{"stage", "lava", "püüne",
                "Bühne", "TODO"});
        wordsMap.put(Word.SCREEN, new String[]{"screen", "ekraan", "pildikruut",
                "Bildschrim", "TODO"});
        wordsMap.put(Word.EVENTS, new String[]{"events", "üritused", "üritusõ",
                "Ereignisse", "TODO"});
        wordsMap.put(Word.DATEFORMAT, new String[]{"dd/mm/yyyy", "pp.kk.aaaa", "pp.kk.aaaa",
                "tt.mm.jj??", "TODO"});
        wordsMap.put(Word.TIMEFORMAT, new String[]{"hh:mm", "hh:mm", "hh:mm",
                "ss:mm??", "TODO"});
        wordsMap.put(Word.APPLY, new String[]{"apply", "rakenda", "rakenda",
                "TODO", "TODO"});
        wordsMap.put(Word.OK, new String[]{"ok", "ok", "häste",
                "TODO", "TODO"});
        wordsMap.put(Word.PATH, new String[]{"location path", "kausta asukoht", "kausta paik",
                "TODO", "TODO"});
        wordsMap.put(Word.LANGUAGE, new String[]{"language", "keel", "kill",
                "TODO", "TODO"});
        wordsMap.put(Word.THEME, new String[]{"theme", "kujundus", "kujondus",
                "TODO", "TODO"});
        wordsMap.put(Word.LIGHT, new String[]{"light", "hele", "helle",
                "TODO", "TODO"});
        wordsMap.put(Word.DARK, new String[]{"dark", "tume", "tummõ",
                "TODO", "TODO"});
        wordsMap.put(Word.WARM, new String[]{"warm", "soe", "lämmi",
                "TODO", "TODO"});
        wordsMap.put(Word.SETSEATINGTYPE, new String[]{"set seating type", "määra kohtade tüüp", "määrä kotosõ sort",
                "TODO", "TODO"});
        wordsMap.put(Word.SEATINGTYPE, new String[]{"seating type", "kohtade tüüp", "kotosõ sort",
                "TODO", "TODO"});
        wordsMap.put(Word.ENGLISH, new String[]{"english", "inglise keel", "ingliskiil",
                "TODO", "TODO"});
        wordsMap.put(Word.ESTONIAN, new String[]{"estonian", "eesti keel", "eesti kiil",
                "TODO", "TODO"});
        wordsMap.put(Word.VÕRO, new String[]{"võro", "võro kiil", "võro kiil",
                "TODO", "TODO"});
        wordsMap.put(Word.GERMAN, new String[]{"german", "saksa keel", "saksa kiil",
                "deutsch", "TODO"});
        wordsMap.put(Word.RUSSIAN, new String[]{"russian", "vene keel", "vinne kiil",
                "TODO", "pусский язык"});
        wordsMap.put(Word.CHECKOUT, new String[]{"checkout", "lõpeta", "lõpeta",
                "TODO", "TODO"});
        wordsMap.put(Word.TOTAL, new String[]{"total", "kokku", "kokko",
                "TODO", "TODO"});
        wordsMap.put(Word.POS, new String[]{"pos", "müük", "müük",
                "TODO", "TODO"});
        wordsMap.put(Word.FINISH, new String[]{"finish", "lõpeta", "lõpeta",
                "TODO", "TODO"});
        wordsMap.put(Word.COST, new String[]{"cost", "hind", "hind",
                "TODO", "TODO"});
        wordsMap.put(Word.QUANTITY, new String[]{"quantity", "kogus", "ports",
                "TODO", "TODO"});
        wordsMap.put(Word.NEW, new String[]{"new", "uus", "vahtsõnõ",
                "TODO", "TODO"});
        wordsMap.put(Word.POPOVERTEXT, new String[]{"set 0 for unlimited", "vali 0 kui kohtade arv ei ole määratud",
                "vali 0 kui kotosõ arv olõi määrätü", "TODO", "TODO"});
        wordsMap.put(Word.SEATSLEFT, new String[]{"seats left", "istmekohti alles", "kotosõid alale",
                "TODO", "TODO"});
        wordsMap.put(Word.UNLIMITED, new String[]{"unlimited", "lõpmatu", "ilmotsaldaq",
                "TODO", "TODO"});
        wordsMap.put(Word.CURRENCY, new String[]{"cur", "ühik", "mõõt",
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
