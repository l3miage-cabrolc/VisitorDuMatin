package edu.uga.miage.m1.polygons.gui.shapes;

import java.awt.Graphics2D;
import java.io.Serializable;

import edu.uga.miage.m1.polygons.gui.persistence.Visitor;
import edu.uga.miage.m1.polygons.gui.shapes.api.Movable;
import java.awt.Image;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


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
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    
    public abstract  String getType();
    
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }


    public void move(int x, int y){
        this.x = x-25;
        this.y = y-25;
    }


    public void draw(Graphics2D g2) {
        Image bImg;
        try {
            bImg = ImageIO.read(new File("src/main/resources/edu/uga/miage/m1/polygons/gui/images/"+ getType() + ".png"));
            g2.drawImage(bImg, x, y, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public boolean isSelected(int x, int y){
        return ((x-25 > getX()-50) && (x-25<getX()+50)) && ((y-25 > getY()-50) && (y-25<getY()+50));
    }

}