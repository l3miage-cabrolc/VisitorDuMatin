package edu.uga.miage.m1.polygons.gui.command;


import java.util.List;

import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public class EraseShape implements Command{
    
    SimpleShape shape;
    List<SimpleShape> shapes;
    
    public EraseShape(SimpleShape shape, List<SimpleShape> shapes){
        this.shape = shape;
        this.shapes = shapes;
    }

    @Override
    public void execute() {
        shapes.remove(shape);
    }
}
