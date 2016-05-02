package system.data;

import javafx.scene.Node;
import system.graphics.eventCreator.Ticket;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class Event {
    private String name;
    private HashMap<String, Double> tickets = new HashMap<>();
    private LocalDate date;
    private String time;
    private String seatingType;
    private String maxSeats;
    
    public Event() {}

    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public void readyCreator(String name, List<Node> tickets, LocalDate date, String time,
                             String seatingType, String maxSeats) {
        this.resetCreator();
        this.name = name;
        tickets.stream().filter(node -> node instanceof Ticket).
                forEach(node -> this.tickets.put(((Ticket) node).getType(), ((Ticket) node).getPrice()));
        this.date = date;
        this.time = time;
        this.seatingType = seatingType;
        this.maxSeats = maxSeats;
        System.out.println(this.toString());
    }
    
    public boolean isFloorPlanReady() {
        return true; // FIXME: 2.05.2016 
    }
    
    public void readyEvent() {
        // TODO: 1.05.2016 pass event to eventmanager etc.. 
    }

    private void resetCreator() {
        this.name = null;
        this.tickets.clear();
        this.date = null;
        this.time = null;
        this.seatingType = null;
        this.maxSeats = null;
    }

    @Override
    public String toString() {
        return "Event{" +
                "name='" + name + '\'' +
                ", tickets=" + tickets +
                ", date=" + date +
                ", time='" + time + '\'' +
                ", seatingType='" + seatingType + '\'' +
                ", maxSeats=" + maxSeats +
                '}';
    }
}
