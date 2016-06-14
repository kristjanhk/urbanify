package system.data;

import system.MainHandler;
import system.graphics.common.Csstype;
import system.graphics.common.FloorPlanPane;
import system.graphics.common.Scenetype;
import system.graphics.common.ClientScreen;
import system.graphics.eventManager.Controller;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
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
    private ClientScreen activeClientScreen;
    private String companyName;
    private HashSet<Event> events;
    private HashSet<Event> archivedEvents;
    private HashMap<String, HashMap<FloorPlanPane.Property, Object>> savedFloorPlans;
    private Integer globalIndex;
    private byte[] secret;

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

    public ClientScreen getActiveClientScreen() {
        if (this.activeClientScreen != null) {
            return this.activeClientScreen;
        }
        ClientScreen.setActiveScreenType(ClientScreen.PRIMARY);
        return ClientScreen.PRIMARY;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public Integer getGlobalIndex() {
        if (this.globalIndex == null) {
            this.globalIndex = 1;
        }
        return this.globalIndex++;
    }

    public byte[] getKeyBytes() {
        if (this.secret != null) {
            return this.secret;
        } else {
            byte[] salt = genSalt();
            if (salt != null) {
                try {
                    SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
                    PBEKeySpec spec = new PBEKeySpec(genString().toCharArray(), salt, 65536, 128);
                    this.secret = skf.generateSecret(spec).getEncoded();
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    e.printStackTrace();
                }
            }
            return this.getKeyBytes();
        }
    }

    public void saveCurrentData() {
        this.activeLanguage = Lang.getActiveLang();
        this.activeTheme = Csstype.getActiveTheme();
        this.activeClientScreen = ClientScreen.getActiveScreenType();
        this.events = ((Controller) MainHandler.getPrimaryStageHandler().
                getScene(Scenetype.EVENTMANAGER).getController()).getEvents();
        this.archivedEvents = ((Controller) MainHandler.getPrimaryStageHandler().
                getScene(Scenetype.ARCHIVE).getController()).getEvents();
    }

    private static byte[] genSalt() {
        try {
            byte[] salt = new byte[20];
            SecureRandom.getInstance("SHA1PRNG").nextBytes(salt);
            return salt;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String genString() {
        return new BigInteger(130, new SecureRandom()).toString(32);
    }
}