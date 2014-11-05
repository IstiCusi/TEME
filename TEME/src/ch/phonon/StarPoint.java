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
	
	public static StarPoint getDifference (StarPoint a,StarPoint b) {
		
		double x = b.getX()-a.getX();
		double y = b.getY()-a.getY();
		
		StarPoint difference = new StarPoint(x,y);
		return difference;
		
	}
	
	public static double getNorm (StarPoint sPoint) {
		double norm = Math.sqrt(
				Math.pow(sPoint.getX(), 2) + Math.pow(sPoint.getY(), 2)
				);
		return norm;
	}
	
	public static double getDistance (StarPoint begin, StarPoint end) {
		
		double distance = getNorm (getDifference(begin, end));				
		return distance;
	}

	public static double getDotProduct (StarPoint a, StarPoint b) {
		return a.getX()*b.getX() + a.getY()*b.getY();
	}
	
	public static double getAngle (StarPoint a, StarPoint b) {
		
		double normA = getNorm(a);
		double normB = getNorm(b);
		
		double dotProduct = getDotProduct(a, b);
		
		double angle = Math.acos(dotProduct/(normA * normB))*360.0/(2*Math.PI);
		return angle;
		
	}
	
	public static StarPoint getUnitVector (StarPoint vector) {
			
			double normVector = getNorm(vector);
			StarPoint unitVector = new StarPoint(vector.getX()/normVector,vector.getY()/normVector);
			return unitVector;
		}
	
	public static StarPoint getScaleVector (StarPoint vector, double scale) {
		StarPoint scaledVector = new StarPoint(vector.getX()*scale,vector.getY()*scale);
		return scaledVector;
	}
	
	@Override
	public String toString() {
		return "StarPoint: (X" +this.getX()+",Y "+this.getY()+")"; 
	
	}

	
}
