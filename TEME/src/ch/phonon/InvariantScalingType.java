/*************************************************************************
 * 
 *  WWW.PHONON.CH CONFIDENTIAL 
 *
 *  2012 - 2020, Stephan Strauss, www.phonon.ch, Zurich, Switzerland
 *  All Rights Reserved.
 * 
 *************************************************************************/

package ch.phonon;

import ch.phonon.drawables.Drawable;
import ch.phonon.temview.TEMViewState;

/**
 * {@link Drawable} items can have some invariant behaviour concerning the
 * {@link TEMViewState}. E.g you would like to keep the same size of points
 * added to the view. These enum takes care about that. 
 * 
 * @author phonon
 *
 */
public enum InvariantScalingType {
	/**
	 * Drawable is not allowed to scale in its both local directions
	 */
	BOTH,
	/**
	 * Drawable is not allowed to scale in its local x direction
	 */
	FIXEDX,
	/**
	 * Drawable is not allowed to scale in its local y direction
	 */
	FIXEDY
}
