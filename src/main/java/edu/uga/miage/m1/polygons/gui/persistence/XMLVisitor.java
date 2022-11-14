package edu.uga.miage.m1.polygons.gui.persistence;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.Square;
import edu.uga.miage.m1.polygons.gui.shapes.Triangle;

/**
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public class XMLVisitor implements Visitor, Serializable {

    private static final String MIDDLE_REPR = "</x>\n<y>";
    private static final String END_REPR = "</y>\n</shape>\n";
    
    private static final  Logger LOGGER =  Logger.getLogger(Logger.GLOBAL_LOGGER_NAME); 

    private String representation;


    public XMLVisitor() {

        this.representation = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n";

       
    }

    @Override
    public void visit(Circle circle) {
        representation = representation + "<shape>\n<type>circle</type>\n<x>" + circle.getX() + MIDDLE_REPR + circle.getY() + END_REPR;

    }

    @Override
    public void visit(Square square) {
        representation = representation + "<shape>\n<type>square</type>\n<x>" + square.getX() + MIDDLE_REPR + square.getY() + END_REPR;

    }

    @Override
    public void visit(Triangle triangle) {
        representation = representation + "<shape>\n<type>triangle</type>\n<x>" + triangle.getX() + MIDDLE_REPR + triangle.getY() + END_REPR;
    }


    public void save(){

        File file = new File("description.xml");

        try( FileWriter fileWriter =  new FileWriter(file)) {
            fileWriter.write(representation);
            fileWriter.flush();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erreur d ecriture dans le fichier");
        }  
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
