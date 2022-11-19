package edu.uga.miage.m1.polygons.gui.command;

import java.util.List;

import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public interface Command {
    
    public Command execute(SimpleShape shape, List<SimpleShape> shapes);
    
}
