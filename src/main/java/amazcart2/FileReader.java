package amazcart2;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FileReader {
    private String date;
    //"12/13/2018";
    private String startDate;
    //="12/01/2017";
    private String endDate;
    //="02/17/2018";
    private String fileSearchLocation;
    //="C:\\";
    private ArrayList files;

    public FileReader(String startingDay, String endingDay, String fileSearchingLocation) {

        startDate = startingDay;

        endDate = endingDay;

        fileSearchLocation = fileSearchingLocation;

        files = new ArrayList<String>();
    }

    public void RefreshImageList() {
        String fileLocation = "";

        while (new File(fileLocation).exists()) {
            fileLocation = fileLocation + "../";
        }
        File folder = new File(fileSearchLocation);

        filesInFolder(folder);
    }

    public void filesInFolder(final File folder) {
        for (final File file : folder.listFiles()) {
            try {
                if (file.isDirectory()) {
                    filesInFolder(file);

                } else if (file.getName().toLowerCase().contains(".jpg") || file.getName().toLowerCase().contains(".png")) {
                    Path file2 = Paths.get(file.getAbsolutePath());
                    BasicFileAttributes attr = Files.readAttributes(file2, BasicFileAttributes.class);

                    String endDate3 = convertTime(endDate);
                    String startDate3 = convertTime(startDate);
                    Integer endYear = Integer.parseInt(endDate3.substring(0, 4));
                    Integer startYear = Integer.parseInt(startDate3.substring(0, 4));
                    if (endYear.equals(startYear + 1)) {
                        String endDate1 = "12/31/" + startYear;
                        String startDate2 = "01/01/" + endYear;
                        if (filter2(attr.creationTime().toString(), convertTime(startDate), convertTime(endDate1)) == true) {

                            files.add(file.getAbsolutePath() + file.getName());
                        }
                        if (filter2(attr.creationTime().toString(), convertTime(startDate2), convertTime(endDate)) == true) {
                            files.add(file.getAbsolutePath() + file.getName());
                        }

                    } else {
                        if (filter2(attr.creationTime().toString(), convertTime(startDate), convertTime(endDate)) == true) {
                            files.add(file.getAbsolutePath() + file.getName());
                        }
                    }
                }

            } catch (Exception ignore) {
            }

        }
    }

    private boolean filter(String lastModified, String inputDate) {
        if (Integer.parseInt(lastModified.substring(0, 4)) == Integer.parseInt(inputDate.substring(0, 4))) {
            if (Integer.parseInt(lastModified.substring(5, 7)) >= Integer.parseInt(inputDate.substring(5, 7)) && Integer.parseInt(lastModified.substring(5, 7)) <= Integer.parseInt(inputDate.substring(5, 7)) + 2) {
                if (Integer.parseInt(lastModified.substring(8, 10)) >= Integer.parseInt(inputDate.substring(8, 10))) {
                    return true;
                }
            }
        } else if (Integer.parseInt(lastModified.substring(0, 4)) == Integer.parseInt(inputDate.substring(0, 4)) + 1) {
            if (Integer.parseInt(lastModified.substring(5, 7)) <= 12 - Integer.parseInt(inputDate.substring(5, 7)) + 2) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<String> GetImages() {
        return files;
    }

    private boolean filter2(String modifiedDate, String startDate, String endDate) {

        Integer modYear = Integer.parseInt(modifiedDate.substring(0, 4));
        Integer startYear = Integer.parseInt(startDate.substring(0, 4));
        Integer endYear = Integer.parseInt(endDate.substring(0, 4));
        Integer modMonth = Integer.parseInt(modifiedDate.substring(5, 7));
        Integer startMonth = Integer.parseInt(startDate.substring(5, 7));
        Integer endMonth = Integer.parseInt(endDate.substring(5, 7));
        if (modYear.equals(startYear) || modYear.equals(endYear)) {
            if (modMonth > startMonth && modMonth < endMonth) {
                return true;
            } else if (modMonth.equals(startMonth) && modMonth.equals(endMonth)) {
                Integer modDay = Integer.parseInt(modifiedDate.substring(8, 10));
                Integer startDay = Integer.parseInt(startDate.substring(8, 10));
                Integer endDay = Integer.parseInt(endDate.substring(8, 10));
                if (modDay >= startDay && modDay <= endDay) {
                    return true;
                }
            }
        }
        return false;
    }

    public String convertTime(long time) {
        Date date = new Date(time);
        Format format = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
        return format.format(date);
    }

    public String convertTime(String time) {
        Date date = new Date(time);
        Format format = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
        return format.format(date);
    }
}