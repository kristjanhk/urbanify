package system.graphics.ticketInfo.clientLogo;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import system.MainHandler;
import system.graphics.common.AbstractController;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller extends AbstractController {
    @FXML protected BorderPane pane;

    private final MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.mimetypesFileTypeMap.addMimeTypes("image png jpg jpeg gif");
        this.setLogo();
    }

    private void setLogo() {
        Image logo = this.loadLogo();
        if (logo != null) {
            this.pane.setCenter(new ImageView(logo));
        } else {
            this.createDefaultText();
        }
    }

    private Image loadLogo() {
        File logodir = new File(MainHandler.getFileHandler().getPath() + "\\logo");
        if (logodir.exists()) {
            File[] files = logodir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (this.mimetypesFileTypeMap.getContentType(file).split("/")[0].equals("image")) {
                        return new Image(file.toURI().toString());
                    }
                }
            }
        }
        return null;
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
