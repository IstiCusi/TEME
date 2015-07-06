/*************************************************************************
 * 
 *  WWW.PHONON.CH CONFIDENTIAL 
 *
 *  2012 - 2020, Stephan Strauss, www.phonon.ch, Zurich, Switzerland
 *  All Rights Reserved.
 * 
 *************************************************************************/

package ch.phonon;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JTabbedPane;

import ch.phonon.projectproperties.ProjectPropertiesPanel;
import ch.phonon.temview.TEMInspectionPanel;

/**
 * The {@link MainFrame} is the main swing component of the application. It
 * contains a {@link JMenuBar}, a {@link JTabbedPane} and the different panels.
 * Different resources are loaded that realize a special look and feel. The
 * corresponding properties and their values can be found in Window section oft
 * the {@link ResourceBundle}.properties file in the ./config folder
 * 
 * @author phonon
 */
public class MainFrame extends JFrame {

	private static final long serialVersionUID = 3911925935625639082L;

	private JMenuBar menu;
	private JTabbedPane tabbedPane;
	private TEMInspectionPanel temInspectionPanel;
	private ProjectPropertiesPanel projectPropPanel;

	/**
	 * The public standard constructor is visible throughout the application.
	 */
	public MainFrame() {

		// Set the window title name from the entry the properties file
		super(ResourceLoader.getResource("Window_title"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Load the JFrame icon from the reference in the properties file
		Image img = ResourceLoader.getImage(ResourceLoader
				.getResource("Window_icon"));
		setIconImage(img);

		// Obtain the screen size from the toolkit
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double widthDesktop = screenSize.getWidth();
		double heightDesktop = screenSize.getHeight();

		// Load the shrinking factor for the frame size from the properties file
		double windowShrinking = Double.parseDouble(ResourceLoader
				.getResource("Window_shrinking"));

		// Set the bounds of the frame
		setBounds((int) (0.5 * widthDesktop * (1 - windowShrinking)),
				 (int) (0.5 * heightDesktop * (1 - windowShrinking)),
				 (int) (widthDesktop * 0.80), (int) (heightDesktop * 0.80));

		setLayout(new BorderLayout());

		this.menu = new MainMenu();
		add(menu, BorderLayout.NORTH);

		this.tabbedPane = new JTabbedPane();
		this.tabbedPane.setFocusable(true);

		this.temInspectionPanel = new TEMInspectionPanel();
		this.projectPropPanel = new ProjectPropertiesPanel();

		projectPropPanel.registerTEMView(temInspectionPanel.getTEMView());

		tabbedPane.addTab("Project Properties", this.projectPropPanel);
		tabbedPane.addTab("TEM Inspection", this.temInspectionPanel);

		add(tabbedPane);

		setVisible(true);

		// TODO: centerAll has to be after setVisible ; why ? Is there a better
		// design ... this looks somehow not nice.

		temInspectionPanel.centerAll();
		repaint();

	}

}
