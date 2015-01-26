/**
 * 
 */
package ch.phonon.temview;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
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
import ch.phonon.Sound;
import ch.phonon.SoundType;
import ch.phonon.StarPoint;
import ch.phonon.TEMAllied;
import ch.phonon.TextOrientation;
import ch.phonon.drawables.AbstractDrawable;
import ch.phonon.drawables.Drawable;
import ch.phonon.drawables.DrawableCoordinateSystem;
import ch.phonon.drawables.DrawableDiamondStar;
import ch.phonon.drawables.DrawablePicture;
import ch.phonon.drawables.DrawablePoint;
import ch.phonon.drawables.DrawableScaleReference;
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

	/** The temViewState keeps track about zooming, scaling and center position*/
	private TEMViewState temViewState;
	
	/** The initial identity state of the viewPort (Unrotated etc) */
	private AffineTransform initial;
	
	/** The total affine transformation needed that is associated with the temViewState */
	private AffineTransform viewPortTransform;

	/** Model used to store all tem images and their descriptive information */
	private TEMTableModel temTableModel;

	/** The active temAllied container that keeps the tem picture, exif etc */
	private TEMAllied temAllied;
	
	/** Adapter used for user interaction */
	TEMAdapter adapter;
		
	/** Standard drawables to be painted */
	private DrawableText informer;
	private DrawablePicture rose;	//TODO: Can we make a Drawable out of rose ?
	private BufferedImage roseImage;

	/** Standard sounds to be used in the temView */
	private Sound newPoint;
	private Sound killPoint;
	private Sound pageturn;
	private Sound error;

	/** Standard Edit modes and their associated containers (cursors,...) */
	private TEMEditMode temEditMode;

	private DrawableScaleReference drawableScaleReference;
	
	

	// ------------------------ Constructor ------------------------------------

	TEMView() {

		/** Configure properties of TEMView (Color, Sounds, Cursor ...) */
		configureEnvironment(); 

		/** Initialize state of the TEMView */
		this.temViewState = new TEMViewState();
		
		/** Add an empty TEMAllied -- The empty screen of the TEMView */
		temAllied = new TEMAllied();
		DrawableCoordinateSystem cS = new DrawableCoordinateSystem(
				new StarPoint(), (double) 1000, (double) 1000);
		this.temAllied.addDrawable(cS);
		setInformer(this.temAllied.getInformation());
		setVisible(true);
		
		/** Add Listeners to TEMView */
		this.adapter = new TEMAdapter(this);
		this.addMouseListener(this.adapter);
		this.addMouseMotionListener(this.adapter);
		this.addKeyListener(this.adapter);
		

		 drawableScaleReference = new DrawableScaleReference(
				new StarPoint(-900, 550), new StarPoint(-500, 550));
		 drawableScaleReference.setBegin(-800, 400);
		 drawableScaleReference.setBegin(-700, 400);
		 drawableScaleReference.setEnd(-400, 550);
		
	
	}

	// -------------------------------------------------------------------------

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
		
		/** Initialization of the initialTransformation to Identidy (No Transform) */
		this.initial = g2D.getTransform();
		this.initial.setToIdentity();

		/** Calculate the total transformation from the actual temViewState */
		this.viewPortTransform = AbstractDrawable.transformViewPort(initial,
				this.temViewState);

		/** Get the drawable tem picture from the *active* temAllied container */
		Drawable temPicture = this.temAllied.getDrawableTEMPicture();

		/** Handle the situation, when no temPicture is present */
		if (temPicture == null) {
			System.out.println("No tem picture loaded");
		} else {
			temPicture.paint(g2D, viewPortTransform);
		}
		
		/** Obtain all drawables and points from the active temAllied */
		ArrayList<Drawable> drawableList = this.temAllied.getDrawables();
		ArrayList<DrawablePoint> pointList = this.temAllied.getPointsList();

		/** Draw the drawables based on the active viewPort scale/rot/pan state**/
		for (Drawable drawable : drawableList) {
			this.viewPortTransform = AbstractDrawable.transformViewPort(
					initial, this.temViewState);
			drawable.paint(g2D, this.viewPortTransform);

		}
		
		for (DrawablePoint dPoint : pointList) {
			this.viewPortTransform = AbstractDrawable.transformViewPort(
					initial, this.temViewState);
			dPoint.paint(g2D, this.viewPortTransform);

		}

		// TODO: Take out the following testcode block

		this.viewPortTransform = AbstractDrawable.transformViewPort(initial,
				this.temViewState);
		
		drawableScaleReference.paint(g2D, this.viewPortTransform);

		// Paints the actual temAllied information (top right)

		this.informer.paint(g2D, this.initial);

		// Paints the windrose (bottom left)
		
		this.rose.setX(100);
		this.rose.setY(this.getHeight() - 100);
		
		initial.setToRotation(temViewState.rotation / 360 * 2 * 3.1415,
				this.rose.getX(), this.rose.getY());

		
		this.rose.paint(g2D, initial);
		this.initial.setToIdentity();
	}

	
	// -------------------- Shifting scale dimensions --------------------------
	
	public DrawableScaleReference getScaleReference() {
		return this.drawableScaleReference;
	}
	
	
	// ---------------------Adding and removing points -------------------------

	public Point2D.Double getPictureCoordinates (Point2D point) {
		Point2D.Double pt = new Point2D.Double();
		try {
			this.viewPortTransform.inverseTransform(point, pt);
		} catch (NoninvertibleTransformException e) {
			e.printStackTrace();
		}

		return pt;
	}
	
	public void addPoint(Point2D point) {
		
		Point2D.Double pt = getPictureCoordinates(point);

		StarPoint initialStarpoint = new StarPoint(pt.getX(), pt.getY());
		DrawableDiamondStar diamondStar = new DrawableDiamondStar(
				initialStarpoint);
		DrawablePoint pPoint = new DrawablePoint(diamondStar, pt);

		this.temAllied.addPoint(pPoint);
		this.firePropertyChange("addPoint", null, pt);

		new Thread(this.newPoint).start();
		System.out
				.println(ManagementFactory.getThreadMXBean().getThreadCount());

		repaint();

	}

	public void removePoint(int x, int y) {
		
		//TODO Where is the transformation to picture coordinates happening ?!
		// Not good API ... needs to be checked

		if (this.temAllied.removePoint(x, y) == true) {
			new Thread(this.killPoint).start();
			System.out.println(ManagementFactory.getThreadMXBean()
					.getThreadCount());
			repaint();
		}
	}

	// ------------------------Helper functions --------------------------------

	// TODO: Implement Shift-Home to center to fit picture into window

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
		this.newPoint = new Sound(SoundType.POP);
		this.killPoint = new Sound(SoundType.KILL);
		this.pageturn = new Sound(SoundType.PAGETURN);
		this.error = new Sound(SoundType.ERROR);
		
		/** Load the rose placed in the bottom left of the temView */
		try {
			this.roseImage=ResourceLoader.getBufferedImage("rose.png");			
		} catch (IOException ex) {
			System.out.println("Exception: The rose placed can not be loaded");
			System.exit(0);
		}
		
		/** Predefine the rose to save CPU time */
		LocalOrientation localOrienation = new LocalOrientation();
		localOrienation.setScaling(0.35);
		this.rose = new DrawablePicture(new StarPoint(), localOrienation, this.roseImage);
		
		/** http://www.java-gaming.org/index.php?topic=2227.0 */
		System.out.println(Toolkit.getDefaultToolkit().getMaximumCursorColors());
		
		/** Load the standard cursors used inside the temView */
		
		this.temEditMode = new TEMEditMode();
		this.temEditMode.switchToActiveEditTyp(TEMEditType.POINT);
		setCursor (this.temEditMode.getActiveCursor());

	}

	// --------------------information interchange -----------------------------

	public void registerStatusBar(TEMStatusBar statusBar) {
		this.addPropertyChangeListener("coordinateChange",
				(PropertyChangeListener) statusBar);
		this.addPropertyChangeListener("addPoint",
				(PropertyChangeListener) statusBar);
	}

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

		if (evt.getPropertyName().equals("temTableModelChange")) {
			System.out.println("Property change");
			this.temTableModel = (TEMTableModel) (evt.getNewValue());
			this.temAllied = this.temTableModel.getLastItem();
			setInformer(this.temAllied.getInformation());
			repaint();
		}

	}

	// --------------------------Getters and Setters---------------------------

	public TEMViewState getTEMViewState() {
		return temViewState;
	}

	public void setTEMViewState(TEMViewState temViewState) {
		this.temViewState = temViewState;
	}

	public TEMAllied getTemAllied() {
		return temAllied;
	}

	public void setTemAllied(TEMAllied temAllied) {
		this.temAllied = temAllied;
		repaint();
	}
	
	public TEMEditType getTEMEditMode() {
		return this.temEditMode.getActiveEditType();
	}

	// ------------------------ Controls ---------------------------------------

	public void switchToNextTemAllied() {

		try {
			TEMAllied helper = temTableModel.getForwardItem();
			if (helper != null) {
				setInformer(helper.getInformation());
				this.temAllied = helper;
				new Thread(this.pageturn).start();
				System.out.println(ManagementFactory.getThreadMXBean()
						.getThreadCount());
				repaint();
			}
		} catch (Exception e) {
			System.out.println("No TEMAllied loaded");
			new Thread(this.error).start();

		}
	}

	public void switchToPreviousTemAllied() {

		try {
			TEMAllied helper = temTableModel.getBackwardItem();
			if (helper != null) {
				setInformer(helper.getInformation());
				this.temAllied = helper;
				new Thread(this.pageturn).start();
				System.out.println(ManagementFactory.getThreadMXBean()
						.getThreadCount());
				repaint();
			}
		} catch (Exception e) {
			System.out.println("No TEMAllied loaded");
			new Thread(this.error).start();
		}

	}

	public void switchToPreviousEditMode() {
		this.temEditMode.cycleToPreviousEditType();
		this.setCursor(this.temEditMode.getActiveCursor());
		
	}

	public void switchToNextEditMode() {
		this.temEditMode.cycleToNextEditType();
		this.setCursor(this.temEditMode.getActiveCursor());
		
	}

}
