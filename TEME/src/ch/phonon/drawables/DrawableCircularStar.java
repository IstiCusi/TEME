/**
 * 
 */
package ch.phonon.drawables;

import java.awt.Color;
import java.awt.geom.Point2D;

import ch.phonon.LocalOrientation;
import ch.phonon.StarPoint;


public class DrawableCircularStar extends DrawableComposite {

	public DrawableCircularStar(StarPoint starpoint) {
		super();
		
		LocalOrientation initialLocalOrientation = new LocalOrientation(new Point2D.Double(0,0), 0,1.0);
		StarPoint initialStarpoint = starpoint;	
		
		DrawableCircle circle = new DrawableCircle(initialStarpoint, initialLocalOrientation, 10, 10);
		circle.setColor(new Color(0,255,255));
		circle.setInvariantRotation(true);
		circle.setInvariantScaling(true);
		
		DrawableStar star = new DrawableStar(initialStarpoint);
		
		this.add(circle);
		this.add(star);
		
	}

	
	
}
