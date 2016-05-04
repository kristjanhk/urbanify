package system.graphics.eventManager;

import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import system.data.Event;
import system.graphics.common.Scenetype;

public class EventLine extends HBox {
    private Controller parentController;
    private Event event;

    public EventLine(Controller parentController, Event event) {
        super();
        this.parentController = parentController;
        this.event = event;
        this.setMinHeight(110.5);
        this.setMaxHeight(70.5);
        this.setPrefSize(200.0, 70.5);
        this.getStyleClass().add("background");
        this.initChildren(this.event.getName(), this.event.getDate().toString(), this.event.getTime());
    }

    public Event getEvent() {
        return this.event;
    }

    private void initChildren(String name, String date, String time) {
        Button deleteButton = new Button("ó");
        deleteButton.getStyleClass().add("buttonSmall");
        deleteButton.setMinSize(70.5, 70.5);
        deleteButton.setMaxSize(70.5, 70.5);
        deleteButton.setPrefSize(70.5, 70.5);
        deleteButton.setMnemonicParsing(false);
        deleteButton.setOnMouseClicked(event -> {
            this.parentController.getEventsVBox().getChildren().remove(this);
            if (this.parentController.getScene().getScenetype() == Scenetype.EVENTMANAGER) {
                ((Controller) this.parentController.getScene().getStageHandler().
                        getScene(Scenetype.ARCHIVE).getController()).addEventLine(this.event);
            }
        });
        HBox.setMargin(deleteButton, new Insets(0.0, 40.0, 0.0, 0.0));

        TextFlow textFlowName = new TextFlow();
        textFlowName.getStyleClass().add("textFlowNimi");
        textFlowName.setMinHeight(71.5);
        textFlowName.setMaxHeight(71.5);
        textFlowName.setPrefSize(429.0, 71.5);
        textFlowName.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        textFlowName.setOnMouseClicked(event -> {
            if (this.parentController.getScene().getScenetype() == Scenetype.EVENTMANAGER) {
                this.parentController.getScene().getStageHandler().switchSceneTo(Scenetype.POINTOFSALE, this.event);
            } else {
                // TODO: 5.05.2016 aruande ekraan vms
                System.out.println("TODO aruande ekraan");
            }
        });
        HBox.setHgrow(textFlowName, Priority.ALWAYS);

        Text name1 = createText(name);
        textFlowName.getChildren().add(name1);

        TextFlow textFlowDate = new TextFlow();
        textFlowDate.getStyleClass().add("textFlowInfo");
        textFlowDate.setMinSize(180, 71.5);
        textFlowDate.setMaxHeight(71.5);
        textFlowDate.setPrefSize(200.0, 71.5);
        textFlowDate.setTextAlignment(TextAlignment.CENTER);

        Text date1 = createText(date);
        textFlowDate.getChildren().add(date1);

        TextFlow textFlowTime = new TextFlow();
        textFlowTime.getStyleClass().add("textFlowInfo");
        textFlowTime.setMinHeight(71.5);
        textFlowTime.setMaxHeight(71.5);
        textFlowTime.setPrefSize(200.0, 71.5);
        textFlowTime.setTextAlignment(TextAlignment.CENTER);
        HBox.setMargin(textFlowTime, new Insets(0.0, 40.0, 0.0, 0.0));

        Text time1 = createText(time);
        textFlowTime.getChildren().add(time1);

        this.getChildren().addAll(deleteButton, textFlowName,
                createRegion(), textFlowDate,
                createRegion(), textFlowTime);
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
}
