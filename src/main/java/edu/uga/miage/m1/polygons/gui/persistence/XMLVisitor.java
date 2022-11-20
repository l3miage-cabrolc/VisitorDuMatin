package edu.uga.miage.m1.polygons.gui.persistence;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;


/**
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public class XMLVisitor implements Visitor, Serializable {

    private static final String BEGIN_REPR = "<shape>\n<type>";
    private static final String MIDDLE_REPR = "</x>\n<y>";
    private static final String END_REPR = "</y>\n</shape>\n";
    
    private static final  Logger LOGGER =  Logger.getLogger(Logger.GLOBAL_LOGGER_NAME); 

    private String representation;

    public XMLVisitor() {
        this.representation = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<root>\n";
    }

    @Override
    public void visit(SimpleShape simpleShape) {
        representation = representation + BEGIN_REPR + simpleShape.getType() + "</type>\n<x>" + simpleShape.getX() + MIDDLE_REPR + simpleShape.getY() + END_REPR;
    }
    
    public void save(String fileName){
        representation = representation + "</root>";
        File file = new File(fileName);

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
