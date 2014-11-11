/**
 * 
 */
package ch.phonon.temview;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
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
import java.net.URL;
import java.text.AttributedString;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

import ch.phonon.Application;
import ch.phonon.LocalOrientation;
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
 * @author phonon
 *
 */
public class TEMView extends JPanel implements PropertyChangeListener {

	private static final long serialVersionUID = 1L;
	
	TEMAdapter adapter;
	private AffineTransform initial;
	private AffineTransform viewPortTransform;
	
	private TEMAllied temAllied;
	private DrawableText informer;
	private DrawablePicture rose;
	
	private TEMViewState temViewState;

	private BufferedImage roseImage;

	private TEMTableModel temTableModel;

	private Sound newPoint;
	private Sound killPoint;
	private Sound pageturn;
	private Sound error;
	
	// ------------------------ Constructor ------------------------------------
	
	TEMView  () { 
	
		// Initialize state of the TEMView
		
		configureEnvironment();	// configure properties of TEMView (Color...)
		
		
		
		this.temViewState = new TEMViewState();
		this.adapter = new TEMAdapter(this);
					
		temAllied = new TEMAllied();
		
		DrawableCoordinateSystem cS = new DrawableCoordinateSystem 
				(new StarPoint(), (double)1000, (double)1000);
		this.temAllied.addDrawable(cS);
		
		setInformer(this.temAllied.getInformation());
		
		
		
		try {            
			URL url =   Application.getUrl("pics/rose.png");
			 this.roseImage = ImageIO.read(url);
			
		} catch (IOException ex) {
			System.out.println("File not found");
			System.exit(0);
		}
		
		
		
//		Font font = new Font("Helvetica",Font.PLAIN,30);
//		AttributedString information = new AttributedString(this.temAllied.getInformation());
//		information.addAttribute(TextAttribute.FONT, font);
//		information.addAttribute(TextAttribute.FOREGROUND, Color.YELLOW);
//
//		this.informer = new DrawableText(new StarPoint(10,20), new LocalOrientation(), information);
//		this.informer.textOrientation=TextOrientation.LEFT;
		
		setVisible(true);	
		
		// Add Listeners to View
		
		this.addMouseListener(this.adapter);
		this.addMouseMotionListener(this.adapter);		
		this.addKeyListener(this.adapter);
		
		// Define sounds to use in this component
		
		this.newPoint 	= new Sound(SoundType.POP);
		this.killPoint	= new Sound(SoundType.KILL);
		this.pageturn	= new Sound(SoundType.PAGETURN);
		this.error		= new Sound(SoundType.ERROR);
		
				
	}
	
	// -------------------------------------------------------------------------
	
	public void setInformer (String text) {
		Font font = new Font("Helvetica",Font.PLAIN,30);
		AttributedString information = new AttributedString(text);
		information.addAttribute(TextAttribute.FONT, font);
		information.addAttribute(TextAttribute.FOREGROUND, Color.YELLOW);

		this.informer = new DrawableText(new StarPoint(10,20), new LocalOrientation(), information);
		this.informer.textOrientation=TextOrientation.LEFT;
	}
	
	// ---------------------painting of the view -------------------------------	
	
	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent( g );
		Graphics2D g2D = (Graphics2D) g;
		this.initial = g2D.getTransform();
		this.initial.setToIdentity();
		
		this.viewPortTransform = AbstractDrawable.transformViewPort 
				(initial, this.temViewState);
		
		Drawable temPicture = this.temAllied.getDrawableTEMPicture();
		
		if (temPicture==null) {
			//TODO: Fire information to user, that he needs to load a tem
			// picture first, before he can manipulate)
		} else {
			temPicture.paint(g2D, viewPortTransform);
		}
		
		ArrayList<Drawable> drawableList = this.temAllied.getDrawables();
		ArrayList<DrawablePoint> pointList = this.temAllied.getPointsList();
		
		for (Drawable drawable : drawableList) {
			this.viewPortTransform = AbstractDrawable.transformViewPort 
						(initial, this.temViewState);
			drawable.paint(g2D, this.viewPortTransform);
		    
		}
		
		for (DrawablePoint dPoint  : pointList) {
			this.viewPortTransform = AbstractDrawable.transformViewPort 
						(initial, this.temViewState);
			dPoint.paint(g2D, this.viewPortTransform);
		    
		}
		
