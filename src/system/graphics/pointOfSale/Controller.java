package system.graphics.pointOfSale;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import system.data.Event;
import system.data.Word;
import system.graphics.common.AbstractController;
import system.graphics.common.Scenetype;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Müügipunkti controller
 */
public class Controller extends AbstractController {
    public Text pointofsale;
    public VBox eventsVBox;
    public Text total;
    public Button back;
    public Button checkout;

    private Event event;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setLanguage();
    }

    private void init() {
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
        System.out.println(this.event);
    }

    public Event getEvent() {
        return this.event;
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
                if (!this.event.equals(object)) {
                    this.scene.getStageHandler().replaceScene(Scenetype.POINTOFSALE);
                    this.scene.getStageHandler().getScene(Scenetype.POINTOFSALE).
                            getController().prepareToDisplay(object);
                }
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
