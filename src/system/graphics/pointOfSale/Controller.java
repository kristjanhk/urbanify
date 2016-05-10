package system.graphics.pointOfSale;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import system.data.Event;
import system.data.Lang;
import system.data.Word;
import system.graphics.common.AbstractController;
import system.graphics.common.Scenetype;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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
    @FXML protected ImageView qrcode;
    @FXML protected Text total;
    @FXML protected Text totalcost;
    @FXML protected Button back;
    @FXML protected Button checkout;

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

    public void selectSeat(int id) {

    }

    @FXML
    protected void doBack() {
        this.scene.getStageHandler().switchSceneTo(Scenetype.REPORT, this.event);
    }

    @FXML
    protected void doCheckout() {
        if (this.checkout.getText().equals(Word.CHECKOUT.toString())) {
            this.event.setMaxSeats(this.seatsLeft);
            this.eventsVBox.getChildren().forEach(node -> {
                ((Ticket) node).save();
                ((Ticket) node).disableButtons();
            });
            this.createQrCode();
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
        if (this.seatsLeft == -1 || this.seatsLeft > 0) {
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
            this.cost -= ticketprice;
            this.updateTotal();
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

    private void updateTotal() {
        this.totalcost.setText(String.format("%.2f", this.cost) + " " + Lang.getActiveLang().getCurrency());
    }

    private void createQrCode() {
        String tekst = this.event.getName() + " " + this.event.getTime();
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        int width = (int) this.qrcode.getFitWidth();
        int height = (int) this.qrcode.getFitHeight();
        try {
            BitMatrix bytematrix = qrCodeWriter.encode(tekst, BarcodeFormat.QR_CODE, width, height);
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            bufferedImage.createGraphics();
            Graphics2D graphics2D = (Graphics2D) bufferedImage.getGraphics();
            graphics2D.setColor(Color.WHITE);
            graphics2D.fillRect(0, 0, width, height);
            graphics2D.setColor(Color.black);
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (bytematrix.get(i, j)) {
                        graphics2D.fillRect(i, j, 1, 1);
                    }
                }
            }
            this.qrcode.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
        } catch (WriterException e) {
            e.printStackTrace();
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
