package org.openjfx;

import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import amazcart2.FileReader;

public class CalendarDate {

    public void CheckDate(DatePicker toDate, DatePicker fromDate, Label toDateError, Label fromDateError) {
                LocalDate to, from;
                try {
                    to = toDate.getValue();
                    if (to==null) {
                        throw new DateTimeParseException("Invalid Date", toDate.toString(), 0);
                    }
                    toDateError.setVisible(false);
                } catch (DateTimeParseException e) {
                    toDateError.setVisible(true);
                }
                try {
                    from = fromDate.getValue();
                    if (from==null) {
                        throw new DateTimeParseException("Invalid Date", fromDate.toString(), 0);
                    }
            fromDateError.setVisible(false);
        } catch (DateTimeParseException e) {
            fromDateError.setVisible(true);
        }
    }

    private void ReadFiles() {
        FileReader testing=new FileReader("01/09/2019","01/11/2019","C:\\Users\\Jpc\\Documents\\NetBeansProjects");
        testing.readFile();
    }
}
