package edu.uga.miage.m1.polygons.gui.shapes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.uga.miage.m1.polygons.gui.persistence.JSonVisitor;
import edu.uga.miage.m1.polygons.gui.persistence.Visitor;
import edu.uga.miage.m1.polygons.gui.persistence.XMLVisitor;

@ExtendWith(MockitoExtension.class)
class SquareTest {

	private int counterVisitorSquare;

	@Test
	void test_getters() {
		Square s = new Square(0, 0);
		assertEquals(0, s.getX());
		assertEquals(0, s.getY());
	}

	@Test
	void test_visit_with_mock(@Mock Visitor v) {
		Square s = new Square(0, 0);
		s.accept(v);
		verify(v, times(1)).visit(s);
	}

	@Test
    void test_visit_without_mockito() {
                
        Circle c = new Circle(0, 0);
        
        Visitor v = new Visitor() {
            
            @Override
            public void visit(Circle circle) {
                // ne rien faire       
            }

            @Override
            public void visit(Square square) {
                counterVisitorSquare++;
                
            }

            @Override
            public void visit(Triangle triangle) {
                // ne rien faire
                
            }

            
            
        };
        
        c.accept(v);
        assertEquals(1, counterVisitorSquare);
    }
	
	@Test
    void test_Jsonvisitor(){
        Square s = new Square(0,0);

        JSonVisitor visitor = new JSonVisitor();

        visitor.visit(s);

        visitor.save();

        assertEquals("{\"shapes\" : [\n{\n\"type\": \"square\",\n\"x\": " + s.getX() + ",\n\"y\": " + s.getY() + "\n}" +  "] }", visitor.getRepresentation().toString());
    }


    @Test 
    void test_XMLVisitor(){

        Square square = new Square(0,0);

        XMLVisitor visitor = new XMLVisitor();

        visitor.visit(square);

        visitor.save();

        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<shape>\n<type>square</type>\n<x>" + square.getX() +"</x>\n<y>" + square.getY() + "</y>\n</shape>\n", visitor.getRepresentation());

    }
}
