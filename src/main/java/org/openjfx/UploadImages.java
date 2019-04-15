package org.openjfx;

import com.jfoenix.controls.JFXButton;

public class UploadImages {
    private JFXButton uploadButton;

    UploadImages(FXMLController fxmlController) {
        uploadButton = fxmlController.uploadImageButton;
        uploadButton.setOnAction((event) -> SelectImages());
    }

    private void SelectImages() {

    }
}
