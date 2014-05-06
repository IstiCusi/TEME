/**
 * 
 */
package ch.phonon;

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
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;


/**
 * The {@link TEMView} is a {@link JPanel}, that is used to show various
 * {@link Drawable} objects. 
 * @author phonon
 *
 */
public class TEMView extends JPanel {

	private static final long serialVersionUID = 1L;
	
	TEMAdapter adapter;
	private AffineTransform initial;
	private AffineTransform viewPortTransform;
	
	private TEMAllied temAllied;
	private TEMViewState temViewState;
	
	TEMView  () { 
	
		// Initialize state of the TEMView
		
		configureEnvironment();	// configure properties of TEMView (Color...)
		
		this.temViewState = new TEMViewState();
		this.adapter = new TEMAdapter(this);
			
		temAllied = new TEMAllied("./pics/CoordinateChecker.png");
		
		LocalOrientation orientation = new LocalOrientation(new Point2D.Double(0,0),0,1.0);
		StarPoint str = new StarPoint(0,0);
		DrawableCircle circle = new DrawableCircle(str, orientation, 100, 100);
		circle.setColor(new Color(0,255,0));
		
		temAllied.addDrawable(circle);
			
		setVisible(true);	
//		repaint();
		
		// Add Listeners to View
		
		this.addMouseListener(this.adapter);
		this.addMouseMotionListener(this.adapter);		
		this.addKeyListener(this.adapter);
		
		//this.updateUI();
		

	}
	
	public void addPoint ( Point2D point) {
			
			
			Point2D.Double pt = new Point2D.Double();
			 try {
				this.viewPortTransform.inverseTransform(point, pt);
			} catch (NoninvertibleTransformException e) {
				// TODO Auto-generated catch block
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

	public void addStatusBar (TEMStatusBar statusBar) {
		this.addPropertyChangeListener("coordinateChange", (PropertyChangeListener) statusBar);
		this.addPropertyChangeListener("addPoint", (PropertyChangeListener) statusBar);
	}

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
			
	
	
	public TEMViewState getTEMViewState() {
		return temViewState;
	}
	
@Override
public void paintComponent(Graphics g) {
	
	super.paintComponent( g );
	Graphics2D g2D = (Graphics2D) g;
	this.initial = g2D.getTransform();
	
	this.viewPortTransform = AbstractDrawable.transformViewPort 
			(initial, this.temViewState);
	this.temAllied.getTemPicture().paint(g2D, viewPortTransform);
	
	
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

public void setTEMViewState(TEMViewState temViewState) {
	this.temViewState = temViewState;
}

}
