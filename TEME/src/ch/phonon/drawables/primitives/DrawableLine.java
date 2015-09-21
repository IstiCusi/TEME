/*************************************************************************
 * 
 *  WWW.PHONON.CH CONFIDENTIAL 
 *
 *  2012 - 2020, Stephan Strauss, www.phonon.ch, Zurich, Switzerland
 *  All Rights Reserved.
 * 
 *************************************************************************/

package ch.phonon.drawables.primitives;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import ch.phonon.drawables.AbstractDrawable;
import ch.phonon.drawables.Drawable;
import ch.phonon.drawables.decoration.Decoratable;
import ch.phonon.drawables.decoration.DrawableShapeDecorations;
import ch.phonon.drawables.orientation.LocalOrientation;
import ch.phonon.drawables.orientation.StarPoint;

/**
 * 
 * The {@link DrawableLine} is a {@link Drawable} object representing a line.
 * 
 * @author phonon
 * 
 */
public class DrawableLine extends AbstractDrawable
		implements Decoratable<DrawableShapeDecorations> {

	private Line2D line;
	double width, height;

	private DrawableShapeDecorations decorations =
			DrawableShapeDecorations.STANDARD_DECORATIONS;

	/**
	 * This constructor is used to define a {@link DrawableLine} object by
	 * providing its {@link StarPoint} position and {@link LocalOrientation} and
	 * the coordinates of the beginning and end point of the line.
	 * 
	 * @param starpoint
	 * @param localOrientation
	 * @param point1
	 * @param point2
	 */
	public DrawableLine(StarPoint starpoint, LocalOrientation localOrientation,
			Point2D.Double point1, Point2D.Double point2) {

		super(starpoint, localOrientation);

		this.line = new Line2D.Double(point1, point2);

		this.width = this.line.getBounds2D().getWidth();

		this.height = this.line.getBounds2D().getHeight();


	}

	
	@Override
	public void draw(Graphics2D graphicsContext,
			AffineTransform locationTransform) {

		graphicsContext.setColor(this.decorations.getColor());
		graphicsContext.setStroke(this.decorations.getStroke());
		graphicsContext
				.draw(locationTransform.createTransformedShape(this.line));

	}

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
		// A line never contains ...
		return false;
	}

	@Override
	public void applyDecorations(DrawableShapeDecorations d) {
		this.decorations = d;
	}

	@Override
	public DrawableShapeDecorations getDecorations() {
		return this.decorations;
	}
}
