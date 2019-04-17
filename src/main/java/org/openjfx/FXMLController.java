package org.openjfx;

import com.jfoenix.controls.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
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
    JFXDatePicker toDate;
    @FXML
    JFXDatePicker fromDate;
    @FXML
    Label invalidDatesLabel;

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

    //HELP TAB
    @FXML
    private JFXButton helpTabButton;
    @FXML
    private AnchorPane helpTabPane;
    @FXML
    private JFXTabPane helpPane;

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

    private ImageViewer imageViewer;

    private ArrayList<AnchorPane> tabPanes;
    private SearchImages searchImages;

    private EmailHelper emailHelper;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tabPanes = new ArrayList<>(Arrays.asList(viewImageTabPane, uploadTabPane, searchTabPane, shareTabPane, helpTabPane, settingsTabPane));
        //VIEW IMAGES TAB
        imageViewer = new ImageViewer(this);
        viewTabButton.setOnAction((event) -> {
            ShowTab(viewImageTabPane);
            imageViewer.HideOffScreenImages();
        });
        //UPLOAD TAB
        UploadImages uploadImages = new UploadImages(this);
        uploadTabButton.setOnAction((event) -> ShowTab(uploadTabPane));
        //SEARCH TAB
        searchImages = new SearchImages(this);
        searchTabButton.setOnAction((event) -> ShowTab(searchTabPane));
        //SHARE TAB
        shareTabButton.setOnAction((event) -> ShowTab(shareTabPane));
        sendEmailButton.setOnAction(this::SendEmailButtonAction);
        //HELP TAB
        helpTabButton.setOnAction((event) -> ShowTab(helpTabPane));
        //SETTINGS TAB
        settingsTabButton.setOnAction((event) -> ShowTab(settingsTabPane));
        clearCacheButton.setOnAction(this::ClearCacheButtonAction);
        clearCacheAcceptButton.setOnAction(this::ClearCacheAcceptAction);
        clearCacheDenyButton.setOnAction(this::ClearCacheDenyAction);

        emailHelper = new EmailHelper();
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
        } else {
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
