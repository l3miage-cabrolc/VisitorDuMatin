package edu.uga.miage.m1.polygons.gui.shapes;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ShapeFactoryTest {

    @Test
    void shouldGetCircle(){
        ShapeFactory shapeFactory = new ShapeFactory();

        Circle c = new Circle(0, 0);

        Object o = shapeFactory.getShape(c.getType(), 0, 0);

        assertEquals(c.getClass(), o.getClass());

        Circle resCircle = (Circle) o;
        assertEquals(c.getX(), resCircle.getX());
        assertEquals(c.getY(), resCircle.getY());


    }

    @Test
    void shouldGetTriangle(){
        ShapeFactory shapeFactory = new ShapeFactory();

        Triangle c = new Triangle(0, 0);

        Object o = shapeFactory.getShape(c.getType(), 0, 0);

        assertEquals(c.getClass(), o.getClass());

        Triangle resTriangle = (Triangle) o;
        assertEquals(c.getX(), resTriangle.getX());
        assertEquals(c.getY(), resTriangle.getY());


    }

    @Test
    void shouldGetSquare(){
        ShapeFactory shapeFactory = new ShapeFactory();

        Square c = new Square(0, 0);

        Object o = shapeFactory.getShape(c.getType(), 0, 0);

        assertEquals(c.getClass(), o.getClass());

        Square resSquare = (Square) o;
        assertEquals(c.getX(), resSquare.getX());
        assertEquals(c.getY(), resSquare.getY());


    }
    
}
