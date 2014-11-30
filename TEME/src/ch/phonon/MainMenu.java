/**
 * 
 */
package ch.phonon;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * @author phonon
 *
 */
public class MainMenu extends JMenuBar {

	private static final long serialVersionUID = 1L;
	private JMenuItem openFile;
	private JMenuItem saveFile;
	private JMenuItem saveasFile;
	private JMenuItem exitIt;
	
	public MainMenu() {
		super();
		JMenu 	 fileMenue = new JMenu(ResourceLoader.getResource("File"));
		add(fileMenue);
		
		openFile = new JMenuItem(ResourceLoader.getResource("File_openFile"));
		saveFile = new JMenuItem(ResourceLoader.getResource("File_saveFile"));
		saveasFile = new JMenuItem(ResourceLoader.getResource("File_saveasFile"));
		exitIt = new JMenuItem(ResourceLoader.getResource("File_exit"));
		
		fileMenue.add(openFile);
		fileMenue.add(saveFile);
		fileMenue.add(saveasFile);
		fileMenue.add(exitIt);
	}
	
	
}
