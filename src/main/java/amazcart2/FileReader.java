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

                    System.out.println(file.getName());

                    System.out.println(convertTime(file.lastModified()));
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
    public String convertTime(long time){
        Date date = new Date(time);
        Format format = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
        return format.format(date);
    }
}

