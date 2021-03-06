package com.sep;

import com.jfoenix.controls.JFXButton;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.util.Duration;

import java.io.File;

public class Settings {

    private JFXButton clearCacheButton;
    private JFXButton chooseFolder;
    private JFXButton clearCacheAccept;
    private JFXButton clearCacheDeny;
    private AnchorPane clearCachePane;
    private Label directoryLabel;

    Settings(FXMLController fxmlController) {
        clearCacheButton = fxmlController.clearCacheButton;
        chooseFolder = fxmlController.chooseSearchFolder;
        clearCacheAccept = fxmlController.clearCacheAcceptButton;
        clearCacheDeny = fxmlController.clearCacheDenyButton;
        clearCachePane = fxmlController.clearCachePane;
        directoryLabel = fxmlController.searchDirectoryLabel;

        clearCacheButton.setOnAction((event) -> ShowCachePrompt());
        clearCacheAccept.setOnAction((event) -> ClearCache());
        clearCacheDeny.setOnAction((event) -> HideCachePrompt());
        chooseFolder.setOnAction((event) -> ChooseFolder());

        XMLSettingsEditor xmlSettingsEditor = new XMLSettingsEditor();
        directoryLabel.setText(xmlSettingsEditor.GetSearchPath());
    }

    private void ShowCachePrompt() {
        JavaFXHelper.FadeIn(Duration.seconds(0.1), clearCachePane);
    }

    private void HideCachePrompt() {
        JavaFXHelper.FadeOut(Duration.seconds(0.1), clearCachePane);
    }

    private void ClearCache() {
        File file = new File("images.xml");
        if (file.delete()) {
            System.out.println("File deleted");
        }
        else {
            System.out.println("File could not be deleted");
        }
        JavaFXHelper.FadeOut(Duration.seconds(0.1), clearCachePane);
    }

    private void ChooseFolder() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Directory");
        File selectedDirectory = directoryChooser.showDialog(null);
        if (selectedDirectory != null) {
            directoryLabel.setText(selectedDirectory.getAbsolutePath());
            XMLSettingsEditor xmlSettingsEditor = new XMLSettingsEditor();
            xmlSettingsEditor.SetSearchPath(selectedDirectory.getAbsolutePath());
        }
    }
}
