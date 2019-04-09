package org.openjfx;

import amazcart2.FileReader;
import javafx.scene.control.DatePicker;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;


public class SearchImages {
    private ArrayList<String> images;
    private Thread searchThread;
    private FileReader fileReader;
    private FXMLController fxmlController;

    protected SearchImages() {

    }

    private boolean CheckDateValidity(DatePicker date) {
        try {
            if (date.getValue() == null) {
                throw new DateTimeParseException("Invalid Date", date.toString(), 0);
            }
            //fxmlController.WriteToConsole("Working");
            fxmlController.WriteToConsole("Valid Date: " + date.getValue().toString());
            return true;
        } catch (DateTimeParseException e) {
            fxmlController.WriteToConsole("Invalid Date");
            return false;
        }
    }

    private String ConvertDateFormat(DatePicker date) {
        Format format = new SimpleDateFormat("MM/dd/yyyy");
        //date.getvalue returns yyyy/mm/dd
        java.util.Date tempDate = java.sql.Date.valueOf(date.getValue());
        return format.format(tempDate);
    }

    public boolean RefreshImages(DatePicker fromDate, DatePicker toDate) {

        if (!CheckDateValidity(fromDate)) {
            return false;
        }
        if (!CheckDateValidity(toDate)) {
            return false;
        }

        String stringFromDate = ConvertDateFormat(fromDate);
        String stringToDate = ConvertDateFormat(toDate);

        fileReader = new FileReader(stringFromDate, stringToDate,"C:\\");
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
        }
        catch(Exception e) {

        }
        finally {
            images = fileReader.GetImages();
        }
    }

    public void CancelSearch() {
        if (searchThread != null) {
            fileReader.SetRunThread(false);
            images = fileReader.GetImages();
        }
    }

    public ArrayList<String> GetImages() {
        return (ArrayList<String>)images.clone();
    }

    public void SetFXMLController(FXMLController fxmlController) {
        this.fxmlController = fxmlController;
    }
}
