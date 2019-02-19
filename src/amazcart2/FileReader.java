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
import java.nio.file.attribute.FileTime;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 */
public class FileReader {
    public String date="02/13/2018";
    FileReader()
    {

    }
    public void readFile()
    {
        //System.out.println(System.getProperty("../ ")+"\\..");
        String fileLocation="";

        while(new File(fileLocation).exists())
        {
            fileLocation=fileLocation+"../";
        }
        File folder =new File("C:\\");
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
                if(filter(convertTime(file.lastModified()),convertTime(date))==true) {

                    System.out.println(file.getName());

                   System.out.println(convertTime(file.lastModified()));
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
                else
                {
                    //System.out.println(file.getAbsolutePath());
                    //System.out.println(file.getName());;
                }
            }
            catch (Exception ignore) { }

        }
    }

    private boolean filter(String lastModified, String inputDate) {
    if(Integer.parseInt(lastModified.substring(0,4))>Integer.parseInt(inputDate.substring(0,4)))
        {
            //System.out.println("no more searching");
            return true;
        }
    if(Integer.parseInt(lastModified.substring(0,4))==Integer.parseInt(inputDate.substring(0,4)))
    {
        if(Integer.parseInt(lastModified.substring(5,7))>=Integer.parseInt(inputDate.substring(5,7))) {
           // System.out.println(lastModified);
            //System.out.println(lastModified.substring(8, 10));
        }
        if(Integer.parseInt(lastModified.substring(8,10))>=Integer.parseInt(inputDate.substring(8,10))) {
        return true;
        }
    }
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

