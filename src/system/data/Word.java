package system.data;

import java.util.HashMap;

/**
 * Sõnad
 * Hoiab endas sõnu kui konstante
 * Igas isendis on temale vastavad sõnad erinevates keeltes
 * Keele määrab ära Lang klassi activeLanguage
 */
public enum Word {
    NEWEVENT, SETTINGS, ARCHIVE, EVENTCREATOR, POINTOFSALE, NAMEYOUREVENT, PRICE, TICKETYPE, OPENSEATING,
    ASSIGNEDSEATING, NEXT, CANCEL, BACK, CREATE, SAVE, NEWFLOORPLAN, FLOORPLAN, ROWS, SEATSINROW, STAGE, SCREEN,
    EVENTS, SETDATE, SETTIME, SETMAXNROFSEATS, DATEFORMAT, TIMEFORMAT, PATH, LANGUAGE, THEME, LIGHT, DARK,
    SETSEATINGTYPE, SEATINGTYPE, ENGLISH, ESTONIAN, VÕRO, GERMAN, TICKETSTOSELECT, RUSSIAN, CHECKOUT, TOTAL, POS,
    FINISH, COST, QUANTITY, NEW, SEATSLEFT, UNLIMITED, CURRENCY, EDIT, UPDATE, EVENTUPDATER, SHOWQR, AUTHKEY, PRIMARY,
    SECONDARY, SCREENTYPE, TICKETINFO, FOLDER, PDF, REPORTPDF, CREATEPDF, COMPANYNAME, REPORTNR, WORKERNAME;
    private static HashMap<Word, String[]> words = createWords();

