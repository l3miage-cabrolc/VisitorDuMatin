package edu.uga.miage.m1.polygons.gui.shapes;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class CompositeShape extends SimpleShape{

    public static final String TYPE = "composite";


    private SimpleShape simpleShapeSelected;
    
    private ArrayList<SimpleShape> shapes;

    public CompositeShape(){
        super(0, 0);
        this.shapes = new ArrayList<>();
    }

    public void compose(SimpleShape shape){
        this.shapes.add(shape);
    }

    public void addAllShapes(List<SimpleShape> shapes){
        this.shapes.addAll(shapes);
    }

    @Override
    public void draw(Graphics2D g2) {
        for(SimpleShape s : shapes){
            s.draw(g2);
        }
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public void move(int x, int y) {
  
        int xtemp = x - 25 - simpleShapeSelected.getX();
        int ytemp = y - 25 - simpleShapeSelected.getY();
        
        simpleShapeSelected.move(x,y);
        for(SimpleShape simpleShape : shapes){
            if (simpleShape != simpleShapeSelected) {
                simpleShape.setX(simpleShape.getX() + xtemp);
                simpleShape.setY(simpleShape.getY() + ytemp);
            }
        }
    }

    public List<SimpleShape> getShapes() {
        return shapes;
    }

 
    @Override
    public boolean isSelected(int x, int y) {
        for(SimpleShape s : shapes){
            if(s.isSelected(x, y)){
                simpleShapeSelected = s;
                return true;
            }
        }
        return false;
    }
}
