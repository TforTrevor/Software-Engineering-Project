package org.openjfx;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;


public class ImageSearcher {
    private ArrayList<String> imagePaths = new ArrayList<>();
    private Thread searchThread;
    private FileReader fileReader;

    private JFXButton searchImageButton;
    private JFXButton cancelSearchButton;
    private JFXDatePicker startDatePicker;
    private JFXDatePicker endDatePicker;
    private Label invalidDatesLabel;
    private Pane searchingImagesPane;
    
    ImageSearcher(FXMLController fxmlController) {
        searchImageButton = fxmlController.searchImageButton;
        cancelSearchButton = fxmlController.cancelSearchButton;
        startDatePicker = fxmlController.startDatePicker;
        endDatePicker = fxmlController.endDatePicker;
        invalidDatesLabel = fxmlController.invalidDatesLabel;
        searchingImagesPane = fxmlController.searchingImagesPane;

        searchImageButton.setOnAction((event) -> SearchImageButtonAction());
        cancelSearchButton.setOnAction((event) -> CancelSearch());

        searchThread = new Thread(this::SearchThread);
    }

    private void SearchImageButtonAction() {
        searchImageButton.setDisable(true);
        if (!SearchImages(startDatePicker, endDatePicker)) {
            invalidDatesLabel.setVisible(true);
            searchImageButton.setDisable(false);
        } else {
            searchingImagesPane.setVisible(true);
            invalidDatesLabel.setVisible(false);
        }
    }

    private boolean CheckDateValidity(DatePicker date) {
        try {
            if (date.getValue() == null) {
                throw new DateTimeParseException("Invalid Date", date.toString(), 0);
            }
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private boolean SearchImages(DatePicker startDate, DatePicker endDate) {

        if (!CheckDateValidity(startDate)) {
            return false;
        }
        if (!CheckDateValidity(endDate)) {
            return false;
        }

        if (!searchThread.isAlive()) {
            searchThread = new Thread(this::SearchThread);
            searchThread.setDaemon(true);
            searchThread.start();
        }
        return true;
    }

    private void SearchThread() {
        try {
            XMLSettingsEditor xmlSettingsEditor = new XMLSettingsEditor();
            Date startDate = java.sql.Date.valueOf(this.startDatePicker.getValue());
            Date endDate = java.sql.Date.valueOf(this.endDatePicker.getValue());
            fileReader = new FileReader(startDate, endDate, xmlSettingsEditor.GetSearchPath());
            fileReader.SetRunThread(true);
            fileReader.SearchImages();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CancelSearch();
        }
    }

    private void CancelSearch() {
        fileReader.SetRunThread(false);
        ArrayList<File> files = fileReader.GetFiles();
        imagePaths.clear();
        for (int i = 0; i < files.size(); i++) {
            imagePaths.add(files.get(i).getAbsolutePath());
        }

        XMLImageEditor xmlImageEditor = new XMLImageEditor();
        for (int i = 0; i < imagePaths.size(); i++) {
            File file = new File(imagePaths.get(i));
            xmlImageEditor.CreateXMLImage("Test", FilenameUtils.removeExtension(file.getName()), file.getAbsolutePath());
        }
        searchImageButton.setDisable(false);
        searchingImagesPane.setVisible(false);
    }
}
