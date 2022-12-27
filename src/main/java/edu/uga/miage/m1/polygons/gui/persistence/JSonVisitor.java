package edu.uga.miage.m1.polygons.gui.persistence;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONObject;

import edu.uga.miage.m1.polygons.gui.shapes.CompositeShape;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

/**
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public class JSonVisitor implements Visitor, Serializable {


    private static  final Logger LOGGER =  Logger.getLogger(Logger.GLOBAL_LOGGER_NAME); 


    private transient LinkedHashMap<String,Object> currentComposite;

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
