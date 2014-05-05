/**
 * 
 */
package ch.phonon;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;


public class DrawablePoint implements Drawable { 
	
	Drawable drawable 		= null;

	
	public boolean contains(int x, int y) {
		return drawable.contains(x, y);
	}

	Point2D.Double point	= null;	
	
	public DrawablePoint (Drawable drawable, Point2D.Double point) {
		this.drawable = drawable;
		this.point = point;
	}
		

	public Drawable getDrawable() {
		return drawable;
	}

	public Point2D.Double getPoint() {
		return point;
	}

	public void setDrawable(Drawable drawable) {
		this.drawable = drawable;
	}

	public void setPoint(Point2D.Double point) {
		this.point = point;
	}

	public void paint(Graphics2D graphicsContext, AffineTransform transformation) {
		drawable.paint(graphicsContext, transformation);
	}

	


		
}
