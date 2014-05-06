/**
 * 
 */
package ch.phonon;

import java.awt.Color;
import java.awt.geom.Point2D;

/**
 * @author phonon
 *
 */
public class DrawableStar extends DrawableComposite {
	
	public DrawableStar (StarPoint starpoint) {
		super();
		
		StarPoint initialStarpoint = starpoint;	
				
		DrawableLine pLine1 = new DrawableLine(initialStarpoint, 
												new LocalOrientation(), 
												new Point2D.Double(0,0), 
												new Point2D.Double(20,0));
		pLine1.setColor(Color.WHITE);
		pLine1.setInvariantRotation(true);
		pLine1.setInvariantScaling(true);
		
		
		DrawableLine pLine2 = new DrawableLine(initialStarpoint, 
				new LocalOrientation(), 
				new Point2D.Double(0,0), 
				new Point2D.Double(0,20));
		pLine2.setColor(Color.WHITE);
		pLine2.setInvariantRotation(true);
		pLine2.setInvariantScaling(true);
		
		this.add(pLine1);
		this.add(pLine2);
	}
	

}
