/**
 * 
 */
package ch.phonon;

import java.awt.geom.Point2D;
import java.util.Observable;

import ch.phonon.drawables.Drawable;

/**
 * @author phonon
 * A {@link StarPoint} represents the global location of an object in 
 * reference to the global coordinate system. {@link Drawable} objects have 
 * an internal {@link LocalOrientation} relative to the {@link StarPoint}.
 */
public class StarPoint extends Observable {

	Point2D.Double starpoint;
	/** 
	 * The standard constructor is identified with the global coordinate
	 * (0,0) of the user space.
	 */
	public StarPoint() {
		this.starpoint = new Point2D.Double();
		starpoint.x=0;
		starpoint.y=0;
	}
	
	/**
	 * This constructor allows to set directly the components of the star
	 * point in the user space.
	 * @param x component in user space
	 * @param y component in user space
	 */
	public StarPoint(double x, double y) {
		this.starpoint = new Point2D.Double();
		starpoint.x=x;
		starpoint.y=y;
	}
	/** 
	 * 
	 * @param point
	 */
	public StarPoint(Point2D.Double point) {
		this.starpoint = new Point2D.Double();
		if ( point instanceof Point2D.Double ) {
			starpoint = (Point2D.Double) point.clone();
		}
	}

	public void setPoint (Point2D.Double point) {
		if ( point instanceof Point2D.Double ) {
			starpoint = (Point2D.Double) point.clone();
		}
		setChanged();
		notifyAll();
	}

	public double getX() {
		return starpoint.x;
	}
	
	public double getY() {
		return starpoint.y;
	}
	
	@Override
	public Object clone()  {
		return new StarPoint(this.starpoint.x, this.starpoint.y);
	}
	
	

}
