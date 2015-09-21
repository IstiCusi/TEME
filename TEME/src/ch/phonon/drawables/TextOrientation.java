/*************************************************************************
 * 
 *  WWW.PHONON.CH CONFIDENTIAL 
 *
 *  2012 - 2020, Stephan Strauss, www.phonon.ch, Zurich, Switzerland
 *  All Rights Reserved.
 * 
 *************************************************************************/

package ch.phonon.drawables;

import ch.phonon.drawables.orientation.LocalOrientation;
import ch.phonon.drawables.orientation.StarPoint;
import ch.phonon.drawables.primitives.DrawableText;

/**
 * This enum is used to describe the orientation of text written to the tem view
 * using a {@link DrawableText} object. The orientation is measured relative to
 * the {@link LocalOrientation}, {@link StarPoint}.
 * 
 * @author phonon
 * 
 */
public enum TextOrientation {
	/** Centered orientation */
	CENTER, /** Left orientation */
	LEFT
};
