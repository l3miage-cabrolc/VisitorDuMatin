package edu.uga.miage.m1.polygons.gui.command;

import java.util.List;

import edu.uga.miage.m1.polygons.gui.shapes.CompositeShape;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public class Compose implements Command{


    private CompositeShape compositeShape;

    private List<CompositeShape> compositeShapes;


    private SimpleShape simpleShape;


    public Compose(CompositeShape compositeShape, List<CompositeShape> compositeShapes){
        this.compositeShape = compositeShape;
        this.compositeShapes = compositeShapes;
        
    }

    @Override
    public void execute() {
        this.compositeShape.addSimpleShape(simpleShape);
        this.compositeShapes.add(compositeShape);

    }

}
