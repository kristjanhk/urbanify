package system;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import system.graphics.common.Csstype;
import system.data.JsonFile;
import system.data.Lang;

import java.io.*;

public class FileHandler {
    private Gson gson;
    private JsonFile jsonFile;
    private String jsonPath;

    public FileHandler() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.loadData();
    }

    public void setPath(String path) {
        this.jsonPath = path;
    }

    public String getPath() {
        return jsonPath;
    }

    public JsonFile getData() {
        return this.jsonFile;
    }

    private void setActiveConstants(Csstype csstype, Lang lang) {
        Csstype.setActiveTheme(csstype);
        Lang.setActiveLang(lang);
        //this.jsonFile.saveCurrentData();
    }

    public void saveData() {
        this.setJsonPath();
        this.jsonFile.saveCurrentData();
        try (FileWriter fileWriter = new FileWriter(this.jsonPath + "\\andmed.json")) {
            fileWriter.write(this.gson.toJson(this.jsonFile));
        } catch (IOException e) {
            e.printStackTrace();
            // TODO: 24.04.2016 catch incorrect path, etc??
        }
    }

    private void loadData() {
        this.jsonPath = loadJsonPath();
        try (BufferedReader br = new BufferedReader(new FileReader(this.jsonPath + "\\andmed.json"))) {
            this.jsonFile = this.gson.fromJson(br, JsonFile.class);
            this.setActiveConstants(this.jsonFile.getActiveTheme(), this.jsonFile.getActiveLanguage());
        } catch (IOException ignored) {
            this.resetData();
        }
    }

    private void resetData() {
        this.jsonFile = new JsonFile();
        this.jsonFile.init();
        this.setActiveConstants(Csstype.DARK, Lang.ENGLISH);
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
            // TODO: 29.04.2016 handle unable to write file
        }
    }
}
