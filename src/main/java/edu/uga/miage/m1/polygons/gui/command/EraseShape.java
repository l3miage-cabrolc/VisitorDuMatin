package edu.uga.miage.m1.polygons.gui.command;

import java.awt.Graphics2D;
import java.util.List;

import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public class EraseShape implements Command{
    
    Graphics2D g2;

    public EraseShape(Graphics2D g2){
        this.g2 = g2;
    }

    @Override
    public Command execute(SimpleShape shape, List<SimpleShape> shapes) {
        // TODO Auto-generated method stub
        return null;
    }
}
