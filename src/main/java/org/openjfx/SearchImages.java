package org.openjfx;

import javafx.scene.control.DatePicker;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import amazcart2.FileReader;

public class SearchImages {
    private SearchImages() {

    }
    private static boolean CheckDate(DatePicker date) {
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

    private static String ConvertDateFormat(DatePicker date) {
        Format format = new SimpleDateFormat("MM/dd/yyyy");
        //toDate.getvalue returns yyyy/mm/dd
        java.util.Date tempTo = java.sql.Date.valueOf(date.getValue());
        String formattedString = format.format(tempTo);
        return formattedString;
    }

    public static ArrayList<String> GetImages(DatePicker fromDate, DatePicker toDate) {
        if (CheckDate(fromDate) == false) {
            return null;
        }
        if (CheckDate(toDate) == false) {
            return null;
        }
        String stringFromDate = ConvertDateFormat(fromDate);
        String stringToDate = ConvertDateFormat(toDate);

        FileReader fileReader = new FileReader(stringFromDate, stringToDate,"C:\\");
        fileReader.RefreshImageList();

        return fileReader.GetImages();
    }

    private void ReadFiles() {
        FileReader testing = new FileReader("01/09/2019","01/11/2019","C:\\Users\\Jpc\\Documents\\NetBeansProjects");
        testing.RefreshImageList();
    }
}
