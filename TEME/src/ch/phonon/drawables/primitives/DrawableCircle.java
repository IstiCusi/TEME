/*************************************************************************
 * 
 *  WWW.PHONON.CH CONFIDENTIAL 
 *
 *  2012 - 2020, Stephan Strauss, www.phonon.ch, Zurich, Switzerland
 *  All Rights Reserved.
 * 
 *************************************************************************/

//TODO: Use the DrawableProperties 

package ch.phonon.drawables.primitives;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

import ch.phonon.drawables.AbstractDrawable;
import ch.phonon.drawables.Drawable;
import ch.phonon.drawables.decoration.Decoratable;
import ch.phonon.drawables.decoration.DrawableShapeDecorations;
import ch.phonon.drawables.orientation.LocalOrientation;
import ch.phonon.drawables.orientation.StarPoint;

/**
 * 
 * The {@link DrawableCircle} is a {@link Drawable} object representing a
 * circle.
 * 
 * @author phonon
 * 
 */
public class DrawableCircle extends AbstractDrawable
		implements Decoratable<DrawableShapeDecorations> {

	private Ellipse2D ellipse;
	double width, height;
	AffineTransform localTransform;

	private DrawableShapeDecorations decorations =
			DrawableShapeDecorations.STANDARD_DECORATIONS;

	// ----------------------------Constructors --------------------------------

	/**
	 * This constructor is used to define a {@link DrawableCircle} object by
	 * providing its {@link StarPoint} position and {@link LocalOrientation}
	 * 
	 * @param starpoint
	 *            global position of the {@link DrawableCircle}
	 * @param localOrientation
	 *            local orientation of the {@link DrawableCircle}
	 * @param width
	 *            width of the {@link DrawableCircle}
	 * @param height
	 *            height of the {@link DrawableCircle}
	 */
	public DrawableCircle(StarPoint starpoint,
			LocalOrientation localOrientation, int width, int height) {

		super(starpoint, localOrientation);

		this.width = width;
		this.height = height;
		this.ellipse = new Ellipse2D.Double(0, 0, width, height);

	}

	// ----------------------- Drawable contract -------------------------------
	
	@Override
	public void draw(Graphics2D graphicsContext,
			AffineTransform locationTransform) {

		this.localTransform = locationTransform;

		graphicsContext.setColor(this.decorations.getColor());
		graphicsContext.setStroke(this.decorations.getStroke());
		graphicsContext
				.draw(locationTransform.createTransformedShape(this.ellipse));

	}

	// ----------------------- Decoratable contract ----------------------------

	/**
	 * Applies {@link DrawableShapeDecorations} to the Drawable
	 */

	@Override
	public void applyDecorations(DrawableShapeDecorations d) {
		this.decorations = d;
	}

	/**
	 * Returns a delegate from the used {@link DrawableShapeDecorations} of this
	 * {@link DrawableCircle}.
	 * 
	 * @return {@link DrawableShapeDecorations}
	 * 
	 */
	@Override
	public DrawableShapeDecorations getDecorations() {
		return this.decorations;
	}

	// ----------------------- Setter and Getters ------------------------------
	
	@Override
	public double getWidth() {
		return this.width;
	}

	@Override
	public double getHeight() {
		return this.height;
	}

	@Override
	public boolean contains(int x, int y) {
		return this.localTransform.createTransformedShape(this.ellipse)
				.contains(x, y);
	}
	
}
