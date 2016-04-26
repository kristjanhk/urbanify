package demo;

import java.util.Date;
import javafx.scene.control.Control;

public class DateChooser extends Control{

    private static final String DEFAULT_STYLE_CLASS = "date-chooser";
    private Date date;

    public DateChooser(Date preset) {
        getStyleClass().setAll(DEFAULT_STYLE_CLASS);
        this.date = preset;
    }

    public DateChooser() {
        this(new Date(System.currentTimeMillis()));
    }

    @Override
    public String getUserAgentStylesheet() {
        return "demo/calendar.css";
    }

    public Date getDate() {
        return date;
    }
}
