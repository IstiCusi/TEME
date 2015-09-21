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
import ch.phonon.drawables.primitives.DrawableBox;
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
public final class DrawableDiamondStar extends DrawableComposite {

	static private DrawableShapeDecorations pBoxDecoration =
			new DrawableShapeDecorations.Builder().color(new Color(0, 255, 255))
					.build();

	static private LocalOrientation localOrientation =
			new LocalOrientation(new Point2D.Double(0, 0), 45, 1.0);

	/**
	 * Constructs the star at the {@link StarPoint} location.
	 * 
	 * @param starpoint
	 */
	public DrawableDiamondStar(StarPoint starpoint) {
		super();

		DrawableBox pBox = new DrawableBox(starpoint, localOrientation, 10, 10);
		pBox.setInvariantRotation(true);
		pBox.setInvariantScaling(true);

		pBox.applyDecorations(pBoxDecoration);

		DrawableStar star = new DrawableStar(starpoint);

		this.add(pBox);
		this.add(star);

	}

}
