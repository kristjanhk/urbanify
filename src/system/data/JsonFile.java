package system.data;

import system.MainHandler;
import system.graphics.common.Csstype;
import system.graphics.common.Scenetype;
import system.graphics.eventManager.Controller;

import java.util.HashSet;

public class JsonFile {
    private Lang activeLanguage;
    private Csstype activeTheme;
    private HashSet<Event> events;
    private HashSet<Event> archivedEvents;

    public JsonFile() {}

    public void init(){
        this.events = new HashSet<>();
    }

    public Lang getActiveLanguage() {
        return this.activeLanguage;
    }

    public Csstype getActiveTheme() {
        return this.activeTheme;
    }

    public HashSet<Event> getEvents(Scenetype scenetype) {
        if (scenetype == Scenetype.ARCHIVE && this.archivedEvents != null) {
            return this.archivedEvents;
        } else if (scenetype == Scenetype.EVENTMANAGER && this.events != null){
            return this.events;
        }
        return new HashSet<>();
    }

    public void saveCurrentData() {
        this.activeLanguage = Lang.getActiveLang();
        this.activeTheme = Csstype.getActiveTheme();
        this.events = ((Controller) MainHandler.getStageHandlers().get(0).
                getScene(Scenetype.EVENTMANAGER).getController()).getEvents();
        this.archivedEvents = ((Controller) MainHandler.getStageHandlers().get(0).
                getScene(Scenetype.ARCHIVE).getController()).getEvents();
    }
}
