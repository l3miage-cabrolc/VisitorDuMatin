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


	@Test
	void test_soleil_visitor() {
		var t = new Soleil(0,0);
		String expectedRepresentation = 
				String.format("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><shapes><shape><type>%s</type><x>%d</x><y>%d</y></shape></shapes>", "soleil", t.getX(), t.getY());
		
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
	void shouldImport(){
		List<SimpleShape> expected = new ArrayList<>();
		expected.add(new Circle(149, 21));
		expected.add(new Triangle(96, 124));
		expected.add(new Square(263, 129));
		expected.add(new Triangle(70, 220));

		List<SimpleShape> result = new XMLVisitor().importFile("testImport.xml");
		for(int i=0; i<expected.size(); i++){
			assertEquals(expected.get(i), result.get(i));
		}

	}

 }
