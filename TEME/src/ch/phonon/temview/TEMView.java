/*************************************************************************
 * 
 * WWW.PHONON.CH CONFIDENTIAL
 *
 * 2012 - 2020, Stephan Strauss, www.phonon.ch, Zurich, Switzerland All Rights
 * Reserved.
 * 
 *************************************************************************/

package ch.phonon.temview;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.font.TextAttribute;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.text.AttributedString;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import ch.phonon.LocalOrientation;
import ch.phonon.ResourceLoader;
import ch.phonon.Scales;
import ch.phonon.Sound;
import ch.phonon.SoundType;
import ch.phonon.StarPoint;
import ch.phonon.TEMAllied;
import ch.phonon.TextOrientation;
import ch.phonon.drawables.AbstractDrawable;
import ch.phonon.drawables.Drawable;
import ch.phonon.drawables.DrawableDiamondStar;
import ch.phonon.drawables.DrawablePicture;
import ch.phonon.drawables.DrawablePoint;
import ch.phonon.drawables.DrawableScaleReference;
import ch.phonon.drawables.DrawableScaleReference.ActiveState;
import ch.phonon.drawables.DrawableText;
import ch.phonon.projectproperties.TEMTableModel;

/**
 * The {@link TEMView} is a {@link JPanel}, that is used to show various
 * {@link Drawable} objects.
 * 
 * @author phonon
 * 
 */
public class TEMView extends JPanel implements PropertyChangeListener {

	private static final long serialVersionUID = 1L;

	/**
	 * The temViewState keeps track about zooming, scaling and center position
	 */
	private TEMViewState temViewState;

	/** The initial identity state of the viewPort (Unrotated etc) */
	private AffineTransform initial;

	/**
	 * The total affine transformation needed that is associated with the
	 * temViewState
	 */
	private AffineTransform viewPortTransform;

	/** Model used to store all tem images and their descriptive information */
	private TEMTableModel temTableModel;

	/** The active temAllied container that keeps the tem picture, exif etc */
	private TEMAllied temAllied = null;
	// private DrawableScaleReference activeScaleReference;

	/** Adapter used for user interaction */
	TEMAdapter adapter;

	/** Standard drawables to be painted */
	private DrawableText informer;
	private DrawablePicture rose; // TODO: Can we make a Drawable out of rose ?
	private BufferedImage roseImage;

	/** Standard sounds to be used in the temView */
	private Sound newPointSound;
	private Sound killPointSound;
	private Sound pageturnSound;
	private Sound errorSound;

	/** Standard Edit modes and their associated containers (cursors,...) */
	private TEMEditMode temEditMode;

	// ------------------------ Constructor ------------------------------------

	TEMView() {

		/** Configure properties of TEMView (Color, Sounds, Cursor ...) */
		configureEnvironment();

		/**
		 * Initialize state of the TEMView. The values will be set to the
		 * correct values after complete construction of the TEMView, for
		 * example in the containing {@link MainFrame} class by calling the
		 * centerAll function of the TEMView
		 */
		this.temViewState = new TEMViewState();

		/** Add an empty TEMAllied -- The empty screen of the TEMView */
		temAllied = TEMTableModel.standardAllied;

		/** Add Listeners to TEMView */
		this.adapter = new TEMAdapter(this);
		this.addMouseListener(this.adapter);
		this.addMouseMotionListener(this.adapter);
		this.addKeyListener(this.adapter);

//		DrawableCoordinateSystem cS = new DrawableCoordinateSystem(
//				new StarPoint(), (double) 1000, (double) 1000);
//		this.temAllied.addDrawable(cS);
		setInformer(this.temAllied.getInformation());
		setVisible(true);

		// activeScaleReference = new DrawableScaleReference(new StarPoint(-900,
		// 550), new StarPoint(-500, 550));

	}

	// -------------------------------------------------------------------------

