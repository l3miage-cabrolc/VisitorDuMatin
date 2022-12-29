package edu.uga.miage.m1.polygons.gui.command;

import java.util.List;

import edu.uga.miage.m1.polygons.gui.shapes.CompositeShape;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public class Compose implements Command{


    private CompositeShape compositeShape;

    private List<SimpleShape> shapes;

    private SimpleShape simpleShape;


    public Compose(CompositeShape compositeShape, List<SimpleShape> shapes, SimpleShape simpleShape){
        this.compositeShape = compositeShape;
        this.shapes = shapes;
        this.simpleShape = simpleShape;
    }

    @Override
    public void execute() {
        this.compositeShape.addShape(simpleShape);
        shapes.remove(simpleShape);

    }

    @Override
    public void cancel() {
       this.compositeShape.removeShape(simpleShape);
        shapes.add(simpleShape);
    }

    @Override
    public String toString() {
        return getClass().getName();
    }

}
