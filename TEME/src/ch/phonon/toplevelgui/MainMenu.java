/*************************************************************************
 * 
 *  WWW.PHONON.CH CONFIDENTIAL 
 *
 *  2012 - 2020, Stephan Strauss, www.phonon.ch, Zurich, Switzerland
 *  All Rights Reserved.
 * 
 *************************************************************************/
package ch.phonon.toplevelgui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import ch.phonon.ResourceLoader;

/**
 * The {@link MainMenu} class represents the menu bar of the application. It is
 * mainly used to save and load the state of the application.
 * 
 * @author phonon
 *
 */
public class MainMenu extends JMenuBar {

	private static final long serialVersionUID = 1L;
	private JMenuItem openFile;
	private JMenuItem saveFile;
	private JMenuItem saveasFile;
	private JMenuItem exitIt;

	/**
	 * The public standard constructor is visible throughout the application.
	 */
	public MainMenu() {
		super();
		JMenu fileMenue = new JMenu(ResourceLoader.getResource("File"));
		add(fileMenue);

		openFile = new JMenuItem(ResourceLoader.getResource("File_openFile"));
		saveFile = new JMenuItem(ResourceLoader.getResource("File_saveFile"));
		saveasFile =
				new JMenuItem(ResourceLoader.getResource("File_saveasFile"));
		exitIt = new JMenuItem(ResourceLoader.getResource("File_exit"));

		fileMenue.add(openFile);
		fileMenue.add(saveFile);
		fileMenue.add(saveasFile);
		fileMenue.add(exitIt);
	}

}
