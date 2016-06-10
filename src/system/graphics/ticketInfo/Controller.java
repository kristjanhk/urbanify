package system.graphics.ticketInfo;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import system.MainHandler;
import system.data.Word;
import system.graphics.common.AbstractController;
import system.graphics.common.ClientScreen;
import system.graphics.common.FloorPlanPane;
import system.graphics.report.Ticket;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller extends AbstractController {
    @FXML protected BorderPane borderPane;
    @FXML protected Text title;
    @FXML protected Text datetime;
    @FXML protected Text tickettype;
    @FXML protected Text cost;
    @FXML protected Text quantity;
    @FXML protected Text total;
    @FXML protected VBox ticketVBox;
    @FXML protected Text total2;
    @FXML protected Text totalQuantity;
    @FXML protected Text totalPrice;
    @FXML protected HBox rightContent;

    private system.graphics.pointOfSale.Controller parentController;
    private FloorPlanPane floorPlan;
    private boolean locked = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setLanguage();
    }

    public void init() {
        this.title.setText(this.parentController.getEvent().getName());
        this.setDatetime();
        if (!ClientScreen.isSecondScreenEnabled()) {
            this.setBackButton();
        }
    }

    /**
     * Meetod, mis loob Zxing teegiga qr koodi
     * https://github.com/zxing/zxing
     * Genereerib maatriksi andmetest, mis joonistatakse canvasega pildile
     *
     * @param ticketdata list piletite andmetest, mis on juba vajalikul kujul
     */
    public void createQrCode(ArrayList<String> ticketdata, String totalcost) {
        this.rightContent.getChildren().clear();
        StringBuilder tekst = new StringBuilder(this.parentController.getEvent().getName() + "\n" +
                this.parentController.getEvent().getFormattedDate() + " " +
                this.parentController.getEvent().getTime() + "\n");
        ticketdata.forEach(tekst::append);
        tekst.append(totalcost); // FIXME: 11.05.2016 currency symbol bugine?
        ImageView qrcode = MainHandler.createQrCode(tekst.toString(), 350.0, 45);
        this.rightContent.getChildren().add(qrcode);
        this.locked = true;
    }

    public boolean isLocked() {
        return this.locked;
    }

    public FloorPlanPane getFloorPlan() {
        return this.floorPlan;
    }

    public void setFloorPlan(FloorPlanPane floorPlan) {
        this.floorPlan = floorPlan;
        this.floorPlan.loadFloorPlan(this.parentController.getEvent());
        this.rightContent.getChildren().add(this.floorPlan);
    }

    public void updateTicket(String ticketname, ArrayList<Double> ticketdata, String currency) {
        for (Node node : this.ticketVBox.getChildren()) {
            if (((Ticket) node).getTicketName().equals(ticketname)) {
                ((Ticket) node).update(ticketdata);
                return;
            }
        }
        this.ticketVBox.getChildren().add(new Ticket(ticketname, ticketdata, currency));
    }

    public void removeTicket(String ticketname) {
        for (Node node : this.ticketVBox.getChildren()) {
            if (((Ticket) node).getTicketName().equals(ticketname)) {
                this.ticketVBox.getChildren().remove(node);
                return;
            }
        }
    }

    private void setBackButton() {
        Button back = new Button(Word.BACK.toString());
        back.setMnemonicParsing(false);
        back.getStyleClass().add("bottomButton");
        back.setOnMouseClicked(event -> {
            this.parentController.cancel();
            this.scene.getStageHandler().getStage().close();
        });
        HBox hBox = new HBox(back);
        HBox.setMargin(back, new Insets(0.0, 40.0, 40.0, 0.0));
        hBox.setAlignment(Pos.BOTTOM_RIGHT);
        hBox.setMinSize(0.0, 0.0);
        BorderPane.setAlignment(hBox, Pos.CENTER);
        this.borderPane.setBottom(hBox);
    }

    private void setDatetime() {
        this.datetime.setText(this.parentController.getEvent().getFormattedDate() + " " +
                this.parentController.getEvent().getTime());
    }

    public void updateTotals() {
        int quantity = 0;
        double total = 0.0;
        for (Node node : this.ticketVBox.getChildren()) {
            quantity += ((Ticket) node).getAmount();
            total += ((Ticket) node).getPrice();
        }
        this.totalQuantity.setText(String.valueOf(quantity));
        this.totalPrice.setText(String.format("%.2f", total) + " " +
                this.parentController.getEvent().getCurrency());
    }

    @Override
    public <T> void prepareToDisplay(T object) {
        if (object instanceof system.graphics.pointOfSale.Controller) {
            this.parentController = (system.graphics.pointOfSale.Controller) object;
            this.parentController.setClientScreenController(this);
        }
    }

    @Override
    public void setLanguage() {
        this.tickettype.setText(Word.TICKETYPE.toString());
        this.cost.setText(Word.COST.toString());
        this.quantity.setText(Word.QUANTITY.toString());
        this.total.setText(Word.TOTAL.toString());
        this.total2.setText(Word.TOTAL.toString());
    }
}
