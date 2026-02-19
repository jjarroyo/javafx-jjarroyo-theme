package com.jjarroyo.components;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polygon;
import javafx.stage.Popup;
import javafx.stage.Window;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.ParallelTransition;
import javafx.util.Duration;

public class JPopover extends Popup {

    public enum Theme { LIGHT, DARK }
    public enum Position { TOP, RIGHT, BOTTOM, LEFT }

    private final VBox container;
    private final Polygon arrow;
    private final VBox contentBox;
    
    private Theme theme = Theme.LIGHT;
    private Position position = Position.TOP;
    
    // Content Parts
    private final Label titleLabel;
    private final Label bodyLabel;
    private final HBox footerBox;
    
    public JPopover() {
        // Root Container (Bubbles + Arrow)
        container = new VBox();  
        container.getStyleClass().add("j-popover");
        container.setMouseTransparent(false);
        container.setAlignment(javafx.geometry.Pos.CENTER); // Fix: Center arrow relative to content
        
        // Content Box (The visual card)
        contentBox = new VBox();
        contentBox.getStyleClass().add("j-popover-content");
        
        // Arrow
        arrow = new Polygon(0, 0, 10, 10, 20, 0); // Down pointing arrow shape logic varies by orientation
        arrow.getStyleClass().add("j-popover-arrow");
        
        // Assemble initial structure (Default: Arrow Top or Bottom? JPopover structure changes based on pos)
        // We'll dynamic assemble in show() or configure()
        
        // Init Content Parts
        titleLabel = new Label();
        titleLabel.getStyleClass().add("j-popover-title");
        titleLabel.setWrapText(true);
        titleLabel.setManaged(false);
        titleLabel.setVisible(false);
        
        bodyLabel = new Label();
        bodyLabel.getStyleClass().add("j-popover-text");
        bodyLabel.setWrapText(true);
        
        footerBox = new HBox();
        footerBox.getStyleClass().add("j-popover-footer");
        footerBox.setManaged(false);
        footerBox.setVisible(false);
        
        contentBox.getChildren().addAll(titleLabel, bodyLabel, footerBox);
        getContent().add(container);
        setAutoHide(true);
    }
    
    public JPopover setTitle(String title) {
        titleLabel.setText(title);
        boolean hasText = title != null && !title.isEmpty();
        titleLabel.setVisible(hasText);
        titleLabel.setManaged(hasText);
        return this;
    }
    
    public JPopover setBody(String text) {
        bodyLabel.setText(text);
        return this;
    }
    
    public JPopover setContentNode(Node content) {
        contentBox.getChildren().clear();
        contentBox.getChildren().add(content);
        return this;
    }
    
    public JPopover setTheme(Theme theme) {
        this.theme = theme;
        updateTheme();
        return this;
    }
    
    public JPopover setPosition(Position pos) {
        this.position = pos;
        return this;
    }
    
    private void updateTheme() {
        container.getStyleClass().remove("j-popover-dark");
        if (theme == Theme.DARK) {
            container.getStyleClass().add("j-popover-dark");
        }
    }
    
    // Confirmation Mode Helper
    public JPopover setConfirmationMode(String yesText, String noText, Runnable onYes) {
        footerBox.getChildren().clear();
        
        Button btnNo = new Button(noText);
        btnNo.getStyleClass().addAll("btn", "btn-sm", "btn-light-danger"); // Or light-secondary
        btnNo.setOnAction(e -> hide());
        
        Button btnYes = new Button(yesText);
        btnYes.getStyleClass().addAll("btn", "btn-sm", "btn-primary");
        btnYes.setOnAction(e -> {
            hide();
            if (onYes != null) onYes.run();
        });
        
        footerBox.getChildren().addAll(btnNo, btnYes);
        footerBox.setVisible(true);
        footerBox.setManaged(true);
        return this;
    }
    
    public void show(Node target) {
        // 1. Re-assemble container based on orientation
        // We need the correct container type (VBox for Vertical, HBox for Horizontal)
        javafx.scene.layout.Pane activeContainer;
        
        arrow.getPoints().clear();
        
        if (position == Position.TOP || position == Position.BOTTOM) {
            VBox vBox = new VBox();            
            vBox.getStyleClass().add("j-popover");
            vBox.setMouseTransparent(false);
            vBox.setAlignment(javafx.geometry.Pos.CENTER);
            if (theme == Theme.DARK) vBox.getStyleClass().add("j-popover-dark");
            
            if (position == Position.TOP) {
                arrow.getPoints().addAll(0d, 0d, 10d, 10d, 20d, 0d); // Point Down
                vBox.getChildren().addAll(contentBox, arrow);
            } else {
                arrow.getPoints().addAll(0d, 10d, 10d, 0d, 20d, 10d); // Point Up
                vBox.getChildren().addAll(arrow, contentBox);
            }
            activeContainer = vBox;
        } else {
            HBox hBox = new HBox();          
            hBox.getStyleClass().add("j-popover");
            hBox.setMouseTransparent(false);
            hBox.setAlignment(javafx.geometry.Pos.CENTER);
            if (theme == Theme.DARK) hBox.getStyleClass().add("j-popover-dark");
            
            if (position == Position.LEFT) {
                arrow.getPoints().addAll(0d, 0d, 10d, 10d, 0d, 20d); // Point Right
                hBox.getChildren().addAll(contentBox, arrow);
            } else { // RIGHT
                arrow.getPoints().addAll(10d, 0d, 0d, 10d, 10d, 20d); // Point Left
                hBox.getChildren().addAll(arrow, contentBox);
            }
            activeContainer = hBox;
        }
        
        getContent().clear();
        getContent().add(activeContainer);
        
        // 2. Calculate Coordinates
        Window window = target.getScene().getWindow();
        Bounds bounds = target.localToScreen(target.getBoundsInLocal());
        
        // Show off-screen first to calculate size
        super.show(window);
        
        double x = 0;
        double y = 0;
        double width = activeContainer.getWidth();
        double height = activeContainer.getHeight();
        
        if (position == Position.TOP) {
            x = bounds.getMinX() + (bounds.getWidth() / 2) - (width / 2);
            y = bounds.getMinY() - height - 10;
        } else if (position == Position.BOTTOM) {
            x = bounds.getMinX() + (bounds.getWidth() / 2) - (width / 2);
            y = bounds.getMaxY() + 10;
        } else if (position == Position.LEFT) {
            x = bounds.getMinX() - width - 10;
            y = bounds.getMinY() + (bounds.getHeight() / 2) - (height / 2);
        } else if (position == Position.RIGHT) {
            x = bounds.getMaxX() + 10;
            y = bounds.getMinY() + (bounds.getHeight() / 2) - (height / 2);
        }
        
        setAnchorX(x);
        setAnchorY(y);
        
        // Animation
        playEntranceAnimation(activeContainer);
    }
    
    private void playEntranceAnimation(Node node) {
        FadeTransition fade = new FadeTransition(Duration.millis(200), node);
        fade.setFromValue(0);
        fade.setToValue(1);
        
        ScaleTransition scale = new ScaleTransition(Duration.millis(200), node);
        scale.setFromX(0.8);
        scale.setFromY(0.8);
        scale.setToX(1.0);
        scale.setToY(1.0);
        
        ParallelTransition pt = new ParallelTransition(fade, scale);
        pt.play();
    }
}

