package system.graphics.common;

import javafx.scene.text.Text;

public class LimitedText extends Text {
    private int lengthLimit;
    private String originalText;

    public LimitedText() {}

    public LimitedText(String text) {
        super(text);
    }

    public String getOriginalText() {
        return this.originalText;
    }

    public void setOriginalText(String limitedtext) {
        this.originalText = limitedtext;
        if (limitedtext.length() > this.lengthLimit) {
            super.setText(limitedtext.substring(0, this.lengthLimit) + "...");
            System.out.println("limited text set");
        } else {
            super.setText(limitedtext);
            System.out.println("normal text set");
        }

    }

    public String getLengthLimit() {
        return String.valueOf(this.lengthLimit);
    }

    public void setLengthLimit(String limit) {
        this.lengthLimit = Integer.parseInt(limit);
        System.out.println("limit set");
    }
}
