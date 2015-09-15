/*************************************************************************
 * 
 * WWW.PHONON.CH CONFIDENTIAL
 *
 * 2012 - 2020, Stephan Strauss, www.phonon.ch, Zurich, Switzerland All Rights
 * Reserved.
 * 
 *************************************************************************/

package ch.phonon.drawables;

import java.awt.Color;
import java.awt.geom.Point2D;

import ch.phonon.InvariantScalingType;
import ch.phonon.LocalOrientation;
import ch.phonon.StarPoint;

// TODO: We need to check, when the begin and end rectangles
// are on minimal distance.==> Exception

/**
 * The {@link DrawableScaleReference} is a complex interactively used
 * {@link Drawable}, that represents a scale to measure distances in pixel
 * (picture coordinates) in a given graphical context. The scale is spanned by a
 * begin point and an end point that can be manipulated by grips (that are not
 * counted as part of the measuring meter) . The provided functions allow to
 * manipulate the positions and allow to check individually the different grips
 * of the scale to contain a given point ( {@link #rightGripContains(int, int)},
 * {@link #leftGripContains(int, int)}).
 * 
 * @author phonon
 * 
 */
public final class DrawableScaleReference extends DrawableComposite {

	/**
	 * Get the center point of the scale
	 * 
	 * @return center point of the scale in picture coordinates
	 */
	public StarPoint getCenterStarPoint() {
		return centerStarPoint;
	}

	/**
	 * This enum lists the possible activity states of the scale. When a scale
	 * is active it is visually highlighted. Active scales can be addressed e.g.
	 * for deletion or modification.
	 * 
	 * @author phonon
	 *
	 */
	public static enum ActiveState {

		/**
		 * highlighted when active
		 */
		ACTIVE {

			@Override
					DrawableShapeDecorations getDecorations() {
				return ACTIVE_DECORATIONS;
			}
		},
		/**
		 * not highlighted when inactive
		 */
		INACTIVE {

			@Override
					DrawableShapeDecorations getDecorations() {
				return INACTIVE_DECORATIONS;
			}
		};

		abstract DrawableShapeDecorations getDecorations();

		/**
		 * Active color choice for the outer box of the scale
		 */
		static final DrawableShapeDecorations ACTIVE_DECORATIONS =
				new DrawableShapeDecorations.Builder()
						.color(new Color(0x00F767)).buildImmutable();

		/**
		 * Inactive color choice for the outer box of the scale
		 */
		static final DrawableShapeDecorations INACTIVE_DECORATIONS =
				new DrawableShapeDecorations.Builder()
						.color(new Color(0x890ADF)).buildImmutable();

	};

	private ActiveState activeState;

	double sizeOfGrip = 20.0; // TODO: put into properties file

	private StarPoint begin;

	private double distance;
	private StarPoint relativeVector;
	private double angle;
	private double xCenter;
	private double yCenter;

	private StarPoint centerStarPoint;
	private LocalOrientation localOrientationOuterBox;
	private DrawableBox outerBox;
	private StarPoint starPointLeftGrip;

	private LocalOrientation localOrientationLeftGrip;
	private DrawableBox leftGrip;
	final static private DrawableShapeDecorations leftGripDecorations =
			new DrawableShapeDecorations.Builder().color(new Color(0xFFEA00))
					.filling(true).buildImmutable();

	private StarPoint starPointRightGrip;
	private DrawableBox rightGrip;
	final static private DrawableShapeDecorations rightGripDecorations =
			new DrawableShapeDecorations.Builder().color(new Color(0xFFEA00))
					.filling(true).buildImmutable();

	final static private DrawableShapeDecorations markerDecorations =
			new DrawableShapeDecorations.Builder().color(Color.RED)
					.buildImmutable();

	private StarPoint end;

	private LocalOrientation localOrientationRightGrip;

	private DrawableLine distanceMarkerLeft;

	private LocalOrientation localOrientationLeftMarker;

	private LocalOrientation localOrientationRightMarker;

	private DrawableLine distanceMarkerRight;

