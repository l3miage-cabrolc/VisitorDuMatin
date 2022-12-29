package edu.uga.miage.m1.polygons.gui.shapes;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.junit.jupiter.api.Test;
import edu.uga.miage.m1.polygons.gui.persistence.JSonVisitor;
import edu.uga.miage.m1.polygons.gui.persistence.Visitor;
import edu.uga.miage.m1.polygons.gui.persistence.XMLVisitor;

public class SoleilTest {
    private int counterVisitorSoleil;


	@Test
	void test_getters() {
		Soleil c = new Soleil(0, 0);
		assertEquals(-25, c.getX());
		assertEquals(-25, c.getY());
	}
	
	@Test
	void test_visit_without_mockito() {
				
		Soleil c = new Soleil(0, 0);
		
		Visitor v = new Visitor() {
			
			@Override
			public void visit(SimpleShape Soleil) {
				counterVisitorSoleil++;			
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
		assertEquals(1, counterVisitorSoleil);
	}

	@Test
	void test_Jsonvisitor(){
		Soleil c = new Soleil(0,0);

		JSonVisitor visitor = new JSonVisitor();

		visitor.visit(c);

		visitor.save("/exemple.json");

		assertEquals("{\"shapes\":[{\"type\":\"soleil\",\"x\":" + c.getX() + ",\"y\":" + c.getY() + "}" +  "]}", visitor.getRepresentation().toString());
	}


	@Test 
	void test_XMLVisitor(){

		Soleil soleil = new Soleil(0,0);

		XMLVisitor visitor = new XMLVisitor();

		visitor.visit(soleil);

		visitor.save("/exemple.xml");

		assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><shapes><shape><type>soleil</type><x>" + soleil.getX() +"</x><y>" + soleil.getY() + "</y></shape></shapes>", visitor.getRepresentation());

	}

	@Test
	void shouldMoveSoleil(){
		Soleil soleil = new Soleil(0, 0);
		soleil.move(50, 50);

		assertEquals(25, soleil.getX());
		assertEquals(25, soleil.getY());
	}
}
