package edu.uga.miage.m1.polygons.gui.command;

import java.util.List;

import edu.uga.miage.m1.polygons.gui.shapes.CompositeShape;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public class Compose implements Command{


    private CompositeShape compositeShape;

    private List<SimpleShape> simpleShapes;


    private SimpleShape simpleShape;


    public Compose(CompositeShape compositeShape, List<SimpleShape> simpleShapes){
        this.compositeShape = compositeShape;
        this.simpleShapes = simpleShapes;
        
    }

    @Override
    public void execute() {
        this.compositeShape.compose(simpleShape);
        this.simpleShapes.add(compositeShape);

    }

    public void setSimpleShape(SimpleShape simpleShape) {
        this.simpleShape = simpleShape;
    }

}
