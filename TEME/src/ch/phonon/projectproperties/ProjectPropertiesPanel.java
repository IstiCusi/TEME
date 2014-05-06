
package ch.phonon.projectproperties;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ch.phonon.Application;
import ch.phonon.TEMAllied;
import ch.phonon.temview.TEMView;


public class ProjectPropertiesPanel extends JPanel implements ActionListener  {

	private static final long serialVersionUID = 1L;
	private JButton openButton; 
	private JFileChooser temPictureFile;
	
	
	private TEMAllied temAllied;
	
	public ProjectPropertiesPanel() {
		
		
		// TODO Implement the properties panel 
		// TODO Add here a LayoutManager 
		
		JTextField projectName 		= new JTextField(20);
		JTextField projectId   		= new JTextField(20);
		JTextField technology  		= new JTextField(20);
		
		JLabel	projectNameLabel 	= new JLabel("Project Name:", JLabel.RIGHT);
		JLabel	projectIdLabel		= new JLabel("Project Id  :", JLabel.RIGHT);
		JLabel	technologyLabel		= new JLabel("Technology  :", JLabel.RIGHT);
		
		projectNameLabel.setDisplayedMnemonic('N');
		projectIdLabel.setDisplayedMnemonic('I');
		technologyLabel.setDisplayedMnemonic('T');
		
		projectNameLabel.setLabelFor(projectName);
		projectIdLabel.setLabelFor(projectId);
		technologyLabel.setLabelFor(technology);
		
		URL url =   Application.getUrl("pics/Open16.gif");
		openButton = new JButton("Open a TEM picture ...", new ImageIcon(url));
		openButton.addActionListener(this);
		temPictureFile = new JFileChooser();
		
		add(projectNameLabel);
		add(projectName);
		
		add(projectIdLabel);
		add(projectId);
		
		add(technologyLabel);
		add (technology);

		add(openButton);
		
		// TODO: Add here a table with TEM pictures (that you can load with New, Clear, Delete, Copy .. )
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		 if (e.getSource() == openButton) {
	            int returnVal = temPictureFile.showOpenDialog(this);

	            if (returnVal == JFileChooser.APPROVE_OPTION) {
	                File file = temPictureFile.getSelectedFile();    
	                System.out.println(file.getAbsolutePath());
	                this.temAllied = new TEMAllied (file.getAbsolutePath());
	                firePropertyChange("temAlliedChange", null, this.temAllied);
	                System.out.println("Opening: " + file.getName() + ".");
	            } else {
	            	System.out.println("Canceled");
	            }
		 }
	}

	
	// TODO: Instead we could try t implemento registerTEMInspectionPanel and
	// shift the TEMRegistration into the ProjectPropertiesPanel class.
	// Seems to be a little bit better ... but I miss the feeling of MVC. 
	// HOW ? I need help with this point.
	
	public void registerTEMView (TEMView temView) {
		this.addPropertyChangeListener("temAlliedChange", (PropertyChangeListener) temView);
	}
	
	
}
