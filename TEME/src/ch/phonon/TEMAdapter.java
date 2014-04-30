/**
 * 
 */
package ch.phonon;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

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

	
	public TEMAdapter(TEMView temView) {
		this.temView = temView; 
		this.temBegin = new TEMViewState();
	}

	
	@Override
	public void keyTyped(KeyEvent e) {
		System.out.println("keyTyped");
	}

	// TODO: Important ... since the TEMViewer is embedded in TEMInspectionPanel
	// the KeyListener part does not work anymore ... no idea why ... 
	// we need a solution here.
	
	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("keyPressed");		
		if (e.getKeyCode()==KeyEvent.VK_HOME) {
			System.out.println("HOME!!"); 
			this.temView.centerAll();
					
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
	
		if (SwingUtilities.isLeftMouseButton(e) &&  e.isShiftDown()==false) {
			//System.out.println("x "+e.getX()+" y"+e.getY());
			// TODO: Check if cast from integer to double works here 
			Point2D.Double point = new Point2D.Double(e.getX(), e.getY());
			this.temView.addPoint(point);
		}
		
		if (SwingUtilities.isLeftMouseButton(e) &&  e.isShiftDown()==true) {
			System.out.println("Huch ....");
			ArrayList<Drawable> pointList = temView.getPointsList();
			for (Drawable element : pointList) {
				if (element.contains(e.getX(),e.getY())) {
					int index = pointList.indexOf(element);
					pointList.remove(index);
					temView.setPointsList(pointList);
				}
			}
//			if (temView.getDrawableList().get(2).contains(e.getX(),e.getY())) {
//				System.out.println("Getroffen");
//			}
		}
			
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
		this.delta_x = e.getX()-this.cursorBegin_x;
		this.delta_y = e.getY()-this.cursorBegin_y;
		
		TEMViewState temViewState = this.temView.getTEMViewState();

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
	}	
	
	@Override	
	public void mouseEntered(MouseEvent e) {
	    System.out.println("Mouse entered!");	 
		this.temView.grabFocus();
	 }
	
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO: Inform TEMStatusPanel about actual cursor position and 
		// the actual position also calcaulated by setPoint in TEMView.
		Point2D.Double point = new Point2D.Double(e.getX(), e.getY());
		this.temView.fireCoordinatePropertyChange(point);
	   }

	    
	
	
	
}
