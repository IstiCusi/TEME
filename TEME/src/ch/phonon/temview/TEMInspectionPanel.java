/**
 * 
 */
package ch.phonon.temview;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import ch.phonon.ResourceLoader;

/**
 * @author phonon
 *
 */
public class TEMInspectionPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	public TEMView temView;
	private TEMStatusBar statusBar;
	private JButton switchToNextPictureButton;
	private JButton switchToPreviousPictureButton; 
	
	public TEMInspectionPanel() {
		
		
		this.temView 	= new TEMView();
		this.statusBar	= new TEMStatusBar();
		temView.registerStatusBar(statusBar);
	
		setLayout(new BorderLayout());
		add(this.temView,BorderLayout.CENTER);
		add(this.statusBar,BorderLayout.SOUTH);
		
		URL url = null;
		ImageIcon icon = null;
		Image scaledIconImage = null;
		
		url =   ResourceLoader.getUrl("pics/Previous.png");
		icon = new ImageIcon(url);
		scaledIconImage = icon.getImage().getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(scaledIconImage);
		
		switchToPreviousPictureButton = new JButton(icon);
	
		//setFocusTraversalKeysEnabled(false);
		
		//	switchToNextPictureButton.setFocusable(false);
		
		add(switchToPreviousPictureButton,BorderLayout.WEST);
		
		
		url =   ResourceLoader.getUrl("pics/Next.png");
		icon = new ImageIcon(url);
		scaledIconImage = icon.getImage().getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(scaledIconImage);

		
		switchToNextPictureButton 	= new JButton(icon);
		add(switchToNextPictureButton,BorderLayout.EAST);
		
		
		switchToNextPictureButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				temView.switchToNextTemAllied();
			}
		});
		
		switchToPreviousPictureButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				temView.switchToPreviousTemAllied();
			}
		});

		
		
//		URL url =   ResourceLoader.getUrl("pics/Open16.gif");
//		JButton fixedPoint = new JButton(">", new ImageIcon(url));
//		
//		add(fixedPoint,BorderLayout.AFTER_LINE_ENDS);
		
		
		setVisible(true);
		
			
	}
	
	public void centerAll() {
		this.temView.centerAll();
	}
	
	public TEMView getTEMView() {
		return this.temView;
	}


}
