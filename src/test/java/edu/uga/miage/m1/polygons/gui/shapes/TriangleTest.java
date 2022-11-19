package edu.uga.miage.m1.polygons.gui.shapes;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import edu.uga.miage.m1.polygons.gui.persistence.JSonVisitor;
import edu.uga.miage.m1.polygons.gui.persistence.Visitor;
import edu.uga.miage.m1.polygons.gui.persistence.XMLVisitor;


class TriangleTest {


	private int counterVisitorTriangle;


	@Test
	void test_getters() {
		Triangle t = new Triangle(0, 0);
		assertEquals(-25, t.getX());
		assertEquals(-25, t.getY());
	}

	@Test
	void test_visit_without_mockito() {
				
		Triangle t = new Triangle(0, 0);
		
		Visitor v = new Visitor() {

			@Override
			public void visit(SimpleShape triangle) {
				counterVisitorTriangle++;
				
			}

			public void save(String fileName){}
			
			
		};
		
		t.accept(v);
		assertEquals(1, counterVisitorTriangle);
	}

	@Test
    void test_Jsonvisitor(){
        Triangle t = new Triangle(0,0);

        JSonVisitor visitor = new JSonVisitor();

        visitor.visit(t);

        visitor.save("/exemple.json");

        assertEquals("{\"shapes\" : [\n{\n\"type\": \"triangle\",\n\"x\": " + t.getX() + ",\n\"y\": " + t.getY() + "\n}" +  "] }", visitor.getRepresentation().toString());
    }


    @Test 
    void test_XMLVisitor(){

        Triangle triangle = new Triangle(0,0);

        XMLVisitor visitor = new XMLVisitor();

        visitor.visit(triangle);

        visitor.save("/exemple.xml");

        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<shape>\n<type>triangle</type>\n<x>" + triangle.getX() +"</x>\n<y>" + triangle.getY() + "</y>\n</shape>\n", visitor.getRepresentation());

    }

	@Test
	void shouldMoveTriangle(){
		Triangle triangle = new Triangle(0, 0);
		triangle.move(50, 50);

		assertEquals(25, triangle.getX());
		assertEquals(25, triangle.getY());
	}
}
