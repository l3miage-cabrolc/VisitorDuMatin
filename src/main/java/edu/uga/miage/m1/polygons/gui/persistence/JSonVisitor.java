package edu.uga.miage.m1.polygons.gui.persistence;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import org.json.simple.parser.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import edu.uga.miage.m1.polygons.gui.shapes.CompositeShape;
import edu.uga.miage.m1.polygons.gui.shapes.ShapeFactory;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

/**
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public class JSonVisitor implements Visitor, Serializable {


    private static  final Logger LOGGER =  Logger.getLogger(Logger.GLOBAL_LOGGER_NAME); 


    private transient LinkedHashMap<String,Object> currentComposite;


    private List<SimpleShape> importResult;

    private  transient ArrayList<LinkedHashMap<String, Object>> currentSimpleShapes;

    private  transient LinkedHashMap<String, Object> shapes;

    private transient ArrayList<LinkedHashMap<String, Object>> simpleShapes;

    public JSonVisitor() {


        simpleShapes = new ArrayList<>();
        shapes = new LinkedHashMap<>();

    }
    
    public void visit(SimpleShape simpleShape) {

        LinkedHashMap<String, Object> shape = new LinkedHashMap<>();


        shape.put("type", simpleShape.getType());

        shape.put("x", simpleShape.getX());
        shape.put("y", simpleShape.getY());
       
        if(currentComposite==null){
            simpleShapes.add(shape);
        }else{
            currentSimpleShapes.add(shape);
        }
        
    }

    @Override
    public void visit(CompositeShape compositeShape){
        currentComposite = new LinkedHashMap<>();
        currentSimpleShapes = new ArrayList<>();

        for(SimpleShape s : compositeShape.getShapes()){
            visit(s);
        }
        currentComposite.put("composite", currentSimpleShapes);

        simpleShapes.add(currentComposite);

        currentComposite = null;
        currentSimpleShapes = null;
    }

    public void save(String fileName) {

        shapes.put("shapes", simpleShapes);

        JSONObject jsonShapes = new JSONObject(shapes);

        try (FileWriter file = new FileWriter(fileName)) {
            file.write(jsonShapes.toJSONString()); 
            file.flush();
 
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "error");
        }
        
    }


    public List<SimpleShape> importFile(String filename){
        
        importResult = new ArrayList<>();
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
                parseShape(shapeObject, shapeFactory);
            }
        } catch (IOException | ParseException e) {
            LOGGER.log(Level.SEVERE, "ERREUR");
        }
    
        return importResult;
    }

    private void parseShape(Object shapeObject, ShapeFactory shapeFactory){

        
        JSONObject jsonShape = (JSONObject) shapeObject;

        if(jsonShape.get("type")!=null){
            importResult.add(shapeFactory.getShape((String)jsonShape.get("type"), Integer.parseInt(jsonShape.get("x").toString()), Integer.parseInt(jsonShape.get("y").toString())));
        }else{
            Object iterable = jsonShape.get("composite");
            JSONArray jsonArray = (JSONArray) iterable;
            CompositeShape newComposite = shapeFactory.getCompositeShape();
            for(Object simpleS : jsonArray){
                JSONObject jsonS = (JSONObject) simpleS;
                SimpleShape s = shapeFactory.getShape((String)jsonS.get("type"), Integer.parseInt(jsonS.get("x").toString()), Integer.parseInt(jsonS.get("y").toString()));
                newComposite.addShape(s);
            }
            importResult.add(newComposite);
        }
    }

    /**
     * @return the representation in JSon example for a Circle
     *
     *         <pre>
     * {@code
     *  {
     *     "shape": {
     *     	  "type": "circle",
     *        "x": -25,
     *        "y": -25
     *     }
     *  }
     * }
     *         </pre>
     */
    public String getRepresentation() {

        JSONObject jsonShapes = new JSONObject(shapes);

        return jsonShapes.toJSONString();
    }


    
  
}
