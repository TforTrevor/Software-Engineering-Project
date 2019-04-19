package org.openjfx;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class ShareImages {

    private HBox imageOptions;
    private JFXButton shareButton;

    private StackPane sharePane;
    private JFXButton closeShareButton;
    private JFXTextField recipientTextField;
    private JFXTextField subjectTextField;
    private JFXTextArea bodyTextArea;
    private JFXButton sendEmailButton;
    private Label emailLabel;

    private ImageViewer imageViewer;
    private EmailHelper emailHelper;

    ShareImages(FXMLController fxmlController) {
        imageOptions = fxmlController.imageOptions;
        shareButton = fxmlController.imageOptionsShare;
        sharePane = fxmlController.sharePane;
        closeShareButton = fxmlController.closeShareButton;
        recipientTextField = fxmlController.recipientTextField;
        subjectTextField = fxmlController.subjectTextField;
        bodyTextArea = fxmlController.bodyTextArea;
        sendEmailButton = fxmlController.sendEmailButton;
        emailLabel = fxmlController.emailLabel;
        imageViewer = fxmlController.imageViewer;

        shareButton.setOnAction((event) -> OpenSharePane());
        closeShareButton.setOnAction((event) -> CloseSharePane());
        sendEmailButton.setOnAction((event) -> SendEmail());

        emailHelper = new EmailHelper();
    }

    private void OpenSharePane() {
        closeShareButton.setVisible(true);
        JavaFXHelper.FadeIn(Duration.seconds(0.25), sharePane);
        JavaFXHelper.FadeOut(Duration.seconds(0.1), imageOptions);
        recipientTextField.clear();
        subjectTextField.clear();
        bodyTextArea.clear();
    }

    private void CloseSharePane() {
        emailLabel.setText("");
        closeShareButton.setVisible(false);
        JavaFXHelper.FadeOut(Duration.seconds(0.25), sharePane);
        JavaFXHelper.FadeIn(Duration.seconds(0.1), imageOptions);
    }

    private void SendEmail() {
        Thread emailThread = new Thread(() -> {
            Platform.runLater(() ->{
                sendEmailButton.setDisable(true);
            });
            String recipients = recipientTextField.getText();
            String[] parsedRecipients = recipients.split(",");
            boolean validEmails = true;
            for (String s : parsedRecipients) {
                if (!emailHelper.VerifyEmail(s)) {
                    validEmails = false;
                }
            }
            if (validEmails) {
                boolean success = emailHelper.RunEmail(imageViewer, parsedRecipients, subjectTextField, bodyTextArea);
                if (success) {
                    imageViewer.DeselectAllImages();
                    Platform.runLater(() -> {
                        sendEmailButton.setDisable(false);
                        emailLabel.setTextFill(Color.GRAY);
                        emailLabel.setText("Sent Successfully");
                        emailLabel.setVisible(true);
                    });
                } else {
                    Platform.runLater(() -> {
                        sendEmailButton.setDisable(false);
                        emailLabel.setTextFill(Color.RED);
                        emailLabel.setText("Error Sending");
                        emailLabel.setVisible(true);
                    });
                }
            } else {
                Platform.runLater(() -> {
                    sendEmailButton.setDisable(false);
                    emailLabel.setTextFill(Color.RED);
                    emailLabel.setText("Invalid Emails");
                    emailLabel.setVisible(true);
                });
            }
        });
        emailThread.setDaemon(true);
        emailThread.start();
    }
}
