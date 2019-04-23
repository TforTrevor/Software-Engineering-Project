package com.sep;

import com.jfoenix.controls.*;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class FXMLController implements Initializable {

    @FXML
    private BorderPane mainPane;
    @FXML
    private VBox tabPane;

    //VIEW IMAGES TAB
    @FXML
    JFXButton viewTabButton;
    @FXML
    AnchorPane viewImageTabPane;
    @FXML
    ScrollPane imagesScrollPane;
    @FXML
    JFXMasonryPane imageMasonryPane;
    @FXML
    StackPane imageViewerPane;
    @FXML
    ImageView imageViewerImageView;
    @FXML
    JFXButton closeImageViewerButton;
    @FXML
    HBox imageOptions;
    @FXML
    JFXButton imageOptionsShare;
    @FXML
    JFXButton imageOptionsRemove;
    @FXML
    JFXTextField imageViewerSearch;
    @FXML
    JFXButton imageViewerOpenButton;

    //UPLOAD TAB
    @FXML
    private JFXButton uploadTabButton;
    @FXML
    private AnchorPane uploadTabPane;
    @FXML
    JFXButton uploadImageButton;
    @FXML
    Label uploadImageLabel;

    //SEARCH TAB
    @FXML
    private JFXButton searchTabButton;
    @FXML
    private AnchorPane searchTabPane;
    @FXML
    Pane searchingImagesPane;
    @FXML
    JFXButton searchImageButton;
    @FXML
    JFXButton cancelSearchButton;
    @FXML
    JFXDatePicker endDatePicker;
    @FXML
    JFXDatePicker startDatePicker;
    @FXML
    Label invalidDatesLabel;

    //SHARE TAB
    //@FXML
    //private AnchorPane shareTabPane;
    @FXML
    StackPane sharePane;
    @FXML
    JFXButton closeShareButton;
    @FXML
    JFXTextField subjectTextField;
    @FXML
    JFXTextField recipientTextField;
    @FXML
    JFXTextArea bodyTextArea;
    @FXML
    JFXButton sendEmailButton;
    @FXML
    Label emailLabel;

    //HELP TAB
    @FXML
    private JFXButton helpTabButton;
    @FXML
    private AnchorPane helpTabPane;
    @FXML
    private JFXTabPane helpPane;
    @FXML
    Text viewImagesHelpText;
    @FXML
    Text uploadImagesHelpText;
    @FXML
    Text searchImagesHelpText;

    //SETTINGS TAB
    @FXML
    private JFXButton settingsTabButton;
    @FXML
    private AnchorPane settingsTabPane;
    @FXML
    AnchorPane clearCachePane;
    @FXML
    JFXButton clearCacheButton;
    @FXML
    JFXButton clearCacheAcceptButton;
    @FXML
    JFXButton clearCacheDenyButton;
    @FXML
    JFXButton chooseSearchFolder;
    @FXML
    Label searchDirectoryLabel;

    ImageViewer imageViewer;

    private ArrayList<AnchorPane> tabPanes;
    private ImageSearcher imageSearcher;
    private ShareImages shareImages;
    private UploadImages uploadImages;
    private Settings settings;
    private FeatureLock featureLock;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tabPanes = new ArrayList<>(Arrays.asList(viewImageTabPane, uploadTabPane, searchTabPane, helpTabPane, settingsTabPane));
        featureLock = new FeatureLock(this);
        //VIEW IMAGES TAB
        imageViewer = new ImageViewer(this);
        shareImages = new ShareImages(this);
        viewTabButton.setOnAction((event) -> {
            //imageViewer.HideOffScreenImages();
            imageViewer.CloseImage();
            imageViewer.ClearImages();
            imageViewer.GetXMLImages();
            imageViewer.ScrollCheck();
            imageViewer.SetLoadAllImages();
            ShowTab(viewImageTabPane);
        });
        //UPLOAD TAB
        uploadImages = new UploadImages(this);
        uploadTabButton.setOnAction((event) -> ShowTab(uploadTabPane));
        //SEARCH TAB
        imageSearcher = new ImageSearcher(this);
        searchTabButton.setOnAction((event) -> ShowTab(searchTabPane));
        //HELP TAB
        helpTabButton.setOnAction((event) -> {
            featureLock.UpdateViewText();
            ShowTab(helpTabPane);
        });
        featureLock.UpdateViewTab();
        //SETTINGS TAB
        settings = new Settings(this);
        settingsTabButton.setOnAction((event) -> ShowTab(settingsTabPane));
    }

    private void ShowTab(AnchorPane keepPane) {
        for (int i = 0; i < tabPanes.size(); i++) {
            if (tabPanes.get(i) != null) {
                if (tabPanes.get(i) == keepPane) {
                    tabPanes.get(i).setVisible(true);
                } else {
                    tabPanes.get(i).setVisible(false);
                }
            }
        }
    }
}
