/*************************************************************************
 * 
 *  WWW.PHONON.CH CONFIDENTIAL 
 *
 *  2012 - 2020, Stephan Strauss, www.phonon.ch, Zurich, Switzerland
 *  All Rights Reserved.
 * 
 *************************************************************************/

package ch.phonon.temview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.plaf.ButtonUI;
import javax.swing.plaf.basic.BasicButtonUI;

import ch.phonon.ResourceLoader;
import ch.phonon.TEMAllied;
import ch.phonon.drawables.Drawable;

/**
 * This JPanel contains all components necessary to inspect TEM pictures: The
 * components included are a {@link TEMView}, that shows the loaded active
 * {@link TEMAllied}, a {@link TEMStatusBar}, that shows important information
 * e.g as the actual mouse cursor position, buttons to switch between different
 * {@link TEMAllied}s and buttons to switch between different
 * {@link TEMEditType}s that are managed by the {@link TEMEditMode} class.
 * @see "This component is initialized by the {@link MainFrame} component." 
 * 
 * @author phonon
 */
public class TEMInspectionPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private TEMView temView;
	private JButton switchToNextPictureButton;
	private JButton switchToPreviousPictureButton;

	private JPanel statusBarPanel;
	private TEMStatusBar statusBar;

	private JButton switchToNextEditMode;

	private JButton switchtoPreviousEditMode;

	private JPanel switchEditModePanel;

	/**
	 * Constructs the TEMInspection panel in arranging all views as described
	 * in the class description. 
	 */
	public TEMInspectionPanel() {

		URL url = null;
		ImageIcon icon = null;
		Image scaledIconImage = null;

		Border emptyBorder = BorderFactory.createEmptyBorder();
		setBorder(emptyBorder);

		/** Add all subpanels to the InspectionPanel -------------------------- */

		setLayout(new BorderLayout());

		this.temView = new TEMView();
		this.statusBar = new TEMStatusBar();
		temView.registerStatusBar(statusBar);

		add(this.temView, BorderLayout.CENTER);

		statusBarPanel = new JPanel();
		statusBarPanel.setLayout(new BorderLayout());
		statusBarPanel.add(this.statusBar, BorderLayout.CENTER);

		switchEditModePanel = new JPanel();
		switchEditModePanel.setBorder(emptyBorder);
		switchEditModePanel.setLayout(new FlowLayout());
		statusBarPanel.add(switchEditModePanel, BorderLayout.EAST);

		add(statusBarPanel, BorderLayout.SOUTH);

		/** switchEditModePanel Definitions ----------------------------------- */

		url = ResourceLoader.getUrl("pics/PreviousEditMode.png");
		icon = new ImageIcon(url);
		scaledIconImage = icon.getImage().getScaledInstance(16, 16,
				java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(scaledIconImage);
		switchtoPreviousEditMode = new JButton(icon);

		url = ResourceLoader.getUrl("pics/NextEditMode.png");
		icon = new ImageIcon(url);
		scaledIconImage = icon.getImage().getScaledInstance(16, 16,
				java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(scaledIconImage);
		switchToNextEditMode = new JButton(icon);

		switchEditModePanel.add(switchtoPreviousEditMode);
		switchEditModePanel.add(switchToNextEditMode);

		switchtoPreviousEditMode.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				temView.switchToPreviousEditMode();
			}
		});

		switchToNextEditMode.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				temView.switchToNextEditMode();
			}
		});

		/** Switch/Cycle through TEM pictures (TEMAllieds) --------------------- */

		// Border border = BorderFactory.createLineBorder(new
		// Color(Integer.parseInt(
		// ResourceLoader.getResource("TEMViewSwitch_Border_Color").substring(2),
		// 16)));

		url = ResourceLoader.getUrl("pics/Previous.png");
		icon = new ImageIcon(url);
		scaledIconImage = icon.getImage().getScaledInstance(64, 64,
				java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(scaledIconImage);
		switchToPreviousPictureButton = new JButton(icon);
		switchToPreviousPictureButton.setUI((ButtonUI) BasicButtonUI
				.createUI(switchToPreviousPictureButton));
		switchToPreviousPictureButton.setBackground(new Color(Integer.parseInt(
				ResourceLoader.getResource("TEMViewSwitch_Color").substring(2),
				16)));
		add(switchToPreviousPictureButton, BorderLayout.WEST);

		url = ResourceLoader.getUrl("pics/Next.png");
		icon = new ImageIcon(url);
		scaledIconImage = icon.getImage().getScaledInstance(64, 64,
				java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(scaledIconImage);
		switchToNextPictureButton = new JButton(icon);
		switchToNextPictureButton.setUI((ButtonUI) BasicButtonUI
				.createUI(switchToNextPictureButton));
		switchToNextPictureButton.setBackground(new Color(Integer.parseInt(
				ResourceLoader.getResource("TEMViewSwitch_Color").substring(2),
				16)));
		add(switchToNextPictureButton, BorderLayout.EAST);

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

		setVisible(true);

	}



	/**
	 * This function delegates the centerAll function from the {@link TEMView}. 
	 * Because the {@link TEMView} constructors do not have completed information
	 * of the actual width and height, this function is triggered after construction
	 * of the TEMView to center all {@link Drawable}s. 
	 */
	public void centerAll() {
		this.temView.centerAll();
	}

	/**
	 * Gives back a reference to the {@link TEMView} component
	 * @return associated tem view component of the {@link TEMInspectionPanel}.
	 */
	public TEMView getTEMView() {
		return this.temView;
	}

}
