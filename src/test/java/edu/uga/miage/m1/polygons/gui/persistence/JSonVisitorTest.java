package edu.uga.miage.m1.polygons.gui.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;
import edu.uga.miage.m1.polygons.gui.shapes.Soleil;
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
				String.format("{\"shapes\":[{\"type\":\"%s\",\"x\":%d,\"y\":%d}]}", "circle", c.getX(), c.getY());
	
		
		JSonVisitor visitor = new JSonVisitor();
		c.accept(visitor);
		visitor.save("./exemple.json");
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
				String.format("{\"shapes\":[{\"type\":\"%s\",\"x\":%d,\"y\":%d}]}", "triangle", t.getX(), t.getY());
		
		JSonVisitor visitor = new JSonVisitor();
		t.accept(visitor);
		visitor.save("./exemple.json");
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
				String.format("{\"shapes\":[{\"type\":\"%s\",\"x\":%d,\"y\":%d}]}", "square", s.getX(), s.getY());

		
		JSonVisitor visitor = new JSonVisitor();
		s.accept(visitor);
		visitor.save("./exemple.json");
		String representation = visitor.getRepresentation();
		if (representation == null) {
			fail("The visitor sequence must be implemented for the square");
		}
		
		assertEquals(expectedRepresentation, representation);
	}

	@Test
	void test_soleil_visitor() {
		var s = new Soleil(0, 0);
		String expectedRepresentation = 
				String.format("{\"shapes\":[{\"type\":\"%s\",\"x\":%d,\"y\":%d}]}", "soleil", s.getX(), s.getY());

		
		JSonVisitor visitor = new JSonVisitor();
		s.accept(visitor);
		visitor.save("./exemple.json");
		String representation = visitor.getRepresentation();
		if (representation == null) {
			fail("The visitor sequence must be implemented for the square");
		}
		
		assertEquals(expectedRepresentation, representation);
	}


	@Test
	void shouldImport(){
		List<SimpleShape> expected = new ArrayList<>();
		expected.add(new Circle(263, 168));
		expected.add(new Triangle(605, 164));
		expected.add(new Square(389, 262));

		List<SimpleShape> result = new JSonVisitor().importFile("testImport.json");
		for(int i=0; i<expected.size(); i++){
			assertEquals(expected.get(i), result.get(i));
		}

	}

}
