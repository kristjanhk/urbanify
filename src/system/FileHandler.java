package system;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import system.graphics.common.Csstype;
import system.data.JsonFile;
import system.data.Lang;
import system.graphics.common.ClientScreen;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * Failihaldaja
 * Tegeleb failist laadimisega ja salvestamisega
 * andmed.bin faili salvestatakse andmed.json faili asukoht, seda kuvatakse ka seadete all
 * andmed.json faili salvestatakse vajalikud andmed
 * Kasutatakse Google Gson teeki https://github.com/google/gson
 */
public class FileHandler {
    private Gson gson;
    private JsonFile jsonFile;
    private String jsonPath;

    public FileHandler() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.loadData();
    }

    public String getPath() {
        return jsonPath;
    }

    public JsonFile getData() {
        return this.jsonFile;
    }

    private void setActiveConstants(Csstype csstype, Lang lang, ClientScreen clientScreen) {
        Csstype.setActiveTheme(csstype);
        Lang.setActiveLang(lang);
        ClientScreen.setActiveScreenType(clientScreen);
    }

    public void saveData() {
        this.setJsonPath();
        this.jsonFile.saveCurrentData();
        try (FileWriter fileWriter = new FileWriter(this.jsonPath + "\\andmed.json")) {
            fileWriter.write(this.gson.toJson(this.jsonFile));
        } catch (IOException ignored) {}
    }

    private void loadData() {
        this.jsonPath = loadJsonPath();
        try (BufferedReader br = new BufferedReader(new FileReader(this.jsonPath + "\\andmed.json"))) {
            this.jsonFile = this.gson.fromJson(br, JsonFile.class);
            this.setActiveConstants(this.jsonFile.getActiveTheme(), this.jsonFile.getActiveLanguage(),
                    this.jsonFile.getActiveClientScreen());
        } catch (IOException ignored) {
            this.resetData();
        }
    }

    public boolean moveJson(String path) {
        try {
            Files.move(new File(this.jsonPath + "\\andmed.json").toPath(),
                    new File(path + "\\andmed.json").toPath(), StandardCopyOption.REPLACE_EXISTING);
            this.jsonPath = path;
            return true;
        } catch (IOException ignored) {
            return false;
        }
    }

    private void resetData() {
        this.jsonFile = new JsonFile();
        this.setActiveConstants(Csstype.DARK, Lang.ENGLISH, ClientScreen.PRIMARY);
    }

    private String loadJsonPath() {
        try (DataInputStream dis = new DataInputStream(new FileInputStream("andmed.bin"))) {
            return dis.readUTF();
        } catch (IOException ignored) {
            return System.getProperty("user.dir");
        }
    }

    private void setJsonPath() {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream("andmed.bin"))) {
            dos.writeUTF(this.jsonPath);
        } catch (IOException e) {
            System.exit(1);
        }
    }
}
