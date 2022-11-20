package edu.uga.miage.m1.polygons.gui.persistence;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import edu.uga.miage.m1.polygons.gui.shapes.ShapeFactory;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public class Parser {

    private static final String XML = "xml";
    private static final String JSON = "json";
    
    private static String getFileType(String file){
        int index = file.lastIndexOf('.');
        if(index > 0) {
            return file.substring(index + 1);
            }
        return null;
    }

    public static ArrayList<SimpleShape> parseXMLFile(File file){



        return null;
    }


    public static ArrayList<SimpleShape> parseJsonFile(String filename){

        ArrayList<SimpleShape> result = new ArrayList<>();
        ShapeFactory shapeFactory = new ShapeFactory();


        JSONParser jsonParser = new JSONParser();
         
        FileReader reader;
        try {
            reader = new FileReader(filename);
            Object obj = jsonParser.parse(reader);

            JSONObject jsonObject = (JSONObject) obj;

            Object iterable = jsonObject.get("shapes");

            JSONArray jsonArray = (JSONArray) iterable;
 
            for(Object shapeObject : jsonArray){
                result.add(parseShape(shapeObject, shapeFactory));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        
            //Read JSON file
       
 
    
    

        return result;
    }

    private static SimpleShape parseShape(Object shapeObject, ShapeFactory shapeFactory){
        JSONObject jsonShape = (JSONObject) shapeObject;
        return shapeFactory.getShape((String)jsonShape.get("type"), Integer.parseInt(jsonShape.get("x").toString()), Integer.parseInt(jsonShape.get("y").toString()));
    }


    public  static List<SimpleShape> importFrom(String filename){
        if( getFileType(filename).equals(JSON)){
            return parseJsonFile(filename);
        }
        return new ArrayList<>();
    }


    
}
