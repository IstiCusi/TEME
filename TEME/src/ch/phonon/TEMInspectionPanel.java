/**
 * 
 */
package ch.phonon;

import java.awt.BorderLayout;

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
		temView.addStatusBar(statusBar);
	
		setLayout(new BorderLayout());
		add(this.temView,BorderLayout.CENTER);
		add(this.statusBar,BorderLayout.SOUTH);
		
		setVisible(true);
		
			
	}
	
	public void centerAll() {
		this.temView.centerAll();
	}


}
