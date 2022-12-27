package edu.uga.miage.m1.polygons.gui.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.Square;
import edu.uga.miage.m1.polygons.gui.shapes.Triangle;

/**
 *  @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 *
 */
class XMLVisitorTest {

	@Test
	void test_circle_visitor() {
		var c = new Circle(0, 0);
		String expectedRepresentation = 
				String.format("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><shapes><shape><type>%s</type><x>%d</x><y>%d</y></shape></shapes>", "circle", c.getX(), c.getY());
		XMLVisitor visitor = new XMLVisitor();
		c.accept(visitor);
		
		visitor.save("test.xml");
		String representation = visitor.getRepresentation();
		if (representation == null) {
			fail("The visitor sequence must be implemented for the circle");
		}
		
		assertEquals(expectedRepresentation, representation);
	}	

	@Test
	void test_triangle_visitor() {
		var t = new Triangle(0, 0);
		String expectedRepresentation = 
				String.format("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><shapes><shape><type>%s</type><x>%d</x><y>%d</y></shape></shapes>", "triangle", t.getX(), t.getY());
		
		XMLVisitor visitor = new XMLVisitor();
		t.accept(visitor);
		visitor.save("test.xml");
		String representation = visitor.getRepresentation();
		if (representation == null) {
			fail("The visitor sequence must be implemented for the circle");
		}
		
		assertEquals(expectedRepresentation, representation);
	}

	@Test
	void test_square_visitor() {
		var t = new Square(0,0);
		String expectedRepresentation = 
				String.format("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><shapes><shape><type>%s</type><x>%d</x><y>%d</y></shape></shapes>", "square", t.getX(), t.getY());
		
		XMLVisitor visitor = new XMLVisitor();
		t.accept(visitor);
		visitor.save("test.xml");
		String representation = visitor.getRepresentation();
		if (representation == null) {
			fail("The visitor sequence must be implemented for the circle");
		}
		
		assertEquals(expectedRepresentation, representation);
	}

 }
