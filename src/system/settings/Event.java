package system.settings;

import java.util.HashMap;

public class Event {
    private String name;
    private HashMap<String, Double> tickets = new HashMap<>();
    private String date; // TODO: 1.05.2016 date type?
    private String time; // TODO: 1.05.2016 time type? 
    private String seatingType = null;
    private int maxSeats;
    
    public Event() {}

    public void setName(String name) {
        this.name = name;
    }

    public void addTicket(String ticketType, double price) {
        this.tickets.put(ticketType, price);
    }
    
    public void removeTicket(String ticketType) {
        this.tickets.remove(ticketType);
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSeatingType() {
        return this.seatingType;
    }

    public void setSeatingType(String seatingType) {
        this.seatingType = seatingType;
    }

    public void setMaxSeats(int maxSeats) {
        this.maxSeats = maxSeats;
    }
    
    public void readyEvent() {
        // TODO: 1.05.2016 pass event to eventmanager etc.. 
    }
}
