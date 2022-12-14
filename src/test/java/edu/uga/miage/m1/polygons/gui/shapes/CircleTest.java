package edu.uga.miage.m1.polygons.gui.shapes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

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
		assertEquals(-25, c.getX());
		assertEquals(-25, c.getY());
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

			public void visit(CompositeShape compositeShape){}

			@Override
			public void save(String fileName){}

			@Override
			public List<SimpleShape> importFile(String filename){
				return null;
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

		assertEquals("{\"shapes\":[{\"type\":\"circle\",\"x\":" + c.getX() + ",\"y\":" + c.getY() + "}" +  "]}", visitor.getRepresentation().toString());
	}


	@Test 
	void test_XMLVisitor(){

		Circle circle = new Circle(0,0);

		XMLVisitor visitor = new XMLVisitor();

		visitor.visit(circle);

		visitor.save("/exemple.xml");

		assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><shapes><shape><type>circle</type><x>" + circle.getX() +"</x><y>" + circle.getY() + "</y></shape></shapes>", visitor.getRepresentation());

	}

	@Test
	void shouldMoveCircle(){
		Circle circle = new Circle(0, 0);
		circle.move(50, 50);

		assertEquals(25, circle.getX());
		assertEquals(25, circle.getY());
	}
}
