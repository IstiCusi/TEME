/*************************************************************************
 * 
 *  WWW.PHONON.CH CONFIDENTIAL 
 *
 *  2012 - 2020, Stephan Strauss, www.phonon.ch, Zurich, Switzerland
 *  All Rights Reserved.
 * 
 *************************************************************************/

//TODO Maybe we can shift this into the TEMEditMode class as inner class

package ch.phonon.temview;

import ch.phonon.TEMAllied;
import ch.phonon.drawables.DrawableScaleReference;

/**
 * The TEMEditType classifies different modes of the {@link TEMView}. These
 * modes can be reached by cycling through this {@link Enum} type.
 * 
 * @author phonon
 * 
 */
public enum TEMEditType {

	/**
	 * Edit mode that is used to add/remove/modify points in the active
	 * {@link TEMAllied}
	 */
	POINT,

	/**
	 * Scale mode that is used to add/remove/modify the
	 * {@link DrawableScaleReference} (s) in the active {@link TEMAllied}
	 */
	SCALE;

	/**
	 * Cycle to the next {@link TEMEditMode} identified by the
	 * {@link TEMEditType}
	 * 
	 * @return next {@link TEMEditType}
	 */
	public TEMEditType getNext() {
		return values()[(ordinal() + 1) % values().length];
	}

	/**
	 * Cycle to the previous {@link TEMEditMode} identified by the
	 * {@link TEMEditType}
	 * 
	 * @return previous {@link TEMEditType}
	 */
	public TEMEditType getPrevious() {
		return values()[(this.ordinal() + values().length - 1)
				% values().length];
	}

}