		// TODO: Take out the following testcode block 
		
		DrawableScaleReference drawableScaleReference = 
				new DrawableScaleReference(new StarPoint(-194,-36), new StarPoint(-25,96));
		this.viewPortTransform = AbstractDrawable.transformViewPort 
				(initial, this.temViewState);
		drawableScaleReference.paint(g2D,  this.viewPortTransform);
		
		// Paints the actual temAllied information (top right)
		
		this.informer.paint(g2D, this.initial);
		
		// Paints the windrose (bottom left)
		
		LocalOrientation localOrienation = new LocalOrientation();
		localOrienation.setScaling(0.35);
		this.rose = new DrawablePicture(
					new StarPoint(100,this.getHeight()-100), 
					localOrienation, this.roseImage);
	
		// TODO Maybe it is more save to make a copy of initial
		// not to have a clash with the other Drawables
		
		initial.setToRotation(	temViewState.rotation/360*2*3.1415, 
								this.rose.getStarPoint().getX(),
								this.rose.getStarPoint().getY());
		
		this.rose.paint(g2D, initial);
	}


	// ---------------------Adding and removing points -------------------------	
	
	public void addPoint ( Point2D point) {
			
			
			Point2D.Double pt = new Point2D.Double();
			 try {
				this.viewPortTransform.inverseTransform(point, pt);
			} catch (NoninvertibleTransformException e) {
				e.printStackTrace();
			}
			
			StarPoint initialStarpoint = new StarPoint(pt.getX(),pt.getY());
			DrawableDiamondStar diamondStar = new DrawableDiamondStar(initialStarpoint);
			DrawablePoint pPoint = new DrawablePoint(diamondStar,pt);
			
			this.temAllied.addPoint(pPoint);
			this.firePropertyChange("addPoint", null, pt);
			
			new Thread(this.newPoint).start();
			System.out.println(ManagementFactory.getThreadMXBean().getThreadCount());

			repaint();

		}
	
	public void removePoint (int x, int y) {
		
		if (this.temAllied.removePoint(x, y)==true) {
			new Thread(this.killPoint).start();
			System.out.println(ManagementFactory.getThreadMXBean().getThreadCount());
			repaint();
		}
	}

	// ------------------------Helper functions --------------------------------	


	//TODO: Implement Shift-Home to center to fit picture into window
	

	public void centerAll() {		
		temViewState.cWidth  =  this.getWidth();
		temViewState.cHeight =  this.getHeight();
		temViewState.x = temViewState.cWidth/2;
		temViewState.y = temViewState.cHeight/2;
		temViewState.scaling = 1;
		temViewState.rotation = 0;
		this.repaint();
	}

	private void configureEnvironment() {
		
		setOpaque(true);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		
		setBorder(BorderFactory.createLineBorder(Color.white));		
		setBackground( new Color (
				Integer.parseInt(
						Application.getResource("TEMView_Color").substring(2),16)
				)
		);

		try {
			URL url =   Application.getUrl("pics/Cross.gif");
			BufferedImage curBufferedImage = ImageIO.read(url);
			Cursor bufferedCursor = Toolkit.getDefaultToolkit().createCustomCursor(curBufferedImage, new Point(32,32), "Cross");
			setCursor(bufferedCursor);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	// --------------------information interchange -----------------------------	
	
	public void registerStatusBar (TEMStatusBar statusBar) {
		this.addPropertyChangeListener("coordinateChange", (PropertyChangeListener) statusBar);
		this.addPropertyChangeListener("addPoint", (PropertyChangeListener) statusBar);
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
			this.temTableModel = (TEMTableModel)(evt.getNewValue());
			this.temAllied=this.temTableModel.getLastItem();
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
	
	// ------------------------ Controls ---------------------------------------
	
	public void switchToNextTemAllied() {
		
		try {
			TEMAllied helper = temTableModel.getForwardItem();
			if (helper != null) {
				setInformer(helper.getInformation());
				this.temAllied = helper;
				new Thread(this.pageturn).start();
				System.out.println(ManagementFactory.getThreadMXBean().getThreadCount());
				
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
				System.out.println(ManagementFactory.getThreadMXBean().getThreadCount());
		
				repaint();
			}
		} catch (Exception e) {
			System.out.println("No TEMAllied loaded");
			new Thread(this.error).start();
		}
		
	}
	
	
}
