package system.graphics.ticketInfo.clientLogo;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import system.graphics.common.AbstractController;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller extends AbstractController {
    @FXML protected BorderPane pane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setLogo();
    }

    private void setLogo() {
        File logodir = new File(this.getFileHandler().getPath() + "\\logo");
        if (logodir.exists()) {
            File[] files = logodir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (new MimetypesFileTypeMap().getContentType(file).split("/")[0].equals("image")) {
                        this.createImage(file);
                        return;
                    }
                }
            }
        }
        this.createDefaultText();
    }

    private void createImage(File file) {
        this.pane.setCenter(new ImageView(new Image(file.toURI().toString())));
    }

    private void createDefaultText() {
        Text text = new Text("Piletikassa");
        text.setStrokeType(StrokeType.OUTSIDE);
        text.setStrokeWidth(0.0);
        text.getStyleClass().add("topText");
        this.pane.setCenter(text);
    }

    @Override
    public void setLanguage() {}
}
