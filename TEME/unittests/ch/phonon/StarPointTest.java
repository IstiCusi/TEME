/**
 * 
 */
package ch.phonon;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author phonon
 *
 */
public class StarPointTest {

	
	/**
	 * Test method for {@link ch.phonon.StarPoint#createRotatedStarPoint(ch.phonon.StarPoint, double)}.
	 */
	@Test
	public void testCreateRotatedStarPoint() {
		
		double epsilon = 1e-7; 
		
		StarPoint originalStarPoint = new StarPoint (10, 0);
		StarPoint newStarPoint = StarPoint.createRotatedStarPoint(originalStarPoint, Math.toRadians(+90));
		assertNotSame("Not a new object was created", originalStarPoint, newStarPoint);
		
		assertEquals(10.0, originalStarPoint.getX(),epsilon);
		assertEquals( 0.0, originalStarPoint.getY(),epsilon);
		
		assertEquals( 0.0, newStarPoint.getX(),epsilon);
		assertEquals(10.0, newStarPoint.getY(),epsilon);
		
	}

	
	/**
	 * Test method for {@link ch.phonon.StarPoint#rotateStarPoint(double)}.
	 */
	@Test
	public void testRotateStarPoint() {
		
		StarPoint originalStarPoint = new StarPoint (10, 0);
		
			double epsilon = 1e-7; 
		
		
		// Check counter clock rotation for positive angle 
		// and error propagation 
		
		for (int i = 0 ; i < 1e3 ; i++) {
		
			originalStarPoint.rotateStarPoint(Math.toRadians(0));
			assertEquals(10.0, originalStarPoint.getX(),epsilon);
			assertEquals( 0.0, originalStarPoint.getY(),epsilon);
			
			originalStarPoint.rotateStarPoint(Math.toRadians(+90));
			assertEquals( 0.0, originalStarPoint.getX(),epsilon);
			assertEquals(10.0, originalStarPoint.getY(),epsilon);
	
			originalStarPoint.rotateStarPoint(Math.toRadians(+90));		
			assertEquals(-10.0, originalStarPoint.getX(),epsilon);
			assertEquals(  0.0, originalStarPoint.getY(),0.1);
			
			originalStarPoint.rotateStarPoint(Math.toRadians(+90));		
			assertEquals(0.0, originalStarPoint.getX(),epsilon);
			assertEquals(-10.0, originalStarPoint.getY(),epsilon);
			
			originalStarPoint.rotateStarPoint(Math.toRadians(+90));		
			assertEquals(10.0, originalStarPoint.getX(),epsilon);
			assertEquals(0.0, originalStarPoint.getY(),epsilon);

		}

		
		
	}


	/**
	 * Test method for {@link ch.phonon.StarPoint#getDifference(ch.phonon.StarPoint, ch.phonon.StarPoint)}.
	 */
	@Test
	public void testGetDifference()  {
		
		StarPoint start = 		new StarPoint (10, 3);
		StarPoint end   = 		new StarPoint (4, 10);
		StarPoint start2end = 	new StarPoint (-6, 7);
				
		StarPoint result = StarPoint.getDifference(start, end);
		
		System.out.println(start2end);
		System.out.println(result);
				
		assertTrue(result.equals(start2end));
		
	}


	/**
	 * Test method for {@link ch.phonon.StarPoint#equals(java.lang.Object)}.
	 */
	@Test
	public void testEquals() {
		
		StarPoint P1 = 		new StarPoint (10.0000000001, 1);
		StarPoint P2   = 	new StarPoint (10.0000000000, 1);

		assertFalse(P1.equals(P2));
	}

}
