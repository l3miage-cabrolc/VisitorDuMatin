package edu.uga.miage.m1.polygons.gui.shapes;

import java.awt.Graphics2D;
import java.io.Serializable;

import edu.uga.miage.m1.polygons.gui.api.Movable;
import edu.uga.miage.m1.polygons.gui.persistence.Visitor;


/**
 * This interface defines the <tt>SimpleShape</tt> extension. This extension
 * is used to draw shapes. 
 * 
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 *
 */
public abstract class SimpleShape implements Movable, Serializable{

    int x;
    int y;


    boolean selected;

    
    /**
     * Method to draw the shape of the extension.
     * @param g2 The graphics object used for painting.
     **/

    protected SimpleShape(int x, int y) {
        this.x = x - 25;
        this.y = y - 25;
        selected = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public abstract String getType();
    
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }


    public void move(int x, int y){
        this.x = x-25;
        this.y = y-25;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public abstract void draw(Graphics2D g2);

}