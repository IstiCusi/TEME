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

import ch.phonon.InvariantScalingType;
import ch.phonon.LocalOrientation;
import ch.phonon.StarPoint;

//TODO: We need to check, when the begin and end rectangles 
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
public class DrawableScaleReference extends DrawableComposite {

	double sizeOfGrip = 20.0; // TODO: put into properties file

	private StarPoint begin;

	private double distance;
	private StarPoint relativeVector;
	private StarPoint unitVector;
	private double angle;
	private double xMiddle;
	private double yMiddle;

	private StarPoint drawableMiddleStarPoint;
	private LocalOrientation localOrientationOuterBox;
	private DrawableBox outerBox;
	private StarPoint starPointLeftGrip;

	private LocalOrientation localOrientationLeftGrip;
	private DrawableBox leftGrip;
	private StarPoint starPointRightGrip;
	private DrawableBox rightGrip;

	private StarPoint end;

	private LocalOrientation localOrientationRightGrip;

	/**
	 * Constructs a {@link DrawableScaleReference} based on a begin point and
	 * and end point.  
	 * 
	 * @param begin
	 * @param end
	 */
	public DrawableScaleReference(StarPoint begin, StarPoint end) {
		super();

		this.begin = begin;
		this.end = end;

		distance = StarPoint.getDistance(begin, end);

		relativeVector = StarPoint.getDifference(begin, end);
		unitVector = StarPoint.getUnitVector(relativeVector);

		System.out.println(relativeVector);
		System.out.println(unitVector);

		angle = StarPoint
				.getOrientedAngle(new StarPoint(10, 0), relativeVector);

		xMiddle = begin.getX() + relativeVector.getX() / 2.0;
		yMiddle = begin.getY() + relativeVector.getY() / 2.0;

		drawableMiddleStarPoint = new StarPoint(xMiddle, yMiddle);

		localOrientationOuterBox = new LocalOrientation(
				new Point2D.Double(0, 0), angle, 1.0);

		outerBox = new DrawableBox(drawableMiddleStarPoint,
				localOrientationOuterBox,
				(int) (Math.round(distance) + 2 * sizeOfGrip), (int) sizeOfGrip);
		outerBox.setColor(new Color(0x890ADF));

		starPointLeftGrip = StarPoint.getDifference(
				StarPoint.getScaledVector(unitVector, sizeOfGrip / 2), begin);
		localOrientationLeftGrip = new LocalOrientation(
				new Point2D.Double(0, 0), angle, 1.0);
		leftGrip = new DrawableBox(starPointLeftGrip, localOrientationLeftGrip,
				(int) (sizeOfGrip), (int) (sizeOfGrip) - 4);
		leftGrip.setColor(new Color(0xFFEA00));
		leftGrip.setFilled(true);

		starPointRightGrip = StarPoint.getDifference(
				StarPoint.getScaledVector(unitVector, -sizeOfGrip / 2), end);
		localOrientationRightGrip = new LocalOrientation(new Point2D.Double(0,
				0), angle, 1.0);
		rightGrip = new DrawableBox(starPointRightGrip,
				localOrientationRightGrip, (int) (sizeOfGrip),
				(int) (sizeOfGrip) - 4);
		rightGrip.setColor(new Color(0xFFEA00));
		rightGrip.setFilled(true);

		// TODO: Length of the ScaleReference should scale with the temView
		// state, the thickness however should stay the same.

		outerBox.setInvariantRotation(false);
		outerBox.setInvariantScaling(true);
		outerBox.invariantScalingType = InvariantScalingType.FIXEDY;

		leftGrip.setInvariantRotation(false);
		leftGrip.setInvariantScaling(true);
		leftGrip.invariantScalingType = InvariantScalingType.FIXEDY;

		rightGrip.setInvariantRotation(false);
		rightGrip.setInvariantScaling(true);
		rightGrip.invariantScalingType = InvariantScalingType.FIXEDY;

		this.add(outerBox);
		this.add(leftGrip);
		this.add(rightGrip);

	}

	/**
	 * Get the measured distance represented as {@link StarPoint} vector in 
	 * picture coordinate dimensions.
	 * @return relative vector between the left (begin) and right end (end) of the scale.
	 */
	public StarPoint getRelativeVector() {
		return relativeVector;
	}

