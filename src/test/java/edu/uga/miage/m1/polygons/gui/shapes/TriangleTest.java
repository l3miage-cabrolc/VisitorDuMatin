package edu.uga.miage.m1.polygons.gui.shapes;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.uga.miage.m1.polygons.gui.persistence.Visitor;


class TriangleTest {


	private int counterVisitorTriangle;


	@Test
	void test_getters() {
		Triangle t = new Triangle(0, 0);
		assertEquals(-25, t.getX());
		assertEquals(-25, t.getY());
	}
	
	@Test
	void test_visit_with_mock(@Mock Visitor v) {
		Triangle t = new Triangle(0, 0);		
		t.accept(v);
		verify(v, times(1)).visit(t);
	}

	@Test
	void test_visit_without_mockito() {
				
		Triangle t = new Triangle(0, 0);
		
		Visitor v = new Visitor() {
			
			@Override
			public void visit(Circle circle) {
				// ne rien faire 		
			}

			@Override
			public void visit(Square square) {
				// ne rien faire
				
			}

			@Override
			public void visit(Triangle triangle) {
				counterVisitorTriangle++;
				
			}

			
			
		};
		
		t.accept(v);
		assertEquals(1, counterVisitorTriangle);
	}


}
