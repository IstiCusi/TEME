/*******************************************************************************
 * 
 * WWW.PHONON.CH CONFIDENTIAL
 *
 * 2012 - 2020, Stephan Strauss, www.phonon.ch, Zurich, Switzerland All Rights
 * Reserved.
 * 
 ******************************************************************************/

package ch.phonon.temview;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.EnumMap;

import com.sun.xml.internal.bind.v2.TODO;

import ch.phonon.ResourceLoader;

/**
 * The {@link TEMEditMode} class is used by the {@link TEMView} to obtain
 * updated components as e.g. the mouse pointer {@link Cursor} for a given
 * activated {@link TEMEditType}. The class allows to switch and cycle through
 * the modes of the {@link TEMEditType}.
 * 
 * @author phonon
 * 
 */
public class TEMEditMode {

	private EnumMap<TEMEditType, Cursor> cursors =
			new EnumMap<TEMEditType, Cursor>(TEMEditType.class);
	private TEMEditType activeType;

	TEMEditMode() {

		try {
			
			//TODO: Please check, why either on new Ubuntu releases, java versions or different graphics boards the cursor
			// point (64,64) is needed while on other systems that is different (is this maybe also related
			// to screen resolution) -- the gif is of size 128x128 -- therefore 64x64 is logical. Maybe a bug in old 
			// java releases ? 32 was used before

			this.activeType = TEMEditType.POINT;

			BufferedImage curBufferedImage =
					ResourceLoader.getBufferedImage("Cross.gif");
			Cursor bufferedCursor =
					Toolkit.getDefaultToolkit().createCustomCursor(
							curBufferedImage, new Point(64,64), "Cross");
			this.cursors.put(TEMEditType.POINT, bufferedCursor);

			curBufferedImage =
					ResourceLoader.getBufferedImage("Cross_Scale.png");
			bufferedCursor = Toolkit.getDefaultToolkit().createCustomCursor(
					curBufferedImage, new Point(64, 64), "Scale");
			this.cursors.put(TEMEditType.SCALE, bufferedCursor);

		} catch (IOException e) {
			System.out.println("Exception: Standard Cursors can not be loaded");
			e.printStackTrace();
		}

	}

	/**
	 * Obtain the active edit type of the {@link TEMView}.
	 * 
	 * @return active edit type
	 */
	public TEMEditType getActiveEditType() {
		return activeType;
	}

	/**
	 * Cycle to the next mode identified by the {@link TEMEditType}
	 * 
	 * @return next {@link TEMEditType}
	 */
	public TEMEditType cycleToNextEditType() {
		activeType = activeType.getNext();
		return activeType;
	}

	/**
	 * Cycle to the previous mode identified by the {@link TEMEditType}
	 * 
	 * @return next {@link TEMEditType}
	 */
	public TEMEditType cycleToPreviousEditType() {
		activeType = activeType.getPrevious();
		return activeType;
	}

	/**
	 * Switch to a distinct {@link TEMEditType}
	 * 
	 * @param type
	 */
	public void switchToActiveEditTyp(TEMEditType type) {
		this.activeType = type;
	}

	/**
	 * Get mouse pointer {@link Cursor} associated with the active
	 * {@link TEMEditType}
	 * 
	 * @return active cursor
	 * 
	 */
	public Cursor getActiveCursor() {
		return cursors.get(activeType);
	}

}
