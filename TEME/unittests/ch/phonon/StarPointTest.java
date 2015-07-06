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


	/**
		 * Test method for {@link ch.phonon.StarPoint#getOrientedAngle(ch.phonon.StarPoint, ch.phonon.StarPoint)}.
		 */
		@Test
		public void testGetOrientedAngle()  {
			
			double epsilon = 1e-7; 
			
			StarPoint a = 		new StarPoint (1, 0);
			
			StarPoint b = 		new StarPoint (0, 1);
			double result = StarPoint.getOrientedAngle(a, b);
			assertEquals(90, result,epsilon);
			
			b = 		new StarPoint (-1, 0);
			result = StarPoint.getOrientedAngle(a, b);
			assertEquals(180, result,epsilon);
			
			b = 		new StarPoint (0, -1);
			result = StarPoint.getOrientedAngle(a, b);
			assertEquals(-90, result,epsilon);
	
			b = 		new StarPoint (1, 0);
			result = StarPoint.getOrientedAngle(a, b);
			assertEquals(0, result,epsilon);
			
			
			
		}


	/**
	 * Test method for {@link ch.phonon.StarPoint#getAngle(ch.phonon.StarPoint, ch.phonon.StarPoint)}.
	 */
	@Test
	public void testGetAngle()  {
		
		double epsilon = 1e-7;
		
		StarPoint a = 		new StarPoint (1, 0);
		
		StarPoint b = 		new StarPoint (0, 1);
		double result = StarPoint.getAngle(a, b);
		assertEquals(90, result,epsilon);
		assertEquals(StarPoint.getAngle(a, b), StarPoint.getAngle(b, a),epsilon);
		
		b = 		new StarPoint (-1, 0);
		result = StarPoint.getAngle(a, b);
		assertEquals(180, result,epsilon);
		assertEquals(StarPoint.getAngle(a, b), StarPoint.getAngle(b, a),epsilon);
		
		b = 		new StarPoint (0, -1);
		result = StarPoint.getAngle(a, b);
		assertEquals(90, result,epsilon);
		assertEquals(StarPoint.getAngle(a, b), StarPoint.getAngle(b, a),epsilon);
		
		b = 		new StarPoint (-0.70719, -0.70719);
		result = StarPoint.getAngle(a, b);
		assertEquals(135, result,epsilon);
		assertEquals(StarPoint.getAngle(a, b), StarPoint.getAngle(b, a),epsilon);
				
		b = 		new StarPoint (1, 0);
		result = StarPoint.getAngle(a, b);
		assertEquals(0, result,epsilon);
		assertEquals(StarPoint.getAngle(a, b), StarPoint.getAngle(b, a),epsilon);
		
	}


	/**
				 * Test method for {@link ch.phonon.StarPoint#getCounterClockWiseAngle(ch.phonon.StarPoint, ch.phonon.StarPoint)}.
				 */
				@Test
				public void testGetCounterClockWiseAngle()  {
					
					double epsilon = 1e-7;
					
					StarPoint a = 		new StarPoint (1, 0);
					
					StarPoint b = 		new StarPoint (0, 1);
					double result = StarPoint.getCounterClockWiseAngle(a, b);
					assertEquals(90, result,epsilon);
					
					b = 		new StarPoint (-1, 0);
					result = StarPoint.getCounterClockWiseAngle(a, b);
					assertEquals(180, result,epsilon);
		
					b = 		new StarPoint (-0.70719, -0.70719);
					result = StarPoint.getCounterClockWiseAngle(a, b);
					assertEquals(180+45, result,epsilon);
		
					b = 		new StarPoint (1, 0);
					result = StarPoint.getAngle(a, b);
					assertEquals(0, result,epsilon);

				}

}
