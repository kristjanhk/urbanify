package system;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class FileHandler {
    private Gson gson;
    private JsonFile jsonFile;
    private String path;

    public FileHandler() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.path = "test.json";
        this.jsonFile = loadData();
    }

    public void setPath(String path) {
        this.path = path;
    }

    public JsonFile getData() {
        return this.jsonFile;
    }

    private JsonFile resetData() {
        JsonFile jsonFile = new JsonFile();
        jsonFile.createLanguages();
        return jsonFile;
    }

    public void saveData() {
        try (FileWriter fileWriter = new FileWriter(this.path)) {
            fileWriter.write(this.gson.toJson(this.jsonFile));
        } catch (IOException e) {
            e.printStackTrace();
            // TODO: 24.04.2016 catch incorrect path, etc??
        }
    }

    private JsonFile loadData() {
        try (BufferedReader br = new BufferedReader(new FileReader(this.path))) {
            return this.gson.fromJson(br, JsonFile.class);
        } catch (IOException ignored) {
            return resetData();
        }
    }
}
