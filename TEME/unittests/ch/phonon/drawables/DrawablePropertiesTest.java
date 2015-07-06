/*************************************************************************
 * 
 *  WWW.PHONON.CH CONFIDENTIAL 
 *
 *  2012 - 2020, Stephan Strauss, www.phonon.ch, Zurich, Switzerland
 *  All Rights Reserved.
 * 
 *************************************************************************/
package ch.phonon.drawables;

import static org.junit.Assert.*;

import java.awt.BasicStroke;
import java.awt.Color;

import org.junit.Test;

/**
 * @author phonon
 *
 */
public class DrawablePropertiesTest {

	/**
	 * Tests the functionality of the {@link DrawableProperties} class. 
	 */
	@Test
	public void test() {
		
		BasicStroke stroke 	= new BasicStroke(10);
		Color		color	= new Color(12,15,100); 
		
		DrawableProperties myProperties =
				new DrawableProperties.Builder().
									color(color).
									stroke(stroke).
				build();
		
		assertSame(stroke, myProperties.getStroke());
		assertSame(color, myProperties.getColor());
										
	}

}
