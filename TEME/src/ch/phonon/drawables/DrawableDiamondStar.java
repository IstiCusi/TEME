/*************************************************************************
 * 
 *  WWW.PHONON.CH CONFIDENTIAL 
 *
 *  2012 - 2020, Stephan Strauss, www.phonon.ch, Zurich, Switzerland
 *  All Rights Reserved.
 * 
 *************************************************************************/

package ch.phonon.drawables;

import java.awt.Color;
import java.awt.geom.Point2D;

import ch.phonon.LocalOrientation;
import ch.phonon.StarPoint;
import ch.phonon.temview.TEMView;


/**
 * 
 * This star is a {@link DrawableComposite}, that may represents a mathematical
 * point in space. It does not scale by scaling and is also invariant to
 * rotation operations of the {@link TEMView}. The {@link DrawableDiamondStar}
 * can be understood as the pictorial representation of the {@link StarPoint}.
 * 
 * @author phonon
 * 
 * @see DrawableStar
 * @see DrawableCircularStar
 */
public class DrawableDiamondStar extends DrawableComposite {

	/**
	 * Constructs the star at the {@link StarPoint} location. 
	 * @param starpoint
	 */
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
