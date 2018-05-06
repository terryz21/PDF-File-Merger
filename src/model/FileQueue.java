package model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class FileQueue {
    private List<File> listFiles; // list of input files
    private List<String> listFileNames; // names of files in queue to be merged

    private boolean mergeable;  // true if more than 2 files in queue


    // constructor
    public FileQueue() {
        listFiles = new ArrayList<>();
        listFileNames = new ArrayList<>();
        mergeable = false;
    }

    // getters
    public boolean isMergeable() {
        return mergeable;
    }

    public List<File> getAllFiles() {
        return listFiles;
    }

    public List<String> getAllFileNames() {
        return listFileNames;
    }

    // adders
    public void addFile(File file) {
        listFiles.add(file);
    }

    public void addFileName(String name) {
        listFileNames.add(name);
    }
}
