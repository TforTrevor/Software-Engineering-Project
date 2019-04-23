package com.sep;

import com.jfoenix.controls.JFXButton;
import javafx.scene.text.Text;

public class FeatureLock {

    private Text viewImagesHelpText;
    static JFXButton viewTabButton;

    XMLImageEditor xmlImageEditor = new XMLImageEditor();

    FeatureLock(FXMLController fxmlController) {
        viewImagesHelpText = fxmlController.viewImagesHelpText;
        viewTabButton = fxmlController.viewTabButton;
    }

    void UpdateViewText() {
        if (xmlImageEditor.imagesDocumentExists()) {
            viewImagesHelpText.setText("You can view images by clicking on the view images tab on the left.\nYou can select multiple images at a time by clicking on multiple check boxes.\nYou can select images by click on the check box in the upper left of every image.\nYou can press refresh images to refresh the image viewer.\nYou can view images by clicking on the view images tab on the left.");
        } else {
            viewImagesHelpText.setText("Right now, you have no images to view.\nUse the search images and upload images tabs to add some.");
        }
    }

    void UpdateViewTab() {
        if (xmlImageEditor.imagesDocumentExists()) {
            viewTabButton.setDisable(false);
        } else {
            viewTabButton.setDisable(true);
        }
    }

    static void EnableViewTab() {
        viewTabButton.setDisable(false);
    }


}

