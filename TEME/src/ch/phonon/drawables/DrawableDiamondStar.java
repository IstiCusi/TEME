/**
 * 
 */
package ch.phonon.drawables;

import java.awt.Color;
import java.awt.geom.Point2D;

import ch.phonon.LocalOrientation;
import ch.phonon.StarPoint;


public class DrawableDiamondStar extends DrawableComposite {

	public DrawableDiamondStar(StarPoint starpoint) {
		super();
		
		LocalOrientation initialLocalOrientation = new LocalOrientation(new Point2D.Double(0,0), 45,1.0);
		StarPoint initialStarpoint = starpoint;	
		
		DrawableBox pBox = new DrawableBox(initialStarpoint, initialLocalOrientation, 10, 10);
		pBox.setColor(new Color(0,255,255));
		pBox.setInvariantRotation(true);
		pBox.setInvariantScaling(true);
		
		DrawableStar star = new DrawableStar(initialStarpoint);
		
		this.add(pBox);
		this.add(star);
		
	}

	
	
}
