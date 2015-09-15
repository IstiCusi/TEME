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
 * rotation operations of the {@link TEMView}. The {@link DrawableStar} can be
 * Understood as the pictoral representation of the {@link StarPoint}.
 * 
 * @author phonon
 * 
 * @see DrawableCircularStar
 * 
 */
public final class DrawableStar extends DrawableComposite {

	final static private DrawableShapeDecorations decoration =
			new DrawableShapeDecorations.Builder().color(Color.WHITE)
					.buildImmutable();

	/**
	 * Constructs the star at the {@link StarPoint} location.
	 * 
	 * @param starpoint
	 */
	public DrawableStar(StarPoint starpoint) {
		super();

		StarPoint initialStarpoint = starpoint;

		DrawableLine pLine1 =
				new DrawableLine(initialStarpoint, new LocalOrientation(),
						new Point2D.Double(0, 0), new Point2D.Double(20, 0));
		pLine1.setInvariantRotation(true);
		pLine1.setInvariantScaling(true);

		pLine1.applyDecorations(decoration);

		DrawableLine pLine2 =
				new DrawableLine(initialStarpoint, new LocalOrientation(),
						new Point2D.Double(0, 0), new Point2D.Double(0, 20));
		pLine2.setInvariantRotation(true);
		pLine2.setInvariantScaling(true);

		pLine2.applyDecorations(decoration);

		this.add(pLine1);
		this.add(pLine2);
	}

}
