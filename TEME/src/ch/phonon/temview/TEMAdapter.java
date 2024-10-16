// TODO: Change TEMAdapter from inheritance of MouseAdapter to
// composition -- delegate/forward ?!

// TODO: Put Keyevent mapping into resource bundle
// http://stackoverflow.com/questions/521199/java-menu-mnemonics-in-resource-files

/*******************************************************************************
 * 
 * WWW.PHONON.CH CONFIDENTIAL
 *
 * 2012 - 2020, Stephan Strauss, www.phonon.ch, Zurich, Switzerland All Rights
 * Reserved.
 * 
 ******************************************************************************/

package ch.phonon.temview;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import javax.swing.SwingUtilities;
import javax.swing.Timer;

import ch.phonon.drawables.composites.DrawableScaleReference;

/**
 * This is the central adapter listened by the {@link TEMView} to handle
 * {@link TEMView} specific keyboard and mouse events.
 * 
 * @author phonon
 */
public class TEMAdapter extends MouseAdapter implements KeyListener {

	// TODO Maybe this enums are useful later ... not sure
	// private KeyState CtrlkeyState;
	// private KeyState ShiftkeyState;

	private final TEMView temView;
	// private TEMAllied activeTemAllied;
	// private Scales scales;

	private final TEMViewState temBegin;
	private int cursorBegin_x, cursorBegin_y;
	private int delta_x, delta_y;
	private final TemAdapterScaleTreatment temAdapterScaleTreatment;

	private Point2D.Double actualMousePosition;

	private DrawableScaleReference oldScaleChoice;
	//private TEMAllied oldTEMAllied;

	private ScaleAnimationTimerListener scaleAnimationTimerListener;

	/**
	 * This constructor registers the {@link TEMView} to this adapter (copy by
	 * reference). The TEMView reference is than used in the {@link KeyListener}
	 * and {@link MouseAdapter} methods to trigger events based on user input.
	 * 
	 * @param temView
	 */
	public TEMAdapter(TEMView temView) {
		this.temView = temView;
		this.temBegin = new TEMViewState();
		this.temAdapterScaleTreatment = new TemAdapterScaleTreatment(temView);
		this.scaleAnimationTimerListener =
				new ScaleAnimationTimerListener(temView);
		temView.scaleMarkerAnimationTimer =
				new Timer(50, this.scaleAnimationTimerListener);
	}

	@Override
	public void keyTyped(KeyEvent e) {

		// System.out.println("keyTyped: " + e.getExtendedKeyCode());

	}

	@Override
	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_HOME) {
			this.temView.centerAll();
		}

		if (e.getKeyCode() == KeyEvent.VK_PAGE_UP) {
			this.temView.switchToPreviousTemAllied();
		}

		if (e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
			this.temView.switchToNextTemAllied();
		}

		if (e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_TAB) {

			this.temView.switchToPreviousEditMode();

		}

		if (!e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_TAB) {
			this.temView.switchToNextEditMode();
		}

		if (!e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_INSERT
				&& this.temView.getTEMEditMode() == TEMEditType.SCALE) {
			this.temView.newScale(this.temView
					.getPictureCoordinates(this.actualMousePosition));
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

		if (SwingUtilities.isLeftMouseButton(e) && !e.isShiftDown()
				&& this.temView.getTEMEditMode() == TEMEditType.POINT) {

			Point2D.Double point = new Point2D.Double(e.getX(), e.getY());
			this.temView.addPoint(point);
		}

		if (SwingUtilities.isLeftMouseButton(e) && e.isShiftDown()
				&& this.temView.getTEMEditMode() == TEMEditType.POINT) {
			this.temView.removePoint(e.getX(), e.getY());
		}

		if (SwingUtilities.isLeftMouseButton(e)
				&& this.temView.getTEMEditMode() == TEMEditType.SCALE) {

			DrawableScaleReference chosenScale = this.temView
					.chooseScale(new Point2D.Double(e.getX(), e.getY()));

			if (chosenScale != null) {

				if (chosenScale != this.oldScaleChoice
						|| chosenScale.distanceMarkerState()==0.0) {
					temView.zeroAllDistanceMarkers();
					temView.scaleMarkerAnimationTimer.start();
				}
				this.oldScaleChoice = chosenScale;
			//	this.oldTEMAllied = temView.getTemAllied();

				boolean isMiddleGripPressed =
						chosenScale.middleGripContains(e.getX(), e.getY());

				if (isMiddleGripPressed)
					this.temAdapterScaleTreatment.treatMiddleGripPressed(e);

				if (chosenScale.leftGripContains(e.getX(), e.getY()))
					this.temAdapterScaleTreatment.treatLeftGripPressed(e);
				if (chosenScale.rightGripContains(e.getX(), e.getY()))
					this.temAdapterScaleTreatment.treatRightGripPressed(e);

			}
		}

	}

	@Override
	public void mouseDragged(MouseEvent e) {

		this.delta_x = e.getX() - this.cursorBegin_x;
		this.delta_y = e.getY() - this.cursorBegin_y;

		TEMViewState temViewState = this.temView.getTEMViewState();

		if (SwingUtilities.isLeftMouseButton(e)
				&& this.temView.getTEMEditMode() == TEMEditType.SCALE) {

			this.temAdapterScaleTreatment.treatGripMovement(e);

		}

		if (SwingUtilities.isMiddleMouseButton(e) && e.isShiftDown()) {

			// Translate

			temViewState.x = this.temBegin.x + this.delta_x;
			temViewState.y = this.temBegin.y + this.delta_y;

		}

		if (SwingUtilities.isMiddleMouseButton(e) && e.isControlDown()) {

			// Scaling

			temViewState.setScaling(
					this.temBegin.getScaling() - this.delta_y * 0.01);

		}

		// TODO: I do not like this conditional expressions - check if there is
		// not a better way
		if (SwingUtilities.isMiddleMouseButton(e) && !e.isShiftDown()
				&& !e.isControlDown()) {

			// Rotate

			this.delta_y = e.getY() - this.cursorBegin_y;
			temViewState.rotation = this.temBegin.rotation - this.delta_y;
		}

		this.temView.repaint();

	}

	@Override
	public void mouseReleased(MouseEvent e) {

		if (SwingUtilities.isMiddleMouseButton(e)) {
			this.delta_x = 0;
			this.delta_y = 0;
		}

		if (SwingUtilities.isLeftMouseButton(e)
				&& this.temView.getTEMEditMode() == TEMEditType.SCALE) {

			this.temAdapterScaleTreatment.treatGripReleased();

		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		this.temView.grabFocus();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		actualMousePosition = new Point2D.Double(e.getX(), e.getY());
		this.temView.fireCoordinatePropertyChange(actualMousePosition);
	}

}
