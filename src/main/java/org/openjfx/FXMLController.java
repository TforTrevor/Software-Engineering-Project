package org.openjfx;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Arrays;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class FXMLController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private Label invalidDatesLabel;
    @FXML
    private DatePicker toDate;
    @FXML
    private DatePicker fromDate;
    @FXML
    private VBox TabPane;
    @FXML
    private BorderPane mainPane;
    @FXML
    private JFXButton viewTabButton;
    @FXML
    private JFXButton uploadTabButton;

    //SEARCH TAB
    @FXML
    private JFXButton searchTabButton;
    @FXML
    private AnchorPane searchTabPane;
    @FXML
    private JFXButton searchImageButton;
    @FXML
    private JFXButton cancelSearchButton;

    @FXML
    private JFXButton shareTabButton;
    @FXML
    private JFXButton settingsTabButton;
    @FXML
    private AnchorPane viewTabPane;
    @FXML
    private AnchorPane uploadTabPane;
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
    private ImageView resultsImage;

    private ArrayList<String> filesArr;
    private ArrayList<ImageView> images;
    private ArrayList<AnchorPane> tabPanes;
    private SearchImages searchImages = new SearchImages();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image image = new Image("file:"+"C:\\Users\\godbo\\OneDrive\\Pictures\\Desktop\\angrycat.png");
        System.out.println("Image loading error? " + image.isError());
        resultsImage.setImage(image);
        images=new ArrayList<ImageView>();
        tabPanes = new ArrayList<>(Arrays.asList(viewTabPane, uploadTabPane, searchTabPane, shareTabPane, settingsTabPane));
        uploadTabButton.setOnAction(this::UploadTabAction);
        searchTabButton.setOnAction(this::SearchTabAction);
        searchImageButton.setOnAction(this::SearchImageButtonAction);
        cancelSearchButton.setOnAction(this::CancelSearchButtonAction);
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
        searchImageButton.setDisable(true);
        if (!searchImages.RefreshImages(fromDate, toDate)) {
            invalidDatesLabel.setVisible(true);
        }
        else {
            //Display images
            invalidDatesLabel.setVisible(false);
        }
    }

    private void CancelSearchButtonAction(ActionEvent event) {
        searchImages.CancelSearch();
        filesArr = searchImages.GetImages();
        searchImageButton.setDisable(false);
    }

    private void SettingsTabAction(ActionEvent event) {
        ShowTab(settingsTabPane);
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

    private void ShowTab(AnchorPane keepPane) {
        for (int i = 0; i < tabPanes.size(); i++) {
            if (tabPanes.get(i) != null) {
                if (tabPanes.get(i) == keepPane) {
                    tabPanes.get(i).setVisible(true);
                }
                else {
                    tabPanes.get(i).setVisible(false);
                }
            }
        }
    }
}
