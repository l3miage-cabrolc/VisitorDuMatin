package edu.uga.miage.m1.polygons.gui.shapes;

import java.util.ArrayList;

public class ComposedShape {
    
    ArrayList<SimpleShape> shapes;

    public ComposedShape(){
        this.shapes = new ArrayList<>();
    }

    public void addSimpleShape(SimpleShape shape){
        this.shapes.add(shape);
    }
}
