/**
 * 
 */
package ch.phonon;

import ch.phonon.drawables.Drawable;


public interface Positionable {

	/** 
	 * Obtain the {@link LocalOrientation} of of the {@link Drawable} 
	 * in relation to the {@link StarPoint}
	 * @return {@link LocalOrientation} the local orientation
	 */
	public LocalOrientation getLocalOrientationState();	
	public void 			setLocalOrientationState(LocalOrientation localOrientation);
	
	/**
	 * Obtain the StarPoint of the {@link Positionable} 
	 * @return {@link StarPoint}
	 */
	public void 			setStarPoint(StarPoint starPoint);
	
	public 					LocalOrientation getInitialOrientationState();
	public void 			setInitialOrientationState(LocalOrientation initialOrientationState);
	
	public double 			getWidth () ;
	public double 			getHeight() ;
	
	public void 			setInvariantScaling(boolean invariantScaling);
	public boolean 		getInvariantScaling();
	
	public void 			setInvariantRotation(boolean invariantRotation);
	public boolean 		getInvariantRotation();
	
}
