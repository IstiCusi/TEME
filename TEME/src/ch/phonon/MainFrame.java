/**
 * 
 */
package ch.phonon;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JTabbedPane;

import ch.phonon.projectproperties.ProjectPropertiesPanel;
import ch.phonon.temview.TEMInspectionPanel;

/**
 * @author phonon
 *
 */
public class MainFrame extends JFrame {

	
	private static final long serialVersionUID = 3911925935625639082L;
	
	private JMenuBar menu;
	private JTabbedPane tabbedPane;
	private TEMInspectionPanel temInspectionPanel;
	private ProjectPropertiesPanel projectPropPanel;
	
	public MainFrame() {
		
		super(Application.getResource("Window_title"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//setFocusTraversalKeysEnabled(false);
		
		java.net.URL url = ClassLoader.getSystemResource("ch/phonon/pics/icon.png");
		
		System.out.println(url.toString());
		
		
		
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.createImage(url);
		setIconImage(img);

	
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();	
		double widthDesktop =   screenSize.getWidth();
		double heightDesktop =  screenSize.getHeight();		
		double windowShrinking = Double.parseDouble(Application.getResource("Window_shrinking"));
	
		setBounds( 	(int)(0.5*widthDesktop  *(1-windowShrinking)), 
					(int)(0.5*heightDesktop *(1-windowShrinking)), 
					(int)(widthDesktop * 0.80), (int)(heightDesktop* 0.80));
		
		setLayout (new BorderLayout());
	
		this.menu = new MainMenu();		
		add(menu,BorderLayout.NORTH);
		
		this.tabbedPane  = new JTabbedPane();
		this.tabbedPane.setFocusable(true);
		
		this.temInspectionPanel = new TEMInspectionPanel();
		this.projectPropPanel 	= new ProjectPropertiesPanel();
		
		projectPropPanel.registerTEMView(temInspectionPanel.getTEMView());
		
		tabbedPane.addTab("Project Properties", this.projectPropPanel);
		tabbedPane.addTab("TEM Inspection", this.temInspectionPanel);
		
		add(tabbedPane);
		
		setVisible(true);
		
		
		// TODO: centerAll has to be after setVisible ; why ? Is there a better
		// design  ... this looks somehow not nice. 
		
		temInspectionPanel.centerAll();
		repaint();
		
	}

	
	
	
	
}
