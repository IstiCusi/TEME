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
 * This star is a {@link DrawableComposite}, that may represent a mathematical
 * point in space. It does not scale by scaling and is also invariant to
 * rotation operations of the {@link TEMView}. The {@link DrawableCircularStar}
 * can be Understood as the pictorial representation of the {@link StarPoint}.
 * 
 * @author phonon
 * 
 * @see DrawableStar
 * @see DrawableDiamondStar
 */
public class DrawableCircularStar extends DrawableComposite {

	/**
	 * Constructs the star at the {@link StarPoint} location. 
	 * @param starpoint
	 */
	public DrawableCircularStar(StarPoint starpoint) {
		super();

		LocalOrientation initialLocalOrientation = new LocalOrientation(
				new Point2D.Double(0, 0), 0, 1.0);
		StarPoint initialStarpoint = starpoint;

		DrawableCircle circle = new DrawableCircle(initialStarpoint,
				initialLocalOrientation, 10, 10);
		circle.setColor(new Color(0, 255, 255));
		circle.setInvariantRotation(true);
		circle.setInvariantScaling(true);

		DrawableStar star = new DrawableStar(initialStarpoint);

		this.add(circle);
		this.add(star);

	}

}
