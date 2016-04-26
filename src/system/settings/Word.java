package system.settings;

import java.util.HashMap;

public enum Word {
    NEWEVENT, OPENEVENT, SETTINGS, ARCHIVE, EVENTCREATOR, NAMEYOUREVENT, PRICE, TICKETYPE, OPENSEATING, ASSIGNEDSEATING,
    NEXT, CANCEL, BACK, CREATE, SAVE, NEWFLOORPLAN, FLOORPLANS, FLOORTYPE, ROWS, SEATSINROW, STAGE, SCREEN, EVENTS,
    SETDATE, SETTIME, MAXNROFSEATS, DATEFORMAT, TIMEFORMAT;
    private static HashMap<Word, String[]> words = createLanguages();

    private static HashMap<Word, String[]> createLanguages() {
        HashMap<Word, String[]> wordsMap = new HashMap<>();
        wordsMap.put(Word.NEWEVENT, new String[]{"new event", "uus üritus", "vahtsõnõ ettevõtminõ?",
                "neue Ereignis", "todo"});
        wordsMap.put(Word.OPENEVENT, new String[]{"open event", "ava üritused", "valla ettevõtminõsõ?",
                "offene Ereignis", "todo"});
        wordsMap.put(Word.SETTINGS, new String[]{"settings", "seaded", "todo",
                "Einstellungen", "todo"});
        wordsMap.put(Word.ARCHIVE, new String[]{"archive", "arhiiv", "kiräkogo?",
                "Archiv", "todo"});
        wordsMap.put(Word.EVENTCREATOR, new String[]{"event creator", "ürituse looja", "ettevõtminõsõ luuja?",
                "Ereigniserzeuger??", "todo"});
        wordsMap.put(Word.NAMEYOUREVENT, new String[]{"name your event", "nimeta üritus", "todo",
                "nennen Sie Ihre Ereignis", "todo"});
        wordsMap.put(Word.PRICE, new String[]{"price", "hind", "hind",
                "Preis", "todo"});
        wordsMap.put(Word.TICKETYPE, new String[]{"ticket type", "pileti tüüp", "paprõ sort",
                "Ticket-typ", "todo"});
        wordsMap.put(Word.OPENSEATING, new String[]{"open seating", "vabad kohad", "vaba kotosõ",
                "freie Sitzplätze", "todo"});
        wordsMap.put(Word.ASSIGNEDSEATING, new String[]{"assigned seating", "määratud kohad", "määrät kotosõ",
                "zuweisen Sitzplätze?", "todo"});
        wordsMap.put(Word.SETDATE, new String[]{"set date", "määra kuupäev", "määrä kuupäiv",
                "Datum einstellen", "todo"});
        wordsMap.put(Word.SETTIME, new String[]{"set time", "määra aeg", "määrä aig",
                "Zeit einstellen", "todo"});
        wordsMap.put(Word.MAXNROFSEATS, new String[]{"max nr of seats", "ülim arv kohti", "kõgõ suurõmb nummõr kotosõ",
                "maximale Anzahl der Sitze", "todo"});
        wordsMap.put(Word.NEXT, new String[]{"next", "edasi", "edesi",
                "weiter", "todo"});
        wordsMap.put(Word.CANCEL, new String[]{"cancel", "katkesta", "todo",
                "abbrechen", "todo"});
        wordsMap.put(Word.BACK, new String[]{"back", "tagasi", "tagasi",
                "zurück", "todo"});
        wordsMap.put(Word.CREATE, new String[]{"create", "loo", "luu",
                "erstellen", "todo"});
        wordsMap.put(Word.SAVE, new String[]{"save", "salvesta", "todo",
                "speichern", "todo"});
        wordsMap.put(Word.NEWFLOORPLAN, new String[]{"new floorplan", "uus saaliplaan", "luu",
                "neue Gebäudeplan?", "todo"});
        wordsMap.put(Word.FLOORPLANS, new String[]{"plan", "plaan", "plaan/kava",
                "Plan", "todo"});
        wordsMap.put(Word.FLOORTYPE, new String[]{"type", "tüüp", "sort",
                "Typ", "todo"});
        wordsMap.put(Word.ROWS, new String[]{"rows", "read", "rinnan",
                "Reihen", "todo"});
        wordsMap.put(Word.SEATSINROW, new String[]{"seats in row", "kohad reas", "kotosõ rian",
                "Sitze in Reihe", "todo"});
        wordsMap.put(Word.STAGE, new String[]{"stage", "lava", "laba",
                "Bühne", "todo"});
        wordsMap.put(Word.SCREEN, new String[]{"screen", "ekraan", "pildikruut",
                "Bildschrim", "todo"});
        wordsMap.put(Word.EVENTS, new String[]{"events", "üritused", "ettevõtmõsõ",
                "Ereignisse", "todo"});
        wordsMap.put(Word.DATEFORMAT, new String[]{"dd.mm.yyyy", "pp.kk.aaaa", "pp.kk.aaaa",
                "tt.mm.jj??", "todo"});
        wordsMap.put(Word.TIMEFORMAT, new String[]{"hh:mm", "hh:mm", "hh:mm",
                "ss:mm??", "todo"});
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
