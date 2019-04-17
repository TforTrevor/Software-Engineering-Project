package org.openjfx;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.File;

public class Settings {

    private JFXButton clearCacheButton;
    private JFXButton chooseFolder;
    private JFXButton clearCacheAccept;
    private JFXButton clearCacheDeny;
    private AnchorPane clearCachePane;

    Settings(FXMLController fxmlController) {
        clearCacheButton = fxmlController.clearCacheButton;
        chooseFolder = fxmlController.chooseSearchFolder;
        clearCacheAccept = fxmlController.clearCacheAcceptButton;
        clearCacheDeny = fxmlController.clearCacheDenyButton;
        clearCachePane = fxmlController.clearCachePane;

        clearCacheButton.setOnAction((event) -> ShowCachePrompt());
        clearCacheAccept.setOnAction((event) -> ClearCache());
        clearCacheDeny.setOnAction((event) -> HideCachePrompt());
        chooseFolder.setOnAction((event) -> ChooseFolder());
    }

//    private void ClearCacheButtonAction(ActionEvent event) {
//        settingsTabPane.setDisable(true);
//        clearCachePane.setVisible(true);
//        tabPane.setDisable(true);
//    }
//
//    private void ClearCacheAcceptAction(ActionEvent event) {
//        settingsTabPane.setDisable(false);
//        clearCachePane.setVisible(false);
//        tabPane.setDisable(false);
//    }
//
//    private void ClearCacheDenyAction(ActionEvent event) {
//        settingsTabPane.setDisable(false);
//        clearCachePane.setVisible(false);
//        tabPane.setDisable(false);
//    }

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

    }
}
