package edu.uga.miage.m1.polygons.gui.shapes;

import edu.uga.miage.m1.polygons.gui.persistence.Visitable;



public class Soleil extends SimpleShape implements Visitable{
    
    private static final String TYPE = "soleil";

    public Soleil(int x, int y) {
        super(x, y);
       
    }

    @Override
    public String getType() {
        return TYPE;
    }




}
 