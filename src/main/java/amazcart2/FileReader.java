/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amazcart2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 */
public class FileReader {
    private String date="12/13/2018";
    private String startDate="12/01/2017";
    private String endDate="02/17/2018";
    private String fileSearchLocation="C:\\";
    FileReader(String startingDay, String endingDay,String fileSearchingLocation)
    {
        if(startingDay!=null) {
            startDate = startingDay;
        }
        if(endingDay!=null) {
            endDate = endingDay;
        }
        if(fileSearchingLocation!=null)
        {
            fileSearchLocation=fileSearchingLocation;
        }
    }
    public void readFile()
    {
        //System.out.println(System.getProperty("../ ")+"\\..");
        String fileLocation="";

        while(new File(fileLocation).exists())
        {
            fileLocation=fileLocation+"../";
        }
        File folder =new File(fileSearchLocation);
        // folder=new File(folder.getParent());
        //folder=new File(folder.getParent());
        // folder=new File(folder.getParent());
        System.out.println(folder.getAbsolutePath());
        //for(File file:folder.listFiles())
        //{
        //   System.out.println(file.getName());
        //}
        //searchDirForFile("C:\\");
        filesInFolder(folder);

    }

    public void filesInFolder(final File folder) {
        //System.out.println(folder.getName());
        for (final File file : folder.listFiles()) {
            try
            {
                if (file.isDirectory()) {
                    // System.out.println(file.getAbsolutePath());
                    //  System.out.println(file);

                    filesInFolder(file);

                } else if(file.getName().toLowerCase().contains(".jpg")||file.getName().toLowerCase().contains(".png")) {
                    Path file2= Paths.get(file.getAbsolutePath());
                    BasicFileAttributes attr = Files.readAttributes(file2, BasicFileAttributes.class);
            /*    if(filter(attr.creationTime().toString(),convertTime(date))==true) {

                    System.out.println(file.getName());

                   System.out.println(convertTime(file.lastModified()));
                }
*/
                   String endDate3=convertTime(endDate);
                    String startDate3=convertTime(startDate);
            Integer endYear = Integer.parseInt(endDate3.substring(0, 4));
                    Integer startYear = Integer.parseInt(startDate3.substring(0, 4));
        if(endYear.equals(startYear + 1))
        {

           String endDate1="12/31/"+startYear;
          // System.out.println(endDate1);
           String startDate2="01/01/"+endYear;
          // System.out.println(startDate2);
           if(filter2(attr.creationTime().toString(),convertTime(startDate),convertTime(endDate1))==true)
            {
                System.out.println(file.getName());

                System.out.println(convertTime(file.lastModified()));
            }
           if(filter2(attr.creationTime().toString(),convertTime(startDate2),convertTime(endDate))==true)
           {
               System.out.println(file.getName());

               System.out.println(convertTime(file.lastModified()));
           }

        }
        else {
            if (filter2(attr.creationTime().toString(), convertTime(startDate), convertTime(endDate)) == true) {
                System.out.println(file.getName());

                System.out.println(convertTime(file.lastModified()));
            }
        }
           /* FileTime fileTime;
try {
    fileTime = Files.getLastModifiedTime(Paths.get(file.getAbsolutePath()));
    printFileTime(fileTime);
} catch (IOException e) {
    System.err.println("Cannot get the last modified time - " + e);
}
            */
                }

            }
            catch (Exception ignore) { }

        }
    }

    private boolean filter(String lastModified, String inputDate) {
    if(Integer.parseInt(lastModified.substring(0,4))==Integer.parseInt(inputDate.substring(0,4)))
    {
        if(Integer.parseInt(lastModified.substring(5,7))>=Integer.parseInt(inputDate.substring(5,7))&&Integer.parseInt(lastModified.substring(5,7))<=Integer.parseInt(inputDate.substring(5,7))+2) {

            // System.out.println(lastModified);
            //System.out.println(lastModified.substring(8, 10));

            if (Integer.parseInt(lastModified.substring(8, 10)) >= Integer.parseInt(inputDate.substring(8, 10))) {
                return true;
            }
        }
    }
    else if(Integer.parseInt(lastModified.substring(0,4))==Integer.parseInt(inputDate.substring(0,4))+1)
    {
            if(Integer.parseInt(lastModified.substring(5,7))<=12-Integer.parseInt(inputDate.substring(5,7))+2)
            {
            return true;
            }
    }
        return false;
    }
    private boolean filter2(String modifiedDate,String startDate, String endDate) {

        Integer modYear = Integer.parseInt(modifiedDate.substring(0, 4));
        Integer startYear = Integer.parseInt(startDate.substring(0, 4));
        Integer endYear = Integer.parseInt(endDate.substring(0, 4));
        Integer modMonth = Integer.parseInt(modifiedDate.substring(5, 7));
        Integer startMonth = Integer.parseInt(startDate.substring(5, 7));
        Integer endMonth = Integer.parseInt(endDate.substring(5, 7));
       /* if(endYear.equals(startYear+1))
        {
//2016 09 29 19:32:39 found fix algo
            if(modYear.equals(startYear) || modYear.equals(endYear)) {
                if (modMonth <= endMonth) {
                    Integer modDay = Integer.parseInt(modifiedDate.substring(8, 10));
                    Integer startDay = Integer.parseInt(startDate.substring(8, 10));
                    Integer endDay = Integer.parseInt(endDate.substring(8, 10));
                    if (modDay >= startDay && modDay <= endDay) {
                        return true;
                    }

                }
            }
        }
        */
         if (modYear.equals(startYear) || modYear.equals(endYear)) {
            if(modMonth>startMonth&&modMonth < endMonth)
            {
                return true;
            }
            else if (modMonth.equals(startMonth) && modMonth.equals(endMonth)) {
                Integer modDay = Integer.parseInt(modifiedDate.substring(8, 10));
                Integer startDay = Integer.parseInt(startDate.substring(8, 10));
                Integer endDay = Integer.parseInt(endDate.substring(8, 10));
                if (modDay >= startDay && modDay <= endDay) {
                    return true;
                }
            }
        }
     /*  else if(endYear.equals(startYear+1))
        {
           if(modYear<)
            Integer modMonth=Integer.parseInt(modifiedDate.substring(5,7));
            Integer startMonth=Integer.parseInt(startDate.substring(5,7));
            Integer endMonth=Integer.parseInt(endDate.substring(5,7));
            if(modMonth <= endMonth) {
                Integer modDay = Integer.parseInt(modifiedDate.substring(8, 10));
                Integer startDay = Integer.parseInt(startDate.substring(8, 10));
                Integer endDay = Integer.parseInt(endDate.substring(8, 10));
                if (modDay >= startDay && modDay <= endDay) {
                    return true;
                }
            }
            */

    return false;
    }

    public String convertTime(long time){
        Date date = new Date(time);
        Format format = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
        return format.format(date);
    }
    public String convertTime(String time){

        Date date = new Date(time);
        Format format = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
        return format.format(date);
    }
}

