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

    protected SearchImages() {

    }

    private boolean CheckDate(DatePicker date) {
        LocalDate to;
        try {
            to = date.getValue();
            if (to == null) {
                throw new DateTimeParseException("Invalid Date", date.toString(), 0);
            }
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private String ConvertDateFormat(DatePicker date) {
        Format format = new SimpleDateFormat("MM/dd/yyyy");
        //toDate.getvalue returns yyyy/mm/dd
        java.util.Date tempTo = java.sql.Date.valueOf(date.getValue());
        return format.format(tempTo);
    }

    public boolean RefreshImages(DatePicker fromDate, DatePicker toDate) {
        if (!CheckDate(fromDate)) {
            return false;
        }
        if (!CheckDate(toDate)) {
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
        fileReader.SetRunThread(false);
        images = fileReader.GetImages();
    }

    public ArrayList<String> GetImages() {
        return (ArrayList<String>)images.clone();
    }
}
