package org.openjfx;

import com.jfoenix.controls.JFXButton;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.List;

public class UploadImages {
    private JFXButton uploadButton;
    private Label uploadLabel;

    UploadImages(FXMLController fxmlController) {
        uploadButton = fxmlController.uploadImageButton;
        uploadButton.setOnAction((event) -> SelectImages());
        uploadLabel = fxmlController.uploadImageLabel;
    }

    private void SelectImages() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Images");
        ExtensionFilter extensionFilter = new ExtensionFilter("Images (*.png, *.jpg)", "*.png", "*.jpg");
        fileChooser.getExtensionFilters().add(extensionFilter);
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);
        if (selectedFiles != null) {
            XMLImageEditor xmlImageEditor = new XMLImageEditor();
            for (int i = 0; i < selectedFiles.size(); i++) {
                String path = selectedFiles.get(i).getAbsolutePath();
                path.replace("\\", "/");
                xmlImageEditor.CreateXMLImage("Test", FilenameUtils.removeExtension(selectedFiles.get(i).getName()), path);
            }
            uploadLabel.setText("Uploaded " + selectedFiles.size() + " images.");
        }
    }
}
