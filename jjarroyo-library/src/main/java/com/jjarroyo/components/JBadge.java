package com.jjarroyo.components;

import javafx.scene.control.Label;

public class JBadge extends Label {

    private static final String DEFAULT_STYLE = "badge-primary";

    public JBadge() {
        super();
        init();
    }

    public JBadge(String text) {
        super(text);
        init();
    }

    public JBadge(String text, String... styleClasses) {
        super(text);
        init();
        addClass(styleClasses);
    }

    private void init() {
        // Add base class and default variant
        getStyleClass().addAll("badge", DEFAULT_STYLE);
    }

    public JBadge addClass(String... styleClasses) {
        for (String style : styleClasses) {
            // Intelligent replacement: 
            // If the user adds a new badge variant (starts with "badge-" but isn't just "badge"),
            // remove the default primary style.
            // EXCEPTION: Do not remove default if it's just a size or shape modifier.
            if (style != null && style.startsWith("badge-") && !style.equals("badge")
                    && !style.equals("badge-sm") && !style.equals("badge-lg") && !style.equals("badge-circle")) {
                getStyleClass().remove(DEFAULT_STYLE);
            }
        }
        getStyleClass().addAll(styleClasses);
        return this;
    }
}

