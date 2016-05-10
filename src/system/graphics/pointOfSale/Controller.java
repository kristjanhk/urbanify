package system.graphics.pointOfSale;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import system.data.Event;
import system.data.Lang;
import system.data.Word;
import system.graphics.common.AbstractController;
import system.graphics.common.Scenetype;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;

/**
 * Müügipunkti controller
 */
public class Controller extends AbstractController {
    public Text pointofsale;
    public Text name;
    public Text datetime;
    public Text seats;
    public VBox eventsVBox;
    public Text total;
    public Button back;
    public Button checkout;

    private Event event;
    private int seatsLeft;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setLanguage();
    }

    private void init() {
        this.name.setText(this.event.getName());
        this.setDatetime();
        this.seatsLeft = this.event.getMaxSeats();
        this.updateSeatsLeft();
        for (String ticket : this.event.getTickets().keySet()) {
            this.eventsVBox.getChildren().add(new Ticket(this, ticket, this.event));
        }
    }

    public void selectSeat(int id) {

    }

    @FXML
    protected void doBack() {
        this.scene.getStageHandler().switchSceneTo(Scenetype.REPORT, this.event);
    }

    @FXML
    protected void doCheckout() {
        if (this.checkout.getText().equals(Word.CHECKOUT.toString())) {
            // TODO: 10.05.2016 generate qr code
            this.event.setMaxSeats(this.seatsLeft);
            this.eventsVBox.getChildren().forEach(node -> {
                ((Ticket) node).save();
                ((Ticket) node).disableButtons();
            });
            this.checkout.setText(Word.NEW.toString());
            System.out.println(this.event);
        } else {
            this.scene.getStageHandler().switchSceneTo(Scenetype.POINTOFSALE, this.event);
        }
    }

    public Event getEvent() {
        return this.event;
    }

    public boolean occupySeat() {
        if (this.seatsLeft == -1 || this.seatsLeft > 0) {
            this.seatsLeft--;
            this.updateSeatsLeft();
            return true;
        }
        return false;
    }

    public void freeSeat() {
        if (this.seatsLeft != -1) {
            this.seatsLeft++;
            this.updateSeatsLeft();
        }
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
        this.datetime.setText(this.event.getDate().format(
                DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(Lang.getActiveLang().getLocale())) +
                " " + this.event.getTime());
    }

    private void updateSeatsLeft() {
        this.seats.setText("seats left: " + this.seatsLeft);
    }

    @Override
    public void setLanguage() {
        this.pointofsale.setText(Word.POINTOFSALE.toString());
        this.total.setText(Word.TOTAL.toString());
        this.back.setText(Word.BACK.toString());
        this.checkout.setText(Word.CHECKOUT.toString());
    }
}
