/**
 * 
 */
package ch.phonon;

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
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.text.AttributedString;
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
	
	private 	ArrayList<Drawable> 	 drawableList; // contains all Drawables 
	private		ArrayList<DrawablePoint> pointsList; 
	
	private TEMViewState temViewState;
	
	TEMView  () { 
	
		// Initialize state of the TEMView
		
		configureEnvironment();	// configure properties of TEMView (Color...)
		
		this.temViewState = new TEMViewState();
		this.adapter = new TEMAdapter(this);
				
		StarPoint initialStarpoint = null;
		LocalOrientation initialLocalOrientation = null;
				
		initialLocalOrientation = new LocalOrientation(new Point2D.Double(0,0), 0,1.0);
		
		// Populate Drawables in ArrayList 
		
		this.drawableList = new ArrayList<Drawable>();
		this.pointsList = new ArrayList<DrawablePoint>();
		
		initialStarpoint = new StarPoint(0,0);
		DrawablePicture temPicture = new DrawablePicture(initialStarpoint, 
						initialLocalOrientation, "./pics/CoordinateChecker.png");
		drawableList.add(temPicture);
		
		
		initialStarpoint = new StarPoint(0,0);
		DrawableBox aBox = new DrawableBox(initialStarpoint, 
											  initialLocalOrientation, 800, 600);
		drawableList.add(aBox);
		
		
		initialStarpoint = new StarPoint(400,300);
		DrawableBox bBox = new DrawableBox(initialStarpoint, 
											  initialLocalOrientation, 400, 300);
		drawableList.add(bBox);

		
		initialLocalOrientation = new LocalOrientation(new Point2D.Double(200,150), 90,1.0);
		initialStarpoint = new StarPoint(-400,-300);
		DrawableBox cBox = new DrawableBox(initialStarpoint, 
											  initialLocalOrientation, 400, 300);
		drawableList.add(cBox);
		
		initialLocalOrientation = new LocalOrientation(new Point2D.Double(200,150), 45,0.5);
		initialStarpoint = new StarPoint(-400,-300);
		DrawableBox dBox = new DrawableBox(initialStarpoint, 
											  initialLocalOrientation, 100, 100);
		dBox.setColor(new Color(0,255,0));
		drawableList.add(dBox);
		
		initialLocalOrientation = new LocalOrientation(new Point2D.Double(200,150), 45,1.0);
		initialStarpoint = new StarPoint(-400,-300);
		DrawableBox eBox = new DrawableBox(initialStarpoint, 
											  initialLocalOrientation, 100, 100);
		eBox.setColor(new Color(0,255,0));
		drawableList.add(eBox);
		
		initialLocalOrientation = new LocalOrientation(new Point2D.Double(200,150), 45,2.0);
		initialStarpoint = new StarPoint(-400,-300);
		DrawableBox fBox = new DrawableBox(initialStarpoint, 
											  initialLocalOrientation, 100, 100);
		fBox.setColor(new Color(50,0,120));
		drawableList.add(fBox);
		
		initialLocalOrientation = new LocalOrientation(new Point2D.Double(0,0), 90,2.0);
		initialStarpoint = new StarPoint(0, 0);
		Font font = new Font("Helvetica",Font.PLAIN,30);
		AttributedString aString = new AttributedString("TEMEVIEW");
		aString.addAttribute(TextAttribute.FONT, font);
		aString.addAttribute(TextAttribute.FOREGROUND, Color.YELLOW);

		DrawableText text = new DrawableText(initialStarpoint, 
											  initialLocalOrientation, aString);
		drawableList.add(text);
		
		
		Point2D.Double p1 = new Point2D.Double(0, 0);
		Point2D.Double p2 = new Point2D.Double(0, 800);
		initialLocalOrientation = new LocalOrientation(new Point2D.Double(0,0), 45,1.0);
		initialStarpoint = new StarPoint(0,0);
		DrawableLine line= new DrawableLine(initialStarpoint, 
											  initialLocalOrientation, p1, p2);
		line.setColor(new Color(0,0,255));
		drawableList.add(line);
		
		
		setVisible(true);	
		repaint();
		
		// Add Listeners to View
		
		this.addMouseListener(this.adapter);
		this.addMouseMotionListener(this.adapter);		
		this.addKeyListener(this.adapter);
		
		
		

	}
	
	public void addPoint ( Point2D point) {
			
			
			Point2D.Double pt = new Point2D.Double();
			 try {
				this.viewPortTransform.inverseTransform(point, pt);
			} catch (NoninvertibleTransformException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			LocalOrientation initialLocalOrientation = new LocalOrientation(new Point2D.Double(0,0), 45,1.0);
			StarPoint initialStarpoint = new StarPoint(pt.getX(),pt.getY());	
			
			DrawableBox pBox = new DrawableBox(initialStarpoint, initialLocalOrientation, 10, 10);
			pBox.setColor(new Color(0,255,255));
			pBox.setInvariantRotation(true);
			pBox.setInvariantScaling(true);
			
			DrawableLine pLine1 = new DrawableLine(initialStarpoint, 
													new LocalOrientation(), 
													new Point2D.Double(0,0), 
													new Point2D.Double(20,0));
			pLine1.setColor(Color.WHITE);
			pLine1.setInvariantRotation(true);
			pLine1.setInvariantScaling(true);
			
			
			DrawableLine pLine2 = new DrawableLine(initialStarpoint, 
					new LocalOrientation(), 
					new Point2D.Double(0,0), 
					new Point2D.Double(0,20));
			pLine2.setColor(Color.WHITE);
			pLine2.setInvariantRotation(true);
			pLine2.setInvariantScaling(true);
			
			
			DrawableComposite composite = new DrawableComposite();
			composite.add(pBox);
			composite.add(pLine1);
			composite.add(pLine2);
			
			
			DrawablePoint pPoint = new DrawablePoint(composite,pt);
			
			//DrawablePoint pPoint = new DrawablePoint(pBox,pt);
			
			this.pointsList.add(pPoint);
			
			this.firePropertyChange("addPoint", null, pt);
			
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
			
	
	/**
	 * @return the drawableList
	 */
	public ArrayList<Drawable> getDrawableList() {
		return drawableList;
	}
	
	/**
	 * @return the pointsList
	 */
	public ArrayList<DrawablePoint> getPointsList() {
		return pointsList;
	}

	public TEMViewState getTEMViewState() {
		return temViewState;
	}
	
@Override
public void paintComponent(Graphics g) {
	
	super.paintComponent( g );
	Graphics2D g2D = (Graphics2D) g;
	this.initial = g2D.getTransform();
	
	
	for (Drawable drawable : drawableList) {
		this.viewPortTransform = AbstractDrawable.transformViewPort 
					(initial, this.temViewState);
		drawable.paint(g2D, this.viewPortTransform);
	    
	}
	
	for (Drawable drawable : pointsList) {
		this.viewPortTransform = AbstractDrawable.transformViewPort 
					(initial, this.temViewState);
		drawable.paint(g2D, this.viewPortTransform);
	    
	}

}

/**
 * @param pointsList the pointsList to set
 */
public void setPointsList(ArrayList<DrawablePoint> pointsList) {
	this.pointsList = pointsList;
	repaint();
}

public void setTEMViewState(TEMViewState temViewState) {
	this.temViewState = temViewState;
}

}
