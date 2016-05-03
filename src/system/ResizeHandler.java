package system;

import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

//http://stackoverflow.com/questions/19455059/allow-user-to-resize-an-undecorated-stage
public class ResizeHandler implements javafx.event.EventHandler<MouseEvent> {
    private Stage stage;
    private Cursor cursorEvent = Cursor.DEFAULT;
    private int border = 4;
    private double startX = 0;
    private double startY = 0;

    public ResizeHandler(Stage stage) {
        this.stage = stage;
    }

    public void addResizeListener(Scene scene) {
        scene.addEventHandler(MouseEvent.MOUSE_MOVED, this);
        scene.addEventHandler(MouseEvent.MOUSE_PRESSED, this);
        scene.addEventHandler(MouseEvent.MOUSE_DRAGGED, this);
        ObservableList<Node> children = scene.getRoot().getChildrenUnmodifiable();
        for (Node child : children) {
            addListenerDeeply(child, this);
        }
    }

    public Cursor getCursor() {
        return this.cursorEvent;
    }

    private static void addListenerDeeply(Node node, javafx.event.EventHandler<MouseEvent> listener) {
        node.addEventHandler(MouseEvent.MOUSE_MOVED, listener);
        node.addEventHandler(MouseEvent.MOUSE_PRESSED, listener);
        node.addEventHandler(MouseEvent.MOUSE_DRAGGED, listener);
        if (node instanceof Parent) {
            Parent parent = (Parent) node;
            ObservableList<Node> children = parent.getChildrenUnmodifiable();
            for (Node child : children) {
                addListenerDeeply(child, listener);
            }
        }
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        EventType<? extends MouseEvent> mouseEventType = mouseEvent.getEventType();
        Scene scene = stage.getScene();

        double mouseEventX = mouseEvent.getSceneX();
        double mouseEventY = mouseEvent.getSceneY();
        double sceneWidth = scene.getWidth();
        double sceneHeight = scene.getHeight();

        if (MouseEvent.MOUSE_MOVED.equals(mouseEventType)) {
            if (mouseEventX < border && mouseEventY < border) {
                cursorEvent = Cursor.NW_RESIZE;
            } else if (mouseEventX < border && mouseEventY > sceneHeight - border) {
                cursorEvent = Cursor.SW_RESIZE;
            } else if (mouseEventX > sceneWidth - border && mouseEventY < border) {
                cursorEvent = Cursor.NE_RESIZE;
            } else if (mouseEventX > sceneWidth - border && mouseEventY > sceneHeight - border) {
                cursorEvent = Cursor.SE_RESIZE;
            } else if (mouseEventX < border) {
                cursorEvent = Cursor.W_RESIZE;
            } else if (mouseEventX > sceneWidth - border) {
                cursorEvent = Cursor.E_RESIZE;
            } else if (mouseEventY < border) {
                cursorEvent = Cursor.N_RESIZE;
            } else if (mouseEventY > sceneHeight - border) {
                cursorEvent = Cursor.S_RESIZE;
            } else {
                cursorEvent = Cursor.DEFAULT;
            }
            scene.setCursor(cursorEvent);
        } else if (MouseEvent.MOUSE_PRESSED.equals(mouseEventType)) {
            startX = stage.getWidth() - mouseEventX;
            startY = stage.getHeight() - mouseEventY;
        } else if (MouseEvent.MOUSE_DRAGGED.equals(mouseEventType)) {
            if (!Cursor.DEFAULT.equals(cursorEvent)) {
                mouseEvent.consume();
                if (!Cursor.W_RESIZE.equals(cursorEvent) && !Cursor.E_RESIZE.equals(cursorEvent)) {
                    double minHeight = stage.getMinHeight() > (border * 2) ? stage.getMinHeight() : (border * 2);
                    if (Cursor.NW_RESIZE.equals(cursorEvent) || Cursor.N_RESIZE.equals(cursorEvent) ||
                            Cursor.NE_RESIZE.equals(cursorEvent)) {
                        if (stage.getHeight() > minHeight || mouseEventY < 0) {
                            stage.setHeight(stage.getY() - mouseEvent.getScreenY() + stage.getHeight());
                            stage.setY(mouseEvent.getScreenY());
                        }
                    } else {
                        if (stage.getHeight() > minHeight || mouseEventY + startY - stage.getHeight() > 0) {
                            stage.setHeight(mouseEventY + startY);
                        }
                    }
                }

                if (!Cursor.N_RESIZE.equals(cursorEvent) && !Cursor.S_RESIZE.equals(cursorEvent)) {
                    double minWidth = stage.getMinWidth() > (border * 2) ? stage.getMinWidth() : (border * 2);
                    if (Cursor.NW_RESIZE.equals(cursorEvent) || Cursor.W_RESIZE.equals(cursorEvent) ||
                            Cursor.SW_RESIZE.equals(cursorEvent)) {
                        if (stage.getWidth() > minWidth || mouseEventX < 0) {
                            stage.setWidth(stage.getX() - mouseEvent.getScreenX() + stage.getWidth());
                            stage.setX(mouseEvent.getScreenX());
                        }
                    } else {
                        if (stage.getWidth() > minWidth || mouseEventX + startX - stage.getWidth() > 0) {
                            stage.setWidth(mouseEventX + startX);
                        }
                    }
                }
            }

        }
    }
}
