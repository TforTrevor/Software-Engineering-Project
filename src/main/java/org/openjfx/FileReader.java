package org.openjfx;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Date;

public class FileReader {

    private Date startDate;
    private Date endDate;
    private String filePath;
    private ArrayList<File> files = new ArrayList<>();
    private String[] fileExtensions = {".png", ".jpg", ".gif"};
    private boolean runThread;

    public FileReader(Date startDate, Date endDate, String filePath) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.filePath = filePath;
    }

    public void SearchImages() {
        //Recursively search directories
        SearchDirectory(new File(filePath));

        for (int i = 0; i < files.size(); i++) {
            System.out.println("Found " + files.get(i).getName());
        }
    }

    private void SearchDirectory(File directory) {
        if (directory.isDirectory()) {
            Search(directory);
        } else {
            System.out.println(directory.getAbsoluteFile() + " is not a directory!");
        }
    }

    private void Search(File file) {
        if (runThread) {
            if (file.isDirectory()) {
                if (file.canRead()) {
                    for (int i = 0; i < file.listFiles().length; i++) {
                        File temp = file.listFiles()[i];
                        if (temp.isDirectory()) {
                            Search(temp);
                        } else {
                            if (CheckFile(temp.getAbsoluteFile())) {
                                files.add(temp.getAbsoluteFile());
                            }
                        }
                    }
                }
            } else {
                System.out.println(file.getAbsoluteFile() + "Permission Denied");
            }
        }
    }

    private boolean CheckFile(File file) {
        try {
            Path path = Paths.get(file.toURI());
            BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);
            Date date = Date.from(attributes.creationTime().toInstant());
            if (date.after(startDate) && date.before(endDate)) {
                for (int i = 0; i < fileExtensions.length; i++) {
                    if (file.getName().toLowerCase().endsWith(fileExtensions[i])) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean GetRunThread() {
        return runThread;
    }

    public void SetRunThread(boolean state) {
        runThread = state;
    }

    public ArrayList<File> GetFiles() {
        return (ArrayList<File>) files.clone();
    }
}
