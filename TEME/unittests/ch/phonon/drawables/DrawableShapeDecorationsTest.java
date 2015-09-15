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
public class DrawableShapeDecorationsTest {

	/**
	 * Tests the functionality of the {@link DrawableShapeDecorations} class
	 * concerning a mutable object.
	 */
	@Test
	public void buildTest() {

		BasicStroke stroke = new BasicStroke(10);
		Color color = new Color(12, 15, 100);

		DrawableShapeDecorations myProperties =
				new DrawableShapeDecorations.Builder().color(color)
						.stroke(stroke).build();

		assertSame(stroke, myProperties.getStroke());
		assertSame(color, myProperties.getColor());

	}

	/**
	 * Tests the functionality of the {@link DrawableShapeDecorations} class
	 * concerning an immutable object creation.
	 */
	@Test
	public void buildImmutableTest() {

		BasicStroke stroke = new BasicStroke(10);
		Color color = new Color(12, 15, 100);

		DrawableShapeDecorations myProperties =
				new DrawableShapeDecorations.Builder().color(color)
						.stroke(stroke).buildImmutable();

		assertSame(stroke, myProperties.getStroke());
		assertSame(color, myProperties.getColor());

	}

	/**
	 * Test if the assertion holds, that a {@link UnsupportedOperationException}
	 * is thrown in case of a client that tries to modify the obtained immutable
	 * object.
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void ImmutabilityTest() {

		BasicStroke stroke = new BasicStroke(10);
		Color color = new Color(12, 15, 100);

		DrawableShapeDecorations myProperties =
				new DrawableShapeDecorations.Builder().color(color)
						.stroke(stroke).buildImmutable();

		myProperties.setColor(new Color(13, 88, 2));

		// The state of the myProperties object should have not been changed

		assertSame(stroke, myProperties.getStroke());
		assertSame(new Color(13, 88, 2), myProperties.getColor());


	}

}
