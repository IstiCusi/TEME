/*************************************************************************
 * 
 *  WWW.PHONON.CH CONFIDENTIAL 
 *
 *  2012 - 2020, Stephan Strauss, www.phonon.ch, Zurich, Switzerland
 *  All Rights Reserved.
 * 
 *************************************************************************/

// TODO: Represent the Point as Point in PictureSpace (See preliminary class 
// definition 
// TODO: Check, if the default constructor should not be private ... 
// (use a unit test) because the intial state can only be defined when 
// a Drawable is present.

package ch.phonon.drawables;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import ch.phonon.temview.TEMView;

/**
 * The {@link DrawablePoint} is a drawable item (a {@link Drawable}) that is
 * additionally associated with a position in picture coordinates. This data
 * structure is added to the pool, because the typical contains() function of
 * the AWT shapes refers to coordinates relative to the {@link TEMView}, aka
 * mouse coordinate, while the point is identified in the space of the original
 * picture. Both the {@link Drawable} item as also the associated location in
 * picture coordinates are components of this class and delegated by its
 * methods.
 * 
 * @author phonon
 * 
 */
public class DrawablePoint implements Drawable {

	Drawable drawable = null;
	Point2D.Double point = null;

	/**
	 * The constructor constructs a {@link DrawablePoint} by associating the
	 * drawable with the point in picture coordinate.
	 * 
	 * @param drawable
	 * @param point
	 */
	public DrawablePoint(Drawable drawable, Point2D.Double point) {
		this.drawable = drawable;
		this.point = point;
	}

	@Override
	public boolean contains(int x, int y) {
		return drawable.contains(x, y);
	}

	@Override
	public void paint(Graphics2D graphicsContext, AffineTransform transformation) {
		drawable.paint(graphicsContext, transformation);
	}

	/**
	 * This method gives back the reference of the associated {@link Drawable}
	 * item.
	 * 
	 * @return associated {@link Drawable}
	 */
	public Drawable getDrawable() {
		return drawable;
	}

	/**
	 * This method gives back the reference of the associated point in picture
	 * coordinates.
	 * 
	 * @return associated point
	 */
	public Point2D.Double getPoint() {
		return point;
	}

	/**
	 * This method associates a new {@link Drawable} item
	 * 
	 * @param drawable
	 *            to associate
	 */
	public void setDrawable(Drawable drawable) {
		this.drawable = drawable;
	}

	/**
	 * This method associates a new point in picture space coordinates
	 * 
	 * @param point
	 */
	public void setPoint(Point2D.Double point) {
		this.point = point;
	}

}
