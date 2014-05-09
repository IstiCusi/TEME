/**
 * 
 */
package ch.phonon.drawables;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;
import java.awt.geom.Point2D;

import ch.phonon.LocalOrientation;
import ch.phonon.StarPoint;

/**
 * @author phonon
 *
 */
public class DrawableCoordinateSystem extends DrawableComposite {
	
	public DrawableCoordinateSystem (StarPoint starpoint, double height, double width) {
		super();
		
		StarPoint initialStarpoint = starpoint;	
		
		
		
		LocalOrientation orientation = new LocalOrientation(new Point2D.Double(0,0),0,1.0);
		StarPoint str = new StarPoint(0,0);
		DrawableCircle circle = new DrawableCircle(str, orientation, 20, 20);
		circle.setColor(new Color(0,255,0));
		circle.setInvariantRotation(false);
		circle.setInvariantScaling(true);
	
		float dash[] = { 10.0f };		
		Stroke stroke =  new BasicStroke(3.0f, BasicStroke.CAP_BUTT,
		        BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
		//Stroke stroke=new BasicStroke(3.0f);
		

		DrawableLine pLine1 = new DrawableLine(initialStarpoint, 
												new LocalOrientation(), 
												new Point2D.Double(0,0), 
												new Point2D.Double(5*width,0));
		pLine1.setColor(new Color(0,255,0));
		pLine1.setInvariantRotation(false);
		pLine1.setInvariantScaling(true);
		pLine1.setStroke(stroke);
		
		
		DrawableLine pLine2 = new DrawableLine(initialStarpoint, 
													new LocalOrientation(), 
													new Point2D.Double(0,0), 
													new Point2D.Double(0,5*height));
		pLine2.setColor(new Color(0,255,0));
		pLine2.setStroke(stroke);
		pLine2.setInvariantRotation(false);
		pLine2.setInvariantScaling(true);
		
		this.add(pLine1);
		this.add(pLine2);
		this.add(circle);
	}
	

}
