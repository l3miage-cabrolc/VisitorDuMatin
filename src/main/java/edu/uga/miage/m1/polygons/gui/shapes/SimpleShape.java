package edu.uga.miage.m1.polygons.gui.shapes;

import java.awt.Graphics2D;

import edu.uga.miage.m1.polygons.gui.persistence.Visitor;


/**
 * This interface defines the <tt>SimpleShape</tt> extension. This extension
 * is used to draw shapes. 
 * 
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 *
 */
public abstract class SimpleShape {

    int x;
    int y;
    
    /**
     * Method to draw the shape of the extension.
     * @param g2 The graphics object used for painting.
     **/
    void draw(Graphics2D g2) {}

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public String getClassName() {
        String name = this.getClass().toString();
        return (name.substring(name.lastIndexOf(".")+1, name.length())).toLowerCase();
    }
    
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}