	/**
	 * Constructs a {@link DrawableScaleReference} based on a begin point and
	 * and end point. Any new constructed scale in active state.
	 * 
	 * @see ActiveState
	 * @param begin
	 * @param end
	 */
	public DrawableScaleReference(StarPoint begin, StarPoint end) {
		super();

		this.activeState = ActiveState.ACTIVE;

		this.begin = begin;
		this.end = end;

		calculateDimensions();

		this.centerStarPoint = new StarPoint();
		this.localOrientationOuterBox = new LocalOrientation();

		this.outerBox = new DrawableBox();
		this.outerBox.setHeight((int) sizeOfGrip);
		this.outerBox.applyDecorations(ActiveState.ACTIVE_DECORATIONS);

		this.starPointLeftGrip = this.begin;
		this.localOrientationLeftGrip = new LocalOrientation();
		this.leftGrip =
				new DrawableBox(starPointLeftGrip, localOrientationLeftGrip,
						-10, 0, (int) (sizeOfGrip), (int) (sizeOfGrip));
		this.leftGrip.applyDecorations(leftGripDecorations);

		this.starPointRightGrip = this.end;
		this.localOrientationRightGrip =
				new LocalOrientation(new Point2D.Double(0, 0), angle, 1.0);
		this.rightGrip =
				new DrawableBox(starPointRightGrip, localOrientationRightGrip,
						+10, 0, (int) (sizeOfGrip), (int) (sizeOfGrip));
		this.rightGrip.applyDecorations(rightGripDecorations);

		this.localOrientationLeftMarker = new LocalOrientation();
		this.distanceMarkerLeft =
				new DrawableLine(this.begin, localOrientationLeftMarker,
						new Point2D.Double(0, 0), new Point2D.Double(0, 30));
		distanceMarkerLeft.applyDecorations(markerDecorations);

		this.localOrientationRightMarker = new LocalOrientation();
		this.distanceMarkerRight =
				new DrawableLine(this.end, localOrientationLeftMarker,
						new Point2D.Double(0, 0), new Point2D.Double(0, 30));
		this.distanceMarkerRight.applyDecorations(markerDecorations);

		this.outerBox.setInvariantScaling(true);
		this.outerBox.invariantScalingType = InvariantScalingType.FIXEDY;

		this.leftGrip.setInvariantScaling(true);
		this.rightGrip.setInvariantScaling(true);

		this.distanceMarkerRight.setInvariantScaling(true);
		this.distanceMarkerLeft.setInvariantScaling(true);

		updateDrawables();

		this.add(outerBox);
		this.add(leftGrip);
		this.add(rightGrip);

		this.add(distanceMarkerLeft);
		this.add(distanceMarkerRight);
	}

	/**
	 * Get the measured distance represented as {@link StarPoint} vector in
	 * picture coordinate dimensions.
	 * 
	 * @return relative vector between the left (begin) and right end (end) of
	 *         the scale.
	 */
	public StarPoint getRelativeVector() {
		return relativeVector;
	}

	private void update() {

		calculateDimensions();
		updateDrawables();

	}

	private void updateDrawables() {

		/** Outer Box */

		this.centerStarPoint.setX(xCenter);
		this.centerStarPoint.setY(yCenter);

		localOrientationOuterBox.setRotation(angle);

		this.outerBox.setStarPoint(centerStarPoint);
		this.outerBox.setLocalOrientationState(localOrientationOuterBox);
		this.outerBox.setWidth((int) (Math.round(distance)));

		/** Left Grip */

		this.starPointLeftGrip = this.begin;
		this.leftGrip.setStarPoint(starPointLeftGrip);
		this.localOrientationLeftGrip.setRotation(angle);
		this.leftGrip.setLocalOrientationState(localOrientationLeftGrip);

		/** Right Grip */

		this.starPointRightGrip = this.end;
		this.rightGrip.setStarPoint(starPointRightGrip);
		this.localOrientationRightGrip.setRotation(angle);
		this.rightGrip.setLocalOrientationState(localOrientationRightGrip);

		/** Left distance marker */

		this.distanceMarkerLeft.setX(begin.getX());
		this.distanceMarkerLeft.setY(begin.getY());
		this.localOrientationLeftMarker.setRotation(angle);
		this.distanceMarkerLeft
				.setLocalOrientationState(localOrientationLeftMarker);

		/** Right distance marker */

		this.distanceMarkerRight.setX(end.getX());
		this.distanceMarkerRight.setY(end.getY());
		this.localOrientationRightMarker.setRotation(angle);
		this.distanceMarkerRight
				.setLocalOrientationState(localOrientationLeftMarker);
	}

