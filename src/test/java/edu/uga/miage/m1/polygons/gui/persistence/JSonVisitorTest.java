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
class JSonVisitorTest {

	@Test
	void test_circle_visitor() {
		var c = new Circle(0, 0);
		String expectedRepresentation = 
				String.format("{\"shapes\" : [\n{\n\"type\": \"%s\",\n\"x\": %d,\n\"y\": %d\n}] }", "circle", c.getX(), c.getY());
	
		
		JSonVisitor visitor = new JSonVisitor();
		c.accept(visitor);
		visitor.save();
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
				String.format("{\"shapes\" : [\n{\n\"type\": \"%s\",\n\"x\": %d,\n\"y\": %d\n}] }", "triangle", t.getX(), t.getY());
		
		JSonVisitor visitor = new JSonVisitor();
		t.accept(visitor);
		visitor.save();
		String representation = visitor.getRepresentation();
		if (representation == null) {
			fail("The visitor sequence must be implemented for the triangle");
		}
		assertEquals(expectedRepresentation,visitor.getRepresentation());
	}

	@Test
	void test_square_visitor() {
		var s = new Square(0, 0);
		String expectedRepresentation = 
				String.format("{\"shapes\" : [\n{\n\"type\": \"%s\",\n\"x\": %d,\n\"y\": %d\n}] }", "square", s.getX(), s.getY());

		
		JSonVisitor visitor = new JSonVisitor();
		s.accept(visitor);
		visitor.save();
		String representation = visitor.getRepresentation();
		if (representation == null) {
			fail("The visitor sequence must be implemented for the square");
		}
		
		assertEquals(expectedRepresentation, representation);
	}

}
