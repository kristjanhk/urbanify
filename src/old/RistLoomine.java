package old;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class RistLoomine extends Application {
    public static double suurus = 1.0;
    public static double suurusX;
    public static double suurusY;
    public static double mainX;
    public static double mainY;
    public static int laiusmax = 0;
    public static int kõrgusmax = 0;
    public static int laius = 0;
    public static int kõrgus = 0;

    public static Circle teeRuut() {
        //Rectangle kujund = new Rectangle(50,50);
        Circle kujund = new Circle(25, Color.GRAY);
        kujund.setCenterX(150 + laius * (50 + 10));
        return kujund;
    }

    public static Group teeRida() {
        Group rida = new Group();
        laius = 0;
        for (int i = 0; i < laiusmax; i++) {
            rida.getChildren().add(teeRuut());
            rida.setLayoutY(150 + kõrgus * (50 + 10));
            laius++;
        }
        return rida;
    }

    public static Group teeTulp() {
        kõrgus = 0;
        Group tulp = new Group();
        for (int i = 0; i < kõrgusmax; i++) {
            tulp.getChildren().add(teeRida());
            kõrgus++;
        }
        return tulp;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void muudaSuurust(Scene stseen1, Pane paan) {
        if ((mainY > stseen1.getHeight() - 50)) {
            resize(stseen1, paan);
        }
        if (mainX > stseen1.getWidth() - 150) {
            resize(stseen1, paan);
        }
    }

    public static void resize(Scene stseen1, Pane paan) {
        suurusY = (stseen1.getHeight() - 150) / mainY;
        suurusX = (stseen1.getWidth() - 150) / mainX;
        if (suurusY < suurusX) {
            suurus = suurusY;
        } else {
            suurus = suurusX;
        }
        paan.setScaleX(suurus);
        paan.setScaleY(suurus);
        paan.resizeRelocate(10, 10, 0, 0);
    }


    @Override
    public void start(Stage primaryStage) {
        BorderPane piiripaan = new BorderPane();
        Pane paan = new Pane();
        Pane paan2 = new Pane();
        Group põhigrupp = new Group();
        Button suurendaX = new Button("suurenda X");
        Button suurendaY = new Button("suurenda Y");
        Button vähendaX = new Button("vähenda X");
        Button vähendaY = new Button("vähenda Y");
        suurendaX.setLayoutX(10);
        suurendaX.setLayoutY(10);
        suurendaY.setLayoutX(100);
        suurendaY.setLayoutY(10);
        vähendaX.setLayoutX(10);
        vähendaX.setLayoutY(80);
        vähendaY.setLayoutX(100);
        vähendaY.setLayoutY(80);

        Label laius = new Label(Integer.toString(laiusmax));
        Label kõrgus = new Label(Integer.toString(kõrgusmax));
        laius.setLayoutX(10);
        laius.setLayoutY(50);
        kõrgus.setLayoutX(100);
        kõrgus.setLayoutY(50);


        paan.getChildren().addAll(põhigrupp);
        paan2.getChildren().addAll(suurendaX, suurendaY, vähendaX, vähendaY, kõrgus, laius);
        BorderPane.setAlignment(paan, Pos.TOP_LEFT);
        piiripaan.getChildren().add(paan);
        piiripaan.setRight(paan2);

        Scene stseen1 = new Scene(piiripaan, 1280, 720, Color.valueOf("E6E6E5"));
        suurendaY.setOnMouseClicked(event -> {
            kõrgusmax++;
            if (laiusmax == 0) {
                laiusmax++;
            }
            System.out.println(laiusmax + " - " + kõrgusmax);
            põhigrupp.getChildren().clear();
            põhigrupp.getChildren().add(teeTulp());
            põhigrupp.getChildren().addAll(teeRida());
            laius.setText(Integer.toString(laiusmax));
            kõrgus.setText(Integer.toString(kõrgusmax + 1));
            mainX = laiusmax * 60 + 100;
            mainY = (kõrgusmax - 1) * 60 + 100;
            muudaSuurust(stseen1, paan);
        });

        suurendaX.setOnMouseClicked(event -> {
            laiusmax++;
            if (kõrgusmax == -1) {
                kõrgusmax++;
            }
            System.out.println(laiusmax + " - " + kõrgusmax);
            põhigrupp.getChildren().clear();
            põhigrupp.getChildren().add(teeTulp());
            põhigrupp.getChildren().addAll(teeRida());
            laius.setText(Integer.toString(laiusmax));
            kõrgus.setText(Integer.toString(kõrgusmax + 1));
            mainX = laiusmax * 60 + 100;
            mainY = (kõrgusmax - 1) * 60 + 100;
            muudaSuurust(stseen1, paan);
        });

        vähendaY.setOnMouseClicked(event -> {
            if (kõrgusmax >= 0) {
                kõrgusmax--;
            }
            System.out.println(laiusmax + " - " + kõrgusmax);
            põhigrupp.getChildren().clear();
            põhigrupp.getChildren().add(teeTulp());
            põhigrupp.getChildren().addAll(teeRida());
            if (kõrgusmax == -1) {
                põhigrupp.getChildren().clear();
            }
            laius.setText(Integer.toString(laiusmax));
            kõrgus.setText(Integer.toString(kõrgusmax + 1));
            mainX = laiusmax * 60 + 100;
            mainY = (kõrgusmax - 1) * 60 + 100;
            muudaSuurust(stseen1, paan);
        });

        vähendaX.setOnMouseClicked(event -> {
            System.out.println(laiusmax + " - " + kõrgusmax);
            if (laiusmax > 0) {
                laiusmax--;
            }
            if (kõrgusmax == -1) {
                põhigrupp.getChildren().clear();
                laius.setText(Integer.toString(laiusmax));
                kõrgus.setText(Integer.toString(kõrgusmax + 1));
            }
            if (kõrgusmax > -1) {
                põhigrupp.getChildren().clear();
                põhigrupp.getChildren().add(teeTulp());
                põhigrupp.getChildren().addAll(teeRida());
                laius.setText(Integer.toString(laiusmax));
                kõrgus.setText(Integer.toString(kõrgusmax + 1));
                mainX = laiusmax * 60 + 100;
                mainY = (kõrgusmax - 1) * 60 + 100;
                muudaSuurust(stseen1, paan);
            }
        });

        primaryStage.setTitle("Floorplan");
        primaryStage.setScene(stseen1);
        primaryStage.show();
    }
}
