package edu.uga.miage.m1.polygons.gui.command;

import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public class Move implements Command{

    private SimpleShape simpleShape;
    
    private int x;
    private int y;

    public Move(SimpleShape simpleShape, int x, int y){
        this.simpleShape = simpleShape;
        this.x = x;
        this.y = y;
    }
    
    @Override
    public void execute() {
        simpleShape.move(x, y);
        
    }
    @Override
    public void cancel(){
        simpleShape.move(-x,-y);
    }

    @Override
    public String toString() {
        return getClass().getName();
    }
}
