/*************************************************************************
 * 
 *  WWW.PHONON.CH CONFIDENTIAL 
 *
 *  2012 - 2020, Stephan Strauss, www.phonon.ch, Zurich, Switzerland
 *  All Rights Reserved.
 * 
 *************************************************************************/

package ch.phonon;

import java.awt.geom.Point2D;

import ch.phonon.drawables.Drawable;

/** 
 * A {@link StarPoint} represents the global location of an object 
 * in reference to the global coordinate system in the pictureSystem 
 * base . {@link Drawable}
 * objects have an internal {@link LocalOrientation} relative to the
 * {@link StarPoint}.
 * @author phonon
 */
// public class StarPoint extends Observable {
public class StarPoint extends PointInPictureBase {

	Point2D.Double star;
	
	/* ********************** Constructors ************************************* */

	/**
	 * The standard constructor is identified with the global coordinate (0,0)
	 * of the PictureBase.
	 */
	public StarPoint() {
		this.star = new Point2D.Double();
		star.x = 0;
		star.y = 0;
	}

	/**
	 * This constructor allows to set directly the components of the star point
	 * in the PictureBase.
	 * 
	 * @param x component in PictureBase
	 * @param y component in PictureBase
	 */
	public StarPoint(double x, double y) {
		this.star = new Point2D.Double();
		star.x = x;
		star.y = y;
	}

	/**
	 *  This constructor creates a StarPoint based on a {@link Point2D}.
	 *  
	 * @param point
	 */
	public StarPoint(Point2D.Double point) {
		this.star = new Point2D.Double();
		if (point instanceof Point2D.Double) {
			star = (Point2D.Double) point.clone();
		}
	}

	/**
	 * @param point set position of the StarPoint
	 */
	public void setPoint(StarPoint point) {
		this.star.x = point.getX();
		this.star.y = point.getY();
	}

	/**
	 * @param point position of the StarPoint 
	 */
	public void setPoint(Point2D.Double point) {
		if (point instanceof Point2D.Double) {
			star = (Point2D.Double) point.clone();
		}
	}

	/**
	 * @return X component of the {@link StarPoint}
	 */
	public double getX() {
		return star.x;
	}

	
	/**
	 * @return Y component of the {@link StarPoint}
	 */
	public double getY() {
		return star.y;
	}

	
	/**
	 * @param x component of the {@link StarPoint}
	 */
	public void setX(double x) {
		star.x = x;
	}

	
	/**
	 * @param y component of the {@link StarPoint}
	 */
	public void setY(double y) {
		star.y = y;
	}

	@Override
	public Object clone() {
		return new StarPoint(this.star.x, this.star.y);
	}

	/**
	 * Get vector difference (end - start) as {@link StarPoint} representation
	 * @param start substrahend vector 
	 * @param end  	minuend vector 
	 * @return vector difference 
	 */
	public static StarPoint getDifference(StarPoint start, StarPoint end) {
			
		double x = end.getX() - start.getX();
		double y = end.getY() - start.getY();

		StarPoint difference = new StarPoint(x, y);
		return difference;

	}

	/**
	 * Rotates the components of the <b>this </b>object about an angle and
	 * gives back a reference for convinience. 
	 * @param angle
	 * @return reference to <b>this </b>
	 */
	public StarPoint rotateStarPoint(double angle) {

		double x = this.getX();
		double y = this.getY();
		double xNew = (x * Math.cos(angle)) - (y * Math.sin(angle));
		double yNew = (x * Math.sin(angle)) + (y * Math.cos(angle));
		this.setX(xNew);
		this.setY(yNew);
		return this;
	}
	/**
	 * Rotates the components of a {@link StarPoint} about an angle and
	 * gives back a new {@link StarPoint} object with the resulting rotated
	 * {@link StarPoint}. 
	 * @param a original {@link StarPoint} to be rotated 
	 * @param angle 
	 * @return new rotated {@link StarPoint} object
	 */
	public static StarPoint createRotatedStarPoint(StarPoint a, double angle) {

		double x = a.getX();
		double y = a.getY();
		double xNew = (x * Math.cos(angle)) - (y * Math.sin(angle));
		double yNew = (x * Math.sin(angle)) + (y * Math.cos(angle));
		return new StarPoint(xNew, yNew);
	}

	/**
	 * Calculates vector sum of two {@link StarPoint}s and gives back a new
	 * object.
	 * @param a first {@link StarPoint} vector
	 * @param b second {@link StarPoint} vector
	 * @return new object of the sum of the two 
	 */
	public static StarPoint getSum(StarPoint a, StarPoint b) {

		double x = b.getX() + a.getX();
		double y = b.getY() + a.getY();

		StarPoint sum = new StarPoint(x, y);
		return sum;

	}

