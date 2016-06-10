package system.graphics.pointOfSale;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import system.MainHandler;
import system.data.Event;
import system.data.Word;
import system.graphics.common.AbstractController;
import system.graphics.common.ClientScreen;
import system.graphics.common.FloorPlanPane;
import system.graphics.common.Scenetype;
import system.graphics.floorPlanner.Seat;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Müügipunkti controller
 */
public class Controller extends AbstractController {
    @FXML protected Text pointofsale;
    @FXML protected Text name;
    @FXML protected Text datetime;
    @FXML protected HBox seatsHbox;
    @FXML protected Text seats;
    @FXML protected VBox ticketsVbox;
    @FXML protected HBox rightContent;
    @FXML protected Text total;
    @FXML protected Text totalcost;
    @FXML protected Button back;
    @FXML protected Button checkout;

    private Event event;
    private SimpleIntegerProperty seatsLeft = new SimpleIntegerProperty();
    private double cost = 0.0;
    private FloorPlanPane floorPlan;
    private system.graphics.ticketInfo.Controller clientController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setLanguage();
    }

    private void init() {
        this.name.setText(this.event.getName());
        this.setDatetime();
        for (String ticket : this.event.getTickets().keySet()) {
            this.ticketsVbox.getChildren().add(new Ticket(this, ticket, this.event));
        }
        if (this.event.getFloorPlan() != null) {
            this.floorPlan = new FloorPlanPane(this);
            this.rightContent.getChildren().add(this.floorPlan);
            this.floorPlan.loadFloorPlan(this.event);
            this.seatsLeft.set(0);
        } else {
            this.seatsLeft.set(this.event.getMaxSeats());
        }
        this.updateTotal();
        this.validateCheckoutButton();
        this.updateSeatsLeft();
        this.resetClientScreen();
    }

    @FXML
    protected void doBack() {
        this.clientController.getScene().getStageHandler().switchSceneTo(Scenetype.CLIENTLOGO);
        this.scene.getStageHandler().switchSceneTo(Scenetype.REPORT, this.event);
    }

    @FXML
    protected void doCheckout() {
        if (this.checkout.getText().equals(Word.CHECKOUT.toString())) {
            this.event.setMaxSeats(this.seatsLeft.get());
            ArrayList<String> ticketdata = new ArrayList<>();
            this.ticketsVbox.getChildren().forEach(node -> {
                ticketdata.add(((Ticket) node).getCurrentData());
                ((Ticket) node).save();
                ((Ticket) node).disableButtons(false);
            });
            if (this.floorPlan != null) {
                this.seatsHbox.setVisible(false);
                this.floorPlan.save(null, this.floorPlan.getSavedFloorPlanImageTypeString(this.event), this.event);
                this.checkout.setDisable(FloorPlanPane.getSeatsLeft(this.event) == 0);
            } else {
                this.checkout.setDisable(this.seatsLeft.get() == 0);
            }
            this.clientController.createQrCode(ticketdata, this.totalcost.getText());
            this.checkout.setText(Word.NEW.toString());
            if (!ClientScreen.isSecondScreenEnabled()) {
                this.clientController.getScene().getStageHandler().showStage();
            }
        } else {
            this.scene.getStageHandler().switchSceneTo(Scenetype.POINTOFSALE, this.event);
        }
    }

    public Event getEvent() {
        return this.event;
    }

    public SimpleIntegerProperty getSeatsLeftProperty() {
        return this.seatsLeft;
    }

    public void cancel() {
        this.scene.getStageHandler().switchSceneTo(Scenetype.REPORT, this.event);
    }

    private void resetClientScreen() {
        MainHandler.getSecondaryStageHandler().replaceScene(Scenetype.TICKETINFO);
        MainHandler.getSecondaryStageHandler().switchSceneTo(Scenetype.TICKETINFO, this);
        this.clientController.init();
        if (this.floorPlan != null) {
            FloorPlanPane clientFloorPlan = new FloorPlanPane(this);
            this.clientController.setFloorPlan(clientFloorPlan);
        }
    }

    public void setClientScreenController(system.graphics.ticketInfo.Controller controller) {
        this.clientController = controller;
    }

    public system.graphics.ticketInfo.Controller getClientController() {
        return this.clientController;
    }

    public boolean occupySeat(double ticketprice) {
        if (this.seatsLeft.get() == -1) {
            this.cost += ticketprice;
            this.updateTotal();
            return true;
        } else if (this.seatsLeft.get() > 0) {
            this.seatsLeft.set(this.seatsLeft.get() - 1);
            this.updateSeatsLeft();
            this.cost += ticketprice;
            this.updateTotal();
            return true;
        }
        return false;
    }

    public void freeSeat(double ticketprice) {
        if (this.seatsLeft.get() != -1) {
            this.seatsLeft.set(this.seatsLeft.get() + 1);
            this.updateSeatsLeft();
        }
        this.cost -= ticketprice;
        this.updateTotal();
        this.validateCheckoutButton();
    }

    public boolean addSeat(ArrayList<Integer> coordinates) {
        if (!this.clientController.isLocked()) {
            this.seatsLeft.set(this.seatsLeft.get() + 1);
            this.updateSeatsLeft();
            for (Node node : this.ticketsVbox.getChildren()) {
                ((Ticket) node).enableAddTicketButton();
            }
            this.clientController.getFloorPlan().setSeatStyle(Seat.Seattype.OCCUPIED, coordinates);
            return true;
        }
        return false;
    }

    public boolean removeSeat(ArrayList<Integer> coordinates) {
        if (this.seatsLeft.get() > 0) {
            this.seatsLeft.set(this.seatsLeft.get() - 1);
            this.updateSeatsLeft();
            this.clientController.getFloorPlan().setSeatStyle(Seat.Seattype.AVAILABLE, coordinates);
            return true;
        }
        return false;
    }

    private void setDatetime() {
        this.datetime.setText(this.event.getFormattedDate() + " " + this.event.getTime());
    }

    private void updateSeatsLeft() {
        if (this.floorPlan != null) {
            this.seats.setText(Word.TICKETSTOSELECT.toString() + ": " + this.seatsLeft.intValue());
        } else {
            this.seats.setText(Word.SEATSLEFT.toString() + ": " +
                    (this.seatsLeft.get() == -1 ? Word.UNLIMITED.toString() : this.seatsLeft.intValue()));
        }

    }

    private void updateTotal() {
        this.totalcost.setText(String.format("%.2f", this.cost) + " " + this.event.getCurrency());
    }

    protected void validateCheckoutButton() {
        boolean valid = !(this.cost == 0.0);
        if (this.floorPlan != null && this.seatsLeft.get() > 0) {
            valid = false;
        }
        this.checkout.setDisable(!valid);
    }

    /**
     * Meetod, mis valmistab stseeni ette enne selle kuvamist
     * Kontrollib, kas vastava üritusega stseen on valmis, vastasel juhul loob uue stseeni antud üritusega
     *
     * @param object suvaline object, kontrollitakse, kas see on üritus
     */
    @Override
    public <T> void prepareToDisplay(T object) {
        if (object instanceof Event) {
            if (this.event != null) {
                this.scene.getStageHandler().replaceScene(Scenetype.POINTOFSALE);
                this.scene.getStageHandler().getScene(Scenetype.POINTOFSALE).
                        getController().prepareToDisplay(object);
            } else {
                this.event = ((Event) object);
                this.init();
            }
        }
    }

    @Override
    public void setLanguage() {
        this.pointofsale.setText(Word.POINTOFSALE.toString());
        this.total.setText(Word.TOTAL.toString());
        this.back.setText(Word.BACK.toString());
        this.checkout.setText(Word.CHECKOUT.toString());
    }
}
