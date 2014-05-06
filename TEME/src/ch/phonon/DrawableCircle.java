/**
 * 
 */
package ch.phonon;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;



public class DrawableCircle extends AbstractDrawable {

	private Ellipse2D ellipse;	
	private Color 	  color;
	double width, height; 
	AffineTransform localTransform;
	
	
	DrawableCircle(StarPoint starpoint, LocalOrientation localOrientation, int width, int height) {
		
		super(starpoint, localOrientation);
		
		this.width=width;
		this.height=height;
		this.ellipse= new Ellipse2D.Double(0, 0, width, height);
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
		graphicsContext.draw(locationTransform.createTransformedShape(this.ellipse));
		
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
		return this.localTransform.createTransformedShape(this.ellipse).contains(x, y);
	}
}

