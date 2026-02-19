package com.jjarroyo.components;

import javafx.scene.control.Button;
import javafx.scene.shape.SVGPath;

public class JButton extends Button {

    private static final String DEFAULT_STYLE = "btn-primary";

    public JButton() {
        super();
        init();
    }

    public JButton(String text) {
        super(text);
        init();
    }

    public JButton(String text, JIcon icon) {
        super(text);
        init();
        setIcon(icon);
    }

    private void init() {
        // Add base class and default variant
        getStyleClass().addAll("btn", DEFAULT_STYLE);
    }

    public JButton addClass(String... styleClasses) {
        for (String style : styleClasses) {
            // Intelligent replacement:
            // If the user adds a new btn variant (starts with "btn-" but isn't just "btn"),
            // remove the default primary style.
            // EXCEPTION: Do not remove default if it's just a size modifier.
            if (style != null && style.startsWith("btn-") && !style.equals("btn") 
                    && !style.equals("btn-sm") && !style.equals("btn-lg")) {
                getStyleClass().remove(DEFAULT_STYLE);
            }
        }
        getStyleClass().addAll(styleClasses);
        return this;
    }

    public void setIcon(JIcon icon) {
        if (icon != null) {
            SVGPath svg = new SVGPath();
            svg.setContent(icon.getPath());
            svg.getStyleClass().add("icon-svg");
            svg.setScaleX(0.8);
            svg.setScaleY(0.8);
            svg.setStyle("-fx-fill: -fx-text-fill;"); 
            
            setGraphic(svg);
            setGraphicTextGap(8);
            getStyleClass().add("btn-has-icon"); // Add marker class
        } else {
            setGraphic(null);
            getStyleClass().remove("btn-has-icon");
        }
    }
}

