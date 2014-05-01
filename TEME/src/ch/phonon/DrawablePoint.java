/**
 * 
 */
package ch.phonon;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;


public class DrawablePoint implements Drawable { 
	
	Drawable drawable 		= null;
	Point2D.Double point	= null;	
	
	public DrawablePoint (Drawable drawable, Point2D.Double point) {
		this.drawable = drawable;
		this.point = point;
	}
		
	/**
	 * @return the drawable
	 */
	public Drawable getDrawable() {
		return drawable;
	}

	/**
	 * @return the point
	 */
	public Point2D.Double getPoint() {
		return point;
	}

	/**
	 * @param drawable the drawable to set
	 */
	public void setDrawable(Drawable drawable) {
		this.drawable = drawable;
	}

	/**
	 * @param point the point to set
	 */
	public void setPoint(Point2D.Double point) {
		this.point = point;
	}

	/**
	 * @param graphicsContext
	 * @param transformation
	 * @see ch.phonon.Drawable#paint(java.awt.Graphics2D, java.awt.geom.AffineTransform)
	 */
	public void paint(Graphics2D graphicsContext, AffineTransform transformation) {
		drawable.paint(graphicsContext, transformation);
	}

	/**
	 * @return
	 * @see ch.phonon.Drawable#getLocalOrientationState()
	 */
	public LocalOrientation getLocalOrientationState() {
		return drawable.getLocalOrientationState();
	}

	/**
	 * @param localOrientation
	 * @see ch.phonon.Drawable#setLocalOrientationState(ch.phonon.LocalOrientation)
	 */
	public void setLocalOrientationState(LocalOrientation localOrientation) {
		drawable.setLocalOrientationState(localOrientation);
	}

	/**
	 * @return
	 * @see ch.phonon.Drawable#getStarPoint()
	 */
	public StarPoint getStarPoint() {
		return drawable.getStarPoint();
	}

	/**
	 * @param starPoint
	 * @see ch.phonon.Drawable#setStarPoint(ch.phonon.StarPoint)
	 */
	public void setStarPoint(StarPoint starPoint) {
		drawable.setStarPoint(starPoint);
	}

	/**
	 * @return
	 * @see ch.phonon.Drawable#getInitialOrientationState()
	 */
	public LocalOrientation getInitialOrientationState() {
		return drawable.getInitialOrientationState();
	}

	/**
	 * @param initialOrientationState
	 * @see ch.phonon.Drawable#setInitialOrientationState(ch.phonon.LocalOrientation)
	 */
	public void setInitialOrientationState(
			LocalOrientation initialOrientationState) {
		drawable.setInitialOrientationState(initialOrientationState);
	}

	/**
	 * @return
	 * @see ch.phonon.Drawable#getWidth()
	 */
	public double getWidth() {
		return drawable.getWidth();
	}

	/**
	 * @return
	 * @see ch.phonon.Drawable#getHeight()
	 */
	public double getHeight() {
		return drawable.getHeight();
	}

	/**
	 * @param invariantScaling
	 * @see ch.phonon.Drawable#setInvariantScaling(boolean)
	 */
	public void setInvariantScaling(boolean invariantScaling) {
		drawable.setInvariantScaling(invariantScaling);
	}

	/**
	 * @return
	 * @see ch.phonon.Drawable#getInvariantScaling()
	 */
	public boolean getInvariantScaling() {
		return drawable.getInvariantScaling();
	}

	/**
	 * @param invariantRotation
	 * @see ch.phonon.Drawable#setInvariantRotation(boolean)
	 */
	public void setInvariantRotation(boolean invariantRotation) {
		drawable.setInvariantRotation(invariantRotation);
	}

	/**
	 * @return
	 * @see ch.phonon.Drawable#getInvariantRotation()
	 */
	public boolean getInvariantRotation() {
		return drawable.getInvariantRotation();
	}

	/**
	 * @param x
	 * @param y
	 * @return
	 * @see ch.phonon.Drawable#contains(int, int)
	 */
	public boolean contains(int x, int y) {
		return drawable.contains(x, y);
	}
		
}
