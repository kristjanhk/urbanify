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
        System.out.println(this.toString()); // TODO: 4.05.2016 remove
    }
    
    public boolean isFloorPlanReady() {
        return true; // FIXME: 2.05.2016 
    }

    private void resetCreator() {
        this.name = null;
        this.tickets.clear();
        this.date = null;
        this.time = null;
        this.seatingType = null;
        this.maxSeats = null;
    }

    public String getName() {
        return this.name;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public String getTime() {
        return this.time;
    }

    public HashMap<String, Double> getTickets() {
        return this.tickets;
    }

    public double getTicketPrice(String ticketName) {
        return this.tickets.get(ticketName);
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