package edu.uga.miage.m1.polygons.gui.shapes.api;

import java.util.ArrayList;

import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public interface Composable {
    
    public void compose(ArrayList<SimpleShape> shapes);

}
