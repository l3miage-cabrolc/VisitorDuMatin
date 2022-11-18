package edu.uga.miage.m1.polygons.gui.persistence;

public class Parser {

    private static final String XML = "xml";
    private static final String JSON = "json";
    
    private String getFileType(String file){
        int index = file.lastIndexOf('.');
        if(index > 0) {
            return file.substring(index + 1);
            }
        return null;
    }

    
}
