/*************************************************************************
 * 
 *  WWW.PHONON.CH CONFIDENTIAL 
 *
 *  2012 - 2020, Stephan Strauss, www.phonon.ch, Zurich, Switzerland
 *  All Rights Reserved.
 * 
 *************************************************************************/

// TODO: In the contract of the Positional I claim, that there exists 
// an initial state. This should be actually forced by an constructor.
// We have to check this contract. Are the functions setInitialOrientationState
// necessary.

package ch.phonon.drawables.orientation;

import ch.phonon.drawables.AbstractDrawable;
import ch.phonon.drawables.Drawable;
import ch.phonon.temview.TEMViewState;

/**
 * This interface represents functions necessary to identify position and
 * orientation and size of objects. The global position is identified with the
 * {@link StarPoint}. The local orientation around the {@link StarPoint} is
 * determined by the {@link LocalOrientation}. Together with the
 * {@link Drawable} interface it presents the core functions of any fully
 * implemented object that is based on the {@link AbstractDrawable} .
 * 
 * @author phonon
 * 
 */
public interface Positionable {

	// LocalOrientation --------------------------------------------------------

	/**
	 * Obtain the {@link LocalOrientation} of the {@link Positionable} in
	 * relation to the {@link StarPoint}
	 * 
	 * @return {@link LocalOrientation} the local orientation
	 */
	public LocalOrientation getLocalOrientationState();

	/**
	 * Sets the {@link LocalOrientation} of the {@link Positionable}
	 * 
	 * @param localOrientation
	 */
	public void setLocalOrientationState(LocalOrientation localOrientation);

	// StarPoint ---------------------------------------------------------------

	/**
	 * Returns the global position of an {@link Positionable} object.
	 * 
	 * @see StarPoint
	 * @return StarPoint global position of the object
	 */
	public StarPoint getStarPoint();

	/**
	 * Sets the StarPoint of the {@link Positionable}
	 * 
	 * @param starPoint
	 *            global position of the {@link Positionable}
	 * @see StarPoint
	 */
	public void setStarPoint(StarPoint starPoint);

	// Initial LocalOrientation (When object is created)

	/**
	 * The function return the initial local orientation state of the object
	 * associated with the creation of it.
	 * 
	 * @return initial orientation state ( {@link LocalOrientation} )
	 */
	public LocalOrientation getInitialOrientationState();

	/**
	 * The function sets the initial local orientation state of the object
	 * normally associated with the creation of the object.
	 * 
	 * @param initialOrientationState
	 */
	public void setInitialOrientationState(
			LocalOrientation initialOrientationState);

	/**
	 * @return the width of the initial object in the user space
	 */
	public double getWidth();

	/**
	 * @return the height of the initial object in the user space
	 */
	public double getHeight();

	/**
	 * Switches on and off invariantScaling. When <b>true</b>, the object is not
	 * scaled in size when zoomed. This allows to implement point representation
	 * always visible and independent of the {@link TEMViewState}.
	 * 
	 * @param invariantScaling
	 */
	public void setInvariantScaling(boolean invariantScaling);

	/**
	 * @return the InvariantScaling state of the item.
	 * @see Positionable#setInvariantScaling(boolean) for more information
	 */
	public boolean getInvariantScaling();

	/**
	 * Switches on and off invariantRotation. When <b>true</b>, the object is
	 * not rotated about it's {@link StarPoint}, when temView is rotated. This
	 * allows to implement point representation always independent of the
	 * rotation state of the {@link TEMViewState}.
	 * 
	 * @param invariantRotation
	 */
	public void setInvariantRotation(boolean invariantRotation);

	/**
	 * @return the InvariantRotation state of the item.
	 * @see Positionable#setInvariantRotation(boolean) for more information
	 */
	public boolean getInvariantRotation();

}
