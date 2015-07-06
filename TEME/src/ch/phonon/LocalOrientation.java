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
import ch.phonon.drawables.DrawableComposite;

// TODO: Rotation and Shifting are not commutative 
// We need somehow represent correctly this fact in saying that 
// we first rotate and than shift.

/**
 * Instances of the class {@link LocalOrientation} store information
 * about the local orientation/position of a {@link Drawable} relative to the 
 * StarPoint. The {@link LocalOrientation} of the {@link Drawable}  may shift 
 * away relative to the {@link StarPoint} and rotate  and scale the object around 
 * it's own origin (most of the time, the center). The LocalOrientation is mainly
 * used in cases of {@link DrawableComposite}s that share together one StarPoint
 * but all individual {@link Drawable} components have different {@link LocalOrientation}s.
 * 
 * @author phonon
 *
 */
public class LocalOrientation  extends PointInPictureBase implements Cloneable {
	
/**
 * The standard constuctor identifies the local orientation of the {@link Drawable}
 * in position of the global {@link StarPoint}. The {@link Drawable} is not 
 * rotated (rotation=0) and is not scaled (scaling=1).	
 */
	public LocalOrientation () {
		this.localX = new Point2D.Double(0, 0);
		this.rotation=0;
		this.scaling=1;		
	}
	
	/**
	 * Defines the local orientation of the Drawable relative to the 
	 * {@link StarPoint}, that means first scaled (scaling), than rotated about an 
	 * angle (rotation), and than translated about the local vector (localX)
	 * @param localX translation (last operation)
	 * @param rotation (second operation)
	 * @param scaling (first operation)
	 */
	public LocalOrientation( Point2D localX, double rotation, double scaling) {
		this.localX=localX;
		this.rotation=rotation;
		this.scaling=scaling;
	}
	
	private Point2D    	localX;
	private double 		rotation;
	private double 		scaling;
	
	/**
	 * Sets the local point of the Drawable relative to the StarPoint location 
	 * @param localX point with components valid in PictureSpace
	 */
	public void setLocalX(Point2D localX) {
		this.localX = localX;
	}

	/**
	 * Rotates the Drawable around the local point specified by {@link LocalOrientation#setLocalX}
	 * @param rotation in grad 
	 */
	public void setRotation(double rotation) {
		this.rotation = rotation;
	}

	/**
	 * Returns the local position relative the StarPoint  
	 * @return local location 
	 */
	public Point2D getLocalX() {
		return this.localX;
	}
	
	/**
	 * Returns the rotation around the local position
	 * @return rotation in grad 
	 */
	public double getRotation() {
		return rotation;
	}
	
	/**
	 * @return scaling around the local position
	 */
	public double getScaling() {
		return scaling;
	}

	/**
	 * Sets the scaling around the local position
	 * @param scaling
	 */
	public void setScaling(double scaling) {
		this.scaling = scaling;
	}

	@Override
	public LocalOrientation clone() {		
		try {
			LocalOrientation newLocalOrientation = (LocalOrientation)super.clone();
			newLocalOrientation.setLocalX((Point2D)this.localX.clone());
			newLocalOrientation.setRotation(this.rotation);
			newLocalOrientation.setScaling(this.scaling);
			return newLocalOrientation;
		} catch (CloneNotSupportedException e) {
			throw new AssertionError();
		}		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((localX == null) ? 0 : localX.hashCode());
		long temp;
		temp = Double.doubleToLongBits(rotation);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(scaling);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LocalOrientation other = (LocalOrientation) obj;
		if (localX == null) {
			if (other.localX != null)
				return false;
		} else if (!localX.equals(other.localX))
			return false;
		if (Double.doubleToLongBits(rotation) != Double
				.doubleToLongBits(other.rotation))
			return false;
		if (Double.doubleToLongBits(scaling) != Double
				.doubleToLongBits(other.scaling))
			return false;
		return true;
	}
	

}
