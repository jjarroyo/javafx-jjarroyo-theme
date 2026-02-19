package com.jjarroyo.demo.views;

import com.jjarroyo.components.JCard;
import com.jjarroyo.components.JList;
import com.jjarroyo.components.JTable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class DataView extends ScrollPane {

    public static class User {
        private final SimpleStringProperty name;
        private final SimpleStringProperty email;
        private final SimpleStringProperty role;

        public User(String name, String email, String role) {
            this.name = new SimpleStringProperty(name);
            this.email = new SimpleStringProperty(email);
            this.role = new SimpleStringProperty(role);
        }

        public String getName() { return name.get(); }
        public String getEmail() { return email.get(); }
        public String getRole() { return role.get(); }
    }

    private ObservableList<User> data;

    public DataView() {
        getStyleClass().add("j-scroll-pane");
        setFitToWidth(true);
        setPadding(new Insets(24));
        
        VBox content = new VBox();
        content.setSpacing(24);
        
        // Header
        VBox header = new VBox();
        header.setSpacing(8);
        Label title = new Label("Data Tables & Lists");
        title.getStyleClass().add("page-title");
        Label subtitle = new Label("Advanced data presentation with sorting, pagination, and infinite scroll.");
        subtitle.getStyleClass().add("page-subtitle");
        header.getChildren().addAll(title, subtitle);
        content.getChildren().add(header);
        
        // Dummy Data
        data = FXCollections.observableArrayList();
        for (int i = 1; i <= 20; i++) {
            data.add(new User("User " + i, "user" + i + "@example.com", i % 3 == 0 ? "Admin" : "User"));
        }
        
        // 1. JTable Example
        JCard cardTable = new JCard("Users Table", "Sortable columns and pagination.");
        
        JTable<User> table = new JTable<>();
        
        TableColumn<User, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(cell -> cell.getValue().name);
        
        TableColumn<User, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(cell -> cell.getValue().email);
        
        TableColumn<User, String> roleCol = new TableColumn<>("Role");
        roleCol.setCellValueFactory(cell -> cell.getValue().role);
        
        table.getLastTable().getColumns().addAll(nameCol, emailCol, roleCol);
        table.setItems(data);
        
        // Simulate pagination logic
        table.getPagination().totalPagesProperty().set(5);
        table.getPagination().currentPageProperty().addListener((obs, old, newVal) -> {
            System.out.println("Loading page " + newVal);
            // Here you would fetch new data
        });
        
        cardTable.setBody(table);
        content.getChildren().add(cardTable);
        
        // 2. JList Example
        JCard cardList = new JCard("Users List", "Card-style rows with infinite scroll.");
        
        JList<User> list = new JList<>();
        list.setItems(data);
        
        // Custom Cell Factory for Card Look
        list.setCellFactory(param -> new ListCell<User>() {
            @Override
            protected void updateItem(User item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                    setStyle("-fx-background-color: transparent;");
                } else {
                    // Create Card Layout
                    VBox card = new VBox(4);
                    card.getStyleClass().add("j-list-card");
                    
                    Label nameLbl = new Label(item.getName());
                    nameLbl.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
                    
                    Label emailLbl = new Label(item.getEmail());
                    emailLbl.setStyle("-fx-text-fill: -color-slate-500; -fx-font-size: 13px;");
                    
                    HBox row = new HBox(10);
                    Label roleBadge = new Label(item.getRole());
                    roleBadge.setStyle("-fx-background-color: -color-primary-100; -fx-text-fill: -color-primary-700; -fx-padding: 2px 8px; -fx-background-radius: 4px; -fx-font-size: 11px;");
                    
                    Region spacer = new Region();
                    HBox.setHgrow(spacer, Priority.ALWAYS);
                    
                    row.getChildren().addAll(nameLbl, spacer, roleBadge);
                    card.getChildren().addAll(row, emailLbl);
                    
                    setGraphic(card);
                    setStyle("-fx-background-color: transparent; -fx-padding: 5px 0;"); // Spacing between cells
                }
            }
        });
        
        // Simulate Infinite Scroll
        list.setOnScrollBottom(() -> {
            System.out.println("Scrolled to bottom! Loading more...");
            // Add dummy data
            int start = data.size() + 1;
            for (int i = start; i < start + 5; i++) {
                data.add(new User("New User " + i, "new" + i + "@example.com", "Guest"));
            }
        });
        
        cardList.setBody(list);
        content.getChildren().add(cardList);

        setContent(content);
    }
}


