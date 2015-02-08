/**
 * 
 */
package ch.phonon;

import java.awt.geom.Point2D;
import ch.phonon.drawables.Drawable;



// TODO: Rotation and Shifting are not commutative 
// We need somehow represent correctly this fact in saying that 
// we first rotate and than shift.

/**
 * Instances of the class {@link LocalOrientation} store information
 * about the local orientation of a {@link Drawable}. The {@link LocalOrientation}
 * of the {@link Drawable}  may shift and rotate the object relative to the {@link StarPoint}. 
 
 * 
 * @author phonon
 *
 */
public class LocalOrientation  {
	
	
	public LocalOrientation () {
		this.localX = new Point2D.Double(0, 0);
		this.rotation=0;
		this.scaling=1;		
	}
	
	public LocalOrientation( Point2D localX, double rotation, double scaling) {
		this.localX=localX;
		this.rotation=rotation;
		this.scaling=scaling;
	}
	
	private Point2D    	localX;
	private double 		rotation;
	private double 		scaling;
	
	public void setLocalX(Point2D localX) {
		this.localX = localX;
	}

	public void setRotation(double rotation) {
		this.rotation = rotation;
	}

	public Point2D getLocalX() {
		return this.localX;
	}
	
	public double getRotation() {
		return rotation;
	}
	
	public double getScaling() {
		return scaling;
	}

	public void setScaling(double scaling) {
		this.scaling = scaling;
	}

	@Override
	public Object clone() {
		//TODO: I dislike to use a constructor to generate a clone() 
		// The clone contract in java is somehow fishy ... how to handle
		// this. 
		return new LocalOrientation(this.localX, this.rotation,this.scaling);
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
