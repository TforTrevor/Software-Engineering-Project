package org.openjfx;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class FXMLController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private VBox TabPane;
    @FXML
    private BorderPane mainPane;
    @FXML
    private AnchorPane UploadPane;
    @FXML
    private AnchorPane SearchPane;
    @FXML
    private AnchorPane SettingsPane;
    @FXML
    private AnchorPane CachePane;
    @FXML
    private JFXButton ViewTab;
    @FXML
    private JFXButton UploadTab;
    @FXML
    private JFXButton SearchTab;
    @FXML
    private JFXButton ShareTab;
    @FXML
    private JFXButton SettingsTab;
    @FXML
    private JFXButton CacheButton;
    @FXML
    private JFXButton ClearYes;
    @FXML
    private JFXButton ClearNo;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        GaussianBlur blur = new GaussianBlur();
        GaussianBlur blurRemove = new GaussianBlur();
        blur.setRadius(10);
        blurRemove.setRadius(0);
        UploadTab.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SearchPane.setVisible(false);
                UploadPane.setVisible(true);
                SettingsPane.setVisible(false);
            }
        });
        SearchTab.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SearchPane.setVisible(true);
                UploadPane.setVisible(false);
                SettingsPane.setVisible(false);
            }
        });
        SettingsTab.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SearchPane.setVisible(false);
                UploadPane.setVisible(false);
                SettingsPane.setVisible(true);
            }
        });
        CacheButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SettingsPane.setDisable(true);
                CachePane.setVisible(true);
                TabPane.setDisable(true);
                SettingsPane.setEffect(blur);
            }
        });
        ClearYes.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SettingsPane.setDisable(false);
                CachePane.setVisible(false);
                TabPane.setDisable(false);
                SettingsPane.setEffect(blurRemove);
                TabPane.setEffect(blurRemove);
            }
        });
        ClearNo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SettingsPane.setDisable(false);
                CachePane.setVisible(false);
                TabPane.setDisable(false);
                SettingsPane.setEffect(blurRemove);
                TabPane.setEffect(blurRemove);
            }
        });
    }

    public double GetMainPaneWidth() {
        return mainPane.getPrefWidth();
    }

    public double GetMainPaneHeight() {
        return mainPane.getPrefHeight();
    }
}
