/*************************************************************************
 * 
 *  WWW.PHONON.CH CONFIDENTIAL 
 *
 *  2012 - 2020, Stephan Strauss, www.phonon.ch, Zurich, Switzerland
 *  All Rights Reserved.
 * 
 *************************************************************************/

package ch.phonon.drawables.composites;

import java.awt.Color;
import java.awt.geom.Point2D;

import ch.phonon.drawables.DrawableComposite;
import ch.phonon.drawables.decoration.DrawableShapeDecorations;
import ch.phonon.drawables.orientation.LocalOrientation;
import ch.phonon.drawables.orientation.StarPoint;
import ch.phonon.drawables.primitives.DrawableCircle;
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

	static private DrawableShapeDecorations circleDecoration =
			new DrawableShapeDecorations.Builder().color(new Color(0, 255, 255))
					.build();

	static private LocalOrientation localOrientation =
			new LocalOrientation(new Point2D.Double(0, 0), 0, 1.0);

	/**
	 * Constructs the star at the {@link StarPoint} location.
	 * 
	 * @param starpoint
	 */
	public DrawableCircularStar(StarPoint starpoint) {
		super();

		DrawableCircle circle =
				new DrawableCircle(starpoint, localOrientation, 10, 10);
		circle.setInvariantRotation(true);
		circle.setInvariantScaling(true);

		circle.applyDecorations(circleDecoration);

		DrawableStar star = new DrawableStar(starpoint);

		this.add(circle);
		this.add(star);

	}

}
