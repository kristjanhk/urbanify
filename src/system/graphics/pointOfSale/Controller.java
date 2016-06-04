package system.graphics.pointOfSale;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import system.MainHandler;
import system.data.Event;
import system.data.Lang;
import system.data.Word;
import system.graphics.common.AbstractController;
import system.graphics.common.FloorPlanPane;
import system.graphics.common.Scenetype;

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
    @FXML protected Text seats;
    @FXML protected VBox eventsVBox;
    @FXML protected HBox rightContent;
    @FXML protected Text total;
    @FXML protected Text totalcost;
    @FXML protected Button back;
    @FXML protected Button checkout;

    private Event event;
    private int seatsLeft;
    private double cost = 0.0;
    private FloorPlanPane floorPlan;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setLanguage();
    }

    private void init() {
        this.name.setText(this.event.getName());
        this.setDatetime();
        for (String ticket : this.event.getTickets().keySet()) {
            this.eventsVBox.getChildren().add(new Ticket(this, ticket, this.event));
        }
        if (this.event.getFloorPlan() != null) {
            this.floorPlan = new FloorPlanPane(this);
            this.rightContent.getChildren().add(this.floorPlan);
            this.floorPlan.loadFloorPlan(this.event);
            this.seatsLeft = 0;
        } else {
            this.seatsLeft = this.event.getMaxSeats();
        }
        this.updateTotal();
        this.validateCheckoutButton();
        this.updateSeatsLeft();
    }

    @FXML
    protected void doBack() {
        this.scene.getStageHandler().switchSceneTo(Scenetype.REPORT, this.event);
    }

    @FXML
    protected void doCheckout() {
        if (this.checkout.getText().equals(Word.CHECKOUT.toString())) {
            this.event.setMaxSeats(this.seatsLeft);
            ArrayList<String> ticketdata = new ArrayList<>();
            this.eventsVBox.getChildren().forEach(node -> {
                ticketdata.add(((Ticket) node).getCurrentData());
                ((Ticket) node).save();
                ((Ticket) node).disableButtons();
            });
            if (this.floorPlan != null) {
                this.floorPlan.save(null, this.floorPlan.getSavedFloorPlanImageTypeString(this.event), this.event);
            }
            this.createQrCode(ticketdata);
            this.checkout.setText(Word.NEW.toString());
        } else {
            this.scene.getStageHandler().switchSceneTo(Scenetype.POINTOFSALE, this.event);
        }
    }

    public Event getEvent() {
        return this.event;
    }

    public boolean occupySeat(double ticketprice) {
        if (this.seatsLeft == -1) {
            this.cost += ticketprice;
            this.updateTotal();
            return true;
        } else if (this.seatsLeft > 0) {
            this.seatsLeft--;
            this.updateSeatsLeft();
            this.cost += ticketprice;
            this.updateTotal();
            return true;
        }
        return false;
    }

    public void freeSeat(double ticketprice) {
        if (this.seatsLeft != -1) {
            this.seatsLeft++;
            this.updateSeatsLeft();
        }
        this.cost -= ticketprice;
        this.updateTotal();
        this.validateCheckoutButton();
    }

    public void addSeat() {
        this.seatsLeft++;
        this.updateSeatsLeft();
    }

    public boolean removeSeat() {
        if (this.seatsLeft > 0) {
            this.seatsLeft--;
            this.updateSeatsLeft();
            return true;
        }
        return false;
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

    private void setDatetime() {
        this.datetime.setText(this.event.getFormattedDate() + " " + this.event.getTime());
    }

    private void updateSeatsLeft() {
        if (this.floorPlan != null) {
            this.seats.setText(Word.TICKETSTOSELECT.toString() + ": " + this.seatsLeft);
        } else {
            this.seats.setText(Word.SEATSLEFT.toString() + ": " +
                    (this.seatsLeft == -1 ? Word.UNLIMITED.toString() : this.seatsLeft));
        }

    }

    private void updateTotal() {
        this.totalcost.setText(String.format("%.2f", this.cost) + " " + Lang.getActiveLang().getCurrency());
    }

    protected void validateCheckoutButton() {
        boolean valid = !(this.cost == 0.0);
        if (this.floorPlan != null && this.seatsLeft > 0) {
            valid = false;
        }
        this.checkout.setDisable(!valid);
    }

    /**
     * Meetod, mis loob Zxing teegiga qr koodi
     * https://github.com/zxing/zxing
     * Genereerib maatriksi andmetest, mis joonistatakse canvasega pildile
     *
     * @param ticketdata list piletite andmetest, mis on juba vajalikul kujul
     */
    private void createQrCode(ArrayList<String> ticketdata) {
        this.rightContent.getChildren().clear();
        StringBuilder tekst = new StringBuilder(this.event.getName() + "\n" + this.event.getFormattedDate() + " " +
                this.event.getTime() + "\n");
        ticketdata.forEach(tekst::append);
        tekst.append(this.totalcost.getText()); // FIXME: 11.05.2016 currency symbol bugine?
        ImageView qrcode = MainHandler.createQrCode(tekst.toString());
        this.rightContent.getChildren().add(qrcode);
    }

    @Override
    public void setLanguage() {
        this.pointofsale.setText(Word.POINTOFSALE.toString());
        this.total.setText(Word.TOTAL.toString());
        this.back.setText(Word.BACK.toString());
        this.checkout.setText(Word.CHECKOUT.toString());
    }
}
