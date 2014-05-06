/**
 * 
 */
package ch.phonon.temview;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

import ch.phonon.Application;
import ch.phonon.LocalOrientation;
import ch.phonon.StarPoint;
import ch.phonon.TEMAllied;
import ch.phonon.drawables.AbstractDrawable;
import ch.phonon.drawables.Drawable;
import ch.phonon.drawables.DrawableCircle;
import ch.phonon.drawables.DrawableDiamondStar;
import ch.phonon.drawables.DrawablePoint;


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
	private TEMViewState temViewState;
	
	// ------------------------ Constructor ------------------------------------
	
	TEMView  () { 
	
		// Initialize state of the TEMView
		
		configureEnvironment();	// configure properties of TEMView (Color...)
		
		
		
		this.temViewState = new TEMViewState();
		this.adapter = new TEMAdapter(this);
			
		//temAllied = new TEMAllied("./pics/CoordinateChecker.png");
		temAllied = new TEMAllied();
		
		LocalOrientation orientation = new LocalOrientation(new Point2D.Double(0,0),0,1.0);
		StarPoint str = new StarPoint(0,0);
		DrawableCircle circle = new DrawableCircle(str, orientation, 100, 100);
		circle.setColor(new Color(0,255,0));
		temAllied.addDrawable(circle);
			
		setVisible(true);	
		
		// Add Listeners to View
		
		this.addMouseListener(this.adapter);
		this.addMouseMotionListener(this.adapter);		
		this.addKeyListener(this.adapter);
				
	}
	
	// ---------------------painting of the view -------------------------------	
	
	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent( g );
		Graphics2D g2D = (Graphics2D) g;
		this.initial = g2D.getTransform();
		
		this.viewPortTransform = AbstractDrawable.transformViewPort 
				(initial, this.temViewState);
		
		Drawable temPicture = this.temAllied.getTemPicture();
		
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
			
			repaint();

		}
	
	public void removePoint (int x, int y) {
		this.temAllied.removePoint(x, y);
		repaint();
	}

	// ------------------------Helper functions --------------------------------	
	
	

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
		setBorder(BorderFactory.createLineBorder(Color.white));		
		setBackground( new Color (
				Integer.parseInt(
						Application.getResource("TEMView_Color").substring(2),16)
				)
		);

		try {
			BufferedImage curBufferedImage = ImageIO.read(new File("pics/Cross.gif"));
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
		
		System.out.println("Reached");
		
		if (evt.getPropertyName().equals("temAlliedChange")) {
			System.out.println("Property change");
			this.temAllied = (TEMAllied)(evt.getNewValue());
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

}
