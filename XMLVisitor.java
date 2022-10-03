package edu.uga.miage.m1.polygons.gui.persistence;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.Square;
import edu.uga.miage.m1.polygons.gui.shapes.Triangle;

/**
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public class XMLVisitor implements Visitor {

    private String representation;

    

    public XMLVisitor() {

        this.representation = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n";

       
    }


    @Override
    public void visit(Circle circle) {
        representation = representation + "<shape>\n<type>circle</type>\n<x>" + circle.getX() +"</x>\n<y>" + circle.getY() + "</y>\n</shape>\n";

    }

    @Override
    public void visit(Square square) {
        representation = representation + "<shape>\n<type>square</type>\n<x>" + square.getX() +"</x>\n<y>" + square.getY() + "</y>\n</shape>\n";

    }

    @Override
    public void visit(Triangle triangle) {
        representation = representation + "<shape>\n<type>triangle</type>\n<x>" + triangle.getX() +"</x>\n<y>" + triangle.getY() + "</y>\n</shape>\n";
    }


    public void save() throws IOException{

        File file = new File("description.xml");

        FileWriter fileWriter = null;

            fileWriter =  new FileWriter(file);
      
        try {
            fileWriter.write(representation);
            fileWriter.flush();

        } catch (IOException e) {
        }

        fileWriter.close();
    }

    /**
     * @return the representation in JSon example for a Triangle:
     *
     *         <pre>
     * {@code
     *  <shape>
     *    <type>triangle</type>
     *    <x>-25</x>
     *    <y>-25</y>
     *  </shape>
     * }
     * </pre>
     */
    public String getRepresentation() {
        return representation;
    }
}
