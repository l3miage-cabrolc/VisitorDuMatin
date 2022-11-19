package edu.uga.miage.m1.polygons.gui.command;
import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JFrame;

import org.junit.jupiter.api.Test;
import java.awt.Graphics2D;

public class DrawShapeTest {
    
    @Test
    void shoulDrawCircle(){

        JFrame frame = new JFrame();

        DrawShape drawShape = new DrawShape((Graphics2D) frame.getGraphics());
    }
}
