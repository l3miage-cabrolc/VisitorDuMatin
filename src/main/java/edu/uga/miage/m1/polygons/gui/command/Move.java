package edu.uga.miage.m1.polygons.gui.command;

import edu.uga.miage.m1.polygons.gui.Myshapes.MySimpleShape;


public class Move implements Command{

    private MySimpleShape simpleShape;
    
    private int x;
    private int y;

    public Move(MySimpleShape simpleShape, int x, int y){
        this.simpleShape = simpleShape;
        this.x = x;
        this.y = y;
    }
    
    @Override
    public void execute() {
        simpleShape.moveTo(x, y);
        
    }
    @Override
    public void cancel(){
        simpleShape.moveTo(-x,-y);
    }

    @Override
    public String toString() {
        return getClass().getName();
    }
}
