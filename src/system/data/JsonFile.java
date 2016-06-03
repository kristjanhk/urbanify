package system.data;

import system.MainHandler;
import system.graphics.common.Csstype;
import system.graphics.common.Scenetype;
import system.graphics.eventManager.Controller;
import system.graphics.floorPlanner.FloorPlan;

import java.util.ArrayList;
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

    public ArrayList<Integer> getFloorPlanDimensions(String name) {
        Object dimensions = this.getFloorPlans().get(name).get(FloorPlan.DIMENSIONS);
        ArrayList<Integer> floorPlanSize = new ArrayList<>();
        for (int i = 0; i < ((ArrayList) dimensions).size(); i++) {
            Object number = ((ArrayList) dimensions).get(i);
            if (number instanceof Number) {
                floorPlanSize.add(((Number) number).intValue());
            }
        }
        return floorPlanSize;
    }

    public ArrayList<ArrayList<Integer>> getFloorPlanUnavailables(String name) {
        Object seats = this.getFloorPlans().get(name).get(FloorPlan.UNAVAILABLE);
        ArrayList<ArrayList<Integer>> unavailables = new ArrayList<>();
        for (Object seatData : (ArrayList) seats) {
            ArrayList<Integer> data = new ArrayList<>(2);
            for (Object coordinate : (ArrayList) seatData) {
                if (coordinate instanceof Number) {
                    data.add(((Number) coordinate).intValue());
                }
            }
            unavailables.add(data);
        }
        return unavailables;
    }

    public void saveFloorPlan(String name, int rows, int columns, ArrayList<ArrayList<Integer>> unavailableSeats) {
        if (this.savedFloorPlans == null) {
            this.savedFloorPlans = new HashMap<>();
        }
        ArrayList<Integer> dimensions = new ArrayList<>(2);
        dimensions.add(rows);
        dimensions.add(columns);
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
