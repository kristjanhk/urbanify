package system;

import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Suuruste muutmise haldaja
 * Lisab kõikidele antud lava tippudele kuulajad, mille järgi otsustab lava suuruse muutmise üle
 * (http://stackoverflow.com/questions/19455059/allow-user-to-resize-an-undecorated-stage)
 */
public class ResizeHandler implements EventHandler<MouseEvent> {
    private Stage stage;
    private Cursor cursorEvent = Cursor.DEFAULT;
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

    private static void addListenerDeeply(Node node, EventHandler<MouseEvent> listener) {
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

    /**
     * Selle meetodiga kuulaja on lisatud igale tipule
     * Kontrollib, kas hiir asub stseeni äärel ning muudab hiire kuju
     * Kui hiir on äärel ja kasutaja proovib lohistada, siis muudetakse akna suurust
     * int border - hiire kuju muutev ala laius ääres
     */
    @Override
    public void handle(MouseEvent mouseEvent) {
        EventType<? extends MouseEvent> mouseEventType = mouseEvent.getEventType();
        Scene scene = this.stage.getScene();
        int border = 10;

        double mouseEventX = mouseEvent.getSceneX();
        double mouseEventY = mouseEvent.getSceneY();
        double sceneWidth = scene.getWidth();
        double sceneHeight = scene.getHeight();

        if (MouseEvent.MOUSE_MOVED.equals(mouseEventType)) {
            if (this.stage.isMaximized()) {
                cursorEvent = Cursor.DEFAULT;
            } else if (mouseEventX < border && mouseEventY < border) {
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
            startX = this.stage.getWidth() - mouseEventX;
            startY = this.stage.getHeight() - mouseEventY;
        } else if (MouseEvent.MOUSE_DRAGGED.equals(mouseEventType)) {
            if (!Cursor.DEFAULT.equals(cursorEvent) && !this.stage.isMaximized()) {
                if (!Cursor.W_RESIZE.equals(cursorEvent) && !Cursor.E_RESIZE.equals(cursorEvent)) {
                    double minHeight = this.stage.getMinHeight();
                    if (Cursor.NW_RESIZE.equals(cursorEvent) || Cursor.N_RESIZE.equals(cursorEvent) ||
                            Cursor.NE_RESIZE.equals(cursorEvent)) {
                        if (this.stage.getHeight() > minHeight || mouseEventY < 0) {
                            if (this.stage.getY() - mouseEvent.getScreenY() + this.stage.getHeight() > minHeight) {
                                this.stage.setHeight(
                                        this.stage.getY() - mouseEvent.getScreenY() + this.stage.getHeight());
                                this.stage.setY(mouseEvent.getScreenY());
                            }
                        }
                    } else {
                        if (this.stage.getHeight() > minHeight || mouseEventY + startY - this.stage.getHeight() > 0) {
                            if (mouseEventY + startY > minHeight) {
                                this.stage.setHeight(mouseEventY + startY);
                            }
                        }
                    }
                }
                if (!Cursor.N_RESIZE.equals(cursorEvent) && !Cursor.S_RESIZE.equals(cursorEvent)) {
                    double minWidth = this.stage.getMinWidth();
                    if (Cursor.NW_RESIZE.equals(cursorEvent) || Cursor.W_RESIZE.equals(cursorEvent) ||
                            Cursor.SW_RESIZE.equals(cursorEvent)) {
                        if (this.stage.getWidth() > minWidth || mouseEventX < 0) {
                            if (this.stage.getX() - mouseEvent.getScreenX() + this.stage.getWidth() > minWidth) {
                                this.stage.setWidth(
                                        this.stage.getX() - mouseEvent.getScreenX() + this.stage.getWidth());
                                this.stage.setX(mouseEvent.getScreenX());
                            }
                        }
                    } else {
                        if (this.stage.getWidth() > minWidth || mouseEventX + startX - this.stage.getWidth() > 0) {
                            if (mouseEventX + startX > minWidth) {
                                this.stage.setWidth(mouseEventX + startX);
                            }
                        }
                    }
                }
            }
        }
    }
}
