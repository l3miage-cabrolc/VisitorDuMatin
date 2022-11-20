package edu.uga.miage.m1.polygons.gui.shapes;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class CompositeShape extends SimpleShape{

    public static final String TYPE = "composite";

    
    private ArrayList<SimpleShape> shapes;

    public CompositeShape(){
        super(0, 0);
        this.shapes = new ArrayList<>();
    }

    public void addSimpleShape(SimpleShape shape){
        this.shapes.add(shape);
    }

    public void addAllShapes(List<SimpleShape> shapes){
        this.shapes.addAll(shapes);
    }

    @Override
    public void draw(Graphics2D g2) {
        //implements drawing a composite shape
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public void move(int x, int y) {
        for(SimpleShape simpleShape : shapes){
            simpleShape.move(x, y);
        }
    }

    public ArrayList<SimpleShape> getShapes() {
        return shapes;
    }

 
}
