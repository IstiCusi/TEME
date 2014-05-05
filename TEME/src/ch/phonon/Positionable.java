/**
 * 
 */
package ch.phonon;


public interface Positionable {

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
	
	public void 			setInvariantScaling(boolean invariantScaling);
	public boolean 		getInvariantScaling();
	
	public void 			setInvariantRotation(boolean invariantRotation);
	public boolean 		getInvariantRotation();
	
}
