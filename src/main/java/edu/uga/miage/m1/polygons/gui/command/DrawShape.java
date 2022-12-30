package edu.uga.miage.m1.polygons.gui.command;


import java.util.List;

import edu.uga.miage.m1.polygons.gui.Myshapes.MyShape;
import edu.uga.miage.m1.polygons.gui.Myshapes.MySimpleShape;

public class DrawShape implements Command{

    MySimpleShape shape;
    List<MySimpleShape> shapes;
    
    public DrawShape(MySimpleShape shape, List<MySimpleShape> shapes){
        this.shape = shape;
        this.shapes = shapes;
    }

    @Override
    public void execute() {
        shapes.add(shape);
    }
    
    @Override
    public void cancel() {
        shapes.remove(shape);
        
    }
    @Override
    public String toString() {
        return getClass().getName();
    }
}
