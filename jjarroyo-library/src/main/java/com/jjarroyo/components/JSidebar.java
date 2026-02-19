package com.jjarroyo.components;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;

public class JSidebar extends VBox {

    public enum CollapseMode {
        COMPACT, // Icons only
        HIDDEN   // Width 0
    }

    private ObservableList<JSidebarItem> items = FXCollections.observableArrayList();
    private VBox itemsContainer;
    private ScrollPane scrollPane;
    private HBox footerContainer;
    private VBox headerContainer;
    private Button toggleBtn;

    private BooleanProperty expanded = new SimpleBooleanProperty(true);
    private ObjectProperty<CollapseMode> collapseMode = new SimpleObjectProperty<>(CollapseMode.COMPACT);
    
    // Widths
    private double expandedWidth = 250;
    private double compactWidth = 70;
    private double hiddenWidth = 0;

    public JSidebar() {
        init();
    }

    private HBox topBar;
    private HBox headerContentWrapper;
    private Node headerNode;

    private void init() {
        getStyleClass().add("j-sidebar");
        setPrefWidth(expandedWidth);
        setMinWidth(Region.USE_PREF_SIZE);
        setMaxWidth(Region.USE_PREF_SIZE);

        // Top Bar (Header + Toggle)
        topBar = new HBox();
        topBar.getStyleClass().add("j-sidebar-header");
        topBar.setAlignment(Pos.CENTER_LEFT);
        
        // Wrapper for User Header (Title/Logo)
        headerContentWrapper = new HBox();
        headerContentWrapper.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(headerContentWrapper, Priority.ALWAYS);
        
        // Toggle Button (Hamburger)
        toggleBtn = new Button();
        toggleBtn.getStyleClass().add("j-sidebar-toggle");
        toggleBtn.setGraphic(JIcon.MENU.view()); // Use Hamburger
        toggleBtn.setOnAction(e -> setExpanded(!isExpanded()));
        
        // Spacer to push toggle to right? No, standard HBox will place them next to each other.
        // If we want Title [Spacer] Toggle:
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        topBar.getChildren().addAll(headerContentWrapper, spacer, toggleBtn);

        // Scrollable Items Container
        itemsContainer = new VBox();
        itemsContainer.getStyleClass().add("j-sidebar-items");
        
        scrollPane = new ScrollPane(itemsContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Disable horizontal scroll
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.getStyleClass().add("j-sidebar-scroll");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        // Remove Footer Container logic
        // ...

        getChildren().addAll(topBar, scrollPane);

        // Items Listener
        items.addListener((ListChangeListener<JSidebarItem>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                   itemsContainer.getChildren().addAll(c.getAddedSubList());
                }
                if (c.wasRemoved()) {
                    itemsContainer.getChildren().removeAll(c.getRemoved());
                }
                checkAutoCollapseMode();
            }
        });
        
        // Animation Listener
        expanded.addListener((obs, oldV, newV) -> animateState(newV));
    }

    private void checkAutoCollapseMode() {
        boolean hasIcons = items.stream().anyMatch(item -> item.getIcon() != null);
        if (hasIcons) {
             setCollapseMode(CollapseMode.COMPACT);
        } else {
             setCollapseMode(CollapseMode.HIDDEN);
        }
    }

    private void animateState(boolean isExpanded) {
        double targetWidth = isExpanded ? expandedWidth : 
                             (getCollapseMode() == CollapseMode.COMPACT ? compactWidth : hiddenWidth);
        
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(prefWidthProperty(), targetWidth);
        KeyFrame kf = new KeyFrame(Duration.millis(300), kv);
        timeline.getKeyFrames().add(kf);
        
        // Handle content visibility
        if (!isExpanded) {
             getStyleClass().add("collapsed");
             if (headerContentWrapper != null) {
                 headerContentWrapper.setVisible(false);
                 headerContentWrapper.setManaged(false);
             }
             if (getCollapseMode() == CollapseMode.COMPACT) getStyleClass().add("compact");
             else getStyleClass().add("hidden");
        } else {
             getStyleClass().remove("collapsed");
             if (headerContentWrapper != null) {
                 headerContentWrapper.setVisible(true);
                 headerContentWrapper.setManaged(true);
             }
             getStyleClass().remove("compact");
             getStyleClass().remove("hidden");
        }

        timeline.play();
    }
    
    public void setHeader(Node header) {
        this.headerNode = header;
        headerContentWrapper.getChildren().clear();
        if (header != null) {
            headerContentWrapper.getChildren().add(header);
        }
    }
    
    public ObservableList<JSidebarItem> getItems() {
        return items;
    }
    
    public boolean isExpanded() {
        return expanded.get();
    }

    public void setExpanded(boolean expanded) {
        this.expanded.set(expanded);
    }
    
    public BooleanProperty expandedProperty() {
        return expanded;
    }
    
    public CollapseMode getCollapseMode() {
        return collapseMode.get();
    }
    
    public void setCollapseMode(CollapseMode mode) {
        this.collapseMode.set(mode);
    }
    
    public ObjectProperty<CollapseMode> collapseModeProperty() {
        return collapseMode;
    }
}

