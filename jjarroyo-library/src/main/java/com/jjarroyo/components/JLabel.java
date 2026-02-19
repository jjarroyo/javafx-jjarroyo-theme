package com.jjarroyo.components;

import javafx.scene.control.Label;

public class JLabel extends Label {

    public JLabel() {
        super();
        init();
    }

    public JLabel(String text) {
        super(text);
        init();
    }

    private void init() {
        // No default style class yet, but good for future extensibility
    }
    
    // Helper to add style classes easily
    public JLabel withStyle(String... styleClasses) {
        getStyleClass().addAll(styleClasses);
        return this;
    }
    
    // Alias for withStyle (consistent with other controls)
    public JLabel addClass(String... styleClasses) {
        return withStyle(styleClasses);
    }
}