	/**
	 * Updates the text informer line in the top left of the temView.
	 * 
	 * @param text
	 *            string informing the user about the actual picture loaded.
	 */
	public void setInformer(String text) {
		Font font = new Font("Helvetica", Font.PLAIN, 30);
		AttributedString information = new AttributedString(text);
		information.addAttribute(TextAttribute.FONT, font);
		information.addAttribute(TextAttribute.FOREGROUND, Color.YELLOW);

		this.informer = new DrawableText(new StarPoint(10, 20),
				new LocalOrientation(), information);
		this.informer.textOrientation = TextOrientation.LEFT;
	}

	// ---------------------painting of the view -------------------------------

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;

		/** Use Antialiasing when rendering */
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2D.setRenderingHints(rh);

		/**
		 * Initialization of the initialTransformation to Identify (No
		 * Transform)
		 */
		this.initial = g2D.getTransform();
		this.initial.setToIdentity();

		/** Calculate the total transformation from the actual temViewState */
		this.viewPortTransform = AbstractDrawable.transformViewPort(initial,
				this.temViewState);

		/**
		 * Get the drawable tem picture from the *active* temAllied container
		 */
		Drawable temPicture = this.temAllied.getDrawableTEMPicture();

		/** Handle the situation, when no temPicture is present */
		if (temPicture != null) {
			temPicture.paint(g2D, viewPortTransform);
		} else {
			// TODO: Decide what you would like to do in case of no tem picture.
		}

		/** Obtain all drawables and points from the active temAllied */
		ArrayList<Drawable> drawableList = this.temAllied.getDrawables();
		ArrayList<DrawablePoint> pointList = this.temAllied.getPointsList();

		/**
		 * Draw the drawables based on the active viewPort scale/rot/pan state
		 **/

		for (Drawable drawable : drawableList) {

			drawable.paint(g2D, this.viewPortTransform);

		}

		for (DrawablePoint dPoint : pointList) {
			this.viewPortTransform = AbstractDrawable.transformViewPort(
					initial, this.temViewState);
			dPoint.paint(g2D, this.viewPortTransform);

		}

		// activeScaleReference.paint(g2D, this.viewPortTransform);

		// List<DrawableScaleReference> scales =
// temAllied.delegateScales().updateAndGetAllScales();

		Scales scalesToDraw = temAllied.delegateScales();

		for (DrawableScaleReference scale : scalesToDraw) {
			scale.paint(g2D, this.viewPortTransform);
		}

		// for (DrawableScaleReference scale:
		// temAllied.getScaleReferencesList()) {
		// this.viewPortTransform = AbstractDrawable.transformViewPort(initial,
		// this.temViewState);
		// scale.paint(g2D, this.viewPortTransform);
		// }

		// TODO: Document LocalOrientation, StarPoint, etc in the latex
		// documentation
		// use the code below for more insight

		// DrawableBox box1 = new DrawableBox(new StarPoint(-100, -100), new
		// LocalOrientation(), 5, 5);
		// box1.setColor(new Color(255, 0, 0));
		// box1.paint(g2D, this.viewPortTransform);
		//
		// LocalOrientation localO2 = new LocalOrientation(new Point(-100,
		// -100), 0, 1);
		// DrawableBox box2 = new DrawableBox(new StarPoint(-100, -100),
		// localO2, 5, 5);
		// box2.setColor(new Color(255, 0, 0));
		// box2.paint(g2D, this.viewPortTransform);
		//
		// LocalOrientation localO3 = new LocalOrientation(new Point(-100,
		// -100), 45.0, 4);
		// DrawableBox box3 = new DrawableBox(new StarPoint(-100, -100),
		// localO3, 5, 5);
		// box3.setColor(new Color(255, 0, 0));
		// box3.paint(g2D, this.viewPortTransform);

		// Paints the actual temAllied information (top right)

		this.informer.paint(g2D, this.initial);

		// Paints the windrose (bottom left)

		this.rose.setX(100);
		this.rose.setY(this.getHeight() - 100);

		initial.setToRotation(temViewState.rotation / 360 * 2 * Math.PI,
				this.rose.getX(), this.rose.getY());

