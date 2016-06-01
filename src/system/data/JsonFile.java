package system.data;

import system.MainHandler;
import system.graphics.common.Csstype;
import system.graphics.common.Scenetype;
import system.graphics.eventManager.Controller;
import system.graphics.floorPlanner.Seat;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Jsoni fail
 * (JSON - JavaScript Object Notation)
 * Hoiab endas vajalikke andmeid isendiväljadel
 *
 * Eeliseks tavalise Data(Input|Output)Streami ees on see, et
 * ei pea muretsema lugemise/salvestamise järjekordade üle ja
 * fail on tavalise tekstiredaktoriga loetav ning muudetav.
 *
 * NB: Gsoni teek loeb ise salvestades klassi isendivälju (ka privaatseid) ja
 * sisselaadides loob uue JsonFile objekti ning väärtustab isendiväljad (ilma kontsruktori abita!)
 */
public class JsonFile {
    private Lang activeLanguage;
    private Csstype activeTheme;
    private HashSet<Event> events;
    private HashSet<Event> archivedEvents;
    private ArrayList<ArrayList<ArrayList<Seat.Seattype>>> savedFloorPlans;

    public JsonFile() {}

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

    public ArrayList<ArrayList<ArrayList<Seat.Seattype>>> getFloorPlans() {
        return this.savedFloorPlans;
    }

    public ArrayList<ArrayList<Seat.Seattype>> getFloorPlan(int id) {
        return this.savedFloorPlans.get(id);
    }

    public void saveFloorPlan(ArrayList<ArrayList<Seat.Seattype>> floorPlan) {
        if (this.savedFloorPlans == null) {
            this.savedFloorPlans = new ArrayList<>();
        }
        this.savedFloorPlans.add(floorPlan);
    }

    public void saveCurrentData() {
        this.activeLanguage = Lang.getActiveLang();
        this.activeTheme = Csstype.getActiveTheme();
        this.events = ((Controller) MainHandler.getStageHandler().
                getScene(Scenetype.EVENTMANAGER).getController()).getEvents();
        this.archivedEvents = ((Controller) MainHandler.getStageHandler().
                getScene(Scenetype.ARCHIVE).getController()).getEvents();
    }
}
