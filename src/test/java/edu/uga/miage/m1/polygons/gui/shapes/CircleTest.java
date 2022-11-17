package edu.uga.miage.m1.polygons.gui.shapes;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
class CircleTest {
	
	private int counterVisitorCircle;


	@Test
	void test_getters() {
		Circle c = new Circle(0, 0);
		assertEquals(-0, c.getX());
		assertEquals(0, c.getY());
	}
	
	@Test
	void test_visit_with_mock(@Mock Visitor v) {
		Circle c = new Circle(0, 0);		
		c.accept(v);
		verify(v, times(1)).visit(c);
	}

	@Test
	void test_visit_without_mockito() {
				
		Circle c = new Circle(0, 0);
		
		Visitor v = new Visitor() {
			
			@Override
			public void visit(SimpleShape circle) {
				counterVisitorCircle++;			
			}

		};
		
		c.accept(v);
		assertEquals(1, counterVisitorCircle);
	}

	@Test
	void test_Jsonvisitor(){
		Circle c = new Circle(0,0);

		JSonVisitor visitor = new JSonVisitor();

		visitor.visit(c);

		visitor.save("/exemple.json");

		assertEquals("{\"shapes\" : [\n{\n\"type\": \"circle\",\n\"x\": " + c.getX() + ",\n\"y\": " + c.getY() + "\n}" +  "] }", visitor.getRepresentation().toString());
	}


	@Test 
	void test_XMLVisitor(){

		Circle circle = new Circle(0,0);

		XMLVisitor visitor = new XMLVisitor();

		visitor.visit(circle);

		visitor.save("/exemple.xml");

		assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<shape>\n<type>circle</type>\n<x>" + circle.getX() +"</x>\n<y>" + circle.getY() + "</y>\n</shape>\n", visitor.getRepresentation());

	}
}
