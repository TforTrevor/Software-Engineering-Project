package org.openjfx;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class FXMLController implements Initializable {
    
    @FXML
    private Label label;

    @FXML
    private Button button;

    @FXML
    private StackPane mainPane;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        label.setText("Hello, JavaFX " + javafxVersion + "\nRunning on Java " + javaVersion + ".");
    }

    public double GetMainPaneWidth() {
        return mainPane.getPrefWidth();
    }

    public double GetMainPaneHeight() {
        return mainPane.getPrefHeight();
    }
}
