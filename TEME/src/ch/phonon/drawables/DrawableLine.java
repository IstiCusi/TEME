/*************************************************************************
 * 
 *  WWW.PHONON.CH CONFIDENTIAL 
 *
 *  2012 - 2020, Stephan Strauss, www.phonon.ch, Zurich, Switzerland
 *  All Rights Reserved.
 * 
 *************************************************************************/

package ch.phonon.drawables;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import ch.phonon.LocalOrientation;
import ch.phonon.StarPoint;

/**
 * 
 * The {@link DrawableLine} is a {@link Drawable} object representing a line.
 * 
 * @author phonon
 * 
 */
public class DrawableLine extends AbstractDrawable {

	private Line2D line;
	private Color color;
	double width, height;
	private Stroke stroke;

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
		;
		this.height = this.line.getBounds2D().getHeight();
		;

		this.color = new Color(255, 0, 0);
		this.stroke = new BasicStroke(2.0f);

	}

	/**
	 * Sets the color of the circle
	 * 
	 * @param color
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Sets the {@link Stroke} type of the circle
	 * 
	 * @param stroke
	 */

	public void setStroke(Stroke stroke) {
		this.stroke = stroke;
	}

	@Override
	void draw(Graphics2D graphicsContext, AffineTransform locationTransform) {

		graphicsContext.setColor(this.color);
		graphicsContext.setStroke(this.stroke);
		graphicsContext.draw(locationTransform
				.createTransformedShape(this.line));

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
}
