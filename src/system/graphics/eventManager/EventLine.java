package system.graphics.eventManager;

import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

public class EventLine extends HBox {
    private VBox parentNode;
    private TextFlow textFlowName;
    private TextFlow textFlowDate;
    private TextFlow textFlowTime;
    private Text name;
    private Text date;
    private Text time;

    public EventLine(VBox parentNode, String name, String date, String time) {
        super();
        this.parentNode = parentNode;
        this.setMinHeight(110.5);
        this.setMaxHeight(70.5);
        this.setPrefSize(200.0, 70.5);
        this.getStyleClass().add("background");
        this.initChildren(name, date, time);
    }

    private void initChildren(String name, String date, String time) {
        Button deleteButton = new Button("ó");
        deleteButton.getStyleClass().add("buttonSmall");
        deleteButton.setMinSize(70.5, 70.5);
        deleteButton.setMaxSize(70.5, 70.5);
        deleteButton.setPrefSize(70.5, 70.5);
        deleteButton.setMnemonicParsing(false);
        deleteButton.setOnMouseClicked(event -> this.parentNode.getChildren().remove(this));
        HBox.setMargin(deleteButton, new Insets(0.0, 40.0, 0.0, 0.0));

        this.textFlowName = new TextFlow();
        this.textFlowName.getStyleClass().add("textFlowNimi");
        this.textFlowName.setMinHeight(71.5);
        this.textFlowName.setMaxHeight(71.5);
        this.textFlowName.setPrefSize(429.0, 71.5);
        this.textFlowName.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        HBox.setHgrow(this.textFlowName, Priority.ALWAYS);

        this.name = createText(name);
        this.textFlowName.getChildren().add(this.name);

        this.textFlowDate = new TextFlow();
        this.textFlowDate.getStyleClass().add("textFlowInfo");
        this.textFlowDate.setMinSize(180, 71.5);
        this.textFlowDate.setMaxHeight(71.5);
        this.textFlowDate.setPrefSize(200.0, 71.5);
        this.textFlowDate.setTextAlignment(TextAlignment.CENTER);

        this.date = createText(date);
        this.textFlowDate.getChildren().add(this.date);

        this.textFlowTime = new TextFlow();
        this.textFlowTime.getStyleClass().add("textFlowInfo");
        this.textFlowTime.setMinHeight(71.5);
        this.textFlowTime.setMaxHeight(71.5);
        this.textFlowTime.setPrefSize(200.0, 71.5);
        this.textFlowTime.setTextAlignment(TextAlignment.CENTER);
        HBox.setMargin(this.textFlowTime, new Insets(0.0, 40.0, 0.0, 0.0));

        this.time = createText(time);
        this.textFlowTime.getChildren().add(this.time);

        this.getChildren().addAll(
                deleteButton,
                this.textFlowName,
                createRegion(),
                this.textFlowDate,
                createRegion(),
                this.textFlowTime);
    }

    private Text createText(String string) {
        Text text = new Text(string);
        text.getStyleClass().add("textEvent");
        text.setStrokeType(StrokeType.OUTSIDE);
        text.setStrokeWidth(0.0);
        text.setWrappingWidth(200.0); // 200.13671875 ?
        text.setStyle("-fx-font-size: 36px");
        return text;
    }

    private Region createRegion() {
        Region region = new Region();
        region.setMinSize(40.0, 40.0);
        region.setMaxSize(40.0, 40.0);
        region.setPrefSize(40.0, 40.0);
        return region;
    }

    public Text getName() {
        return name;
    }

    public Text getDate() {
        return date;
    }

    public Text getTime() {
        return time;
    }
}
