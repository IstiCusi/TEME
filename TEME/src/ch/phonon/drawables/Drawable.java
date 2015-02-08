package ch.phonon.drawables;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import ch.phonon.LocalOrientation;
import ch.phonon.StarPoint;

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
	
	/**
	 * paints the {@link Drawable} into the graphicsContext.
	 * The transformation provided in the signature represents the deviation
	 * from the identity matrix. 
	 * @param graphicsContext
	 * @param transformation
	 */
	public void paint(Graphics2D graphicsContext, AffineTransform transformation); 		
	public boolean contains(int x, int y);
	
}
