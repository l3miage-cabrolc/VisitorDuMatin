package edu.uga.miage.m1.polygons.gui.persistence;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.uga.miage.m1.polygons.gui.shapes.ShapeFactory;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public class Parser {

    private Parser(){}

    private static final  Logger LOGGER =  Logger.getLogger(Logger.GLOBAL_LOGGER_NAME); 


    private static final String XML = "xml";
    private static final String JSON = "json";
    
    public static String getFileType(String file){
        int index = file.lastIndexOf('.');
        if(index > 0) {
            return file.substring(index + 1);
            }
        return "";
    }

    public static List<SimpleShape> parseXMLFile(String fileName){

        ArrayList<SimpleShape> result = new ArrayList<>();

        ShapeFactory shapeFactory = new ShapeFactory();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;

        try {
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(fileName));

            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("shape");

            for (int temp = 0; temp < list.getLength(); temp++){
                Node node = list.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) node;

                    String type = element.getElementsByTagName("type").item(0).getTextContent();

                    int x = Integer.parseInt(element.getElementsByTagName("x").item(0).getTextContent());
                    int y = Integer.parseInt(element.getElementsByTagName("y").item(0).getTextContent());

                    result.add(shapeFactory.getShape(type, x, y));

                }
            }

        } catch (ParserConfigurationException e1) {
            LOGGER.log(Level.SEVERE, "ERREUR");
        } catch (SAXException | IOException  e) {
            LOGGER.log(Level.SEVERE, "ERREUR");
        } 

        
        

        return result;
    }


    public static List<SimpleShape> parseJsonFile(String filename){

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
            LOGGER.log(Level.SEVERE, "ERREUR");
        }
    
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
        if(getFileType(filename).equals(XML)){
            return parseXMLFile(filename);
        }
        return new ArrayList<>();
    }


    
}
