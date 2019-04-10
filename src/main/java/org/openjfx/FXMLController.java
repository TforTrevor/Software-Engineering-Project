package org.openjfx;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;

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
    private JFXButton viewTabButton;
    @FXML
    private AnchorPane viewImageTabPane;
    @FXML
    public ScrollPane imagesScrollPane;
    @FXML
    private JFXButton uploadTabButton;
    @FXML
    JFXMasonryPane imageMasonryPane;
    @FXML
    StackPane imageViewerPane;
    @FXML
    ImageView imageViewerImageView;
    @FXML
    JFXButton closeImageViewerButton;

    //UPLOAD TAB
    @FXML
    private AnchorPane uploadTabPane;

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
    private DatePicker toDate;
    @FXML
    private DatePicker fromDate;
    @FXML
    private Label invalidDatesLabel;

    //SHARE TAB
    @FXML
    private JFXButton shareTabButton;
    @FXML
    private AnchorPane shareTabPane;
    @FXML
    private JFXButton sendEmailButton;
    @FXML
    private JFXTextField subjectTextField;
    @FXML
    private JFXTextField recipientTextField;
    @FXML
    private JFXTextArea bodyTextArea;
    @FXML
    private Label emailLabel;

    //SETTINGS TAB
    @FXML
    private JFXButton settingsTabButton;
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
    private Text consoleLabel;
    private ImageViewer imageViewer;

    private ArrayList<String> filesArr;
    private ArrayList<ImageView> images;
    private ArrayList<AnchorPane> tabPanes;
    private SearchImages searchImages = new SearchImages();

    private EmailHelper emailHelper;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image image = new Image("file:" + "C:\\Users\\godbo\\OneDrive\\Pictures\\Desktop\\angrycat.png");
        System.out.println("Image loading error? " + image.isError());
        images = new ArrayList<ImageView>();
        tabPanes = new ArrayList<>(Arrays.asList(viewImageTabPane, uploadTabPane, searchTabPane, shareTabPane, settingsTabPane));
        //VIEW IMAGES TAB
        viewTabButton.setOnAction((event) -> ShowTab(viewImageTabPane));
        //UPLOAD TAB
        uploadTabButton.setOnAction((event) -> ShowTab(uploadTabPane));
        //SEARCH TAB
        searchTabButton.setOnAction((event) -> ShowTab(searchTabPane));
        searchImageButton.setOnAction(this::SearchImageButtonAction);
        cancelSearchButton.setOnAction(this::CancelSearchButtonAction);
        //SHARE TAB
        shareTabButton.setOnAction((event) -> ShowTab(shareTabPane));
        sendEmailButton.setOnAction(this::SendEmailButtonAction);
        //SETTINGS TAB
        settingsTabButton.setOnAction((event) -> ShowTab(settingsTabPane));
        clearCacheButton.setOnAction(this::ClearCacheButtonAction);
        clearCacheAcceptButton.setOnAction(this::ClearCacheAcceptAction);
        clearCacheDenyButton.setOnAction(this::ClearCacheDenyAction);

        imageViewer = new ImageViewer();
        imageViewer.Initialize(this);
        emailHelper = new EmailHelper();
    }

    public void WriteToConsole(String message) {
        if (!consoleLabel.getText().equals("")) {
            consoleLabel.setText(consoleLabel.getText() + System.lineSeparator());
        }
        consoleLabel.setText(consoleLabel.getText() + message);
    }

    private void SearchImageButtonAction(ActionEvent event) {
        searchImages.SetFXMLController(this);
        searchImageButton.setDisable(true);
        if (!searchImages.RefreshImages(fromDate, toDate)) {
            invalidDatesLabel.setVisible(true);
        } else {
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
        String recipients = recipientTextField.getText();
        String[] parsedRecipients = recipients.split(",");
        boolean validEmails = true;
        for (String s : parsedRecipients) {
            if (!emailHelper.VerifyEmail(s)) {
                validEmails = false;
            }
        }
        if (validEmails) {
            for (String s : parsedRecipients) {
                emailHelper.AddRecipient(s);
            }
            emailHelper.SetSubject(subjectTextField.getText());
            emailHelper.SetBody(bodyTextArea.getText());
            emailHelper.SendEmail();
            emailHelper.ClearAll();
            emailLabel.setTextFill(Color.web("#000000"));
            emailLabel.setText("Sent Successfully");
            emailLabel.setVisible(true);
        }
        else {
            emailLabel.setTextFill(Color.web("#FF0000"));
            emailLabel.setText("Invalid Emails");
            emailLabel.setVisible(true);
        }
    }

    private void ClearCacheButtonAction(ActionEvent event) {
        settingsTabPane.setDisable(true);
        clearCachePane.setVisible(true);
        tabPane.setDisable(true);
    }

    private void ClearCacheAcceptAction(ActionEvent event) {
        settingsTabPane.setDisable(false);
        clearCachePane.setVisible(false);
        tabPane.setDisable(false);
    }

    private void ClearCacheDenyAction(ActionEvent event) {
        settingsTabPane.setDisable(false);
        clearCachePane.setVisible(false);
        tabPane.setDisable(false);
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
