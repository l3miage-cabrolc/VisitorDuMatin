package edu.uga.miage.m1.polygons.gui.persistence;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.Square;
import edu.uga.miage.m1.polygons.gui.shapes.Triangle;

/**
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public class JSonVisitor implements Visitor {


    private class Shape{
        private String type;
        int x;
        int y;

        public Shape(String type, int x, int y){
            this.type = type;
            this.x = x;
            this.y = y;
        }

        public String getType() {
            return type;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    ArrayList<Shape> listOfShapes;

    private StringBuilder representation = null;

    public JSonVisitor() {
    	
    	this.representation = new StringBuilder();
        this.listOfShapes = new ArrayList<>();

    }

    @Override
    public void visit(Circle circle) {
        listOfShapes.add(new Shape("circle", circle.getX(), circle.getX()));
    }

    @Override
    public void visit(Square square) {
        listOfShapes.add(new Shape("square", square.getX(), square.getX()));
    }

    @Override
    public void visit(Triangle triangle) {

        listOfShapes.add(new Shape("square", triangle.getX(), triangle.getX()));

    }



    public void save() throws IOException{


       File file = new File("description.json");

       FileWriter fileWriter = null;


       for(int i=0; i < listOfShapes.size()-1; i++){
            representation.append(representation + "{\n\"type\": \"" + listOfShapes.get(i).getType() + "\",\n\"x\": " + listOfShapes.get(i).getX() + ",\n\"y\": " + listOfShapes.get(i).getY() + "\n},");
       }

       representation.insert(0, "{\"shapes\" : [\n" );
       representation.append("{\n\"type\": \"" + listOfShapes.get(listOfShapes.size()-1).getType() + "\",\n\"x\": " + listOfShapes.get(listOfShapes.size()-1).getX() + ",\n\"y\": " + listOfShapes.get(listOfShapes.size()-1).getY() + "\n}" +  "] }");


        fileWriter =  new FileWriter(file);
        
        try {
            fileWriter.write(representation.toString());
            fileWriter.flush();

        } catch (IOException e) {
        }
        fileWriter.close();
        
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
        return representation.toString();
    }
}
