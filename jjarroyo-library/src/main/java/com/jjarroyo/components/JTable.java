package com.jjarroyo.components;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.Set;
import javafx.collections.ListChangeListener;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;

public class JTable<T> extends VBox {

    private final TableView<T> tableView;
    private final JPagination pagination;
    private Runnable onScrollBottom;
    private boolean infiniteScrollEnabled = false;
    
    private ObservableList<T> allItems = FXCollections.observableArrayList();
    private final IntegerProperty itemsPerPage = new SimpleIntegerProperty(10);
    private final ListChangeListener<T> itemsListener = c -> {
        updatePagination();
        updateTableData();
    };

    public JTable() {
        getStyleClass().add("j-table-wrapper");
        
        // TableView
        tableView = new TableView<>();
        tableView.getStyleClass().add("j-table-view");
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        VBox.setVgrow(tableView, Priority.ALWAYS);

        // Pagination
        pagination = new JPagination();
        pagination.setOnPageChange(this::updateTableData);
        
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
        if (this.allItems != null) {
            this.allItems.removeListener(itemsListener);
        }
        this.allItems = items != null ? items : FXCollections.observableArrayList();
        this.allItems.addListener(itemsListener);
        
        updatePagination();
        updateTableData();
    }
    
    public void setItemsPerPage(int items) {
        this.itemsPerPage.set(items);
        updatePagination();
        updateTableData();
    }
    
    private void updatePagination() {
        int total = allItems.size();
        int pages = (int) Math.ceil((double) total / itemsPerPage.get());
        if (pages == 0) pages = 1;
        pagination.totalPagesProperty().set(pages);
    }

    private void updateTableData() {
        int page = pagination.currentPageProperty().get();
        int totalPages = pagination.totalPagesProperty().get();
        
        if (page > totalPages) {
            page = totalPages;
            if (page < 1) page = 1;
            pagination.currentPageProperty().set(page);
        }
        
        int fromIndex = (page - 1) * itemsPerPage.get();
        int toIndex = Math.min(fromIndex + itemsPerPage.get(), allItems.size());
        
        if (fromIndex <= toIndex && fromIndex < allItems.size() && fromIndex >= 0) {
            tableView.setItems(FXCollections.observableArrayList(allItems.subList(fromIndex, toIndex)));
        } else {
            tableView.setItems(FXCollections.observableArrayList());
        }
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
        if (allItems != null) {
            allItems.add(item);
        } else {
            tableView.getItems().add(item);
        }
    }
}

