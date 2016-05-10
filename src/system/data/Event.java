package system.data;

import javafx.scene.Node;
import system.graphics.eventCreator.Ticket;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Üritus
 * Hoiab endas andmeid ühe ürituse kohta
 * Konstruktor on tühi, et oleks võimalik isendivälju lähtestada ilma uut isendit loomata
 */
public class Event {
    private String name;
    private HashMap<String, ArrayList<Double>> tickets = new HashMap<>();
    private LocalDate date;
    private String time;
    private String seatingType;
    private int maxSeats;
    private boolean active = false;

    public Event() {}

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void readyCreator(String name, List<Node> tickets, LocalDate date, String time,
                             String seatingType, String maxSeats) {
        this.resetCreator();
        this.name = name;
        tickets.stream().filter(node -> node instanceof Ticket).forEach(node -> {
            ArrayList<Double> ticketdata = new ArrayList<>();
            ticketdata.add(((Ticket) node).getPrice());
            ticketdata.add(0.0);
            this.tickets.put(((Ticket) node).getType(), ticketdata);
        });
        this.date = date;
        this.time = time;
        this.seatingType = seatingType;
        this.maxSeats = Integer.parseInt(maxSeats);
        if (this.maxSeats == 0) {
            this.maxSeats = -1;
        }
        this.active = true;
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
        this.maxSeats = -1;
        this.active = false;
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

    public HashMap<String, ArrayList<Double>> getTickets() {
        return this.tickets;
    }

    public double getTicketPrice(String ticketName) {
        return this.tickets.get(ticketName).get(0);
    }

    public double getTicketAmount(String ticketName) {
        return this.tickets.get(ticketName).get(1);
    }

    public void addTicketAmount(String tickettype, double amount) {
        this.tickets.get(tickettype).set(1, this.tickets.get(tickettype).get(1) + amount);
    }

    public int getMaxSeats() {
        return this.maxSeats;
    }

    public void setMaxSeats(int maxSeats) {
        this.maxSeats = maxSeats;
    }

    public boolean isActive() {
        return this.active;
    }

    public void archive() {
        this.active = false;
    }

    @Override
    public String toString() {
        return "Event{" +
                "name='" + name + '\'' +
                ", tickets=" + tickets +
                ", date=" + date +
                ", time='" + time + '\'' +
                ", seatingType='" + seatingType + '\'' +
                ", maxSeats='" + maxSeats + '\'' +
                ", active=" + active +
                '}';
    }
}
