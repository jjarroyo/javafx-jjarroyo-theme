package com.jjarroyo.demo.views;

import com.jjarroyo.components.JCard;
import com.jjarroyo.components.JModal;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;

public class ModalsView extends ScrollPane {

    public ModalsView() {
        getStyleClass().add("j-scroll-pane");
        setFitToWidth(true);
        setPadding(new Insets(24));
        
        VBox content = new VBox();
        content.setSpacing(24);
        
        // Header
        VBox header = new VBox();
        header.setSpacing(8);
        Label title = new Label("Modals");
        title.getStyleClass().add("page-title");
        Label subtitle = new Label("Overlay dialogs with transitions and different sizes.");
        subtitle.getStyleClass().add("page-subtitle");
        header.getChildren().addAll(title, subtitle);
        content.getChildren().add(header);
        
        // 1. Sizes
        JCard cardSizes = new JCard("Modal Sizes", "Small, Medium, Large and Full Screen options.");
        HBox sizesBox = new HBox(12);
        sizesBox.getChildren().addAll(
            createModalTrigger("Small Modal", JModal.Size.SMALL),
            createModalTrigger("Medium Modal", JModal.Size.MEDIUM),
            createModalTrigger("Large Modal", JModal.Size.LARGE),
            createModalTrigger("Full Screen", JModal.Size.FULL)
        );
        cardSizes.setBody(sizesBox);
        content.getChildren().add(cardSizes);

        setContent(content);
    }
    
    private Button createModalTrigger(String text, JModal.Size size) {
        Button btn = new Button(text);
        btn.getStyleClass().addAll("btn", "btn-primary");
        btn.setOnAction(e -> {
            // Create content for modal
            VBox modalContent = new VBox();
            
            // Header
            HBox mHeader = new HBox();
            mHeader.getStyleClass().add("modal-header");
            Label mTitle = new Label(text);
            mTitle.getStyleClass().add("modal-title");
            mHeader.getChildren().add(mTitle);
            
            // Body
            VBox mBody = new VBox();
            mBody.getStyleClass().add("modal-body");
            mBody.getChildren().add(new Label("This is a " + size.name().toLowerCase() + " modal example with transition effects."));
            
            // Footer
            HBox mFooter = new HBox();
            mFooter.getStyleClass().add("modal-footer");
            
            Button closeBtn = new Button("Close");
            closeBtn.getStyleClass().addAll("btn", "btn-light-danger");
            
            // Close logic will be attached after creation or we make sure JModal instance is available
            // We need ref to JModal to close it from button.
            
            // Combine
            modalContent.getChildren().addAll(mHeader, mBody, mFooter);
            
            // Create and show
            JModal modal = new JModal(modalContent, size);
            
            closeBtn.setOnAction(ev -> modal.close());
            mFooter.getChildren().add(closeBtn);
            
            modal.show();
        });
        return btn;
    }
}


