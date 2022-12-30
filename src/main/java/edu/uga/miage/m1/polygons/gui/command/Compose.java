package edu.uga.miage.m1.polygons.gui.command;

import java.util.List;

import edu.uga.miage.m1.polygons.gui.Myshapes.MyComposite;
import edu.uga.miage.m1.polygons.gui.Myshapes.MySimpleShape;
public class Compose implements Command{


    private MyComposite compositeShape;

    private List<MySimpleShape> shapes;

    private MySimpleShape simpleShape;


    public Compose(MyComposite compositeShape, List<MySimpleShape> shapes, MySimpleShape simpleShape){
        this.compositeShape = compositeShape;
        this.shapes = shapes;
        this.simpleShape = simpleShape;
    }

    @Override
    public void execute() {
        // this.compositeShape.addShape(simpleShape);
        // shapes.remove(simpleShape);

    }

    @Override
    public void cancel() {
    //    this.compositeShape.removeShape(simpleShape);
    //     shapes.add(simpleShape);
    }

    @Override
    public String toString() {
        return getClass().getName();
    }

}
