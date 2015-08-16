/*************************************************************************
 * 
 *  WWW.PHONON.CH CONFIDENTIAL 
 *
 *  2012 - 2020, Stephan Strauss, www.phonon.ch, Zurich, Switzerland
 *  All Rights Reserved.
 * 
 *************************************************************************/

package ch.phonon.temview;

import java.awt.event.MouseEvent;

import ch.phonon.StarPoint;
import ch.phonon.drawables.DrawableScaleReference;

/**
 * This class handles the {@link DrawableScaleReference} movement in the
 * {@link TEMView} based on the mouse input from the user. The scale can be
 * transformed in several ways pressing the left, the center or the right grip.
 * The new grip position has to be calculated as shown in the picture below:
 * <p>
 * 
 * <img src="{@docRoot}
 * /../pictures/scaleDescription.png" width="80%" height="80%" >
 * 
 * @author phonon
 * 
 */
public class TemAdapterScaleTreatment {

	private TEMView temView;
	private boolean scaleRightGripChosen = false;
	private StarPoint origGripPosInPicCoord;
	private StarPoint origGrabPosInPicCoord;
	private StarPoint corrInPicCoord;
	private boolean scaleLeftGripChosen = false;;
	private StarPoint newGrabPositionInPicCoord;
	private StarPoint newGripPosition;
	private StarPoint actGrabPosRelativeToGripInPicCoord;
	private StarPoint origGrabPosRelativToGripInPicCoord;
	private StarPoint rotCorrInPicCoord;
	
	private boolean scaleMiddleGripChosen = false;

	@SuppressWarnings("unused")
	private TemAdapterScaleTreatment() {
		/** The standard constructor makes no sense */
		throw new AssertionError();
	}

	/**
	 * This only constructor is used to initialize the class members. The
	 * transfered temView reference delegates the functions to modify the active
	 * {@link DrawableScaleReference} location and orientation parameters.
	 * 
	 * @param temView
	 */
	public TemAdapterScaleTreatment(TEMView temView) {
		this.temView = temView;
		this.origGripPosInPicCoord = new StarPoint();
		this.newGripPosition = new StarPoint();
		this.newGrabPositionInPicCoord = new StarPoint();

	}

	/**
	 * Treats the situation, where the middle grip of the scale is pressed:
	 * picture coordinates or the grab position and the grip position is
	 * calculated and a corrective difference calculated between the grip and
	 * the grab position.
	 * 
	 * @param e
	 */
	public void treatMiddleGripPressed(MouseEvent e) {
		System.out.println("scaleMiddleGripChosen");
		this.scaleMiddleGripChosen  = true;
		this.origGripPosInPicCoord = this.temView.getScaleReference().getCenterStarPoint();		
		this.origGrabPosInPicCoord = new StarPoint(
				this.temView.getPictureCoordinates(e.getX(), e.getY()));
		this.corrInPicCoord = StarPoint.getDifference(
				this.origGrabPosInPicCoord, this.origGripPosInPicCoord);
		
		origGrabPosRelativToGripInPicCoord = StarPoint.getDifference(
				this.temView.getScaleReference().getCenterStarPoint(), 
				this.origGrabPosInPicCoord);

	}

	
	
	/**
	 * Treats the situation, where the right grip of the scale is pressed:
	 * picture coordinates or the grab position and the grip position is
	 * calculated and a corrective difference calculated between the grip and
	 * the grab position.
	 * 
	 * @param e
	 */
	public void treatRightGripPressed(MouseEvent e) {
		System.out.println("scaleRightGripChosen");
		this.scaleRightGripChosen = true;
		this.origGripPosInPicCoord = this.temView.getScaleReference().getEnd();
		this.origGrabPosInPicCoord = new StarPoint(
				this.temView.getPictureCoordinates(e.getX(), e.getY()));
		this.corrInPicCoord = StarPoint.getDifference(
				this.origGrabPosInPicCoord, this.origGripPosInPicCoord);
		
		this.origGrabPosRelativToGripInPicCoord = StarPoint
		.getDifference(this.temView.getScaleReference().getBegin(),
				this.origGrabPosInPicCoord);

	}

