package system.data;

import system.MainHandler;
import system.graphics.common.Csstype;
import system.graphics.common.FloorPlanPane;
import system.graphics.common.Scenetype;
import system.graphics.common.ClientScreen;
import system.graphics.eventManager.Controller;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
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
    private ClientScreen activeClientScreen;
    private HashSet<Event> events;
    private HashSet<Event> archivedEvents;
    private HashMap<String, HashMap<FloorPlanPane.Property, Object>> savedFloorPlans;
    private Integer globalIndex;
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

    public ClientScreen getActiveClientScreen() {
        if (this.activeClientScreen != null) {
            return this.activeClientScreen;
        }
        ClientScreen.setActiveScreenType(ClientScreen.PRIMARY);
        return ClientScreen.PRIMARY;
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

    public void saveKeys(KeyPair keyPair) {
        this.publicKey = keyPair.getPublic().getEncoded();
        this.privateKey = keyPair.getPrivate().getEncoded();
    }

    public PublicKey getPublicKey() {
        if (this.publicKey != null) {
            X509EncodedKeySpec formatted_public_key = new X509EncodedKeySpec(this.publicKey);
            try {
                KeyFactory keyFactory = KeyFactory.getInstance("EC");
                return keyFactory.generatePublic(formatted_public_key);
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                e.printStackTrace();
            }
        } else {
            this.generateKeyPair();
            return this.getPublicKey();
        }
        return null;
    }

    public PrivateKey getPrivateKey() {
        if (this.privateKey != null) {
            PKCS8EncodedKeySpec formatted_private_key = new PKCS8EncodedKeySpec(this.privateKey);
            try {
                KeyFactory keyFactory = KeyFactory.getInstance("EC");
                return keyFactory.generatePrivate(formatted_private_key);
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                e.printStackTrace();
            }
        } else {
            this.generateKeyPair();
            return this.getPrivateKey();
        }
        return null;
    }

    private void generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            keyGen.initialize(256, random);
            KeyPair keyPair = keyGen.generateKeyPair();
            this.saveKeys(keyPair);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
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
}