package org.openjfx;

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
import java.util.Date;


public class ImageSearcher {
    private ArrayList<String> imagePaths = new ArrayList<>();
    private Thread searchThread;
    private FileReader fileReader;

    private JFXButton searchImageButton;
    private JFXButton cancelSearchButton;
    private JFXDatePicker fromDate;
    private JFXDatePicker toDate;
    private Label invalidDatesLabel;
    private Pane searchingImagesPane;

    ImageSearcher(FXMLController fxmlController) {
        searchImageButton = fxmlController.searchImageButton;
        cancelSearchButton = fxmlController.cancelSearchButton;
        fromDate = fxmlController.fromDate;
        toDate = fxmlController.toDate;
        invalidDatesLabel = fxmlController.invalidDatesLabel;
        searchingImagesPane = fxmlController.searchingImagesPane;

        searchImageButton.setOnAction((event) -> SearchImageButtonAction());
        cancelSearchButton.setOnAction((event) -> CancelSearch());

        searchThread = new Thread(this::SearchThread);
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

        XMLSettingsEditor xmlSettingsEditor = new XMLSettingsEditor();
        Date startDate = java.sql.Date.valueOf(fromDate.getValue());
        Date endDate = java.sql.Date.valueOf(toDate.getValue());
        fileReader = new FileReader(startDate, endDate, xmlSettingsEditor.GetSearchPath());

        if (!searchThread.isAlive()) {
            searchThread.setDaemon(true);
            searchThread.start();
        }
        return true;
    }

    private void SearchThread() {
        try {
            fileReader.SetRunThread(true);
            fileReader.SearchImages();
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