		this.rose.paint(g2D, initial);
		this.initial.setToIdentity();
	}

	// ---------------------Adding and removing points -------------------------

	/**
	 * Recalculates for a given point in the actual zoom/pan/scale state of the
	 * temView the corresponding point in picture coordinates of the loaded
	 * image.
	 * 
	 * @param x
	 * @param y
	 * @return point in the base of the picture coordinate system
	 */
	public Point2D.Double getPictureCoordinates(double x, double y) {
		Point2D.Double point = new Point2D.Double(x, y);
		return getPictureCoordinates(point);
	}

	/**
	 * Recalculates for a given point in the actual zoom/pan/scale state of the
	 * temView the corresponding point in picture coordinates of the loaded
	 * image.
	 * 
	 * @param point
	 * @return point in the base of the picture coordinate system
	 */
	public Point2D.Double getPictureCoordinates(Point2D point) {
		Point2D.Double pt = new Point2D.Double();
		try {
			this.viewPortTransform.inverseTransform(point, pt);
		} catch (NoninvertibleTransformException e) {
			e.printStackTrace();
		}

		return pt;
	}

	/**
	 * Adds based on the coordinates of the mouse click inside the zoom/panned/
	 * scaled temView the corresponding point in picture coordinates to the
	 * active temAllied ( see {@link TEMAllied}).
	 * 
	 * @param point
	 *            clicked point in the temView
	 */
	public void addPoint(Point2D point) {

		Point2D.Double pt = getPictureCoordinates(point);

// System.out.println("new point with tem coordinates: " + point.getX() + " " +
// point.getY());
// System.out.println("new point added with picture coordinates: " + pt.getX() +
// " " + pt.getY());

		StarPoint initialStarpoint = new StarPoint(pt.getX(), pt.getY()); // pic
																			// coordinates
		DrawableDiamondStar diamondStar = new DrawableDiamondStar(
				initialStarpoint); // pic
									// coordinates
		DrawablePoint pPoint = new DrawablePoint(diamondStar, pt); // pic
																	// coordinates

		this.temAllied.addPoint(pPoint); // pic coordinates
		this.firePropertyChange("addPoint", null, pt);

		new Thread(this.newPointSound).start();
		System.out.println("Sound Threads:"
				+ ManagementFactory.getThreadMXBean().getThreadCount());

		repaint();

	}

	/**
	 * Remove point from the active temAllied. TODO: Needs to be checked,
	 * because for some reason, I do not need to transform into picture
	 * coordinates, what is really strange.
	 * 
	 * @param x
	 * @param y
	 */
	public void removePoint(int x, int y) {

		// TODO Where is the transformation to picture coordinates happening ?!
		// Not a good API ... needs to be checked

		if (this.temAllied.removePoint(x, y) == true) {
			new Thread(this.killPointSound).start();
			System.out.println("Sound Threads:"
					+ ManagementFactory.getThreadMXBean().getThreadCount());
			repaint();
		}
	}

	// ------------------------Helper functions --------------------------------

	// TODO: Implement Shift-Home to center to fit picture into window

	/**
	 * Centers the picture to the view (scale 1:1 of the original image)
	 */
	public void centerAll() {
		temViewState.cWidth = this.getWidth();
		temViewState.cHeight = this.getHeight();
		temViewState.x = temViewState.cWidth / 2;
		temViewState.y = temViewState.cHeight / 2;
		temViewState.scaling = 1;
		temViewState.rotation = 0;
		this.repaint();
	}

	private void configureEnvironment() {

		Border emptyBorder = BorderFactory.createEmptyBorder();
		setBorder(emptyBorder);

		setOpaque(true);
		setFocusable(true);

		/** Switch off Focus traversal to enable the adapter to use TAB keys */
		setFocusTraversalKeysEnabled(false);

		/** Standard background and frame color */
		setBorder(BorderFactory.createLineBorder(Color.white));
		setBackground(new Color(Integer.parseInt(
				ResourceLoader.getResource("TEMView_Color").substring(2), 16)));

		/** Define sounds to use in this component */
		this.newPointSound = new Sound(SoundType.POP);
		this.killPointSound = new Sound(SoundType.KILL);
		this.pageturnSound = new Sound(SoundType.PAGETURN);
		this.errorSound = new Sound(SoundType.ERROR);

		/** Load the rose placed in the bottom left of the temView */
		try {
			this.roseImage = ResourceLoader.getBufferedImage("rose.png");
		} catch (IOException ex) {
			System.out.println("Exception: The rose can not be loaded");
			System.exit(0);
		}

		/** Predefine the rose to save CPU time */
		LocalOrientation localOrienation = new LocalOrientation();
		localOrienation.setScaling(0.35);
		this.rose = new DrawablePicture(new StarPoint(), localOrienation,
				this.roseImage);

		/** http://www.java-gaming.org/index.php?topic=2227.0 */
		System.out.println("TEMView: This platform supports "
				+ Toolkit.getDefaultToolkit().getMaximumCursorColors()
				+ " cursor colors");

		/** Load the standard cursors used inside the temView */
		this.temEditMode = new TEMEditMode();
		this.temEditMode.switchToActiveEditTyp(TEMEditType.POINT);
		setCursor(this.temEditMode.getActiveCursor());

	}

	// --------------------information interchange -----------------------------

	/**
	 * registers a statusBar as {@link PropertyChangeListener} that is updated
	 * about several changes in the view as the coordinate of the mouse pointer
	 * and the last point added to the view.
	 * 
	 * @param statusBar
	 */
	public void registerStatusBar(TEMStatusBar statusBar) {
		this.addPropertyChangeListener("coordinateChange",
				(PropertyChangeListener) statusBar);
		this.addPropertyChangeListener("addPoint",
				(PropertyChangeListener) statusBar);
	}

	/**
	 * Inform all PropertyChangeListeners about the changed coordinate of the
	 * actual mouse pointer. This is normally called by the a associated
	 * Adapter, that checks the mouse movement as e.g.
	 * {@link MouseAdapter#mouseMoved(java.awt.event.MouseEvent)}
	 * 
	 * @param point
	 *            actual point of the cursor to report to
	 *            PropertyChangeListeners.
	 */
	public void fireCoordinatePropertyChange(Point2D point) {
		Point2D pt = new Point2D.Double();
		try {
			this.viewPortTransform.inverseTransform(point, pt);
		} catch (NoninvertibleTransformException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.firePropertyChange("coordinateChange", null, pt);

	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {

		if (evt.getPropertyName().equals("newSelectionInTable")) {
			this.temAllied = this.temTableModel.getActiveItem();
		}

		if (evt.getPropertyName().equals("temTableModelChange")) {
			System.out.println("Test");
			this.temTableModel = (TEMTableModel) (evt.getNewValue());
			this.temAllied = this.temTableModel.getActiveItem();
			setInformer(this.temAllied.getInformation());
			repaint();
		}

	}

	// --------------------------Getters and Setters---------------------------

	// TODO This getter / setters should be replaced (when we do a refactoring
	// to the Singleton description.

	/**
	 * Returns the actual {@link TEMViewState} of the TEMView. It contains the
	 * rotation/pane/zoom state and width, height of the painting canvas
	 * 
	 * @return {@link TEMViewState}
	 */
	public TEMViewState getTEMViewState() {
		return temViewState;
	}

	/**
	 * Sets the {@link TEMViewState} that contains the rotation/panning/zoom
	 * state of the TEMView.
	 * 
	 * @param temViewState
	 */
	public void setTEMViewState(TEMViewState temViewState) {
		this.temViewState = temViewState;
	}

	/**
	 * Get the active {@link TEMAllied}
	 * 
	 * @return the active temAllied
	 */
	public TEMAllied getTemAllied() {
		return temAllied;
	}

	/**
	 * Sets the active {@link TEMAllied} to work with it and repaints the view
	 * to show all relevant information stored in it.
	 * 
	 * @param temAllied
	 */
	public void setTemAllied(TEMAllied temAllied) {
		this.temAllied = temAllied;
		repaint();
	}

	/**
	 * Get the active edit mode defined by the enum {@link TEMEditType}
	 * 
	 * @return edit mode as item of the enum {@link TEMEditType}
	 */
	public TEMEditType getTEMEditMode() {
		return this.temEditMode.getActiveEditType();
	}

	// ------------------------ Controls ---------------------------------------

	/**
	 * The {@link TEMTableModel} may contain several {@link TEMAllied}s that had
	 * been loaded in a previous step. This method allows to switch to the next
	 * {@link TEMAllied} in the list to be displayed in the {@link TEMView}.
	 */
	public void switchToNextTemAllied() {

		try {
			TEMAllied helper = temTableModel.getForwardItem();
			if (helper != null) {
				setInformer(helper.getInformation());
				this.temAllied = helper;
				new Thread(this.pageturnSound).start();
				System.out.println("Sound Threads:"
						+ ManagementFactory.getThreadMXBean().getThreadCount());
				repaint();
			}
		} catch (Exception e) {
			System.out.println("No TEMAllied loaded");
			new Thread(this.errorSound).start();

		}
	}

	/**
	 * The {@link TEMTableModel} may contain several {@link TEMAllied}s that had
	 * been loaded in a previous step. This method allows to switch to the
	 * previous {@link TEMAllied} in the list to be displayed in the
	 * {@link TEMView}.
	 */
	public void switchToPreviousTemAllied() {

		try {
			TEMAllied helper = temTableModel.getBackwardItem();
			if (helper != null) {
				setInformer(helper.getInformation());
				this.temAllied = helper;
				new Thread(this.pageturnSound).start();
				System.out.println("Sound Threads:"
						+ ManagementFactory.getThreadMXBean().getThreadCount());
				repaint();
			}
		} catch (Exception e) {
			System.out.println("No TEMAllied loaded");
			new Thread(this.errorSound).start();
		}

	}

	/**
	 * The TEMView allows different edit modes that are defined by the
	 * {@link TEMEditType} enumeration and handled in more detail in the
	 * {@link TEMEditMode} class. This method allows to switch to the previous
	 * edit mode in the list before the active one. This also will change the
	 * active cursor shown in the {@link TEMView}.
	 */
	public void switchToPreviousEditMode() {
		this.temEditMode.cycleToPreviousEditType();
		this.setCursor(this.temEditMode.getActiveCursor());

	}

	/**
	 * The TEMView allows different edit modes that are defined by the
	 * {@link TEMEditType} enumeration and handled in more detail in the
	 * {@link TEMEditMode} class. This method allows to switch to the next edit
	 * mode in the list after the active one. This also will change the active
	 * cursor shown in the {@link TEMView}.
	 */
	public void switchToNextEditMode() {
		this.temEditMode.cycleToNextEditType();
		this.setCursor(this.temEditMode.getActiveCursor());

	}

	/**
	 * Generates a new scale in the active {@link TEMAllied} at the mouse
	 * position in delegation of the
	 * {@link Scales#newScale(java.awt.geom.Point2D.Double)} method and repaints
	 * the this TEMView canvas.
	 * 
	 * @param actualMousePosition
	 */
	public void newScale(Point2D.Double actualMousePosition) {

		this.temAllied.delegateScales().newScale(actualMousePosition);
		repaint();

	}

	/**
	 * This delegated method chooses a scale in finding the first that is
	 * reflected by the delivered mouse position in TEMView coordinates. When a
	 * scale is found, that fits and contains the mouse position it is given
	 * back. All other scales are shifted to {@link ActiveState#INACTIVE} state.
	 * 
	 * @see Scales
	 * @see TEMAllied
	 * @param actualMousePosition
	 * @return chosen scale or null
	 */
	public DrawableScaleReference chooseScale(Point2D.Double actualMousePosition) {

		DrawableScaleReference chosenScale = this.temAllied.delegateScales()
				.chooseScale(actualMousePosition);
		repaint();

		return chosenScale;
	}

	/**
	 * This legated method After chosing a scale by mouse pointer (see this
	 * {@link #chooseScale(java.awt.geom.Point2D.Double)} the chosen state is
	 * changed. This function allows to get a reference to the chosen scale.
	 * 
	 * @return chosen scale reference
	 */
	public DrawableScaleReference getChosenScale() {
		DrawableScaleReference chosenScale = this.temAllied.delegateScales()
				.getChosenScale();
		return chosenScale;
	}

}
