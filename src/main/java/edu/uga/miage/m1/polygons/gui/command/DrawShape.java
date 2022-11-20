package edu.uga.miage.m1.polygons.gui.command;


import java.util.List;

import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public class DrawShape implements Command{

    SimpleShape shape;
    List<SimpleShape> shapes;
    
    public DrawShape(SimpleShape shape, List<SimpleShape> shapes){
        this.shape = shape;
        this.shapes = shapes;
    }

    @Override
    public void execute() {
        shapes.add(shape);
    }
    
}
