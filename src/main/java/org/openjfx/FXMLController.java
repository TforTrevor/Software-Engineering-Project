package org.openjfx;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.ArrayList;
import java.util.Arrays;

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
    private AnchorPane uploadTabPane;
    @FXML
    private JFXButton uploadTabButton;
    @FXML
    private AnchorPane searchTabPane;
    @FXML
    private JFXButton searchTabButton;
    @FXML
    private AnchorPane settingsTabPane;
    @FXML
    private JFXButton settingsTabButton;
    @FXML
    private AnchorPane CachePane;
    @FXML
    private JFXButton ViewTab;
    @FXML
    private JFXButton ShareTab;
    @FXML
    private JFXButton clearCacheButton;
    @FXML
    private JFXButton clearCacheAcceptButton;
    @FXML
    private JFXButton clearCacheDenyButton;
    @FXML
    private JFXButton searchImageButton;

    private ArrayList<AnchorPane> tabList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        tabList = new ArrayList<>(Arrays.asList(uploadTabPane, searchTabPane, settingsTabPane));

        uploadTabButton.setOnAction(this::UploadTabAction);

        searchTabButton.setOnAction(this::SearchTabAction);
        searchImageButton.setOnAction(this::SearchImageButtonAction);

        settingsTabButton.setOnAction(this::SettingsTabAction);
        clearCacheButton.setOnAction(this::ClearCacheButtonAction);
        clearCacheAcceptButton.setOnAction(this::ClearCacheAcceptAction);
        clearCacheDenyButton.setOnAction(this::ClearCacheDenyAction);
    }

    private void UploadTabAction(ActionEvent event) {
        ShowTab(uploadTabPane);
    }

    private void SearchTabAction(ActionEvent event) {
        ShowTab(searchTabPane);
    }

    private void SearchImageButtonAction(ActionEvent event) {
        CalendarDate.CheckDate(ToDate, FromDate, ToInvalid, FromInvalid);
    }

    private void SettingsTabAction(ActionEvent event) {
        ShowTab(settingsTabPane);
    }

    private void ClearCacheButtonAction(ActionEvent event) {
        settingsTabPane.setDisable(true);
        CachePane.setVisible(true);
        TabPane.setDisable(true);
    }

    private void ClearCacheAcceptAction(ActionEvent event) {
        settingsTabPane.setDisable(false);
        CachePane.setVisible(false);
        TabPane.setDisable(false);
    }

    private void ClearCacheDenyAction(ActionEvent event) {
        settingsTabPane.setDisable(false);
        CachePane.setVisible(false);
        TabPane.setDisable(false);
    }

    private void ShowTab(AnchorPane keepTab) {
        for (int i = 0; i < tabList.size(); i++) {
            if (tabList.get(i) == keepTab) {
                tabList.get(i).setVisible(true);
            }
            else {
                tabList.get(i).setVisible(false);
            }
        }
    }
}