	private void calculateDimensions() {

		this.distance = StarPoint.getDistance(this.begin, this.end);
		this.relativeVector = StarPoint.getDifference(this.begin, this.end);

		this.angle = StarPoint.getOrientedAngle(new StarPoint(10, 0),
				relativeVector);

		this.xCenter = begin.getX() + relativeVector.getX() / 2.0;
		this.yCenter = begin.getY() + relativeVector.getY() / 2.0;
	}

	/**
	 * Obtain reference of the begin point of the scale
	 * 
	 * @return location in picture coordinates of the begin of the scale
	 */
	public StarPoint getBegin() {
		return begin;
	}

	/**
	 * Obtain reference of the end point of the scale
	 * 
	 * @return location in picture coordinates of the end of the scale
	 */
	public StarPoint getEnd() {
		return end;
	}

	/**
	 * Set the begin of the scale by using a {@link StarPoint}
	 * 
	 * @param begin
	 */
	public void setBegin(StarPoint begin) {
		this.begin = begin;
		update();
	}

	/**
	 * Set the end of the scale by using a {@link StarPoint}
	 * 
	 * @param end
	 */
	public void setEnd(StarPoint end) {
		this.end = end;
		update();
	}

	/**
	 * Set the begin of the scale by coordinates
	 * 
	 * @param x
	 *            x coordinate in picture space
	 * @param y
	 *            y coordinate in picture space
	 */
	public void setBegin(int x, int y) {
		this.begin.setX(x);
		this.begin.setY(y);
		update();
	}

	/**
	 * Set the end of the scale by coordinates
	 * 
	 * @param x
	 *            x coordinate in picture space
	 * @param y
	 *            y coordinate in picture space
	 */
	public void setEnd(int x, int y) {
		this.end.setX(x);
		this.end.setY(y);
		update();
	}

	/**
	 * Checks if the left to right grip gap (middleGrip) of the scale contains
	 * the point given by it's coordinates in the TEMView space (coordinates of
	 * the mouse pointer)
	 * 
	 * @param x
	 *            x-coordinate in the TEMView (Mouse pointer)
	 * @param y
	 *            y-coordinate in the TEMView (Mouse pointer)
	 * @return true, when the point is contained.
	 */
	public boolean middleGripContains(int x, int y) {

		boolean gapContains =
				this.outerBox.contains(x, y) && !this.leftGripContains(x, y)
						&& !this.rightGripContains(x, y);

		return gapContains;
	}

	/**
	 * Check if the drawn right grip of the scale contains the point given by
	 * it's coordinates in the TEMView space (coordinates of the mouse pointer)
	 * 
	 * @param x
	 *            x-coordinate in the TEMView (Mouse pointer)
	 * @param y
	 *            y-coordinate in the TEMView (Mouse pointer)
	 * @return true, when the point is contained.
	 */
	public boolean rightGripContains(int x, int y) {
		return this.rightGrip.contains(x, y);
	}

	/**
	 * Check if the drawn left grip of the scale contains the point given by
	 * it's coordinates in the TEMView space (coordinates of the mouse pointer)
	 * 
	 * @param x
	 *            x-coordinate in the TEMView (Mouse pointer)
	 * @param y
	 *            y-coordinate in the TEMView (Mouse pointer)
	 * @return true, when the point is contained.
	 */
	public boolean leftGripContains(int x, int y) {
		return this.leftGrip.contains(x, y);
	}

	/**
	 * Set the active state of the ScaleReference
	 * 
	 * @param state
	 *            to set
	 */
	public void setActiveState(ActiveState state) {
		this.activeState = state;
		this.outerBox.applyDecorations(state.getDecorations());
	}

	/**
	 * returns the active state of the ScaleReference
	 * 
	 * @return active state
	 */
	public ActiveState getActiveState() {
		return activeState;
	}

}
