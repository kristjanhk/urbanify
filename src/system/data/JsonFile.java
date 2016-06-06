package system.data;

import system.MainHandler;
import system.graphics.common.Csstype;
import system.graphics.common.FloorPlanPane;
import system.graphics.common.Scenetype;
import system.graphics.common.ClientScreentype;
import system.graphics.eventManager.Controller;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
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
    private ClientScreentype activeClientScreen;
    private HashSet<Event> events;
    private HashSet<Event> archivedEvents;
    private HashMap<String, HashMap<FloorPlanPane.Property, Object>> savedFloorPlans;
    private byte[] publicKey;
    private byte[] privateKey;

    public JsonFile() {}

    public Lang getActiveLanguage() {
        if (this.activeLanguage != null) {
            return this.activeLanguage;
        }
        Lang.setActiveLang(Lang.ENGLISH);
        return Lang.ENGLISH;
    }

    public Csstype getActiveTheme() {
        if (this.activeTheme != null) {
            return this.activeTheme;
        }
        Csstype.setActiveTheme(Csstype.DARK);
        return Csstype.DARK;
    }

    public ClientScreentype getActiveClientScreen() {
        if (this.activeClientScreen != null) {
            return this.activeClientScreen;
        }
        ClientScreentype.setActiveScreenType(ClientScreentype.PRIMARY);
        return ClientScreentype.PRIMARY;
    }

    public HashSet<Event> getEvents(Scenetype scenetype) {
        if (scenetype == Scenetype.ARCHIVE && this.archivedEvents != null) {
            return this.archivedEvents;
        } else if (scenetype == Scenetype.EVENTMANAGER && this.events != null){
            return this.events;
        }
        return new HashSet<>();
    }

    public HashMap<String, HashMap<FloorPlanPane.Property, Object>> getFloorPlans() {
        if (this.savedFloorPlans == null) {
            return new HashMap<>();
        }
        return this.savedFloorPlans;
    }

    public HashMap<FloorPlanPane.Property, Object> getFloorPlan(String name) {
        return this.savedFloorPlans.get(name);
    }

    public void saveFloorPlan(String name, HashMap<FloorPlanPane.Property, Object> floorPlan) {
        if (this.savedFloorPlans == null) {
            this.savedFloorPlans = new HashMap<>();
        }
        if (this.savedFloorPlans.containsKey(name)) {
            this.savedFloorPlans.replace(name, floorPlan);
        } else {
            this.savedFloorPlans.put(name, floorPlan);
        }
    }

    public void saveKeys(KeyPair keyPair) {
        this.publicKey = keyPair.getPublic().getEncoded();
        this.privateKey = keyPair.getPrivate().getEncoded();
    }

    public PublicKey getPublicKey() {
        if (this.publicKey != null) {
            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(this.publicKey);
            try {
                KeyFactory keyFactory = KeyFactory.getInstance("DSA", "SUN");
                return keyFactory.generatePublic(pubKeySpec);
            } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeySpecException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public PrivateKey getPrivateKey() {
        if (this.privateKey != null) {
            X509EncodedKeySpec privKeySpec = new X509EncodedKeySpec(this.privateKey);
            try {
                KeyFactory keyFactory = KeyFactory.getInstance("DSA", "SUN");
                return keyFactory.generatePrivate(privKeySpec);
            } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeySpecException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void saveCurrentData() {
        this.activeLanguage = Lang.getActiveLang();
        this.activeTheme = Csstype.getActiveTheme();
        this.activeClientScreen = ClientScreentype.getActiveScreenType();
        this.events = ((Controller) MainHandler.getPrimaryStageHandler().
                getScene(Scenetype.EVENTMANAGER).getController()).getEvents();
        this.archivedEvents = ((Controller) MainHandler.getPrimaryStageHandler().
                getScene(Scenetype.ARCHIVE).getController()).getEvents();
    }
}