package ch.phonon;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/**
 *  A {@link Drawable} is an paintable 2D object.
 *  It is positioned in the global coordinate system represented by a global 
 *  {@link StarPoint}. The orientation of the {@link Drawable} is represented by
 *  the {@link LocalOrientation} that may rotate and shift the object relative 
 *  to the {@link StarPoint}. 
 * 
 * @author phonon
 *
 */
public interface Drawable  {
	
	public void paint(Graphics2D graphicsContext, AffineTransform transformation); 	
	
	/** 
	 * Obtain the {@link LocalOrientation} of of the {@link Drawable} 
	 * in relation to the {@link StarPoint}
	 * @return {@link LocalOrientation} the local orientation
	 */
	public LocalOrientation getLocalOrientationState();	
	public void 			setLocalOrientationState(LocalOrientation localOrientation);
	
	public StarPoint 		getStarPoint();
	public void 			setStarPoint(StarPoint starPoint);
	
	public 					LocalOrientation getInitialOrientationState();
	public void 			setInitialOrientationState(LocalOrientation initialOrientationState);
	
	public double 			getWidth () ;
	public double 			getHeight() ;
	
	public void 		setInvariantScaling(boolean invariantScaling);
	public boolean 		getInvariantScaling();
	
	public void 		setInvariantRotation(boolean invariantRotation);
	public boolean 		getInvariantRotation();

	/**
	 * @param x
	 * @param y
	 */
	public boolean contains(int x, int y);
	

	
}
