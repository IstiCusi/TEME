/**
 * 
 */
package ch.phonon.drawables;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import ch.phonon.LocalOrientation;
import ch.phonon.StarPoint;



public class DrawableBox extends AbstractDrawable {

	private Rectangle2D box;	
	private Color 	  color;
	double width, height; 
	AffineTransform localTransform;
	
	
	DrawableBox(StarPoint starpoint, LocalOrientation localOrientation, int width, int height) {
		
		super(starpoint, localOrientation);
		
		this.width=width;
		this.height=height;
		this.box= new Rectangle2D.Double(0, 0, width, height);
		this.color = new Color(255, 0, 0);

	}
	

	public void setColor (Color color) {
		this.color = color; 
	}
		
	@Override
	void draw(Graphics2D graphicsContext, AffineTransform locationTransform) {
		
		this.localTransform = locationTransform;
		
		graphicsContext.setColor(this.color);
		graphicsContext.setStroke(new BasicStroke(2.0f));
		graphicsContext.draw(locationTransform.createTransformedShape(this.box));
		
	}
	
	public void setWidth(double width) {
		this.width = width;
		// TODO Is it necessary to generate a new object or is it somehow
		// possible to modify the exising box. 
		this.box = new Rectangle2D.Double(0, 0, this.width, this.height);
	}

	public void setHeight(double height) {
		this.height = height;
		this.box = new Rectangle2D.Double(0, 0, this.width, this.height);
	}

	@Override
	public double getWidth() {
		return this.width;
	}

	@Override
	public double getHeight() {
		return this.height;
	}

	
	
	public boolean contains(int x, int y) {
		return this.localTransform.createTransformedShape(this.box).contains(x, y);
	}
}

