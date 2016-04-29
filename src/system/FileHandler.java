package system;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import system.settings.JsonFile;

import java.io.*;

public class FileHandler {
    private Gson gson;
    private JsonFile jsonFile;
    private String jsonPath;

    public FileHandler() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.loadData();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                MainHandler.getFileHandler().saveData();

            }
        });
    }

    private String loadJsonPath() {
        try (DataInputStream dis = new DataInputStream(new FileInputStream("andmed.bin"))) {
            return dis.readUTF();
        } catch (IOException ignored) {
            return System.getProperty("user.dir");
        }
    }

    private void setJsonPath(String path) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream("andmed.bin"))) {
            dos.writeUTF(path);
        } catch (IOException e) {
            System.exit(1);
            // TODO: 29.04.2016 handle unable to write file
        }
    }

    public void setPath(String path) {
        this.jsonPath = path;
    }

    public JsonFile getData() {
        return this.jsonFile;
    }

    private void resetData() {
        this.jsonFile = new JsonFile();
        this.saveData();
    }

    public void saveData() {
        this.saveData(this.jsonFile);
    }

    private void saveData(JsonFile jsonFile) {
        try (FileWriter fileWriter = new FileWriter(this.jsonPath + "\\andmed.json")) {
            fileWriter.write(this.gson.toJson(jsonFile));
        } catch (IOException e) {
            e.printStackTrace();
            // TODO: 24.04.2016 catch incorrect path, etc??
        }
    }

    private void loadData() {
        this.jsonPath = loadJsonPath();
        try (BufferedReader br = new BufferedReader(new FileReader(this.jsonPath + "\\andmed.json"))) {
            this.jsonFile = this.gson.fromJson(br, JsonFile.class);
        } catch (IOException ignored) {
            this.resetData();
        }
    }
}
