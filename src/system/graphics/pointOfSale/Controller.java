package system.graphics.pointOfSale;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import system.data.Event;
import system.data.Lang;
import system.data.Word;
import system.graphics.common.AbstractController;
import system.graphics.common.Csstype;
import system.graphics.common.Scenetype;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Müügipunkti controller
 */
public class Controller extends AbstractController {
    @FXML
    protected Text pointofsale;
    @FXML
    protected Text name;
    @FXML
    protected Text datetime;
    @FXML
    protected Text seats;
    @FXML
    protected VBox eventsVBox;
    @FXML
    protected ImageView qrcode;
    @FXML
    protected Text total;
    @FXML
    protected Text totalcost;
    @FXML
    protected Button back;
    @FXML
    protected Button checkout;

    private Event event;
    private int seatsLeft;
    private double cost = 0.0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setLanguage();
    }

    private void init() {
        this.name.setText(this.event.getName());
        this.setDatetime();
        this.seatsLeft = this.event.getMaxSeats();
        this.updateSeatsLeft();
        this.updateTotal();
        for (String ticket : this.event.getTickets().keySet()) {
            this.eventsVBox.getChildren().add(new Ticket(this, ticket, this.event));
        }
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
            this.createQrCode(ticketdata);
            this.checkout.setText(Word.NEW.toString());
            System.out.println(this.event);
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
        this.seats.setText(Word.SEATSLEFT.toString() + ": " +
                (this.seatsLeft == -1 ? Word.UNLIMITED.toString() : this.seatsLeft));
    }

    private void updateTotal() {
        this.totalcost.setText(String.format("%.2f", this.cost) + " " + Lang.getActiveLang().getCurrency());
    }

    private void createQrCode(ArrayList<String> ticketdata) {
        StringBuilder tekst = new StringBuilder(
                this.event.getName() + "\n" +
                        this.event.getFormattedDate() + " " + this.event.getTime() + "\n");
        ticketdata.forEach(tekst::append);
        tekst.append(this.totalcost.getText()); // FIXME: 11.05.2016 currency symbol bugine?
        try {
            BitMatrix bytematrix = new QRCodeWriter().encode(tekst.toString(),
                    BarcodeFormat.QR_CODE, (int) this.qrcode.getFitWidth(), (int) this.qrcode.getFitHeight());
            Canvas canvas = new Canvas((int) this.qrcode.getFitWidth(), (int) this.qrcode.getFitHeight());
            GraphicsContext gc = canvas.getGraphicsContext2D();
            if (Csstype.getActiveTheme().equals(Csstype.DARK)) {
                gc.setFill(Color.valueOf("262626"));
                gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                gc.setFill(Color.BLACK);
            } else {
                gc.setFill(Color.valueOf("e6e6e5"));
                gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                gc.setFill(Color.valueOf("5b5c5c"));
            }
            for (int i = 0; i < canvas.getHeight(); i++) {
                for (int j = 0; j < canvas.getWidth(); j++) {
                    if (bytematrix.get(i, j)) {
                        gc.fillRect(i, j, 1, 1);
                    }
                }
            }
            this.qrcode.setImage(canvas.snapshot(null, null));
        } catch (WriterException ignored) {
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