	/**
	 * Calculates the length norm of the {@link StarPoint}
	 * @param sPoint {@link StarPoint} vector
	 * @return length of the {@link StarPoint} vector
	 */
	public static double getNorm(StarPoint sPoint) {
		double norm = Math.sqrt(Math.pow(sPoint.getX(), 2)
				+ Math.pow(sPoint.getY(), 2));
		return norm;
	}

	/**
	 * Calculates the scalar distance between two {@link StarPoint}s
	 * @param begin
	 * @param end
	 * @return scalar distance of <b>begin</b> and <b>end</b>
	 */
	public static double getDistance(StarPoint begin, StarPoint end) {

		double distance = getNorm(getDifference(begin, end));
		return distance;
	}

	/**
	 * Calculates vector dot product of two {@link StarPoint} vectors
	 * @param a 
	 * @param b
	 * @return dot product
	 */
	public static double getDotProduct(StarPoint a, StarPoint b) {
		return a.getX() * b.getX() + a.getY() * b.getY();
	}

	/**
	 * Calculates the angle orientation of b above a. It is negative,
	 * when the oriented angle(a,b) > 180 degree.
	 * @param a
	 * @param b
	 * @return angle orientation
	 */
	public static double getAngleOrientation(StarPoint a, StarPoint b) {
		return a.getX() * b.getY() + a.getY() * b.getX();
	}

	/**
	 * Calculates the oriented angle of two {@link StarPoint}s
	 * The angle is oriented from a to b. Angle of b above a 
	 * is counted negative, when it exceeds 180 degree.
	 * @param a
	 * @param b
	 * @return perproduct
	 */	
	public static double getOrientedAngle(StarPoint a, StarPoint b) {

		double angle = getAngle(a, b);
		
		if (getAngleOrientation(a, b) < 0) {
			angle = -angle;
		}
		return angle;
	}

	/**
	 * Calculates the (smaller and always positive angle) of two {@link StarPoint}s
	 * using the cosine equation.
	 * The angle is not oriented: angle(a,b) = angle(b,a) with 0 < angle < 180
	 * @param a
	 * @param b
	 * @return smaller unoriented angle
	 * @see <a href=""http://math.stackexchange.com/questions/317874/
	 * calculate-the-angle-between-two-vectors"">oriented angle</a>
	 */	
	public static double getAngle(StarPoint a, StarPoint b) {
		double normA = getNorm(a);
		double normB = getNorm(b);

		double dotProduct = getDotProduct(a, b);

		double angle = Math.acos(dotProduct / (normA * normB)) * 360.0
				/ (2 * Math.PI);
		return angle;
	}

	/**
	 * Calculates the ciunter clockwise oriented angle, that is positive from 
	 * StarPoint a to b ; 0 <= angle(b,a) < 360.0
	 * 
	 * @param a
	 * @param b
	 * @return glockwise angle(a,b)
	 */
	public static double getCounterClockWiseAngle(StarPoint a, StarPoint b) {

		double dotProduct = getDotProduct(a, b);
		double determinant = getDerminant(a, b);

		double angle = Math.toDegrees(Math.atan2(determinant, dotProduct));
		
		System.out.println(angle);
		
		if (angle < 0 ) {
			angle = 360.0 + angle;
		}
		
		return angle;
	}

	/**
	 * Calculates the determinat spanned by the two StarPoint vectors a and b
	 * @param a
	 * @param b
	 * @return determinant
	 */
	public static double getDerminant(StarPoint a, StarPoint b) {

		double det = a.getX() * b.getY() - a.getY() * b.getX();
		return det;
	}

	/**
	 * Calculates the unit vector in direction of vector in it's 
	 * base.
	 * @param vector
	 * @return unit vector 
	 */
	public static StarPoint getUnitVector(StarPoint vector) {

		double normVector = getNorm(vector);
		StarPoint unitVector = new StarPoint(vector.getX() / normVector,
				vector.getY() / normVector);
		return unitVector;
	}

	
	/**
	 * Gives back a new scaled vector.
	 * @param vector
	 * @param scale
	 * @return scaled vector as {@link StarPoint}.
	 */
	public static StarPoint getScaledVector(StarPoint vector, double scale) {
		StarPoint scaledVector = new StarPoint(vector.getX() * scale,
				vector.getY() * scale);
		return scaledVector;
	}

	
	
	@Override
	/**
	 * Gives back a string representation, that reflects the coordinates
	 * of the StarPoint.
	 */
	public String toString() {
		return "StarPoint: (X " + this.getX() + ", Y " + this.getY() + ")";

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((star == null) ? 0 : star.hashCode());
		return result;
	}
	

	/** 
	 * StarPoint equality means bit level equality of the double precission coordinate
	 * components. The slightest difference would lead already to false.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StarPoint other = (StarPoint) obj;
		if (star == null) {
			if (other.star != null)
				return false;
		} else if (!star.equals(other.star))
			return false;
		return true;
	}
	
}
