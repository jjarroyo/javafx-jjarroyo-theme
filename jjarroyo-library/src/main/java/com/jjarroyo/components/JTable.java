package com.jjarroyo.components;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.Set;

public class JTable<T> extends VBox {

    private final TableView<T> tableView;
    private final JPagination pagination;
    private Runnable onScrollBottom;
    private boolean infiniteScrollEnabled = false;

    public JTable() {
        getStyleClass().add("j-table-wrapper");
        
        // TableView
        tableView = new TableView<>();
        tableView.getStyleClass().add("j-table-view");
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        VBox.setVgrow(tableView, Priority.ALWAYS);

        // Pagination
        pagination = new JPagination();
        
        getChildren().addAll(tableView, pagination);
        
        // Listen for scroll
        tableView.skinProperty().addListener((obs, oldSkin, newSkin) -> {
            if (newSkin != null) {
                tableView.lookupAll(".scroll-bar").stream()
                    .filter(node -> node instanceof ScrollBar)
                    .map(node -> (ScrollBar) node)
                    .filter(sb -> sb.getOrientation() == javafx.geometry.Orientation.VERTICAL)
                    .findFirst()
                    .ifPresent(sb -> {
                        sb.valueProperty().addListener((o, oldVal, newVal) -> {
                            if (infiniteScrollEnabled && newVal.doubleValue() == sb.getMax()) {
                                if (onScrollBottom != null) onScrollBottom.run();
                            }
                        });
                    });
            }
        });
    }
    
    public TableView<T> getLastTable() { return tableView; } // Access for columns
    public JPagination getPagination() { return pagination; }
    
    public void setItems(ObservableList<T> items) {
        tableView.setItems(items);
    }
    
    public void setOnScrollBottom(Runnable action) {
        this.onScrollBottom = action;
        this.infiniteScrollEnabled = true;
    }
    
    public void setPaginationVisible(boolean visible) {
        pagination.setVisible(visible);
        pagination.setManaged(visible);
    }
    
    public void addColumn(String title, String property) {
        javafx.scene.control.TableColumn<T, String> col = new javafx.scene.control.TableColumn<>(title);
        col.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>(property));
        tableView.getColumns().add(col);
    }
    
    public void addItem(T item) {
        tableView.getItems().add(item);
    }
}

