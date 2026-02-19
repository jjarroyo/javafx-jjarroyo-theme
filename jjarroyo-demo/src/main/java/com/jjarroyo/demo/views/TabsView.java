package com.jjarroyo.demo.views;

import com.jjarroyo.components.JCard;
import com.jjarroyo.components.JTab;
import com.jjarroyo.components.JTabs;
import com.jjarroyo.components.JTabs.TabStyle;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class TabsView extends ScrollPane {

    public TabsView() {
        getStyleClass().add("base-view");
        setFitToWidth(true);
        setPadding(new Insets(20));

        VBox content = new VBox(30);
        content.setPadding(new Insets(0, 0, 50, 0));

        // 1. Horizontal Line Tabs
        JCard lineCard = new JCard("Horizontal Line Tabs", "Standard tabs with underline style");
        
        JTabs lineTabs = new JTabs();
        lineTabs.setTabStyle(TabStyle.LINE);
        
        lineTabs.getTabs().add(new JTab("Link 1", new Label("Content for Link 1 - Basic text or any node.")));
        lineTabs.getTabs().add(new JTab("Link 2", new Label("Content for Link 2 - More content here.")));
        lineTabs.getTabs().add(new JTab("Link 3", new Label("Content for Link 3 - Even more content.")));
        
        lineCard.setBody(lineTabs);
        content.getChildren().add(lineCard);

        // 2. Vertical Pills Tabs
        JCard pillsCard = new JCard("Vertical Pills Tabs", "Side tabs with description and background style");
        
        JTabs pillsTabs = new JTabs();
        pillsTabs.setTabStyle(TabStyle.PILLS);
        pillsTabs.setSide(Side.LEFT);
        
        pillsTabs.getTabs().add(new JTab("Link 1", "Description", new Label("Content for Link 1\n\nThis is a vertical tab setup.\nThe active tab has a light green background and green text.")));
        pillsTabs.getTabs().add(new JTab("Link 2", "Description", new Label("Content for Link 2\n\nCheck out the style on the left.")));
        pillsTabs.getTabs().add(new JTab("Link 3", "Description", new Label("Content for Link 3\n\nAnother tab content.")));
        
        pillsCard.setBody(pillsTabs);
        content.getChildren().add(pillsCard);
        
        // 3. Right Side Pills
        JCard rightPillsCard = new JCard("Right Side Pills", "Tabs positioned on the right");
        
        JTabs rightTabs = new JTabs();
        rightTabs.setTabStyle(TabStyle.PILLS);
        rightTabs.setSide(Side.RIGHT);
        
        rightTabs.getTabs().add(new JTab("Profile", "User Settings", new Label("Profile Settings Content")));
        rightTabs.getTabs().add(new JTab("Account", "Billing & Plans", new Label("Account Settings Content")));
        rightTabs.getTabs().add(new JTab("Security", "Password & 2FA", new Label("Security Settings Content")));
        
        rightPillsCard.setBody(rightTabs);
        content.getChildren().add(rightPillsCard);

        setContent(content);
    }
}