	/**
	 * Treats the situation, where the left grip of the scale is pressed:
	 * picture coordinates or the grab position and the grip position is
	 * calculated and a corrective difference calculated between the grip and
	 * the grab position.
	 * 
	 * @param e
	 */
	public void treatLeftGripPressed(MouseEvent e) {
		System.out.println("scaleLeftGripChosen");
		this.scaleLeftGripChosen = true;
		this.origGripPosInPicCoord = this.temView.getScaleReference()
				.getBegin();
		this.origGrabPosInPicCoord = new StarPoint(
				this.temView.getPictureCoordinates(e.getX(), e.getY()));
		this.corrInPicCoord = StarPoint.getDifference(
				this.origGrabPosInPicCoord, this.origGripPosInPicCoord);
		
		this.origGrabPosRelativToGripInPicCoord = StarPoint.getDifference(this.temView
						.getScaleReference().getEnd(), this.origGrabPosInPicCoord);

	}

	/**
	 * Treats the situation, where one of the grips of the scale was pressed and
	 * are now active for moving. The new grip position is recalculated and the
	 * state of the delegated {@link DrawableScaleReference}, that is member of
	 * the {@link TEMView} - the class constructor parameter - is updated. This
	 * function should be called after user scale movement in the
	 * {@link TEMAdapter}.
	 * 
	 * 
	 * @param e
	 */
	public void treatGripMovement(MouseEvent e) {
		
		if (this.scaleMiddleGripChosen == true) {
			
			//TODO: Change names ... somehow weird and not understandable.
			
			this.newGrabPositionInPicCoord.setPoint(this.temView
					.getPictureCoordinates(e.getX(), e.getY()));
			actGrabPosRelativeToGripInPicCoord = StarPoint.getDifference(this.temView
					.getScaleReference().getCenterStarPoint(),
					this.newGrabPositionInPicCoord);				
			StarPoint shiftInPicCoord = StarPoint.getDifference(origGrabPosRelativToGripInPicCoord, actGrabPosRelativeToGripInPicCoord);
			StarPoint newLeftGrip = StarPoint.getSum(this.temView.getScaleReference().getBegin(), shiftInPicCoord);
			StarPoint newRightGrip = StarPoint.getSum(this.temView.getScaleReference().getEnd(), shiftInPicCoord);	
			
			this.temView.getScaleReference().setBegin(newLeftGrip);
			this.temView.getScaleReference().setEnd(newRightGrip);
			
		}
		
		if (this.scaleLeftGripChosen == true) {

			this.newGrabPositionInPicCoord.setPoint(this.temView
					.getPictureCoordinates(e.getX(), e.getY()));

			actGrabPosRelativeToGripInPicCoord = StarPoint.getDifference(this.temView
					.getScaleReference().getEnd(),
					this.newGrabPositionInPicCoord);
			double angle = StarPoint.getCounterClockWiseAngle(
					origGrabPosRelativToGripInPicCoord, actGrabPosRelativeToGripInPicCoord);
			rotCorrInPicCoord = StarPoint.createRotatedStarPoint(
					this.corrInPicCoord, Math.toRadians(angle));
			newGripPosition = StarPoint.getSum(newGrabPositionInPicCoord,
					rotCorrInPicCoord);
			this.temView.getScaleReference().setBegin(newGripPosition);

		}

		if (this.scaleRightGripChosen == true) {
			this.newGrabPositionInPicCoord.setPoint(this.temView
					.getPictureCoordinates(e.getX(), e.getY()));

			actGrabPosRelativeToGripInPicCoord = StarPoint.getDifference(this.temView
					.getScaleReference().getBegin(),
					this.newGrabPositionInPicCoord);
			double angle = StarPoint.getCounterClockWiseAngle(
					origGrabPosRelativToGripInPicCoord, actGrabPosRelativeToGripInPicCoord);
			rotCorrInPicCoord = StarPoint.createRotatedStarPoint(
					this.corrInPicCoord, Math.toRadians(angle));
			newGripPosition = StarPoint.getSum(newGrabPositionInPicCoord,
					rotCorrInPicCoord);
			this.temView.getScaleReference().setEnd(newGripPosition);
		}
	}

	/**
	 * This function needs to be called in the {@link TEMAdapter}, when the grip
	 * is released by the user input. After releasing the grip states are
	 * reinitialized.
	 */
	public void treatGripReleased() {
		System.out.println("Grip released");
		this.scaleLeftGripChosen = false;
		this.scaleRightGripChosen = false;
		this.scaleMiddleGripChosen = false;
		newGripPosition = new StarPoint();
	}

}
