/**
 * 
 */
package ch.phonon.temview;

import java.awt.event.MouseEvent;

import ch.phonon.StarPoint;

/**
 * @author phonon
 *
 */
public class TemAdapterScaleTreatment {
	
	private TEMView temView;
	private boolean scaleRightGripChosen   = false;
	private StarPoint origGripPosInPicCoord;
	private StarPoint origGrabPosInPicCoord;
	private StarPoint corrInPicCoord;
	private boolean scaleLeftGripChosen = false;;
	private StarPoint newGrabPositionInPicCoord;
	private StarPoint newGripPosition;
	private StarPoint actGrabPosRelativeToEnd;
	private StarPoint origGrabPosRelativToEnd;
	private StarPoint rotCorrInPicCoord;
	
	public TemAdapterScaleTreatment(TEMView temView)  {
		this.temView = temView;
		this.origGripPosInPicCoord = new StarPoint();
		this.newGripPosition = new StarPoint();
		this.newGrabPositionInPicCoord = new StarPoint();

	}
	
	/**
	 * @param e
	 */
	public void treatRightGripPressed(MouseEvent e) {
		System.out.println("scaleRightGripChosen");
		this.scaleRightGripChosen = true;
		this.origGripPosInPicCoord = this.temView.getScaleReference().getEnd();
		this.origGrabPosInPicCoord = new StarPoint (this.temView.getPictureCoordinates(e.getX(), e.getY()));	
		this.corrInPicCoord = StarPoint.getDifference(this.origGrabPosInPicCoord,this.origGripPosInPicCoord);
	}


	/**
	 * @param e
	 */
	public void treatLeftGripPressed(MouseEvent e) {
		System.out.println("scaleLeftGripChosen");
		this.scaleLeftGripChosen = true;
		this.origGripPosInPicCoord = this.temView.getScaleReference().getBegin();
		this.origGrabPosInPicCoord = new StarPoint (this.temView.getPictureCoordinates(e.getX(), e.getY()));	
		this.corrInPicCoord = StarPoint.getDifference(this.origGrabPosInPicCoord,this.origGripPosInPicCoord);
	}
	
	/**
	 * @param e
	 */
	public void treatGripMovement(MouseEvent e) {
		if(this.scaleLeftGripChosen==true) {

			this.newGrabPositionInPicCoord.setPoint(this.temView.getPictureCoordinates( e.getX(), e.getY() ));
			
			actGrabPosRelativeToEnd = 		StarPoint.getDifference( this.temView.getScaleReference().getEnd(),this.newGrabPositionInPicCoord);
			origGrabPosRelativToEnd =		StarPoint.getDifference( this.temView.getScaleReference().getEnd(), this.origGrabPosInPicCoord);
			double angle = StarPoint.getClockWiseAngle(origGrabPosRelativToEnd,actGrabPosRelativeToEnd);	
			rotCorrInPicCoord = StarPoint.createRotatedStarPoint(this.corrInPicCoord, Math.toRadians(angle));				
			newGripPosition = StarPoint.getSum(newGrabPositionInPicCoord, rotCorrInPicCoord);	
			this.temView.getScaleReference().setBegin(newGripPosition);
			
		}

		if(this.scaleRightGripChosen==true) {
			this.newGrabPositionInPicCoord.setPoint(this.temView.getPictureCoordinates( e.getX(), e.getY() ));
			
			actGrabPosRelativeToEnd = 		StarPoint.getDifference( this.temView.getScaleReference().getBegin(),this.newGrabPositionInPicCoord);
			origGrabPosRelativToEnd =		StarPoint.getDifference( this.temView.getScaleReference().getBegin(), this.origGrabPosInPicCoord);
			double angle = StarPoint.getClockWiseAngle(origGrabPosRelativToEnd,actGrabPosRelativeToEnd);	
			rotCorrInPicCoord = StarPoint.createRotatedStarPoint(this.corrInPicCoord, Math.toRadians(angle));				
			newGripPosition = StarPoint.getSum(newGrabPositionInPicCoord, rotCorrInPicCoord);	
			this.temView.getScaleReference().setEnd(newGripPosition);
		}
	}
	
	/**
	 * 
	 */
	public void treatGripReleased() {
		System.out.println("Grip released");
		this.scaleLeftGripChosen 	= false;	
		this.scaleRightGripChosen 	= false;
		newGripPosition = new StarPoint();
	}	

	
	

}
