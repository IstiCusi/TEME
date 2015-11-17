/*************************************************************************
 * 
 * WWW.PHONON.CH CONFIDENTIAL
 *
 * 2012 - 2020, Stephan Strauss, www.phonon.ch, Zurich, Switzerland All Rights
 * Reserved.
 * 
 *************************************************************************/

package ch.phonon.temview;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

/**
 *  This ActionListner reacts on a swing Timer to start the scaling of 
 *  the active scale distance markers to maximum extension. 
 *  
 * @author phonon
 *
 */
public class ScaleAnimationTimerListener implements ActionListener {

	private TEMView temView;
	private double markerScale = 0.0;

	/**
	 * @param temView
	 */
	public  ScaleAnimationTimerListener(TEMView temView) {
		super();
		this.temView = temView;
		//this.timer = timer;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		this.markerScale = this.markerScale + 0.15;
		temView.scaleDistanceMarkerOfChosenScale(this.markerScale);
		temView.repaint();
		
		if(this.markerScale >= 1.0) {
			this.markerScale = 0.0;
			((Timer)e.getSource()).stop();
			System.out.println("Timer should be stopped");
		}
		
	}
	
}