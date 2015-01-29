/**
 * 
 */
package ch.phonon.temview;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import javax.swing.SwingUtilities;

import ch.phonon.StarPoint;

enum KeyState {pressed, released};

/**
 * @author phonon
 *
 */
public class TEMAdapter extends MouseAdapter implements KeyListener {

	// TODO Maybe this enums are useful later ... not sure
	KeyState CtrlkeyState;
	KeyState ShiftkeyState;
	
	private TEMView temView;
	
	private TEMViewState temBegin;
	private int cursorBegin_x,cursorBegin_y;
	private int delta_x, delta_y;
	private boolean scaleLeftGripChosen 	= false;
	private boolean scaleRightGripChosen 	= false;
	private StarPoint origGribPosInPicCoord;
	private StarPoint newGribPosition;
	private StarPoint origGrabPosInPicCoord;
	private StarPoint corrInPicCoord;
	private StarPoint newGrabPositionInPicCoord;
	private StarPoint actGrabPosRelativeToEnd;
	private StarPoint origGrabPosRelativToEnd;
	private StarPoint rotCorrInPicCoord;

	
	public TEMAdapter(TEMView temView) {
		this.temView = temView; 
		this.temBegin = new TEMViewState();
		this.origGribPosInPicCoord = new StarPoint();
		this.newGribPosition = new StarPoint();
		this.newGrabPositionInPicCoord = new StarPoint();
	}

	
	@Override
	public void keyTyped(KeyEvent e) {
		
		System.out.println("keyTyped: "+e.getExtendedKeyCode());
		
	}

	
	@Override
	public void keyPressed(KeyEvent e) {	
		if (e.getKeyCode()==KeyEvent.VK_HOME) {
			this.temView.centerAll();
					
		}
		
		// KeyEvent.VK_TAB
		
		if ( e.getKeyCode()==KeyEvent.VK_PAGE_UP ) {
			this.temView.switchToPreviousTemAllied();
		}
		
		if (e.getKeyCode()==KeyEvent.VK_PAGE_DOWN) {
			this.temView.switchToNextTemAllied();
		}
		
		if (e.isShiftDown() && e.getKeyCode()==KeyEvent.VK_TAB ) {
			
			this.temView.switchToPreviousEditMode();
			
		}
		
		if (!e.isShiftDown() && e.getKeyCode()==KeyEvent.VK_TAB ) {
			this.temView.switchToNextEditMode();	
		}

		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		
		if (SwingUtilities.isMiddleMouseButton(e)) {
			this.cursorBegin_x = e.getX();
			this.cursorBegin_y = e.getY();
			this.temBegin.setTEMViewState(temView.getTEMViewState());
		}
	
		if (SwingUtilities.isLeftMouseButton(e) &&  e.isShiftDown()==false && 
				this.temView.getTEMEditMode()==TEMEditType.POINT) {
			
			Point2D.Double point = new Point2D.Double(e.getX(), e.getY());
			this.temView.addPoint(point);
		}
		
		if (SwingUtilities.isLeftMouseButton(e) &&  e.isShiftDown()==true && 
				this.temView.getTEMEditMode()==TEMEditType.POINT) {
			this.temView.removePoint(e.getX(), e.getY());
		}
		
		
		
		
		if (SwingUtilities.isLeftMouseButton(e) && this.temView.getTEMEditMode()==TEMEditType.SCALE) {
		
			if (this.temView.getScaleReference().leftGripContains(e.getX(), e.getY())==true) {
				
				System.out.println("scaleLeftGripChosen");
				this.scaleLeftGripChosen = true;
				
				this.origGribPosInPicCoord = this.temView.getScaleReference().getBegin();
				this.origGrabPosInPicCoord = new StarPoint (this.temView.getPictureCoordinates(e.getX(), e.getY()));	
				this.corrInPicCoord = StarPoint.getDifference(this.origGrabPosInPicCoord,this.origGribPosInPicCoord);
							
			}
			
			if (this.temView.getScaleReference().rightGripContains(e.getX(), e.getY())==true) {
				System.out.println("scaleRightGripChosen");
				this.scaleRightGripChosen = true;
			}

			
		}

		
			
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
		this.delta_x = e.getX()-this.cursorBegin_x;
		this.delta_y = e.getY()-this.cursorBegin_y;
		
		TEMViewState temViewState = this.temView.getTEMViewState();

		if (SwingUtilities.isLeftMouseButton(e) && 
							this.temView.getTEMEditMode()==TEMEditType.SCALE) {
					
			if(this.scaleLeftGripChosen==true) {
	
			
				//this.newGrabPositionInPicCoord = new StarPoint (this.temView.getPictureCoordinates( e.getX(), e.getY() ));
				this.newGrabPositionInPicCoord.setPoint(this.temView.getPictureCoordinates( e.getX(), e.getY() ));
				
				actGrabPosRelativeToEnd = 		StarPoint.getDifference( this.temView.getScaleReference().getEnd(),this.newGrabPositionInPicCoord);
				origGrabPosRelativToEnd =		StarPoint.getDifference( this.temView.getScaleReference().getEnd(), this.origGrabPosInPicCoord);
				double angle = StarPoint.getClockWiseAngle(origGrabPosRelativToEnd,actGrabPosRelativeToEnd);	
			//	double angle2 = StarPoint.getAngle(actGrabPosRelativeToEnd,origGrabPosRelativToEnd);		
				
			//	System.out.println("angle "+angle+" angle2 "+angle2);
				
				rotCorrInPicCoord = StarPoint.rotateStarPoint(this.corrInPicCoord, Math.toRadians(angle));
				
				System.out.println("originalGrabPosition: "+ origGrabPosInPicCoord + " newGrabPosition: "+ newGrabPositionInPicCoord);
				System.out.println("CorrectionVector: "+rotCorrInPicCoord.toString()+" angle "+angle);
				
				//TODO: The new grib position needs to be corrected below ... there is something wrong.
				newGribPosition = StarPoint.getSum(newGrabPositionInPicCoord, rotCorrInPicCoord);
				
				System.out.println("GribPosition: "+ newGribPosition);
	
				this.temView.getScaleReference().setBegin(newGribPosition);
				
			}
		
			if(this.scaleRightGripChosen==true) {
				Point2D.Double pt = this.temView.getPictureCoordinates(
						new Point2D.Double(e.getX(), e.getY())
						);

						System.out.println("Set End to: "+pt.getX()+" "+pt.getY());	
				this.temView.getScaleReference().setEnd((int)pt.getX(), (int)pt.getY());
			}

		
		}
			
				
		if (SwingUtilities.isMiddleMouseButton(e) && e.isShiftDown()==true) {

			// Translate
			
			temViewState.x =  this.temBegin.x+this.delta_x;
			temViewState.y =  this.temBegin.y+this.delta_y;
				
		}

		if (SwingUtilities.isMiddleMouseButton(e) && e.isControlDown()==true) {
			
			// Scaling

			temViewState.scaling = this.temBegin.scaling-this.delta_y*0.01;
		}
		
		// TODO: I do not like this conditional expressions - check if there is not a better way
		if (SwingUtilities.isMiddleMouseButton(e) && e.isShiftDown()==false && e.isControlDown()==false ) {
 
			// Rotate
			
			this.delta_y = e.getY()-this.cursorBegin_y;
			temViewState.rotation= this.temBegin.rotation-this.delta_y;
		}
		
		this.temView.repaint();


	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		if (SwingUtilities.isMiddleMouseButton(e)) {
			this.delta_x=0 ; this.delta_y=0 ; 	
		}
		
		if (SwingUtilities.isLeftMouseButton(e) && 
								this.temView.getTEMEditMode()==TEMEditType.SCALE) {
		
				System.out.println("Grip released");
				this.scaleLeftGripChosen 	= false;	
				this.scaleRightGripChosen 	= false;
				newGribPosition = new StarPoint();

		}
		
	}	
	
	@Override	
	public void mouseEntered(MouseEvent e) {
		this.temView.grabFocus();
	 }
	
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO: Inform TEMStatusPanel about actual cursor position and 
		// the actual position also calculated by setPoint in TEMView.
		Point2D.Double point = new Point2D.Double(e.getX(), e.getY());
		this.temView.fireCoordinatePropertyChange(point);
	   }

	    
	
	
	
}
