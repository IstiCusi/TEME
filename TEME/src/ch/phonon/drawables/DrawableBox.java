/**
 * 
 */
package ch.phonon.drawables;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import ch.phonon.LocalOrientation;
import ch.phonon.StarPoint;

/** The <code>DrawableBox</code> class implements the {@link AbstractDrawable}.
*   and represents a rectangular shape.
*   
*   TODO: Missing is an injection of all possible properties
*  
* @author phonon
*
*/

public class DrawableBox extends AbstractDrawable {

	private Rectangle2D box;	
	private Color 	  color;
	double width, height; 
	AffineTransform localTransform;
	private boolean filled = false;
	
	
	/**
	 * Constructs a drawable box and initializes it (red color)
	 * @param starpoint  global position of the 		{@link DrawableBox}
	 * @param localOrientation local orientation of the {@link DrawableBox}
	 * @param width width of the {@link DrawableBox}
	 * @param height height of the {@link DrawableBox}
	 */
	DrawableBox(StarPoint starpoint, LocalOrientation localOrientation, int width, int height) {
		
		super(starpoint, localOrientation);
		
		this.width=width;
		this.height=height;
		this.box= new Rectangle2D.Double(0, 0, width, height);
		this.color = new Color(255, 0, 0);

	}
	
	/** 
	 * sets the color of the drawable box.
	 * @param color
	 */
	public void setColor (Color color) {
		this.color = color; 
	}

	/**
	 * sets the width of the box
	 * @param width
	 */
	public void setWidth(double width) {
		this.width = width;
		// TODO Is it necessary to generate a new object or is it somehow
		// possible to modify the existing box. 
		this.box = new Rectangle2D.Double(0, 0, this.width, this.height);
	}

	/**
	 * sets the height of the box
	 * @param height
	 */
	public void setHeight(double height) {
		this.height = height;
		this.box = new Rectangle2D.Double(0, 0, this.width, this.height);
	}

	// ************************** Getters **************************************
	
	/* (non-Javadoc)
	 * @see ch.phonon.drawables.AbstractDrawable#getWidth()
	 */
	@Override
	public double getWidth() {
		return this.width;
	}

	@Override
	public double getHeight() {
		return this.height;
	}

	// ********************** Other methods used *******************************
		
	@Override
	void draw(Graphics2D graphicsContext, AffineTransform locationTransform) {
		
		this.localTransform = locationTransform;
	
		graphicsContext.setColor(this.color);
		graphicsContext.setStroke(new BasicStroke(2.0f));
		Shape rotatedBox = locationTransform.createTransformedShape(this.box);
		if (this.getFilled()==true) {
			graphicsContext.fill(rotatedBox);
		}
		graphicsContext.draw(rotatedBox);
		
		
	}
	
	/**
	 * @return
	 */
	public boolean getFilled() {
		// TODO Auto-generated method stub
		return this.filled;
	}
	
	public void setFilled(boolean filled) {
		this.filled = filled;
	}

	@Override
	public boolean contains(int x, int y) {
		return this.localTransform.createTransformedShape(this.box).contains(x, y);
	}
}

