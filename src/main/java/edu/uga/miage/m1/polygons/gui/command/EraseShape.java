package edu.uga.miage.m1.polygons.gui.command;


import java.util.List;

import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public class EraseShape implements Command{
    


    @Override
    public Command execute(SimpleShape shape, List<SimpleShape> shapes) {
        shapes.remove(shape);
        return this;
    }
}
