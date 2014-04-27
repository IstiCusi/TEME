/**
 * 
 */
package ch.phonon;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author phonon
 *
 */
public class ProjectPropertiesPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton btn; 
	
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
		
		add(projectNameLabel);
		add(projectName);
		
		add(projectIdLabel);
		add(projectId);
		
		add(technologyLabel);
		add (technology);
		
		// TODO: Add here a table with TEM pictures (that you can load with New, Clear, Delete, Copy .. )
		
		this.btn =new JButton("Button des zweiten Tabs");
		add(btn);
		
	}

}
