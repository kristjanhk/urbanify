package system.data;

import system.MainHandler;
import system.graphics.common.Csstype;
import system.graphics.common.Scenetype;
import system.graphics.eventManager.Controller;
import system.graphics.floorPlanner.FloorPlan;

import java.util.HashMap;
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
    private HashMap<String, HashMap<FloorPlan, Object>> savedFloorPlans;

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

    public HashMap<String, HashMap<FloorPlan, Object>> getFloorPlans() {
        if (this.savedFloorPlans == null) {
            return new HashMap<>();
        }
        return this.savedFloorPlans;
    }

    public Integer[] getFloorPlanDimensions(String name) {
        return (Integer[]) this.getFloorPlans().get(name).get(FloorPlan.DIMENSIONS);
    }

    @SuppressWarnings("unchecked")
    public HashSet<Integer[]> getFloorPlanUnavailables(String name) {
        return (HashSet<Integer[]>) this.getFloorPlans().get(name).get(FloorPlan.UNAVAILABLE);
    }

    public void saveFloorPlan(String name, int rows, int columns, HashSet<Integer[]> unavailableSeats) {
        if (this.savedFloorPlans == null) {
            this.savedFloorPlans = new HashMap<>();
        }
        Integer[] dimensions = new Integer[]{rows, columns};
        if (!this.savedFloorPlans.containsKey(name)) {
            this.savedFloorPlans.put(name, new HashMap<>());
        } else {
            this.savedFloorPlans.replace(name, new HashMap<>());
        }
        this.savedFloorPlans.get(name).put(FloorPlan.DIMENSIONS, dimensions);
        this.savedFloorPlans.get(name).put(FloorPlan.UNAVAILABLE, unavailableSeats);
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
