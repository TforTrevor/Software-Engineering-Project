package org.openjfx;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Arrays;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.controls.JFXScrollPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

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
    //VIEW IMAGES TAB
    private JFXButton viewTabButton;
    @FXML
    private AnchorPane viewImageTabPane;
    @FXML
    private ScrollPane imagesScrollPane;
    @FXML
    public JFXMasonryPane imageMasonryPane;
    @FXML
    public StackPane imageViewerPane;
    @FXML
    public ImageView imageViewerImageView;
    @FXML
    public JFXButton closeImageViewerButton;

    @FXML
    private JFXButton uploadTabButton;

    //SEARCH TAB
    @FXML
    private JFXButton searchTabButton;
    @FXML
    private AnchorPane searchTabPane;
    @FXML
    private Pane searchingImagesPane;
    @FXML
    private JFXButton searchImageButton;
    @FXML
    private JFXButton cancelSearchButton;

    @FXML
    private JFXButton shareTabButton;
    @FXML
    private JFXButton settingsTabButton;
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
    private Label consoleLabel;

    @FXML
    private JFXButton sendEmailButton;

    private ImageViewer imageViewer;

    private ArrayList<String> filesArr;
    private ArrayList<ImageView> images;
    private ArrayList<AnchorPane> tabPanes;
    private SearchImages searchImages = new SearchImages();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image image = new Image("file:"+"C:\\Users\\godbo\\OneDrive\\Pictures\\Desktop\\angrycat.png");
        System.out.println("Image loading error? " + image.isError());
        images=new ArrayList<ImageView>();
        tabPanes = new ArrayList<>(Arrays.asList(uploadTabPane, searchTabPane, shareTabPane, settingsTabPane));
        viewTabButton.setOnAction(this::ViewTabAction);
        uploadTabButton.setOnAction(this::UploadTabAction);
        searchTabButton.setOnAction(this::SearchTabAction);
        searchImageButton.setOnAction(this::SearchImageButtonAction);
        cancelSearchButton.setOnAction(this::CancelSearchButtonAction);
        settingsTabButton.setOnAction(this::SettingsTabAction);
        clearCacheButton.setOnAction(this::ClearCacheButtonAction);
        clearCacheAcceptButton.setOnAction(this::ClearCacheAcceptAction);
        clearCacheDenyButton.setOnAction(this::ClearCacheDenyAction);
        sendEmailButton.setOnAction(this::SendEmailButtonAction);
        imageViewer = new ImageViewer();
        imageViewer.Initialize(this);
    }

    public void WriteToConsole(String message) {
        if (consoleLabel.getText() != "") {
            consoleLabel.setText(consoleLabel.getText() + System.lineSeparator());
        }
        consoleLabel.setText(consoleLabel.getText() + message);
    }

    private void ViewTabAction(ActionEvent event) {
        ShowTab(viewImageTabPane);
        imageViewer.CreateImageElement();
    }

    private void UploadTabAction(ActionEvent event) {
        ShowTab(uploadTabPane);
    }

    private void SearchTabAction(ActionEvent event) {
        ShowTab(searchTabPane);
    }

    private void SearchImageButtonAction(ActionEvent event) {
        searchImages.SetFXMLController(this);
        searchImageButton.setDisable(true);
        if (!searchImages.RefreshImages(fromDate, toDate)) {
            invalidDatesLabel.setVisible(true);
        }
        else {
            searchingImagesPane.setVisible(true);
            invalidDatesLabel.setVisible(false);
        }
    }

    private void CancelSearchButtonAction(ActionEvent event) {
        searchImages.CancelSearch();
        filesArr = searchImages.GetImages();
        searchImageButton.setDisable(false);
        searchingImagesPane.setVisible(false);
    }

    private void SendEmailButtonAction(ActionEvent event) {
        EmailHelper emailHelper = new EmailHelper();
        emailHelper.AddRecipient("bgodbout2014@fau.edu");
        emailHelper.SetSubject("First Try");
        emailHelper.SetBody("WOOHOO");
        emailHelper.SendEmail();
        emailHelper.ClearRecipients();
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
