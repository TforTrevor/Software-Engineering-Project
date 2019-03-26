package org.openjfx;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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
    private JFXButton viewTabButton;
    @FXML
    private JFXButton uploadTabButton;
    @FXML
    private JFXButton searchTabButton;
    @FXML
    private JFXButton shareTabButton;
    @FXML
    private JFXButton settingsTabButton;
    @FXML
    private AnchorPane viewTabPane;
    @FXML
    private AnchorPane uploadTabPane;
    @FXML
    private AnchorPane searchTabPane;
    @FXML
    private AnchorPane shareTabPane;
    @FXML
    private AnchorPane settingsTabPane;
    @FXML
    private AnchorPane clearCachePane;
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
        uploadTabButton.setOnAction(this::UploadTabAction);
        searchTabButton.setOnAction(this::SearchTabAction);
        searchImageButton.setOnAction(this::SearchImageButtonAction);
        settingsTabButton.setOnAction(this::SettingsTabAction);
        clearCacheButton.setOnAction(this::ClearCacheButtonAction);
        clearCacheAcceptButton.setOnAction(this::ClearCacheAcceptAction);
        clearCacheDenyButton.setOnAction(this::ClearCacheDenyAction);
    }

    private void UploadTabAction(ActionEvent event) {
        searchTabPane.setVisible(false);
        uploadTabPane.setVisible(true);
        settingsTabPane.setVisible(false);
    }

    private void SearchTabAction(ActionEvent event) {
        searchTabPane.setVisible(true);
        uploadTabPane.setVisible(false);
        settingsTabPane.setVisible(false);
    }

    private void SearchImageButtonAction(ActionEvent event) {
        CalendarDate.CheckDate(ToDate, FromDate, ToInvalid, FromInvalid);
    }

    private void SettingsTabAction(ActionEvent event) {
        searchTabPane.setVisible(false);
        uploadTabPane.setVisible(false);
        settingsTabPane.setVisible(true);
    }

    private void ClearCacheButtonAction(ActionEvent event) {
        settingsTabPane.setDisable(true);
        clearCachePane.setVisible(true);
        TabPane.setDisable(true);
    }

    private void ClearCacheAcceptAction(ActionEvent event) {
        settingsTabPane.setDisable(false);
        clearCachePane.setVisible(false);
        TabPane.setDisable(false);
    }

    private void ClearCacheDenyAction(ActionEvent event) {
        settingsTabPane.setDisable(false);
        clearCachePane.setVisible(false);
        TabPane.setDisable(false);
    }
}
