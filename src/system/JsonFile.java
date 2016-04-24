package system;

import java.util.HashMap;

public class JsonFile {
    public enum Word {NEXT, CANCEL, BACK, CREATE}
    public enum Lang {ENGLISH(0), ESTONIAN(1), VÕRO(2), GERMAN(3), RUSSIAN(4);
        private int index;

        Lang(int i) {
            this.index = i;
        }

        public int getIndex() {
            return this.index;
        }
    }
    private HashMap<Word, String[]> lang;

    public JsonFile() {}

    public String getWord(Word word, Lang lang) {
        return this.lang.get(word)[lang.getIndex()];
    }

    public void createLanguages() {
        HashMap<Word, String[]> lang = new HashMap<>();
        lang.put(Word.NEXT, new String[]{"next", "järgmine", "järgmäne", "todo", "todo"});
        lang.put(Word.CANCEL, new String[]{"cancel", "tühista", "tühista?", "todo", "todo"});
        lang.put(Word.BACK, new String[]{"back", "tagasi", "tagasi", "todo", "todo"});
        lang.put(Word.CREATE, new String[]{"create", "loo", "luu?", "todo", "todo"});
        this.lang = lang;
    }
}
