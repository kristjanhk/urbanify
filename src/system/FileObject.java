package system;

import java.util.ArrayList;

public class FileObject {
    private String name;
    private int number;
    private ArrayList<String> list;

    public FileObject(String name, int number, ArrayList<String> list) {
        this.name = name;
        this.number = number;
        this.list = list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "FileObject{" +
                "name='" + name + '\'' +
                ", number=" + number +
                ", list=" + list +
                '}';
    }
}
