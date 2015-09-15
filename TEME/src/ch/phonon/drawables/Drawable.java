
package ch.phonon.drawables;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import ch.phonon.LocalOrientation;
import ch.phonon.StarPoint;

/**
 * A {@link Drawable} is an paintable 2D object. It is positioned in the global
 * coordinate system represented by a global {@link StarPoint}. The orientation
 * of the {@link Drawable} is represented by the {@link LocalOrientation} that
 * may rotate and shift the object relative to the {@link StarPoint}.
 * 
 * @author phonon
 *
 */
public interface Drawable {

	// TODO We should add here (but not in the case of the Drawable composite
	// a general property item (class DrawableProperties) that allows to adjust
	// several items like line thickness, color, filled, not filled ...etc)

	/**
	 * paints the {@link Drawable} into the graphicsContext. The transformation
	 * provided in the signature represents the deviation from the identity
	 * matrix.
	 * 
	 * @param graphicsContext
	 * @param transformation
	 */
	public void paint(Graphics2D graphicsContext,
			AffineTransform transformation);

	// TODO Check if it is really picture space ... I think, it is actual
	// temView space (mouse position space) of the TEMView.
	// We should distinguish this type of spaces by two different point types
	// and than use these point types to distinguish the different bases
	// e.g. in picture coordinates or temView space.

	/**
	 * Checks if the Drawable contains the coordinate (x,y) in the temView space
	 * 
	 * @param x
	 * @param y
	 * @return is true, when it (x,y) is contained in Drawable of the active
	 *         state (most of the time the state that is drawn into the view)
	 */
	public boolean contains(int x, int y);

}