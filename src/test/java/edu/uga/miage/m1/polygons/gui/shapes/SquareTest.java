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
		assertEquals(-25, s.getX());
		assertEquals(-25, s.getY());
	}

	@Test
	void test_visit_with_mock(@Mock Visitor v) {
		Square s = new Square(0, 0);
		s.accept(v);
		verify(v, times(1)).visit(s);
	}

	@Test
    void test_visit_without_mockito() {
                
        Square s = new Square(0, 0);
        
        Visitor v = new Visitor() {
            @Override
            public void visit(SimpleShape triangle) {
                counterVisitorSquare ++;
                
            }
            @Override
            public void save(String fileame){}

            public void visit(CompositeShape compositeShape){}
            
        };
        
        s.accept(v);
        assertEquals(1, counterVisitorSquare);
    }
	
	@Test
    void test_Jsonvisitor(){
        Square s = new Square(0,0);

        JSonVisitor visitor = new JSonVisitor();

        visitor.visit(s);

        visitor.save("/exemple.json");

        assertEquals("{\"shapes\" : [\n{\n\"type\": \"square\",\n\"x\": " + s.getX() + ",\n\"y\": " + s.getY() + "\n}" +  "] }", visitor.getRepresentation().toString());
    }


    @Test 
    void test_XMLVisitor(){

        Square square = new Square(0,0);

        XMLVisitor visitor = new XMLVisitor();

        visitor.visit(square);

        visitor.save("/exemple.xml");

        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><shapes><shape><type>square</type><x>" + square.getX() +"</x><y>" + square.getY() + "</y></shape></shapes>", visitor.getRepresentation());

    }


    @Test
	void shouldMoveSquare(){
		Square s= new Square(0, 0);
		s.move(50, 50);

		assertEquals(25, s.getX());
		assertEquals(25, s.getY());
	}

}
