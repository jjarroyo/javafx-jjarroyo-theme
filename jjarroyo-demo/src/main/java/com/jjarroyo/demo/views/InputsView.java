package com.jjarroyo.demo.views;

import com.jjarroyo.components.JCard;
import com.jjarroyo.components.JLabel;
import com.jjarroyo.components.JInput;
import com.jjarroyo.components.JPasswordInput;
import com.jjarroyo.components.JInputGroup;
import com.jjarroyo.components.JButton;
import com.jjarroyo.components.JIcon;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.FlowPane;

public class InputsView extends ScrollPane {

    public InputsView() {
        VBox content = new VBox();
        content.setSpacing(24);
        content.setPadding(new Insets(24));
        
        setFitToWidth(true);
        setContent(content);
        
        // Page Title
        VBox pageHeader = new VBox();
        JLabel title = new JLabel("Inputs")
            .withStyle("text-2xl", "font-bold", "text-slate-800");
        JLabel subtitle = new JLabel("Estilos de inputs, grupos y validaciones")
            .withStyle("text-base", "text-slate-500");
        pageHeader.getChildren().addAll(title, subtitle);
        
        content.getChildren().add(pageHeader);
        
        // Basic Inputs
        content.getChildren().add(new JCard("Inputs Básicos", createBasicInputs()));

        // Sizes
        content.getChildren().add(new JCard("Tamaños", createSizes()));

        // Input Groups
        content.getChildren().add(new JCard("Input Groups", createInputGroups()));
        
        // Password
        content.getChildren().add(new JCard("Password Input", createPasswordInput()));

        // Text Area
        content.getChildren().add(new JCard("Text Area", createTextArea()));

        // Validation States
        content.getChildren().add(new JCard("Estados de Validación", createValidationInputs()));
    }

    private javafx.scene.Node createValidationInputs() {
        VBox container = new VBox(16);
        
        JInput valid = new JInput("Input válido");
        valid.setStatus("form-input-success");
        container.getChildren().add(valid.createWithLabel("Success", false));
        
        JInput invalid = new JInput("Error en el campo");
        invalid.setStatus("form-input-danger");
        container.getChildren().add(invalid.createWithLabel("Danger", true));
        
        JInput warning = new JInput("Advertencia");
        warning.setStatus("form-input-warning");
        container.getChildren().add(warning.createWithLabel("Warning", false));

        JInput dark = new JInput("Dark Focus");
        dark.setStatus("form-input-dark");
        container.getChildren().add(dark.createWithLabel("Dark Focus", false));
        
        return container;
    }

    private javafx.scene.Node createBasicInputs() {
        VBox container = new VBox(16);
        
        JInput input1 = new JInput("Ejemplo de placeholder...");
        container.getChildren().add(input1.createWithLabel("Label Básico", false));
        
        JInput input2 = new JInput("Campo requerido...");
        container.getChildren().add(input2.createWithLabel("Campo Requerido", true));
        
        JInput input3 = new JInput("Fondo transparente...");
        input3.addClass("form-input-transparent");
        container.getChildren().add(input3.createWithLabel("Input Transparente", false));
        
        return container;
    }
    
    private javafx.scene.Node createSizes() {
        VBox container = new VBox(16);
        
        JInput inputSm = new JInput("Small Input");
        inputSm.addClass("form-input-sm");
        
        JInput inputMd = new JInput("Default Input");
        
        JInput inputLg = new JInput("Large Input");
        inputLg.addClass("form-input-lg");
        
        container.getChildren().addAll(inputSm, inputMd, inputLg);
        return container;
    }

    private javafx.scene.Node createInputGroups() {
        VBox container = new VBox(16);
        
        // Button addon
        JInputGroup group1 = new JInputGroup();
        JInput input1 = new JInput("Buscar...");
        JButton btn1 = new JButton("Search", JIcon.SETTINGS); // Placeholder icon
        group1.add(input1, btn1);
        
        // Text addon (using simplified Label for now, ideally would contain a wrapping HBox for styling)
        // For simplicity, we can use a disabled button or a styled label as addon
        JInputGroup group2 = new JInputGroup();
        JButton addon = new JButton("https://");
        addon.addClass("btn-secondary"); // Use secondary style for addon look
        JInput input2 = new JInput("website.com");
        group2.add(addon, input2);
        
        container.getChildren().addAll(group1, group2);
        return container;
    }
    
    private javafx.scene.Node createPasswordInput() {
        VBox container = new VBox(16);
        
        JPasswordInput pass = new JPasswordInput("Ingrese contraseña...");
        container.getChildren().add(pass.createWithLabel("Contraseña", true));
        
        return container;
    }

    private javafx.scene.Node createTextArea() {
        VBox container = new VBox(16);
        
        com.jjarroyo.components.JTextArea area1 = new com.jjarroyo.components.JTextArea();
        area1.setPromptText("Escribe un comentario largo...");
        container.getChildren().add(area1.createWithLabel("Biografía", false));
        
        com.jjarroyo.components.JTextArea area2 = new com.jjarroyo.components.JTextArea();
        area2.setText("Texto predefinido...\nSegunda línea...");
        area2.setStatus("form-input-success");
        container.getChildren().add(area2.createWithLabel("Descripción (Validado)", true));
        
        return container;
    }
}


