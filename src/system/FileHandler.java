package system;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;

public class FileHandler {
    private FileObject fileObject;
    private Gson gson;

    public FileHandler() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Tere1");
        list.add("Tere2");
        this.fileObject = new FileObject("nimi", 100, list);
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public FileObject getFileObject() {
        return fileObject;
    }

    public void setFileObject(FileObject fileObject) {
        this.fileObject = fileObject;
    }

    public Gson getGson() {
        return gson;
    }

    public void saveFile() {
        String json = this.gson.toJson(this.fileObject);
        try (FileWriter fileWriter = new FileWriter("test.json")) {
            fileWriter.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(json);
    }

    public void loadFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("test.json"))) {
            FileObject fileObject = this.gson.fromJson(br, FileObject.class);
            System.out.println(fileObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
