/**
 * 
 */
package ch.phonon.temview;

import java.awt.BorderLayout;
import java.awt.KeyboardFocusManager;

import javax.swing.JPanel;

/**
 * @author phonon
 *
 */
public class TEMInspectionPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	public TEMView temView;
	private TEMStatusBar statusBar; 
	
	public TEMInspectionPanel() {
		
		this.temView 	= new TEMView();
		this.statusBar	= new TEMStatusBar();
		temView.registerStatusBar(statusBar);
	
		setLayout(new BorderLayout());
		add(this.temView,BorderLayout.CENTER);
		add(this.statusBar,BorderLayout.SOUTH);
		setVisible(true);
		
			
	}
	
	public void centerAll() {
		this.temView.centerAll();
	}
	
	public TEMView getTEMView() {
		return this.temView;
	}


}
