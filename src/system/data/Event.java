package system.data;

import javafx.scene.Node;
import system.MainHandler;
import system.graphics.common.FloorPlanPane;
import system.graphics.common.Scenetype;
import system.graphics.eventCreator.Ticket;
import system.graphics.eventManager.Controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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
    private String currency;
    private LocalDate date;
    private String time;
    private int maxSeats;
    private boolean active = false;
    private HashMap<FloorPlanPane.Property, Object> floorPlan;

    public void readyCreator(String name, List<Node> tickets, String currency, LocalDate date, String time,
                             String maxSeats) {
        this.resetCreator(!this.isActive());
        this.name = name;
        this.saveTickets(tickets);
        this.currency = currency;
        this.date = date;
        this.time = time;
        if (!maxSeats.equals("")) {
            this.maxSeats = Integer.parseInt(maxSeats) - this.getTotalTicketAmount();
        } else {
            this.maxSeats = -1;
        }
    }

    private void saveTickets(List<Node> tickets) {
        HashMap<String, ArrayList<Double>> saveableTickets = new HashMap<>();
        tickets.stream().filter(node -> node instanceof Ticket).forEach(node -> {
            ArrayList<Double> ticketdata = new ArrayList<>();
            ticketdata.add(((Ticket) node).getPrice());
            ticketdata.add((double) this.getTicketAmount(((Ticket) node).getType()));
            saveableTickets.put(((Ticket) node).getType(), ticketdata);
        });
        this.tickets = saveableTickets;
    }

    private void resetCreator(boolean all) {
        this.name = null;
        if (all) {
            this.tickets.clear();
            this.maxSeats = -1;
        }
        this.currency = null;
        this.date = null;
        this.time = null;
    }

    public String getName() {
        return this.name;
    }

    public String getFormattedDate() {
        return this.date.format(
                DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(Lang.getActiveLang().getLocale()));
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

    public int getTicketAmount(String ticketName) {
        if (this.tickets.get(ticketName) != null && this.tickets.get(ticketName).get(1) != null) {
            return this.tickets.get(ticketName).get(1).intValue();
        }
        return 0;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void addTicketAmount(String tickettype, double amount) {
        this.tickets.get(tickettype).set(1, this.tickets.get(tickettype).get(1) + amount);
    }

    public int getTotalTicketAmount() {
        int soldTickets = 0;
        for (String ticket : this.tickets.keySet()) {
            soldTickets += this.getTicketAmount(ticket);
        }
        return soldTickets;
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

    public void setActive() {
        this.active = true;
    }

    public HashMap<FloorPlanPane.Property, Object> getFloorPlan() {
        return floorPlan;
    }

    public void setFloorPlan(HashMap<FloorPlanPane.Property, Object> floorPlan) {
        this.floorPlan = floorPlan;
    }

    public void sendToArchive() {
        this.active = false;
        ((Controller) MainHandler.getStageHandler().
                getScene(Scenetype.EVENTMANAGER).getController()).removeEventLine(this);
        ((Controller) MainHandler.getStageHandler().getScene(Scenetype.ARCHIVE).getController()).addEventLine(this);
    }

    @Override
    public String toString() {
        return "Event{" +
                "name='" + name + '\'' +
                ", tickets=" + tickets +
                ", currency='" + currency + '\'' +
                ", date=" + date +
                ", time='" + time + '\'' +
                ", maxSeats=" + maxSeats +
                ", active=" + active +
                ", floorPlan=" + floorPlan +
                '}';
    }
}
