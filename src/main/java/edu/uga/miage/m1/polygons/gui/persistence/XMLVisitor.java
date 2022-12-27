package edu.uga.miage.m1.polygons.gui.persistence;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import edu.uga.miage.m1.polygons.gui.shapes.CompositeShape;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;



/**
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public class XMLVisitor implements Visitor, Serializable {

    
    private static final  Logger LOGGER =  Logger.getLogger(Logger.GLOBAL_LOGGER_NAME); 

    private Document document;

    private Element root;

    private Element currComposite;

    public XMLVisitor() {
        

        try {
 
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
    
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
    
            document = documentBuilder.newDocument();
    
            // root element
            root = document.createElement("shapes");
            document.appendChild(root);
    
    
        } catch (ParserConfigurationException pce) {
            LOGGER.log(Level.SEVERE, "Error parsing");
        }
    }


    @Override
    public void visit(SimpleShape simpleShape) {

        Element simplaElement = document.createElement("shape");
        Element type = document.createElement("type");
        type.setTextContent(simpleShape.getType());
        Element x = document.createElement("x");
        Element y = document.createElement("y");
        x.setTextContent(simpleShape.getX()+"");
        y.setTextContent(simpleShape.getY()+"");
        simplaElement.appendChild(type);
        simplaElement.appendChild(x);
        simplaElement.appendChild(y);

        if(currComposite==null){
            root.appendChild(simplaElement);
        }else{
            currComposite.appendChild(simplaElement);
        }
        

    }

    public void visit(CompositeShape compositeShape){

        Element compositeElement = document.createElement("composite");
        currComposite = compositeElement;
        for(SimpleShape s: compositeShape.getShapes()){
            visit(s);
        }

        root.appendChild(compositeElement);
        currComposite = null;
    }



    public void save(String fileName){
        try (FileOutputStream output = new FileOutputStream(fileName)) {
            writeXml(document, output);
        } catch (IOException | TransformerException e) {
            e.printStackTrace();
        }
    }


    private static void writeXml(Document doc,
                                 OutputStream output)
            throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);

        transformer.transform(source, result);

    }


    public String getRepresentation(){
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = tf.newTransformer();
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(document), new StreamResult(writer));
            return writer.getBuffer().toString();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        
        return null;
    }

}









