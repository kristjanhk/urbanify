package system;

import system.data.JsonFile;

public class EventHandler {
    private JsonFile data;

    public EventHandler() {
        this.data = MainHandler.getFileHandler().getData();
        // TODO: 1.05.2016 load in events from filehandler
    }
}
