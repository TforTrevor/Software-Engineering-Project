package org.openjfx;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class FXMLController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private Label ToInvalid;
    @FXML
    private Label FromInvalid;
    @FXML
    private DatePicker ToDate;
    @FXML
    private DatePicker FromDate;
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
    private JFXButton uploadTab;
    @FXML
    private JFXButton searchTab;
    @FXML
    private JFXButton ShareTab;
    @FXML
    private JFXButton settingsTab;
    @FXML
    private JFXButton clearCacheButton;
    @FXML
    private JFXButton clearCacheAcceptButton;
    @FXML
    private JFXButton clearCacheDenyButton;
    @FXML
    private JFXButton searchImageButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //GaussianBlur blur = new GaussianBlur();
        //GaussianBlur blurRemove = new GaussianBlur();
        //blur.setRadius(10);
        //blurRemove.setRadius(0);

        uploadTab.setOnAction(this::UploadTabAction);
        searchTab.setOnAction(this::SearchTabAction);
        searchImageButton.setOnAction(this::SearchImageButtonAction);
        settingsTab.setOnAction(this::SettingsTabAction);
        clearCacheButton.setOnAction(this::ClearCacheButtonAction);
        clearCacheAcceptButton.setOnAction(this::ClearCacheAcceptAction);
        clearCacheDenyButton.setOnAction(this::ClearCacheDenyAction);
    }

    private void UploadTabAction(ActionEvent event) {
        SearchPane.setVisible(false);
        UploadPane.setVisible(true);
        SettingsPane.setVisible(false);
    }

    private void SearchTabAction(ActionEvent event) {
        SearchPane.setVisible(true);
        UploadPane.setVisible(false);
        SettingsPane.setVisible(false);
    }

    private void SearchImageButtonAction(ActionEvent event) {
        CalendarDate.CheckDate(ToDate, FromDate, ToInvalid, FromInvalid);
    }

    private void SettingsTabAction(ActionEvent event) {
        SearchPane.setVisible(false);
        UploadPane.setVisible(false);
        SettingsPane.setVisible(true);
    }

    private void ClearCacheButtonAction(ActionEvent event) {
        SettingsPane.setDisable(true);
        CachePane.setVisible(true);
        TabPane.setDisable(true);
        //SettingsPane.setEffect(blur);
    }

    private void ClearCacheAcceptAction(ActionEvent event) {
        SettingsPane.setDisable(false);
        CachePane.setVisible(false);
        TabPane.setDisable(false);
        //SettingsPane.setEffect(blurRemove);
        //TabPane.setEffect(blurRemove);
    }

    private void ClearCacheDenyAction(ActionEvent event) {
        SettingsPane.setDisable(false);
        CachePane.setVisible(false);
        TabPane.setDisable(false);
        //SettingsPane.setEffect(blurRemove);
        //TabPane.setEffect(blurRemove);
    }


}
