package org.openjfx;

import amazcart2.FileReader;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;


public class ImageSearcher {
    private ArrayList<String> images;
    private Thread searchThread;
    private FileReader fileReader;

    private JFXButton searchImageButton;
    private JFXButton cancelSearchButton;
    private JFXDatePicker fromDate;
    private JFXDatePicker toDate;
    private Label invalidDatesLabel;
    private Pane searchingImagesPane;

    private String imageSearchPath;

    ImageSearcher(FXMLController fxmlController) {
        searchImageButton = fxmlController.searchImageButton;
        cancelSearchButton = fxmlController.cancelSearchButton;
        fromDate = fxmlController.fromDate;
        toDate = fxmlController.toDate;
        invalidDatesLabel = fxmlController.invalidDatesLabel;
        searchingImagesPane = fxmlController.searchingImagesPane;

        searchImageButton.setOnAction((event) -> SearchImageButtonAction());
        cancelSearchButton.setOnAction((event) -> CancelSearch());
    }

    private void SearchImageButtonAction() {
        searchImageButton.setDisable(true);
        if (!SearchImages(fromDate, toDate)) {
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

    private String ConvertDateFormat(DatePicker date) {
        Format format = new SimpleDateFormat("MM/dd/yyyy");
        //date.getvalue returns yyyy/mm/dd
        java.util.Date tempDate = java.sql.Date.valueOf(date.getValue());
        return format.format(tempDate);
    }

    private boolean SearchImages(DatePicker fromDate, DatePicker toDate) {

        if (!CheckDateValidity(fromDate)) {
            return false;
        }
        if (!CheckDateValidity(toDate)) {
            return false;
        }

        String stringFromDate = ConvertDateFormat(fromDate);
        String stringToDate = ConvertDateFormat(toDate);

        XMLSettingsEditor xmlSettingsEditor = new XMLSettingsEditor();
        fileReader = new FileReader(stringFromDate, stringToDate, xmlSettingsEditor.GetSearchPath());
        searchThread = new Thread(this::SearchThread);
        if (!searchThread.isAlive()) {
            searchThread.start();
        }

        return true;
    }

    private void SearchThread() {
        try {
            fileReader.SetRunThread(true);
            fileReader.SearchImages();
        } finally {
            images = fileReader.GetImages();
            CancelSearch();
        }
    }

    private void CancelSearch() {
        fileReader.SetRunThread(false);
        images = fileReader.GetImages();

        XMLImageEditor xmlImageEditor = new XMLImageEditor();
        for (int i = 0; i < images.size(); i++) {
            String path = images.get(i);
            path.replace("\\", "/");
            File file = new File(path);
            xmlImageEditor.CreateXMLImage("Test", FilenameUtils.removeExtension(file.getName()), path);
        }

        searchImageButton.setDisable(false);
        searchingImagesPane.setVisible(false);
    }

    public ArrayList<String> GetImages() {
        return (ArrayList<String>) images.clone();
    }
}