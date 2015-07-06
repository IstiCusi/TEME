/*************************************************************************
 * 
 *  WWW.PHONON.CH CONFIDENTIAL 
 *
 *  2012 - 2020, Stephan Strauss, www.phonon.ch, Zurich, Switzerland
 *  All Rights Reserved.
 * 
 *************************************************************************/
package ch.phonon.temview;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author phonon
 *
 */
public class TEMViewStateTest {

	/**
	 * Test contracts of the {@link TEMViewState} class
	 */
	@Test
	public void test() {
		
		TEMViewState state 		= 	new TEMViewState(100,50,20,90,400,500);
		
		/** 
		 * Test copy constructor
		 */
		TEMViewState cpState 	=	new TEMViewState(state);		
		assertEquals(state, cpState);
		assertNotSame(cpState, state);
		
		/** 
		 * Test clone 
		 * TODO: clone should be anyway taken out of the class 
		 */
		
		
	}

}
