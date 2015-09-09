/*************************************************************************
 * 
 *  WWW.PHONON.CH CONFIDENTIAL 
 *
 *  2012 - 2020, Stephan Strauss, www.phonon.ch, Zurich, Switzerland
 *  All Rights Reserved.
 * 
 *************************************************************************/
package ch.phonon.drawables;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import ch.phonon.LocalOrientation;
import ch.phonon.StarPoint;

/**
 * The <code>DrawableBox</code> class implements the {@link AbstractDrawable}.
 * and represents a rectangular shape.
 * 
 * TODO: Missing is an injection of all possible properties as color, filling,
 * anti aliasing etc
 * 
 * @author phonon
 *
 */

public class DrawableBox extends AbstractDrawable {

	private Rectangle2D box;
	private Color color;
	double  x,y;
	double width, height;
	AffineTransform locationTransform;
	private boolean filled = false;

	/**
	 * Constructs a drawable box and initializes it (red color)
	 * 
	 * @param starpoint
	 *            global position of the {@link DrawableBox}
	 * @param localOrientation
	 *            local orientation of the {@link DrawableBox}
	 * @param width
	 *            width of the {@link DrawableBox}
	 * @param height
	 *            height of the {@link DrawableBox}
	 */
	public DrawableBox(StarPoint starpoint, LocalOrientation localOrientation, int width, int height) {

		super(starpoint, localOrientation);

		this.x=0;
		this.y=0;
		this.width = width;
		this.height = height;
		
		// TODO: Is it easier for the compiler to optimize the stack 
		// if we directly use this.x and this.y, when calling 0,0 in Rect.
		
		this.box = new Rectangle2D.Double(this.x, this.y, width, height);
		this.color = new Color(255, 0, 0);

	}
	
	/**
	 * 
	 * Constructs a box centered around x, y in picture coordinates 
	 * @param starpoint
	 * @param localOrientation
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public DrawableBox(StarPoint starpoint, LocalOrientation localOrientation, int x, int y, int width, int height) {

		super(starpoint, localOrientation);

		this.x=x;
		this.y=y;		
		this.width = width;
		this.height = height;
		this.box = new Rectangle2D.Double(this.x, this.y, width, height);
		this.color = new Color(255, 0, 0);

	}


	/**
	 * Creates a standard Drawable at the origin. It is 10x10 pixel of size.
	 */
	public DrawableBox() {
		
		super(new StarPoint(), new LocalOrientation());
		this.x=0;
		this.y=0;		
		this.width = 10;
		this.height = 10;
		this.box = new Rectangle2D.Double(this.x, this.y, width, height);
		this.color = new Color(255, 0, 0);
	}

	/**
	 * sets the color of the drawable box.
	 * 
	 * @param color
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * sets the width of the box
	 * 
	 * @param width
	 */
	public void setWidth(double width) {
		this.width = width;
		// TODO Is it necessary to generate a new object or is it somehow
		// possible to modify the existing box.
		this.box = new Rectangle2D.Double(this.x, this.y, this.width, this.height);
	}

	/**
	 * sets the height of the box
	 * 
	 * @param height
	 */
	public void setHeight(double height) {
		this.height = height;
		this.box = new Rectangle2D.Double(this.x, this.y, this.width, this.height);
	}

	// ************************** Getters **************************************

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

		this.locationTransform = locationTransform;

		graphicsContext.setColor(this.color);
		graphicsContext.setStroke(new BasicStroke(2.0f));
		Shape rotatedBox = locationTransform.createTransformedShape(this.box);
		if ( this.isFilled() ) {
			graphicsContext.fill(rotatedBox);
		}
		graphicsContext.draw(rotatedBox);

	}

	/**
	 * get filling status of box. Is it filled (true) or empty (false)
	 * 
	 * @return filling status of the {@link Drawable}
	 */
	public boolean isFilled() {
		// TODO Auto-generated method stub
		return this.filled;
	}

	/**
	 * set the filling status of the box. When filled is true, than the drawn
	 * box is filled.
	 * 
	 * @param filled
	 */
	public void setFilled(boolean filled) {
		this.filled = filled;
	}

	@Override
	public boolean contains(int x, int y) {

		return this.locationTransform.createTransformedShape(this.box).contains(x, y);
	}
	
}