	private void reCalculateDimensions() {

		// TODO: Express constructor mainly over this function later (code
		// duplication problem)

		distance = StarPoint.getDistance(this.begin, this.end);

		this.relativeVector = StarPoint.getDifference(this.begin, this.end);
		this.unitVector = StarPoint.getUnitVector(this.relativeVector);

		angle = StarPoint
				.getOrientedAngle(new StarPoint(10, 0), relativeVector);

		/** Outer Box */

		xMiddle = begin.getX() + relativeVector.getX() / 2.0;
		yMiddle = begin.getY() + relativeVector.getY() / 2.0;

		drawableMiddleStarPoint.setX(xMiddle);
		drawableMiddleStarPoint.setY(yMiddle);

		localOrientationOuterBox.setRotation(angle);

		outerBox.setStarPoint(drawableMiddleStarPoint);
		outerBox.setLocalOrientationState(localOrientationOuterBox);
		outerBox.setWidth((int) (Math.round(distance) + 2 * sizeOfGrip));
		outerBox.setHeight((int) sizeOfGrip);

		/** Left Grip */

		starPointLeftGrip = StarPoint.getDifference(
				StarPoint.getScaledVector(unitVector, sizeOfGrip / 2), begin);
		localOrientationLeftGrip.setRotation(angle);

		leftGrip.setStarPoint(starPointLeftGrip);
		leftGrip.setLocalOrientationState(localOrientationLeftGrip);

		// TODO: The next properties need not be updated - check this

		leftGrip.setWidth(sizeOfGrip);
		leftGrip.setHeight(sizeOfGrip - 4);

		/** Right Grip */

		starPointRightGrip = StarPoint.getDifference(
				StarPoint.getScaledVector(unitVector, -sizeOfGrip / 2), end);
		localOrientationRightGrip.setRotation(angle);

		rightGrip.setStarPoint(starPointRightGrip);
		rightGrip.setLocalOrientationState(localOrientationRightGrip);

		// TODO: The next properties need not be updated - check this

		rightGrip.setWidth(sizeOfGrip);
		rightGrip.setHeight(sizeOfGrip - 4);

	}

	/**
	 * Obtain reference of the begin point of the scale 
	 * @return location in picture coordinates of the begin of the scale
	 */
	public StarPoint getBegin() {
		return begin;
	}

	/**
	 * Obtain reference of the end point of the scale 
	 * @return location in picture coordinates of the end of the scale
	 */
	public StarPoint getEnd() {
		return end;
	}

	/**
	 * Set the begin of the scale by using a {@link StarPoint}
	 * @param begin
	 */
	public void setBegin(StarPoint begin) {
		this.begin = begin;
		reCalculateDimensions();
	}

	/**
	 * Set the end of  the scale  by using a {@link StarPoint}
	 * @param end
	 */
	public void setEnd(StarPoint end) {
		this.end = end;
		reCalculateDimensions();
	}

	/**
	 * Set the begin of the scale by coordinates
	 * @param x  x coordinate in picture space 
	 * @param y  y coordinate in picture space 
	 */
	public void setBegin(int x, int y) {
		this.begin.setX(x);
		this.begin.setY(y);
		reCalculateDimensions();
	}

	/**
	 * Set the end of the scale by coordinates
	 * @param x  x coordinate in picture space 
	 * @param y  y coordinate in picture space 
	 */
	public void setEnd(int x, int y) {
		this.end.setX(x);
		this.end.setY(y);
		reCalculateDimensions();
	}

	/**
	 * Check if the drawn right grip of the scale contains the 
	 * point given by it's coordinates in the TEMView space
	 * (coordinates of the mouse pointer)
	 * 
	 * @param x x-coordinate in the TEMView (Mouse pointer)
	 * @param y y-coordinate in the TEMView (Mouse pointer)
	 * @return true, when the point is contained.
	 */
	public boolean rightGripContains(int x, int y) {
		return this.rightGrip.contains(x, y);
	}

	/**
	 * Check if the drawn left grip of the scale contains the 
	 * point given by it's coordinates in the TEMView space
	 * (coordinates of the mouse pointer)
	 * 
	 * @param x x-coordinate in the TEMView (Mouse pointer)
	 * @param y y-coordinate in the TEMView (Mouse pointer)
	 * @return true, when the point is contained.
	 */
	public boolean leftGripContains(int x, int y) {
		return this.leftGrip.contains(x, y);
	}

}
