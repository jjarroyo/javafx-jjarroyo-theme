package com.jjarroyo.demo.views;

import com.jjarroyo.components.JCard;
import com.jjarroyo.components.JLabel;
import com.jjarroyo.components.JProgressBar;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;

public class ProgressView extends ScrollPane {

    public ProgressView() {
        VBox content = new VBox();
        content.setSpacing(24);
        content.setPadding(new Insets(24));
        
        setFitToWidth(true);
        setContent(content);
        
        // Page Title
        VBox pageHeader = new VBox();
        JLabel title = new JLabel("Progress")
            .withStyle("text-2xl", "font-bold", "text-slate-800");
        JLabel subtitle = new JLabel("Barras de progreso con estilos y tamaños")
            .withStyle("text-base", "text-slate-500");
        pageHeader.getChildren().addAll(title, subtitle);
        
        content.getChildren().add(pageHeader);
        
        // Basic Progress
        content.getChildren().add(new JCard("Progreso Básico", createBasicProgress()));
        
        // Indeterminate
        content.getChildren().add(new JCard("Indeterminado", createIndeterminateProgress()));
        
        // Colors
        content.getChildren().add(new JCard("Colores", createColorProgress()));
        
        // Sizes
        content.getChildren().add(new JCard("Tamaños", createSizeProgress()));
    }

    private javafx.scene.Node createBasicProgress() {
        VBox container = new VBox(20);
        
        // 50%
        Label l1 = new Label("50%");
        JProgressBar p1 = new JProgressBar(0.5);
        p1.setMaxWidth(Double.MAX_VALUE);
        
        // 75%
        Label l2 = new Label("75%");
        JProgressBar p2 = new JProgressBar(0.75);
        p2.setMaxWidth(Double.MAX_VALUE);
        
        container.getChildren().addAll(l1, p1, l2, p2);
        return container;
    }

    private javafx.scene.Node createIndeterminateProgress() {
        VBox container = new VBox(20);
        
        Label l1 = new Label("Cargando...");
        JProgressBar p1 = new JProgressBar(); // Indeterminate by default if no progress set or set to -1
        p1.setProgress(-1);
        p1.setMaxWidth(Double.MAX_VALUE);
        
        container.getChildren().addAll(l1, p1);
        return container;
    }
    
    private javafx.scene.Node createColorProgress() {
        VBox container = new VBox(20);
        
        for (JProgressBar.ProgressStyle style : JProgressBar.ProgressStyle.values()) {
            Label label = new Label(style.name());
            JProgressBar pb = new JProgressBar(0.6);
            pb.setProgressStyle(style);
            pb.setMaxWidth(Double.MAX_VALUE);
            container.getChildren().addAll(label, pb);
        }
        
        return container;
    }
    
    private javafx.scene.Node createSizeProgress() {
        VBox container = new VBox(20);
        
        Label l1 = new Label("Small");
        JProgressBar sm = new JProgressBar(0.4);
        sm.setSize(JProgressBar.ProgressSize.SM);
        sm.setMaxWidth(Double.MAX_VALUE);
        
        Label l2 = new Label("Default (Medium)");
        JProgressBar md = new JProgressBar(0.6);
        md.setSize(JProgressBar.ProgressSize.MD);
        md.setMaxWidth(Double.MAX_VALUE);
        
        Label l3 = new Label("Large");
        JProgressBar lg = new JProgressBar(0.8);
        lg.setSize(JProgressBar.ProgressSize.LG);
        lg.setMaxWidth(Double.MAX_VALUE);
        
        container.getChildren().addAll(l1, sm, l2, md, l3, lg);
        return container;
    }
}