    private static HashMap<Word, String[]> createWords() {
        HashMap<Word, String[]> wordsMap = new HashMap<>();
        wordsMap.put(Word.NEWEVENT, new String[]{"new event", "uus üritus", "vahtsõnõ üritus",
                "neue Ereignis", "новое мероприятие"});
        wordsMap.put(Word.SETTINGS, new String[]{"settings", "seaded", "säädistüs",
                "Einstellungen", "настройки"});
        wordsMap.put(Word.ARCHIVE, new String[]{"archive", "arhiiv", "kiräkogo",
                "Archiv", "архив"});
        wordsMap.put(Word.EVENTCREATOR, new String[]{"event creator", "ürituse looja", "ürituse luuja",
                "Ereigniserzeuger", "создатель мероприятие"});
        wordsMap.put(Word.POINTOFSALE, new String[]{"point of sale", "müügipunkt", "müügikotus",
                "Verkaufsstelle", "POS-терминал "});
        wordsMap.put(Word.NAMEYOUREVENT, new String[]{"name your event", "nimeta üritus", "märgota üritus",
                "nennen Sie Ihres Ereignis", "назовите мероприятие"});
        wordsMap.put(Word.PRICE, new String[]{"price", "hind", "hind",
                "Preis", "цена"});
        wordsMap.put(Word.TICKETYPE, new String[]{"ticket type", "pileti tüüp", "piledi tüüp",
                "Ticket-typ", "тип билета"});
        wordsMap.put(Word.OPENSEATING, new String[]{"open seating", "vabad kohad", "vaba kotosõ",
                "freie Plätze", "открыт рассадка"});
        wordsMap.put(Word.ASSIGNEDSEATING, new String[]{"assigned seating", "määratud kohad", "määrätü kotosõ",
                "zuweisen Plätze", "назначены рассадка"});
        wordsMap.put(Word.SETDATE, new String[]{"set date", "määra kuupäev", "määrä kuupäiv",
                "Datum setzen", "установить дату"});
        wordsMap.put(Word.SETTIME, new String[]{"set time", "määra aeg", "määrä aig",
                "Zeit setzen", "установить время"});
        wordsMap.put(Word.SETMAXNROFSEATS, new String[]{"set max nr of seats", "määra suurim arv kohti",
                "määrä kõgõ suurõmb nummõr kotosõid", "setzen maximale Anzahl der Plätze",
                "установить максимальное количество мест"});
        wordsMap.put(Word.NEXT, new String[]{"next", "edasi", "edesi",
                "weiter", "следующий "});
        wordsMap.put(Word.CANCEL, new String[]{"cancel", "katkesta", "jätä katski",
                "zurück", "отмена"});
        wordsMap.put(Word.BACK, new String[]{"back", "tagasi", "tagasi",
                "zurück", "назад"});
        wordsMap.put(Word.CREATE, new String[]{"create", "loo", "luu",
                "erstellen", "создайте"});
        wordsMap.put(Word.SAVE, new String[]{"save", "salvesta", "pästäq",
                "speichern", "сохранить"});
        wordsMap.put(Word.NEWFLOORPLAN, new String[]{"new floorplan", "uus saaliplaan", "vahtsõnõ saaliplaan",
                "neue Saalplan", "новый план этажа"});
        wordsMap.put(Word.FLOORPLAN, new String[]{"plan", "plaan", "plaan",
                "Plan", "план"});
        wordsMap.put(Word.ROWS, new String[]{"rows", "read", "riaq",
                "Reihen", "строки"});
        wordsMap.put(Word.SEATSINROW, new String[]{"columns", "veerge", "tulpi",
                "Spalte", "столбцы"});
        wordsMap.put(Word.STAGE, new String[]{"stage", "lava", "püüne",
                "Bühne", "сцена"});
        wordsMap.put(Word.SCREEN, new String[]{"screen", "ekraan", "pildikruut",
                "Bildschrim", "экран"});
        wordsMap.put(Word.EVENTS, new String[]{"events", "üritused", "üritusõ",
                "Ereignisse", "мероприятия"});
        wordsMap.put(Word.DATEFORMAT, new String[]{"dd/mm/yyyy", "pp.kk.aaaa", "pp.kk.aaaa",
                "tt.mm.jjjj", "дд.мм.гггг"});
        wordsMap.put(Word.TIMEFORMAT, new String[]{"hh:mm", "hh:mm", "hh:mm",
                "hh:mm", "чч:мм"});
        wordsMap.put(Word.PATH, new String[]{"location path", "kausta asukoht", "kausta paik",
                "Ordnerpfad", "путь к папке"});
        wordsMap.put(Word.LANGUAGE, new String[]{"language", "keel", "kill",
                "Sprache", "язык"});
        wordsMap.put(Word.THEME, new String[]{"theme", "kujundus", "kujondus",
                "Thema", "тема"});
        wordsMap.put(Word.LIGHT, new String[]{"light", "hele", "helle",
                "hell", "легкий"}); //vene keel pole vist päris õige
        wordsMap.put(Word.DARK, new String[]{"dark", "tume", "tummõ",
                "dunkel", "темно"});
        wordsMap.put(Word.SETSEATINGTYPE, new String[]{"set seating type", "määra kohtade tüüp", "määrä kotosõ sort",
                "Plätze-typ setzen", "установить типа рассадка"});
        wordsMap.put(Word.SEATINGTYPE, new String[]{"seating type", "kohtade tüüp", "kotosõ sort",
                "Plätze-typ", "рассадка тип"});
        wordsMap.put(Word.ENGLISH, new String[]{"english", "inglise keel", "ingliskiil",
                "Englisch", "английский язык"});
        wordsMap.put(Word.ESTONIAN, new String[]{"estonian", "eesti keel", "eesti kiil",
                "Estnisch", "эстонский язык"});
        wordsMap.put(Word.VÕRO, new String[]{"võro", "võro kiil", "võro kiil",
                "Võru dialekt", "выруский диалект"});
        wordsMap.put(Word.GERMAN, new String[]{"german", "saksa keel", "saksa kiil",
                "Deutsch", "немецкий язык"});
        wordsMap.put(Word.RUSSIAN, new String[]{"russian", "vene keel", "vinne kiil",
                "Russisch", "русский язык"});
        wordsMap.put(Word.CHECKOUT, new String[]{"checkout", "lõpeta", "lõpeta",
                "Ende", "конец"});
        wordsMap.put(Word.TOTAL, new String[]{"total", "kokku", "kokko",
                "gesamt", "итог"});
        wordsMap.put(Word.POS, new String[]{"pos", "müük", "müük",
                "Verkauf", "продажа"}); //vale vist vene keeles
        wordsMap.put(Word.FINISH, new String[]{"finish", "lõpeta", "lõpeta",
                "Ende", "конец"});
        wordsMap.put(Word.COST, new String[]{"cost", "hind", "hind",
                "Preis", "цена"});
        wordsMap.put(Word.QUANTITY, new String[]{"quantity", "kogus", "ports",
                "Menge", "количество"});
        wordsMap.put(Word.NEW, new String[]{"new", "uus", "vahtsõnõ",
                "neue", "новый"});
        wordsMap.put(Word.SEATSLEFT, new String[]{"seats left", "istmekohti alles", "kotosõid alale",
                "freie Plätze", "мест осталось"});
        wordsMap.put(Word.UNLIMITED, new String[]{"unlimited", "lõpmatu", "ilmotsaldaq",
                "Unlimitiert", "неограниченный"});
        wordsMap.put(Word.CURRENCY, new String[]{"curr", "ühik", "mõõt",
                "währ", "вал"});
        wordsMap.put(Word.TICKETSTOSELECT, new String[]{"tickets left", "pileteid valida", "piletit validaq",
                "TODO", "билеты осталось"});
        wordsMap.put(Word.EDIT, new String[]{"edit", "muuda", "muuda",
                "TODO", "редактировать"});
        wordsMap.put(Word.UPDATE, new String[]{"update", "uuenda", "vahtsõnda",
                "TODO", "обновлять"});
        wordsMap.put(Word.EVENTUPDATER, new String[]{"event editor", "ürituse muutja", "ürituse muutja",
                "TODO", "редактирование мероприятий"});
        wordsMap.put(Word.SHOWQR, new String[]{"show qr code", "näita qr koodi", "näuta qr kuudi",
                "TODO", "показать QR-код"});
        wordsMap.put(Word.AUTHKEY, new String[]{"get authentication key", "hangi autentimise võti",
                "nõvvutõlli salavõti", "TODO", "получить ключ аутентификации"});
        wordsMap.put(Word.PRIMARY, new String[]{"primary", "peamine", "päämine",
                "TODO", "основной"});
        wordsMap.put(Word.SECONDARY, new String[]{"secondary", "teine", "tõõnõ",
                "TODO", "secondary"});
        wordsMap.put(Word.SCREENTYPE, new String[]{"client screen", "kliendi ekraan", "kundõ pildikruut",
                "TODO", "экран клиента"});
        wordsMap.put(Word.TICKETINFO, new String[]{"ticket", "pilet", "pilet",
                "TODO", "билет"});
        wordsMap.put(Word.FOLDER, new String[]{"Choose application's home folder", "Vali programmi kodokaust",
                "TODO", "TODO", "Выберите домашнюю папку приложения"});
        wordsMap.put(Word.PDF, new String[]{"Save pdf file", "Salvesta pdf fail", "Pästaq pdf teedüstü",
                "TODO", "Сохранить PDF файл"});
        wordsMap.put(Word.REPORTPDF, new String[]{"report creator", "aruande koostaja", "aruandõ kokkopandja",
                "TODO", "создатель сообщения"});
        wordsMap.put(Word.CREATEPDF, new String[]{"create pdf", "loo pdf", "luu pdf",
                "TODO", "создать PDF"});
        wordsMap.put(Word.COMPANYNAME, new String[]{"company name", "ettevõtte nimi", "ettevõttõ nimi",
                "TODO", "название компании"});
        wordsMap.put(Word.REPORTNR, new String[]{"report nr", "aruande nr", "aruandõ nummõr",
                "TODO", "номер отчета"});
        wordsMap.put(Word.WORKERNAME, new String[]{"report author", "aruande koostanud isik",
                "aruandõ kokko säädnü inemine", "TODO", "автор отчета"});
        return wordsMap;
    }

    public static Word toEnum(String translated, Lang lang) {
        for (Word constant : values()) {
            if (constant.inLang(lang).equals(translated)) {
                return constant;
            }
        }
        return null;
    }

    public static Word toEnum(String translated) {
        return toEnum(translated, Lang.getActiveLang());
    }

    public String inLang(Lang lang) {
        return words.get(this)[lang.getIndex()];
    }

    @Override
    public String toString() {
        return this.inLang(Lang.getActiveLang());
    }
}